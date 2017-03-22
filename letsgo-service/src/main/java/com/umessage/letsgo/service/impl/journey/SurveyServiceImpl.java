package com.umessage.letsgo.service.impl.journey;


import com.github.pagehelper.Page;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.core.utils.CSVUtils;
import com.umessage.letsgo.core.utils.DateUtils;
import com.umessage.letsgo.dao.journey.QuestionDao;
import com.umessage.letsgo.dao.journey.ScheduleDao;
import com.umessage.letsgo.dao.journey.SurveyDao;
import com.umessage.letsgo.domain.po.journey.*;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.domain.vo.common.request.ParameMsg;
import com.umessage.letsgo.domain.vo.common.respone.AppMessageResponse;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.common.respone.CustomMsg;
import com.umessage.letsgo.domain.vo.journey.request.*;
import com.umessage.letsgo.domain.vo.journey.response.SurveyDownloadResponse;
import com.umessage.letsgo.domain.vo.journey.response.SurveyQuestionResponse;
import com.umessage.letsgo.domain.vo.journey.response.SurveyResponse;
import com.umessage.letsgo.domain.vo.journey.response.vo.SurveyVo;
import com.umessage.letsgo.domain.vo.security.respone.UserResponse;
import com.umessage.letsgo.service.api.journey.*;
import com.umessage.letsgo.service.api.security.IAppSendMessageService;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.security.IWxInfoService;
import com.umessage.letsgo.service.api.security.IWxSendMessageService;
import com.umessage.letsgo.service.api.team.IMemberService;
import com.umessage.letsgo.service.api.team.ITeamService;
import com.umessage.letsgo.service.common.constant.Constant;
import com.umessage.letsgo.service.common.constant.WxConstant;
import com.umessage.letsgo.service.common.helper.UserLoginHelper;
import com.weixin.service.ITemplateMessage;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zengguoqing on 2016/9/7.
 */
@Service
public class SurveyServiceImpl implements ISurveyService{

    private static Logger logger = Logger.getLogger(SurveyServiceImpl.class);

    @Resource
    private SurveyDao surveyDao;
    @Resource
    private ITeamService teamService;
    @Resource
    private IScheduleService scheduleService;
    @Resource
    private IMemberService memberService;
    @Resource
    private ISurveyDetailService surveyDetailService;
    @Resource
    private QuestionDao questionDao;
    @Resource
    private IAnswerService answerService;
    @Resource
    private UserLoginHelper userLoginHelper;
    @Resource
    private IScheduleDetailCommentService scheduleDetailCommentService;
    @Resource
    protected WxMpService wxMpService;
    @Resource
    private ITemplateMessage templateMessageService;
    @Resource
    private IWxInfoService wxInfoService;
    @Resource
    private IAppSendMessageService appSendMessageService;
    @Resource
    private IScheduleDetailsService scheduleDetailsService;
    @Resource
    private IWxSendMessageService wxSendMessageService;

    @Resource
    private ScheduleDao scheduleDao;

    @Resource
    private IUserService userService;

    @Override
    public SurveyEntity selectByTeamId(Long teamId) {
        return surveyDao.selectByTeamId(teamId);
    }

    @Transactional
    @Override
    public Long insert(SurveyEntity surveyEntity) {
        surveyEntity.setCreateTime(new Date());
        surveyEntity.setDescription("");
        surveyEntity.setSurveyStatus(0);
        surveyEntity.setUpdateTime(new Date());
        surveyEntity.setVersion(0l);
        return surveyDao.insert(surveyEntity);
    }

    /**
     * 发放问卷
     * @param txGroupId
     * @return
     */
    @Override
    public CommonResponse issueSurvey(String txGroupId) {
        // 获取问卷
        TeamEntity teamEntity = teamService.getTeamByTXGroupId(txGroupId);
        SurveyEntity surveyEntity = this.selectByTeamId(teamEntity.getId());
        if (surveyEntity == null) {
            // 如果没有问卷，则直接返回
            return CommonResponse.createNotFoundResponse("尚未创建问卷！");
        }

        // 判断该团队行程是否在最后两天内
        int remainingDays = scheduleService.getRemainingDaysByTeamId(txGroupId);
        if (remainingDays > 2) {
            return CommonResponse.createNotFoundResponse("目前尚未到问卷发放时间！");
        }
        if (teamEntity.getStatus()==3) {
            return CommonResponse.createNotFoundResponse("问卷发放时间已过！");
        }

        List<SurveyDetailEntity> surveyDetailEntityList = new ArrayList<>();

        //查询行程
        ScheduleEntity scheduleEntity = scheduleService.getScheduleByTid(teamEntity.getId());

        // 获取团队的全部游客
        List<MemberEntity> memberEntityList = memberService.getTouristList(txGroupId);
        for (MemberEntity memberEntity : memberEntityList) {
            SurveyDetailEntity surveyDetailEntity = new SurveyDetailEntity();
            surveyDetailEntity.setUserId(memberEntity.getUserId());
            surveyDetailEntity.setTxgroupId(memberEntity.getTeamId());
            surveyDetailEntity.setSurveyId(surveyEntity.getId());

            surveyDetailEntityList.add(surveyDetailEntity);

            //发送微信模板消息
            wxSendMessageService.sendCommentNoticeTemplateMessage(memberEntity,teamEntity,scheduleEntity);

            //发送满意度调查系统信息
            AppMessageResponse responseSchedule = getCustomMsg(memberEntity,scheduleEntity);
            appSendMessageService.sendMessageToJoinInMember(responseSchedule, memberEntity.getUserEntity().getUserName());
        }

        surveyDetailService.addBatch(surveyDetailEntityList);

        CommonResponse response = new CommonResponse();
        response.setRetMsg("成功发放问卷");
        return response;

    }



    //获取系统消息
    private AppMessageResponse getCustomMsg(MemberEntity memberEntity, ScheduleEntity scheduleEntity) {

        ScheduleDetailEntity scheduleDetailEntity = scheduleDetailsService.getTodayScheduleDetail(memberEntity.gettId());
        AppMessageResponse response = new AppMessageResponse();
        logger.info("满意度调查：");
        List<CustomMsg> customMsgs = new ArrayList<>();

        CustomMsg msg1 = new CustomMsg();
        msg1.setTitle("行程日期：");
        msg1.setContent(DateUtils.toString(scheduleEntity.getStartDate(), "yyyy年MM月dd日")+"至"+DateUtils.toString(scheduleEntity.getEndDate(), "yyyy年MM月dd日"));
        customMsgs.add(msg1);

        // 设置响应对象
        response.setScheduleName(scheduleEntity.getName());
        response.setTitle("满意度调查");
        response.setDesc("尊敬的"+memberEntity.getRealName()+"您好，美好的行程已接近尾声，诚邀您参加满意度调查");
        response.setPictureUrl("http://letsgoimg-10049120.image.myqcloud.com/static_pic/waiter/survey.png");
        response.setMsg(customMsgs);
        response.setBottom("点击参加满意度调查问卷");
        response.setTargetUrl(WxConstant.APP_SURVEY_COMMENT/*+"?txGroupId="+urlEncodeTeamId+"&userID="+memberEntity.getUserId()*/);
        response.setFlag("preview");
        response.setMsgType(3);

        ParameMsg parameMsg = new ParameMsg();
        List<ParameMsg> parameMsgList = new ArrayList<>();
        parameMsg.setParameKey("txGroupId");
        parameMsg.setParameValue(memberEntity.getTeamId());
        parameMsgList.add(parameMsg);
        response.setParameMsg(parameMsgList);

        if (scheduleDetailEntity != null){
            response.setDate(null!=scheduleDetailEntity.getScheduleDate()? scheduleDetailEntity.getScheduleDate().getTime():null);
        }
        response.setTeamId(memberEntity.getTeamId());
        response.setScheduleDetaildId(scheduleDetailEntity.getId());
        response.setSubject("满意度调查问卷");
        logger.info(response);
        logger.info("封装满意度调查消息完毕");
        return response;
    }



    //获得调查问卷
    @Override
     public SurveyQuestionResponse getSurvey(SurveyRequest request){
        SurveyDetailRequest req = new SurveyDetailRequest();
        req.setTxgroupId(request.getTxGroupId());
        req.setUserId(request.getUserId());
        SurveyDetailEntity surveyDetailEntity = surveyDetailService.getByUseIdAndTxgroupId(req);
        if (surveyDetailEntity.getSurveyId() == null || surveyDetailEntity.getConfirmStatus()==null) {
            return SurveyQuestionResponse.createNotFoundResponse("未找到问卷！");
        }
        //修改是否第一次 进入  状态值  是否第一次访问 1是；0否 first
        surveyDetailEntity.setFirst(0);
        surveyDetailService.updateFirst(surveyDetailEntity);
        SurveyQuestionResponse response =new SurveyQuestionResponse();
        request.setSurveyId(surveyDetailEntity.getSurveyId());
        String title="";
        SurveyEntity surveyEntity=surveyDao.getSurveyById(surveyDetailEntity.getSurveyId());
        if(surveyEntity!=null){
            title=surveyEntity.getTitle();
        }
        List<QuestionEntity> questionList = questionDao.getSurvey(request);
        response.setQuestionList(questionList);
        response.setTitle(title);
        //领队确认状态1:是，0:否
        if(surveyDetailEntity.getConfirmStatus()==1){//1领队已经确认只能查看
            //状态：0未填写，1已填写领队未确认可编辑操作，2领队已确认查看操作
            response.setState(2);
        }else{//0领队未确认 可以查看修改
              //继续判断是否已经填写了答案
            AnswerRequest re =new AnswerRequest();
            re.setSurveyId(surveyDetailEntity.getSurveyId());
            re.setUserId(request.getUserId());
            boolean hasAnswer=answerService.hasAnswer(re);
            if(hasAnswer){//已经有答案了那就可以修改
                response.setState(1);
            }else{
                response.setState(0);
            }
         }
        return response;
    }


    // 提交调查问卷
    public CommonResponse submitSurvey(SurveyAnswerRequest request){
        List<AnswerEntity> answerList =request.getAnswerList();
        if(answerList==null && answerList.size()<=0){
            return CommonResponse.createNotFoundResponse("未获取到问卷答案");
        }
        UserResponse user = userLoginHelper.getLoginUser();
        /*if(request.getState()!=1) {
            SurveyEntity surveyEntity = surveyDao.getSurveyById(answerList.get(0).getSurveyId());
            //保存模板答案
            if (surveyEntity != null && surveyEntity.getTeamId() != null) {
                this.getScore(surveyEntity.getTeamId(),user.getUserEntity().getId(), surveyEntity.getId(), request.getState());
            }
        }*/
        for (AnswerEntity answer:answerList) {
            if(answer!=null){
                answer.setUserId(user.getUserEntity().getId());
                if(request.getState()==1){//0新增  1编辑
                    answerService.update(answer);
                }else{
                    answerService.add(answer);
                }

            }
        }
        return new CommonResponse();
    }

    @Override
    public SurveyEntity selectById(Long id) {
        return surveyDao.selectById(id);
    }

    /**
     * 获取团队问卷填写状态列表
     * @param userId
     * @param txGroupid
     * @return
     */
    @Override
    public SurveyResponse getAllSurveyStatusById(Long userId, String txGroupid) {
        List<SurveyVo> unConfirmSurveyList = new ArrayList<>();
        List<SurveyVo> confirmedSurveyList = new ArrayList<>();

        SurveyDetailRequest request = new SurveyDetailRequest();
        request.setUserId(userId);
        request.setTxgroupId(txGroupid);

        // 获取未填写的问卷详情列表
        List<SurveyDetailEntity> unConfirmList = surveyDetailService.getNotFilledSurveyDetailList(request);
        if (!CollectionUtils.isEmpty(unConfirmList)) {
            unConfirmSurveyList = fillSurveyVoList(unConfirmList);
        }

        // 获取已填写的问卷详情列表
        List<SurveyDetailEntity> confirmedList = surveyDetailService.getFilledSurveyDetailList(request);
        if (!CollectionUtils.isEmpty(confirmedList)) {
            confirmedSurveyList = fillSurveyVoList(confirmedList);
        }

        SurveyResponse response = new SurveyResponse();
        response.setIsFilled(confirmedSurveyList);
        response.setNotFilled(unConfirmSurveyList);
        return response;
    }

    @Override
    public List<SurveyEntity> getAllSurvey(Long id) {
        return surveyDao.getAllSurvey(id);
    }

    /**
     * 获取问卷及签名
     * @param surveyUserId
     * @return
     */
    @Override
    public SurveyQuestionResponse getSurveyWithSign(Long surveyUserId) {
        SurveyDetailEntity surveyDetailEntity = surveyDetailService.getById(surveyUserId);

        if (surveyDetailEntity.getSurveyId() == null) {
            return SurveyQuestionResponse.createNotFoundResponse("未找到问卷！");
        }

        // 获取问卷
        SurveyEntity surveyEntity = surveyDao.getSurveyWithAnswerById(surveyDetailEntity.getSurveyId());
        SurveyRequest request = new SurveyRequest();
        request.setUserId(surveyDetailEntity.getUserId());
        request.setSurveyId(surveyDetailEntity.getSurveyId());
        List<QuestionEntity> questionList = questionDao.getSurvey(request);
        for (QuestionEntity questionEntity:questionList) {
            String answer = questionEntity.getOptionAnswer();
            if(null!=answer){
                switch (answer){
                    case "1":
                        questionEntity.setOptionAnswer("特别不同意");
                        break;

                    case "2":
                        questionEntity.setOptionAnswer("不同意");
                        break;

                    case "3":
                        questionEntity.setOptionAnswer("一般");
                        break;

                    case "4":
                        questionEntity.setOptionAnswer("同意");
                        break;
                    case "5":
                        questionEntity.setOptionAnswer("非常同意");
                        break;

                    default:
                        questionEntity.setOptionAnswer("非常同意");
                        break;

                }
            }

        }
        SurveyQuestionResponse response = new SurveyQuestionResponse();
        response.setQuestionList(questionList);
        response.setTitle(surveyEntity.getTitle());
        response.setSignUrl(surveyDetailEntity.getSignUrl());   // 签名
        response.setConfirmStatus(surveyDetailEntity.getConfirmStatus());   // 问卷确认状态
        return response;
    }

    @Override
    public SurveyEntity getSurveyByScheduleId(Long scheduleId) {
        SurveyEntity surveyEntity = surveyDao.getSurveyByScheduleId(scheduleId);

        if (surveyEntity != null) {
            SurveyRequest request = new SurveyRequest();
            request.setSurveyId(surveyEntity.getId());
            List<QuestionEntity> questionList = questionDao.getSurvey(request);

            surveyEntity.setQuestionEntityList(questionList);
        }
        return surveyEntity;
    }

    private List<SurveyVo> fillSurveyVoList(List<SurveyDetailEntity> surveyDetailList) {
        List<SurveyVo> surveyVoList = new ArrayList<>();
        for (SurveyDetailEntity surveyDetailEntity : surveyDetailList) {
            SurveyVo surveyVo = new SurveyVo();
            surveyVo.setId(surveyDetailEntity.getId());
            surveyVo.setFillTime(surveyDetailEntity.getSubmitTime());
            surveyVo.setConfirmStatus(surveyDetailEntity.getConfirmStatus());
            surveyVo.setName(surveyDetailEntity.getUserName());
            surveyVo.setPhotoUrl(surveyDetailEntity.getUserPhotoUrl());

            surveyVoList.add(surveyVo);
        }
        return surveyVoList;
    }

    /**
     * 下载已确认问卷
     * @param userId
     * @param txGroupid
     * @return
     */
    @Override
    public SurveyDownloadResponse downloadSurvey(Long userId, String txGroupid) {
        SurveyDetailRequest request = new SurveyDetailRequest();
        request.setUserId(userId);
        request.setTxgroupId(txGroupid);
        // 获取获取已确认的问卷  进行打包
        List<SurveyDetailEntity> surveyDetailList= surveyDetailService.getConfirmSurvey(request);
        SurveyDownloadResponse response = new SurveyDownloadResponse();
        //此处保存打包好的问卷的下载链接 ，目前用的是临时数据，数据有了,拼接url
//      response.setDownloadUrl("http://letsgoimg-10042314.image.myqcloud.com/title_fileId_a7cfd378-228f-48c0-ad23-dbbe1f33d058");
        //downloadurl 跳转controller
        try {
            //对url进行编码
            String encode = URLEncoder.encode(txGroupid, "UTF-8");
            String url = Constant.H5_BASE_URL + "survey/getSurveyDetailPdfUrl?userId=" + userId + "&txGroupid=" + encode;

            logger.info("encode " + encode);
            logger.info("url" + url);
            response.setDownloadUrl(url);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public List<String> surveyStatistics() {
        List<ScheduleEntity> scheduleList = new ArrayList<>();
        ScheduleRequest request = new ScheduleRequest();
        //获取所有出行中的行程
        Page<ScheduleEntity> scheduleEntitys = scheduleDao.selectAll(request);
        for (ScheduleEntity scheduleEntity:scheduleEntitys) {
            if(scheduleEntity.getStatus() == 2){
                scheduleList.add(scheduleEntity);
            }
        }

        //处理表头
        List<String> csvContent = new ArrayList<>();
        String title = null;
        for (ScheduleEntity scheduleEntity:scheduleList) {
            title = "团号" + "," + "行程名称" + "," + "行程开始时间" + "," + "行程结束时间"+ "," + "游客姓名"+ "," + "游客手机号";
            //行程名称
            String  scheduleName = scheduleEntity.getName();
            //行程开始时间
            Date startDate = scheduleEntity.getStartDate();
            //行程结束时间
            Date endDate = scheduleEntity.getEndDate();

            Long teamId = scheduleEntity.getTeamId();
            TeamEntity teamEntity = teamService.getTeamById(teamId);
            //团号
            String teamNum = teamEntity.getTeamNum();
            //根据行程获取问卷信息
            SurveyEntity survery =  getSurveyByScheduleId(scheduleEntity.getId());
            if(null!=survery){
                List<QuestionEntity>  questionList = survery.getQuestionEntityList();
                List<SurveyDetailEntity>  detailEntitys =  survery.getSurveyDetailEntityList();
                if(!CollectionUtils.isEmpty(detailEntitys) && !CollectionUtils.isEmpty(questionList)){
                    for (QuestionEntity question:questionList) {
                        title = title + "," + question.getTitle();
                    }
                    csvContent.add(title);
                }
            }



        }

        //处理内容
        for (ScheduleEntity scheduleEntity:scheduleList) {
            String contentString = null;
            //行程名称
            String  scheduleName = scheduleEntity.getName();

            //行程开始时间
            Date startDate = scheduleEntity.getStartDate();
            //行程结束时间
            Date endDate = scheduleEntity.getEndDate();

            Long teamId = scheduleEntity.getTeamId();
            TeamEntity teamEntity = teamService.getTeamById(teamId);
            //团号
            String teamNum = teamEntity.getTeamNum();

            SurveyEntity survery =  getSurveyByScheduleId(scheduleEntity.getId());
            if(null!=survery){
                List<QuestionEntity>  questionList = survery.getQuestionEntityList();
                List<SurveyDetailEntity>  detailEntitys =  survery.getSurveyDetailEntityList();
                if(!CollectionUtils.isEmpty(detailEntitys) && !CollectionUtils.isEmpty(questionList)){
                    for (int i = 0 ;i<= detailEntitys.size();i++){
                        contentString  = contentString + "," + teamNum + "," + scheduleName + "," + startDate + "," + endDate + "," ;
                        SurveyDetailEntity surDetail =  detailEntitys.get(i);
                        Long userID = surDetail.getUserId();
                        UserEntity userEntity = userService.getUserById(userID);
                        String UserName = userEntity.getRealName();
                        String phone = userEntity.getPhone();
                        contentString = contentString + ","+ UserName + "," + phone;
                        for (QuestionEntity question:questionList) {
                            contentString  = contentString + "," + question.getOptionAnswer();

                        }
                        csvContent.add(contentString);
                    }
                }
            }




            String fileName = scheduleName + "-" + teamNum + ".csv";
            //临时存放路径
            String folder = System.getProperty("java.io.tmpdir");
            String DEST = folder + System.getProperty("file.separator") +  "满意度调查统计 " ;
            DEST =  DEST  + System.getProperty("file.separator") + fileName;
            File file = new File(DEST);
            file.getParentFile().mkdirs();
            CSVUtils.exportCSV(file,csvContent);
        }

        return null;
    }

    //给模板问题传数据  填写答案
    public void  getScore(Long teamId,Long userId,Long surveyId,Integer status) {
        ScheduleEntity scheduleEntity = scheduleService.selectBytId(teamId);
        if (scheduleEntity == null && scheduleEntity.getScheduleDetailList() == null && scheduleEntity.getScheduleDetailList().size() <= 0) {
            throw new BusinessException(ErrorConstant.NOT_FOUND, "未找到对应行程");
        }
        //获取问题列表
        SurveyRequest request = new SurveyRequest();
        request.setUserId(userId);
        request.setSurveyId(surveyId);
        List<QuestionEntity> questionList = questionDao.getSurvey(request);
        if (questionList == null &&questionList.size()<=0) {
            throw new BusinessException(ErrorConstant.NOT_FOUND, "未找到对应问题列表");
        }
        //每日行程列表
        List<ScheduleDetailEntity> scheduleDetailList = scheduleEntity.getScheduleDetailList();
        //获取模板问题加权评分
        this.getAvgScore(scheduleDetailList,surveyId,questionList,userId);

     }
        //获取模板问题加权评分
       public  void getAvgScore(List<ScheduleDetailEntity> scheduleDetailList,Long surveyId,List<QuestionEntity> questionList,Long userId){
           //问题 - 类型1为景点 2为当地交通 3为住宿 4为用餐 5为服务人员  6整体评分
           double type1=0;
           double type2=0;
           double type3=0;
           double type4=0;
           double type5=0;
           double type6=0;
           //获取每日行程点评分
           for (ScheduleDetailEntity sdList : scheduleDetailList) {
               ScheduleDetailCommentEntity scheduleDetailCommentEntity = scheduleDetailCommentService.getComment(sdList.getId(), userId);
               if (scheduleDetailCommentEntity != null) {
                   if (scheduleDetailCommentEntity.getSatisfiedStatus() == 1 || scheduleDetailCommentEntity.getSatisfiedStatus() == 2) {
                       //1为景点 2为当地交通 3为住宿 4为用餐 5为服务人员
                       List<ScoreEntity> scoreEntities=scheduleDetailCommentEntity.getScoreEntities();
                       if(scoreEntities==null&&scoreEntities.size()<=0){
                           type1+=scheduleDetailCommentEntity.getSatisfiedStatus();
                           type2+=scheduleDetailCommentEntity.getSatisfiedStatus();
                           type3+=scheduleDetailCommentEntity.getSatisfiedStatus();
                           type4+=scheduleDetailCommentEntity.getSatisfiedStatus();
                           type5+=scheduleDetailCommentEntity.getSatisfiedStatus();
                           type6+=scheduleDetailCommentEntity.getSatisfiedStatus();
                       }else{
                           type6+=scheduleDetailCommentEntity.getSatisfiedStatus();
                           for (ScoreEntity score : scoreEntities) {
                               if(score.getEvaluateOption()!=null&&score.getEvaluateOption()==1){
                                   type1+=score.getEvaluateOption();
                               }
                               if(score.getEvaluateOption()!=null&&score.getEvaluateOption()==2){
                                   type2+=score.getEvaluateOption();
                               }
                               if(score.getEvaluateOption()!=null&&score.getEvaluateOption()==3){
                                   type3+=score.getEvaluateOption();
                               }
                               if(score.getEvaluateOption()!=null&&score.getEvaluateOption()==4){
                                   type4+=score.getEvaluateOption();
                               }
                               if(score.getEvaluateOption()!=null&&score.getEvaluateOption()==5){
                                   type5+=score.getEvaluateOption();
                               }
                           }
                       }

                   }else {
                       type1+=scheduleDetailCommentEntity.getSatisfiedStatus();
                       type2+=scheduleDetailCommentEntity.getSatisfiedStatus();
                       type3+=scheduleDetailCommentEntity.getSatisfiedStatus();
                       type4+=scheduleDetailCommentEntity.getSatisfiedStatus();
                       type5+=scheduleDetailCommentEntity.getSatisfiedStatus();
                       type6+=scheduleDetailCommentEntity.getSatisfiedStatus();
                   }
               }else{
                   type1+=5;
                   type2+=5;
                   type3+=5;
                   type4+=5;
                   type5+=5;
                   type6+=5;
               }
           }
           //算出模板问题的加权平均数
           DecimalFormat df = new DecimalFormat("0.0");//格式化小数，不足的补0
           type1=(Double.parseDouble(df.format(type1/scheduleDetailList.size())));
           type2=(Double.parseDouble(df.format(type2/scheduleDetailList.size())));
           type3=(Double.parseDouble(df.format(type3/scheduleDetailList.size())));
           type4=(Double.parseDouble(df.format(type4/scheduleDetailList.size())));
           type5=(Double.parseDouble(df.format(type5/scheduleDetailList.size())));
           type6=(Double.parseDouble(df.format(type6/scheduleDetailList.size())));
           //保存答案
           this.checkCommen(surveyId,questionList,userId,type1,type2,type3,type4,type5,type6);
       }

        //5为非常满意 4为满意 3为一般 2为不满意 1为非常不满意   --- satisfiedStatus
        //判断是否需要去 评分表查询（2 和 1 的时候需要去查）
    public void  checkCommen(Long surveyId,List<QuestionEntity> questionList,Long userId,Double type1,Double type2,Double type3,Double type4,Double type5,Double type6 ){
        for (QuestionEntity question : questionList) {
                AnswerEntity answer = new AnswerEntity();
                answer.setSurveyId(surveyId);
                answer.setUserId(userId);
                answer.setQuestionId(question.getId());
                if(question.getType()!=null&&question.getType()==1){
                    answer.setOptionAnswer(type1.toString());
                    answerService.add(answer);
                    continue;
                 }
                if(question.getType()!=null&&question.getType()==2){
                    answer.setOptionAnswer(type2.toString());
                    answerService.add(answer);
                    continue;
                }
                if(question.getType()!=null&&question.getType()==3){
                    answer.setOptionAnswer(type3.toString());
                    answerService.add(answer);
                    continue;
                }
                if(question.getType()!=null&&question.getType()==4){
                    answer.setOptionAnswer(type4.toString());
                    answerService.add(answer);
                    continue;
                }
                if(question.getType()!=null&&question.getType()==5){
                    answer.setOptionAnswer(type5.toString());
                    answerService.add(answer);
                    continue;
                }
                if(question.getType()!=null&&question.getType()==6){
                    answer.setOptionAnswer(type6.toString());
                    answerService.add(answer);
                    continue;
                }

         }
      }

}
