package com.umessage.letsgo.service.impl.team;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qq.tim.ITimRestAPI;
import com.qq.tim.vo.request.DeleteGroupMemberRequest;
import com.umessage.letsgo.core.config.constant.ConfigConstant;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.utils.CountryCode;
import com.umessage.letsgo.core.utils.DateUtils;
import com.umessage.letsgo.core.utils.ExcelOperateUtil;
import com.umessage.letsgo.core.utils.QueryUtils;
import com.umessage.letsgo.dao.team.MemberDao;
import com.umessage.letsgo.domain.po.journey.ScheduleEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.system.LogManage;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.domain.vo.team.requset.WebMemberRequest;
import com.umessage.letsgo.domain.vo.team.respone.WebMemberResponse;
import com.umessage.letsgo.service.api.journey.IScheduleService;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.system.ICloudFileOperateService;
import com.umessage.letsgo.service.api.system.ILogManageService;
import com.umessage.letsgo.service.api.system.IMessageService;
import com.umessage.letsgo.service.api.team.ILeaderService;
import com.umessage.letsgo.service.api.team.IMemberService;
import com.umessage.letsgo.service.api.team.ITeamService;
import com.umessage.letsgo.service.api.team.IWebMemberService;
import com.umessage.letsgo.service.common.constant.Constant;
import com.umessage.letsgo.service.common.constant.HelpConstant;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ZhaoYidong on 2016/6/12.
 */
@Service
public class WebMemberServiceImpl implements IWebMemberService{

    private static final Logger logger = Logger.getLogger(WebMemberServiceImpl.class);
    @Resource
    private MemberDao memberDao;
    @Resource
    private IMemberService memberService;
    @Resource
    private ICloudFileOperateService cloudFileOperateService;
    @Resource
    private ITeamService teamService;
    @Resource
    private IUserService userService;
    @Resource
    private ILeaderService leaderService;
    @Resource
    private IScheduleService scheduleService;
    @Resource
    private IMessageService messageService;
    @Resource
    private ITimRestAPI timRestAPI;
    @Resource
    private ILogManageService logManageService;
    @Resource
    private UserLoginHelper userLoginHelper;

    @Override
    public WebMemberResponse getMemberList(WebMemberRequest request) {
        if (request.getPageSizes() != 0) {
            PageHelper.startPage(request.getPageNums(),request.getPageSizes());
        }
        Page<MemberEntity> memberList =memberDao.selectAll(request);
        //设置多个团队头像地址为用户头像地址
        memberList = this.changePhotoUrlPages(memberList);
        memberList =calculateAge(memberList);
        return getSuccessResponse(memberList);
    }
    //计算年龄
    private Page<MemberEntity> calculateAge(Page<MemberEntity> list){
        for (MemberEntity member:list) {
            String birthday = member.getBirthday();
            if(birthday != null){
                try {
                    Date date =DateUtils.sdfDateOnly.parse(birthday);
                    int age = DateUtils.getAge(date);
                    member.setAge(age);
                } catch (ParseException e) {
                    logger.warn("不能解析日期："+birthday,e);
                }
            }
        }
        return list;
    }
    /**
     *查询成功后获取WebMemberResponse响应
     */
    private WebMemberResponse getSuccessResponse(Page<MemberEntity> memberList){
        WebMemberResponse response = new WebMemberResponse();
        response.setMemberList(memberList);
        response.setTotals(memberList.getTotal());
        response.setPages(memberList.getPages());
        return response;
    }
    @Override
    public int memberAdd(MemberEntity member) {
        member.setPhotoUrl(getPhotoUrl(member));
        member.setRole(3);
        member.setIsAdmin(0);
        member.setPhone(member.getPhone());
        member.setIsNewJoin(0);//是否为新加入（即为自己通过扫描填写资料加入）【0：否；1：是】
//      return memberService.addMember(member);
        return memberService.insertMember(member);
    }

    @Override
    public MemberEntity memberPreview(Long id) {
        MemberEntity memberEntity = memberDao.select(id);
        String countryCode = memberEntity.getCountryCode();
        int length = countryCode.length();
        String phone = memberEntity.getPhone();
        String finalPhone = phone.substring(length);
        memberEntity.setPhone(finalPhone);
        //设置团队头像地址为用户头像地址
        memberEntity = this.changePhotoUrl(memberEntity);
        return memberEntity;
    }

    @Override
    @Transactional
    public int memberUpdate(MemberEntity member) {
        if(member == null || member.getId() == null) return 0;
        MemberEntity memberEntity = memberDao.select(member.getId());
        //设置团队头像地址为用户头像地址
        memberEntity = this.changePhotoUrl(memberEntity);
        if(memberEntity == null) return 0;

        if(isNeedRebuild(memberEntity,member)){//需要重新生成头像
            cloudFileOperateService.deleteFile(memberEntity.getPhotoUrl(), Constant.FACE_URL);
            memberEntity.setPhotoUrl(getPhotoUrl(member));
        }
        memberEntity.setRealName(member.getRealName());
        memberEntity.setSex(member.getSex());
        memberEntity.setBirthday(member.getBirthday());
        memberEntity.setCountryCode(member.getCountryCode());
//        memberEntity.setPhone(QueryUtils.getPhone(member.getPhone()));
        memberEntity.setPhone(member.getCountryCode()+member.getPhone());
        memberEntity.setCardType(member.getCardType());
        memberEntity.setCardNum(member.getCardNum());
        memberEntity.setCardTime(member.getCardTime());
        memberEntity.setQq(member.getQq());
        memberEntity.setWeixin(member.getWeixin());
        memberEntity.setPhotoLibrary1Url(member.getPhotoLibrary1Url());
        memberEntity.setPhotoLibrary2Url(member.getPhotoLibrary2Url());
        memberEntity.setPhotoLibrary3Url(member.getPhotoLibrary3Url());
        memberEntity.setPhotoLibrary4Url(member.getPhotoLibrary4Url());
        memberEntity.setPhotoLibrary5Url(member.getPhotoLibrary5Url());
        memberEntity.setPhotoLibrary6Url(member.getPhotoLibrary6Url());
        memberEntity.setPhotoLibrary7Url(member.getPhotoLibrary7Url());
        memberEntity.setPhotoLibrary8Url(member.getPhotoLibrary8Url());
        memberEntity.setPhotoLibrary9Url(member.getPhotoLibrary9Url());
        memberEntity.setPhotoLibrary10Url(member.getPhotoLibrary10Url());

        memberEntity.setUpdateTime(new Date());
//      int count =memberDao.update(memberEntity);
        int count =memberDao.updateMember(memberEntity);
        //添加日志
        memberEntity.setVersion(memberEntity.getVersion()+1);
        updateMemberLog(memberEntity);
        userService.memberUpdateForUser(memberEntity);
        return count;
    }
    private boolean isNeedRebuild(MemberEntity memberEntity, MemberEntity originalEntity){
        if(originalEntity.getRealName().equals(memberEntity.getRealName())
                && originalEntity.getSex().equals(memberEntity.getSex())){
            return false;
        }
        return true;
    }
    public String getPhotoUrl(MemberEntity memberEntity){
        try {
            if(memberEntity.getSex()!=null&&memberEntity.getSex()==1){
                return ConfigConstant.MALE_HEAD_URL;
            }else{
                return ConfigConstant.FEMALE_HEAD_URL;
            }
            //return PhotoHelper.createMemberImage(memberEntity.getRealName(),memberEntity.getSex());
        } catch (Exception e) {
            logger.warn(memberEntity.getRealName()+"游客生成头像失败\n"+e.getMessage(),e);
        }
        return null;
    }

    @Override
    public CommonResponse memberDelete(Long id) {
        MemberEntity member = memberService.getMember(id);
        if (member.getId() == null ) return CommonResponse.createNotFoundResponse("没有发现该成员");

        memberDao.delete(id);
        TeamEntity teamEntity = teamService.getTeamById(member.gettId());
        teamEntity.setTotalCount(teamEntity.getTotalCount()-1);
        teamService.updateTeam(teamEntity);

        String txGroupId = member.getTeamId();
        if(!StringUtils.isEmpty(txGroupId)){
            deleteGroupMember(txGroupId,member.getUserEntity().getUserName());
        }
        return new CommonResponse();
    }

    private void deleteGroupMember(String txGroupId,String username){
        DeleteGroupMemberRequest delMemberRequest = new DeleteGroupMemberRequest();
        delMemberRequest.setGroupId(txGroupId);
        delMemberRequest.setSilence(1);

        List<String> accountList = new ArrayList<String>();
        accountList.add(username);
        delMemberRequest.setMemberToDelAccount(accountList);

        timRestAPI.deleteGroupMember(delMemberRequest);
    }

    @Override
    public byte[] getMemberExcel(List<MemberEntity> memberList) {
        String[] columnName = getMemberExcelSubject();
        List<Object[]> objList = getMemberData(memberList);

        ExcelOperateUtil excelOperateUtil =ExcelOperateUtil.instance();
        Workbook workbook= excelOperateUtil.generateWordBook();
        Sheet sheet =fetchSingleSheet(excelOperateUtil,workbook,columnName,"游客数据");

        CellStyle dataCellStyle = workbook.createCellStyle();
        dataCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
        dataCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        for (int i=0;i<objList.size();i++){
            Row row = excelOperateUtil.generateRow(sheet,i+2);
            Object[] obj = objList.get(i);
            for (int j=0;j<obj.length;j++) {
                Cell cell = excelOperateUtil.generateCell(row,j);
                setCellValue(cell,obj[j]);
                cell.setCellStyle(dataCellStyle);
            }
        }
        ByteArrayOutputStream outputStream =new ByteArrayOutputStream();
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
        return outputStream.toByteArray();
    }

    private String[] getMemberExcelSubject(){
        return new String[]{"姓名","性别","出生日期","手机","证件类型","证件号码","有效期"};
    }

    private List<Object[]> getMemberData(List<MemberEntity> memberList){
        List<Object[]> objList = new ArrayList<Object[]>();
        for (MemberEntity member: memberList){
            Object[] obj = new Object[7];
            obj[0] = member.getRealName();
            obj[1] = getSex(member.getSex());
            obj[2] = member.getBirthday();
            obj[3] = member.getPhone();
            obj[4] = member.getCardType();
            obj[5] = member.getCardNum();
            obj[6] = member.getCardTime();
            objList.add(obj);
        }
        return objList;
    }

    private void setCellValue(Cell cell,Object columValue){
        if(columValue !=null){
            if(columValue instanceof Date){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cell.setCellValue(sdf.format(columValue));
            }else if(columValue instanceof Integer){
                cell.setCellValue((Integer)columValue);
            }else if(columValue instanceof Double){
                cell.setCellValue((Double) columValue);
            }else if(columValue instanceof BigDecimal){
                BigDecimal cellOriginal =(BigDecimal)columValue ;
                cell.setCellValue(cellOriginal.doubleValue());
            }else{
                cell.setCellValue((String)columValue);
            }
        }
    }

    @Override
    public String getUniqueName(){
        final String excelSuffix = ".xls";
        Random random = new Random();
        long currentTime = System.currentTimeMillis();
        StringBuffer stringBuffer = new StringBuffer(String.valueOf(currentTime));
        stringBuffer.append(random.nextInt(101)).append(excelSuffix);
        return stringBuffer.toString();
    }

    @Override
    public WebMemberResponse memberAddAjax(MemberEntity member) {
        WebMemberResponse verifyResponse = member.dataVerify();

        if(verifyResponse.getRetCode() != 0){
            return verifyResponse;
        }
        member.setPhone(member.getCountryCode()+member.getPhone());
        if(member.getCountryCode().equals(member.getPhone())){
            member.setPhone("");
        }
        //游客在团队中手机号是否重复
        if (member.getPhone() != null && member.getPhone()!="".toString() ){
            boolean   phoneIsRepeat = memberService.memberPhoneIsRepeat(QueryUtils.getPhone(member.getPhone()),member.gettId());
            if(phoneIsRepeat){
                verifyResponse.setRetCode(ErrorConstant.EXIST_PHONENUMBER);
                verifyResponse.setRetMsg("手机号已经存在");
                return verifyResponse;
            }
        }

        //导游和领队的手机号是否存在
      boolean isRepeat = memberService.leaderPhoneIsRepeat(QueryUtils.getPhone(member.getPhone()), member.gettId());
        if(isRepeat){
            verifyResponse.setRetCode(ErrorConstant.EXIST_PHONENUMBER);
            verifyResponse.setRetMsg("该手机号("+member.getPhone()+")已经为领队或导游，暂时不能添加游客！");
            return verifyResponse;
        }
        memberAdd(member);
        TeamEntity teamEntity = teamService.getTeamById(member.gettId());
        int count=0;
        if(teamEntity.getTotalCount()!=null){
            count=teamEntity.getTotalCount();
        }
        teamEntity.setTotalCount(count+1);
        teamService.updateTeam(teamEntity);
        //如果是更新，则加入腾迅云群组，并加好友
        List<MemberEntity> memberList = new ArrayList<MemberEntity>();
        memberList.add(member);
        try {
            addTxGroup(memberList,member.gettId());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        WebMemberResponse webMemberResponse = new WebMemberResponse();
        webMemberResponse.setMember(member);
        return webMemberResponse;
    }

    private String getSex(Integer sex){
        if(sex == 1) return "男";
        if(sex == 2) return "女";
        return "";
    }
    //获取已设置过标题的sheet表单。
    private Sheet fetchSingleSheet(ExcelOperateUtil excelOperateUtil, Workbook workbook,String[] titles,String name){
        Sheet dataSheet=null;
        if(workbook != null && titles != null){
            dataSheet = excelOperateUtil.generateSheet(workbook, name);//生成工作薄
            // sheet主题单元格样式
            CellStyle titleCellStyle = workbook.createCellStyle();
            titleCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
            titleCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            // 列主题单元格样式
            CellStyle culumnCellStyle = workbook.createCellStyle();
            culumnCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
            culumnCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            // 数据单元格样式
            CellStyle dataCellStyle = workbook.createCellStyle();
            dataCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
            dataCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

            // sheet主题字体样式设置
            Font sheetTitleFont = workbook.createFont();
            sheetTitleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
            sheetTitleFont.setFontHeight((short) 350);
            titleCellStyle.setFont(sheetTitleFont);
            // 列主题字体样式设置
            Font culumnTitleFont = workbook.createFont();
            culumnTitleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
            culumnTitleFont.setFontHeight((short) 210);
            culumnCellStyle.setFont(culumnTitleFont);

            // -----------[SHEET ONE]----------------------BEGIN----------------------//
            Row titleRow = excelOperateUtil.generateRow(dataSheet, 0);
            Cell titleCell = excelOperateUtil.generateCell(titleRow, 0,titleCellStyle);
            titleCell.setCellValue(name);//设置第一行标题
            dataSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, titles.length)); // 合并单元格,从0行0列开始,合并1行11列
            // 生成字段的标题行.
            Row columnTitleRow = excelOperateUtil.generateRow(dataSheet, 1);
            //设置字段标题值
            for(int i=0;i<titles.length;i++){
                Cell cell=excelOperateUtil.generateCell(columnTitleRow,i);		// 生成单元格
                cell.setCellValue(titles[i]);//给单元格赋值
                cell.setCellStyle(culumnCellStyle); //给单元格设置样式
                dataSheet.setColumnWidth(i,5000);  //调整列宽
//                dataSheet.autoSizeColumn(i);
            }
        }
        //设置字段值 ,在每个方法中单独写.
        return dataSheet;
    }
    @Override
    public void fileDownload() {

    }

    @Override
    @Transactional
    public WebMemberResponse importMemberExcel(String filePath,Long tId) {
        if(StringUtils.isEmpty(filePath) || tId == null) {
            return WebMemberResponse.createEmptyParameterResponse("文件不存在或团队id为空");
        }

        ExcelOperateUtil excelOperateUtil = ExcelOperateUtil.instance();
        List<Row> rowList = readRowFromExcel(filePath,excelOperateUtil);
        List<String[]> cellList = readCellFromRows(rowList,7,excelOperateUtil);
        List<MemberEntity> memberList = getMemberList(cellList,tId);
        //验证数据完整性
        for (MemberEntity entity:memberList){
//          WebMemberResponse verifyResponse = entity.dataVerify();
            WebMemberResponse verifyResponse = entity.dataVerifyImportExcel();
            if (verifyResponse.getRetCode() != 0){
                return verifyResponse;
            }
        }
        //验证手机号是否重复或已存在
        WebMemberResponse phoneVerifyResponse = phoneRepeatCheck(memberList,tId);
        if(phoneVerifyResponse.getRetCode() != 0){
            return phoneVerifyResponse;
        }
        //注：保存游客信息。
        int count = 0;//记录增加游客数
        for (MemberEntity entity:memberList){
            String birthday = entity.getBirthday();
            String sreplace = birthday;
            if(!StringUtils.isEmpty(birthday)){
                if (birthday.indexOf(".") != -1){
                    sreplace = birthday.replace("-", ".");
                }else if (birthday.indexOf("/") != -1){
                    sreplace = birthday.replace("-", "/");
                }
            }
            entity.setBirthday(sreplace);
            entity.setCountryCode(entity.getCountryCode());
            entity.setIsNewJoin(0);//是否为新加入（即为自己通过扫描填写资料加入）【0：否；1：是】
//          memberService.addMember(entity);
            memberService.insertMember(entity);
            count++;
        }
        TeamEntity teamEntity = teamService.getTeamById(tId);
        int count1=0;
        if(teamEntity.getTotalCount()!=null){
            count1=teamEntity.getTotalCount();
        }
        teamEntity.setTotalCount(count1+count);
        teamService.updateTeam(teamEntity);
        //如果是更新，则加入腾迅云群组，并加好友
        try {
            addTxGroup(memberList,tId);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        WebMemberResponse response = new WebMemberResponse();
        Page<MemberEntity> pageList = new Page<MemberEntity>();
        pageList.addAll(memberList);
        response.setMemberList(pageList);

        //修改行程状态为已发布待确认
        ScheduleEntity scheduleEntity= scheduleService.getScheduleByTeamId(tId);
        if( scheduleEntity == null|| scheduleEntity.getId()==null) {
            return WebMemberResponse.createEmptyParameterResponse("行程不存在或行程id为空");
        }
        ScheduleEntity schedule=new ScheduleEntity();
        schedule.setId(scheduleEntity.getId());
        //发布状态：1全部-2已发布-3未发布-4待确认
        schedule.setProcessStatus(4);
        scheduleService.updateScheduleProcessStatus(schedule);
        return response;
    }


    private  List<MemberEntity> getMemberList(List<String[]> cellList,Long tId){
        List<MemberEntity> memberList = new ArrayList<MemberEntity>();
        for(int i=0;i<cellList.size();i++){
            String[] cells = cellList.get(i);
            MemberEntity member = new MemberEntity();

            String name = cells[0];//名称
            if(!StringUtils.isEmpty(name)){
                member.setRealName(name);
            }

            String sex = cells[1];//性别
            if(!StringUtils.isEmpty(sex)){
                if(sex.contains("男")){
                    member.setSex(1);
                }else if(sex.contains("女")){
                    member.setSex(2);
                }else{
                    member.setSex(0);//性别数据格式错误
                }
            }

            String birthday = cells[2];//出生日期
            if(!StringUtils.isEmpty(birthday)){
                member.setBirthday(birthday);
            }

            String phone = cells[3];//手机
            if(!StringUtils.isEmpty(phone)){
//              phone = formatScientificNotation(phone);
//              phone = QueryUtils.getPhone(phone);
//              member.setPhone(phone);
                if(CountryCode.HandleCountryCodeOne(phone) != null ){
                    String countryCode = CountryCode.HandleCountryCodeOne(phone);
                    member.setCountryCode(countryCode);
                    member.setPhone(phone);
                }else{
                    phone = "+86"+phone;
                    member.setCountryCode("+86");
                    member.setPhone(phone);
                }


            }else {
                member.setCountryCode("+86");
                member.setPhone("");
            }

            String cardType = cells[4];//证件类型
            if(!StringUtils.isEmpty(cardType)){
                member.setCardType(cardType);
            }

            String cardCode = cells[5];//证件号码
            if(!StringUtils.isEmpty(cardCode)){
                member.setCardNum(cardCode);
            }

            String cardTime = cells[6];//证件有效期
            if(!StringUtils.isEmpty(cardTime)){
                member.setCardTime(cardTime);
            }

            member.setRole(3);
            member.setIsAdmin(0);
            member.settId(tId);
            try {
                if(member.getSex()!=null&&member.getSex()==1){
                    member.setPhotoUrl(ConfigConstant.MALE_HEAD_URL);
                }else{
                    member.setPhotoUrl(ConfigConstant.FEMALE_HEAD_URL);
                }
//                String photoUrl = PhotoHelper.createMemberImage(member.getRealName(),member.getSex());
//                member.setPhotoUrl(photoUrl);
            } catch (Exception e) {
                logger.warn(member.getRealName()+"游客生成头像失败\n"+e.getMessage(),e);
            }

            memberList.add(member);
        }
        return memberList;
    }

    public void addTxGroup(List<MemberEntity> memberList, Long tId) throws UnsupportedEncodingException {
        TeamEntity teamEntity = teamService.getTeamById(tId);
        if(StringUtils.isEmpty(teamEntity.getTxGroupid())) return;

        for (MemberEntity member: memberList){
            UserEntity user = userService.getUserByPhone(member.getPhone());
            if(user != null) {//更新已有用户的新成员
                member.setUserId(user.getId());
                member.setJoinStatus(1);
                String txGroupId = teamEntity.getTxGroupid();
                member.setTeamId(txGroupId);
                memberService.updateMember(member);
                MemberEntity memberEntity=memberService.getMember(member.getId());
                //将已有用户加入腾迅云群组中
                if(!StringUtils.isEmpty(txGroupId)){
                    scheduleService.addTXGroup(memberEntity, user, txGroupId);
                    userService.sendInvitationMessage(memberEntity,teamEntity.getTxGroupid());
                    //List<MemberEntity> memberEntityList = memberService.getTourGuideList(txGroupId,null);
                    //scheduleService.addFriend(user.getUserName(), memberEntityList);
                }
            }
        }
        messageService.sendMessage(teamEntity,memberList);
    }

    @Override
    public List<MemberEntity> selectByIds(List<Long> ids) {
        List<MemberEntity> list = memberDao.selectByIds(ids);
        if(list == null){
            list = Collections.emptyList();
        }
        //设置多个团队头像地址为用户头像地址
        list = this.changePhotoUrlList(list);
        return list;
    }

    //读出游客excel模板中第一个表单中的每行。
    private List<Row> readRowFromExcel(String realPath, ExcelOperateUtil excelOperateUtil) {
        //根据路径加载excel文件。
        Workbook workbook = excelOperateUtil.readFromExcel(realPath);
        //读取Excel中的第一个sheet存放的数据
        Sheet dataSheet = (Sheet)excelOperateUtil.readFromWorkbook(workbook, 0);
        List<Row> rowList=new ArrayList<Row>();
        //循环获取所有行。第三行（下标为i=2）为用户手动录入的数据
        for(int i=2;true;i++){
            Row row = excelOperateUtil.readFromSheet(dataSheet,i);
            // 如果是空行则跳出循环
            if (row == null) {
                break;
            }
            rowList.add(row);
        }
        return rowList;
    }
    //读出每行中的每个格子。
    private List<String[]> readCellFromRows(List<Row> rowList,int columnNum, ExcelOperateUtil excelOperateUtil){
        List<String[]> cellList=new ArrayList<String[]>();
        for (Row row : rowList) {
            String[] cellArray=new String[columnNum];
            for (int j = 0; j < columnNum; j++) {
                cellArray[j]= getStringFromCell(excelOperateUtil.readFromRow(row, j));
            }
            cellList.add(cellArray);
        }
        return cellList;
    }

    //读出每个格子的值
    private String getStringFromCell(Cell cell) {
        if (cell == null) return null;
        if(HSSFCell.CELL_TYPE_STRING == cell.getCellType()){
            return cell.getStringCellValue().trim();
        }
        String cellValue = null;
        if (HSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()) {
            cellValue = String.valueOf(cell.getNumericCellValue());
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                Date d = cell.getDateCellValue();
                cellValue = new SimpleDateFormat("yyyy-MM-dd").format(d);
            }
        }
        return cellValue;
    }

    //格式化excel中科学计数方式的数字：手机号，身份证
    private String formatScientificNotation(String str){
        try {
            Double d = Double.parseDouble(str);
            DecimalFormat df = new DecimalFormat("0");
            return df.format(d);
        }catch (Exception e){
            logger.warn("转换excel中数字为整数失败\n"+e.getMessage(),e);
        }
        return "";
    }
    // excel导入游客时检查手机号是否有重复
    private WebMemberResponse phoneRepeatCheck(List<MemberEntity> memberList, Long tId){
        WebMemberResponse verifyResponse = new WebMemberResponse();
        //判断excel中游客是否和领队电话重复。
        for (MemberEntity member: memberList){
            if (!StringUtils.isEmpty(member.getPhone())) {   // 如果手机号不为空才判断
                //导游和领队的手机号是否存在
                boolean isRepeat = memberService.leaderPhoneIsRepeat(QueryUtils.getPhone(member.getPhone()), member.gettId());
                if(isRepeat){
                    verifyResponse.setRetCode(ErrorConstant.EXIST_PHONENUMBER);
                    verifyResponse.setRetMsg("该手机号("+member.getPhone()+")已经为领队或导游，暂时不能添加游客！");
                    return verifyResponse;
                }
            }
        }
        //判断excel中游客电话是否重复，excel中游客电话和该团队内游客是否重复。
        List<MemberEntity> entityList = memberService.getMemberListByTId(tId);
        List<MemberEntity> list = new ArrayList<MemberEntity>();
        list.addAll(memberList);
        list.addAll(entityList);

        Map<String,String> map = new HashMap<String,String>();
        for (MemberEntity member:list){
            String phone = member.getPhone();
            if (!StringUtils.isEmpty(phone) && map.get(phone) != null) {
                verifyResponse.setRetCode(ErrorConstant.EXIST_PHONENUMBER);
                verifyResponse.setRetMsg("手机号("+phone+")重复或已存在！");
                return verifyResponse;
            }
            map.put(phone,phone);
        }
        return verifyResponse;
    }


    /**
     * 设置团队头像地址为用户头像地址
     * @param memberEntity
     * @return
     */
    private MemberEntity changePhotoUrl(MemberEntity memberEntity){
        if(null == memberEntity){
            return memberEntity;
        }
        UserEntity userEntity = memberEntity.getUserEntity();
        if (userEntity != null){
            String photoUrl = userEntity.getPhotoUrl();
            //判断关联用户的头像地址是否为空，不为空设置团队头像地址为用户头像地址
            if (!org.springframework.util.StringUtils.isEmpty(photoUrl)){
                memberEntity.setPhotoUrl(photoUrl);
            }
        }
        return memberEntity;
    }


    /**
     * 设置多个团队头像地址为用户头像地址
     * @param memberList
     * @return
     */
    private Page<MemberEntity> changePhotoUrlPages(Page<MemberEntity> memberList){
        for (MemberEntity memberEntity:memberList) {
            //更改团队头像地址为用户头像地址
            memberEntity = this.changePhotoUrl(memberEntity);
        }
        return memberList;
    }


    /**
     * 设置多个团队头像地址为用户头像地址
     * @param memberList
     * @return
     */
    private List<MemberEntity> changePhotoUrlList(List<MemberEntity> memberList){
        for (MemberEntity memberEntity:memberList) {
            //更改团队头像地址为用户头像地址
            memberEntity = this.changePhotoUrl(memberEntity);
        }
        return memberList;
    }

    //批量导出游客 添加日志
    public int addMemberExportExceLog(TravelAgencyEntity travel){
        LogManage log=new LogManage();
        //1为旅行社端的行程，2为领队自己创建的行程（公司后台的）
        UserEntity userEntity =userService.getUserRole(travel.getUserId());
        if(userEntity!=null&&userEntity.getRole()!=null){
            //5旅行社；6计调；7销售（门店）role
            //日志-账号类型:0旅行社管理员，1门店销售（门店），2计调，3领队，
            if(userEntity.getRole()==5){
                log.setAccountType(HelpConstant.LOGZHLX_LXS);
            }
            if(userEntity.getRole()==6){
                log.setAccountType(HelpConstant.LOGZHLX_JD);
            }
            if(userEntity.getRole()==7){
                log.setAccountType(HelpConstant.LOGZHLX_XS);
            }
        }
        log.setOperateModel(HelpConstant.LOGCZMK_YKGL);
        log.setOperateTime(new Date());
        log.setOperateType("导出游客");
        log.setName(travel.getName());
        log.setUserId(travel.getUserId());
        StringBuffer str = new StringBuffer("");
        str.append(travel.getName()).append("进行了批量导出游客列表");
        log.setOperateContent(str.toString());
        logManageService.add(log);
        return 0;
    }

    //修改 游客 添加日志
    public int updateMemberLog(MemberEntity memberEntity){
        UserResponse userResponse = userLoginHelper.getLoginUser();
        UserEntity user = userResponse.getUserEntity();
        if(user == null || user.getId() == null){
            return 0;
        }
        LogManage log=new LogManage();
        //1为旅行社端的行程，2为领队自己创建的行程（公司后台的）
        UserEntity userEntity =userService.getUserRole(user.getId());
        if(userEntity!=null&&userEntity.getRole()!=null){
            //5旅行社；6计调；7销售（门店）role
            //日志-账号类型:0旅行社管理员，1门店销售（门店），2计调，3领队，
            if(userEntity.getRole()==5){
                log.setAccountType(HelpConstant.LOGZHLX_LXS);
            }
            if(userEntity.getRole()==6){
                log.setAccountType(HelpConstant.LOGZHLX_JD);
            }
            if(userEntity.getRole()==7){
                log.setAccountType(HelpConstant.LOGZHLX_XS);
            }
        }
        log.setOperateModel(HelpConstant.LOGCZMK_YKGL);
        log.setOperateTime(new Date());
        log.setOperateType("修改游客");
        log.setName(user.getRealName());
        log.setUserId(user.getId());
        StringBuffer str = new StringBuffer("");
        str.append(user.getRealName()).append("修改了一个游客");
        str.append("姓名:").append(memberEntity.getRealName()).append(",");
        if(memberEntity.getSex()==1){
            str.append("性别:").append("男性").append(",");
        }else{
            str.append("性别:").append("女性").append(",");
        }
        str.append("出生日期:").append(memberEntity.getBirthday()).append(",");
        str.append("手机号码:").append(memberEntity.getPhone()).append(",");
        str.append("修改时间:").append(DateUtils.toString(memberEntity.getUpdateTime(),DateUtils.DATE_FORMAT_DATETIME)).append(",");
        str.append("版本号:").append(memberEntity.getVersion()).append("}");
        log.setOperateContent(str.toString());
        logManageService.add(log);
        return 0;
    }

}
