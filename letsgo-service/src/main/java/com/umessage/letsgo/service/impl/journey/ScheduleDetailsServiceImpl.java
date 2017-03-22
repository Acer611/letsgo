package com.umessage.letsgo.service.impl.journey;


import com.umessage.letsgo.core.config.constant.ConfigConstant;
import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.core.timezone.TimeZoneUtil;
import com.umessage.letsgo.core.utils.DateUtils;
import com.umessage.letsgo.core.utils.ELUtil;
import com.umessage.letsgo.dao.journey.AlbumScheduleDao;
import com.umessage.letsgo.dao.journey.ScheduleDetailCommentDao;
import com.umessage.letsgo.dao.journey.ScheduleDetailDao;
import com.umessage.letsgo.domain.po.journey.*;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.system.RegionEntity;
import com.umessage.letsgo.domain.po.system.YahooWeather;
import com.umessage.letsgo.domain.po.team.LeaderEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.po.team.TeamEntity;
import com.umessage.letsgo.domain.po.team.TravelAgencyEntity;
import com.umessage.letsgo.domain.vo.common.request.ParameMsg;
import com.umessage.letsgo.domain.vo.common.respone.AppMessageResponse;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.common.respone.CustomMsg;
import com.umessage.letsgo.domain.vo.journey.request.ScheduleDetailRequest;
import com.umessage.letsgo.domain.vo.journey.response.*;
import com.umessage.letsgo.service.api.journey.*;
import com.umessage.letsgo.service.api.security.IAppSendMessageService;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.api.security.IWxSendMessageService;
import com.umessage.letsgo.service.api.system.IRegionService;
import com.umessage.letsgo.service.api.system.IYahooWeatherService;
import com.umessage.letsgo.service.api.team.ILeaderService;
import com.umessage.letsgo.service.api.team.IMemberService;
import com.umessage.letsgo.service.api.team.ITeamService;
import com.umessage.letsgo.service.api.team.ITravelAgencyService;
import com.umessage.letsgo.service.common.constant.WxConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
public class ScheduleDetailsServiceImpl implements IScheduleDetailsService {

    @Resource
    private ScheduleDetailDao scheduleDetailDao;
    @Resource
    private IScenicService scenicService;
    @Resource
    private ITeamService teamService;
    @Resource
    private IMemberService memberService;
    @Resource
    private ILeaderService leaderService;
    @Resource
    private IYahooWeatherService yahooWeatherService;
    @Resource
    private IDestinationService destinationService;
    @Resource
    private IUserService userService;
    @Resource
    private IScheduleService scheduleService;
    @Resource
    private IRegionService regionService;
    @Resource
    private IWxSendMessageService wxSendMessageService;
    @Resource
    private IAppSendMessageService appSendMessageService;
    @Resource
    private IScheduleDetailScenicService scheduleDetailScenicService;
    @Resource
    private IHotelScheduleService hotelScheduleService;
    @Resource
    private IShoppingScheduleService shoppingScheduleService;
    @Resource
    private IOwnExpenseScheduleService ownExpenseScheduleService;
    @Resource
    private ITravelAgencyService travelService;
    @Resource
    private IAlbumScheduleService albumScheduleService;
    @Resource
    private ScheduleDetailCommentDao scheduleDetailCommentDao;

    @Resource
    private AlbumScheduleDao albumSchduleDao;


    Logger logger = Logger.getLogger(ScheduleDetailsServiceImpl.class);

    @Override
    public int addScheduleDetails(ScheduleDetailEntity scheduleDetailsEntity) {
        scheduleDetailsEntity.setVersion(0L);
        Date currentDate = new Date();
        scheduleDetailsEntity.setCreateTime(currentDate);
        scheduleDetailsEntity.setUpdateTime(currentDate);
        return scheduleDetailDao.insert(scheduleDetailsEntity);
    }

    @Override
    public int deleteScheduleDetails(long id) {
        return scheduleDetailDao.delete(id);
    }

    @Override
    public int updateScheduleDetails(ScheduleDetailEntity scheduleDetailsEntity) {
        scheduleDetailsEntity.setUpdateTime(new Date());
        return scheduleDetailDao.update(scheduleDetailsEntity);
    }
    private int updateScheduleDetails(ScheduleDetailEntity detailEntity,ScheduleDetailEntity originalDetail) {
        originalDetail.setScheduleDate(detailEntity.getScheduleDate());
        originalDetail.setStartPlace(detailEntity.getStartPlace());
        originalDetail.setStartPlaceId(detailEntity.getStartPlaceId());

        originalDetail.setDestination1(detailEntity.getDestination1());
        originalDetail.setDestinationId1(detailEntity.getDestinationId1());
        originalDetail.setDestinationId2(detailEntity.getDestinationId2());
        originalDetail.setDestinationId3(detailEntity.getDestinationId3());
        originalDetail.setDestinationId4(detailEntity.getDestinationId4());
        originalDetail.setHotelId(detailEntity.getHotelId());
        originalDetail.setDestination2(detailEntity.getDestination2());
        originalDetail.setDestination3(detailEntity.getDestination3());
        originalDetail.setDestination4(detailEntity.getDestination4());

        originalDetail.setSchedulePhotosUrl1(detailEntity.getSchedulePhotosUrl1());
        originalDetail.setSchedulePhotosUrl2(detailEntity.getSchedulePhotosUrl2());
        originalDetail.setSchedulePhotosUrl3(detailEntity.getSchedulePhotosUrl3());

        originalDetail.setDesc(detailEntity.getDesc());
        originalDetail.setHotelConfirm(detailEntity.getHotelConfirm());
        originalDetail.setHotel(detailEntity.getHotel());
        originalDetail.setHotelInput(detailEntity.getHotelInput());
        originalDetail.setSameLevel(detailEntity.getSameLevel());

        originalDetail.setFlight1(detailEntity.getFlight1());
        originalDetail.setFlight2(detailEntity.getFlight2());
        originalDetail.setTrafficInfo(detailEntity.getTrafficInfo());
        originalDetail.setShoppInfo(detailEntity.getShoppInfo());
        originalDetail.setCateringInfo(detailEntity.getCateringInfo());

        originalDetail.setLeaderId(detailEntity.getLeaderId());//只能添加，不能修改
        originalDetail.setNewEntry(detailEntity.getNewEntry());
        originalDetail.setNewContant(detailEntity.getNewContant());
        originalDetail.setUpdateTime(new Date());
        originalDetail.setCountry1(detailEntity.getCountry1());
        originalDetail.setCountry2(detailEntity.getCountry2());
        originalDetail.setCountry3(detailEntity.getCountry3());
        originalDetail.setCountry4(detailEntity.getCountry4());
        originalDetail.setCountryId1(detailEntity.getCountryId1());
        originalDetail.setCountryId2(detailEntity.getCountryId2());
        originalDetail.setCountryId3(detailEntity.getCountryId3());
        originalDetail.setCountryId4(detailEntity.getCountryId4());
        originalDetail.setTraffic1(detailEntity.getTraffic1());
        originalDetail.setTraffic2(detailEntity.getTraffic2());
        originalDetail.setTraffic3(detailEntity.getTraffic3());
        originalDetail.setTraffic4(detailEntity.getTraffic4());
        originalDetail.setTraffic5(detailEntity.getTraffic5());

        originalDetail.setDestinationId5(detailEntity.getDestinationId5());
        originalDetail.setDestination5(detailEntity.getDestination5());
        originalDetail.setCountryId5(detailEntity.getCountryId5());
        originalDetail.setCountry5(detailEntity.getCountry5());


        return scheduleDetailDao.update(originalDetail);
    }

    //修改每日行程酒店
    public HotelScheduleEntity updateHotelSchedule(ScheduleDetailRequest scheduleDetailRequest, ScheduleDetailEntity scheduleDetail){
        //修改酒店
        HotelScheduleEntity hotelSchedule = scheduleDetailRequest.getHotelScheduleEntity();
        if (hotelSchedule != null){
            hotelSchedule.setScheduleDetailId(scheduleDetail.getId());
            int i = hotelScheduleService.deleteByScheduleDetailId(scheduleDetail.getId());
            hotelScheduleService.insert(hotelSchedule);
        }
        return hotelSchedule;
    }

    //修改每日行程购物场所
    public List<ShoppingScheduleEntity> updateShoppingSchedule(ScheduleDetailRequest scheduleDetailRequest, ScheduleDetailEntity scheduleDetail){
        List<ShoppingScheduleEntity> shoppingScheduleList = scheduleDetailRequest.getShoppingScheduleList();
        shoppingScheduleService.deleteShoppingByScheduleDetailId(scheduleDetail.getId());
        if(null!=shoppingScheduleList){
            for (ShoppingScheduleEntity shoppingSchedule:shoppingScheduleList) {
                shoppingSchedule.setScheduleDetailId(scheduleDetail.getId());
                shoppingScheduleService.insert(shoppingSchedule);
            }
        }
        return shoppingScheduleList;
    }

    //修改每日行程自费项目
    public List<OwnExpenseScheduleEntity> updateOwnExpenseSchedule(ScheduleDetailRequest scheduleDetailRequest, ScheduleDetailEntity scheduleDetail){
        List<OwnExpenseScheduleEntity> ownExpenseScheduleList = scheduleDetailRequest.getOwnExpenseScheduleList();
        ownExpenseScheduleService.deleteOwnExpenseScheduleByScheduleDetailId(scheduleDetail.getId());
        if (ownExpenseScheduleList != null){
            for (OwnExpenseScheduleEntity ownExpenseScheduleEntity:ownExpenseScheduleList) {
                ownExpenseScheduleEntity.setScheduleDetailId(scheduleDetail.getId());
                ownExpenseScheduleService.insert(ownExpenseScheduleEntity);
            }
        }
        return ownExpenseScheduleList;
    }

    @Override
    public ScheduleResponse saveScheduleDeails(ScheduleDetailEntity scheduleDetail) {
        if (scheduleDetail == null) return ScheduleResponse.createNotFoundResponse("没有行程详细");
        addScheduleDetails(scheduleDetail);
        //保存目的地的时区，纬度度，城市和国家
//        List<String> destinationList = getDestinations(scheduleDetail);
//        destinationService.save(destinationList);

        saveScenic(scheduleDetail.getScenicList(), scheduleDetail.getId());
        //如果有导游，加入到团队成员表中
        if (scheduleDetail.getLeaderId() != null && !"".equals(scheduleDetail.getLeaderId())) {
            TeamEntity teamEntity = teamService.selectByScheduleId(scheduleDetail.getJourId());
            boolean phoneIsRepeat = memberService.memberPhoneIsRepeat(scheduleDetail.getPhone(),teamEntity.getId());
            if(!phoneIsRepeat){// 导游不在该团中，将导游加入member。
                addMemberForGuide(scheduleDetail, teamEntity);
                teamEntity.setTotalCount(teamEntity.getTotalCount() + 1);
                teamService.updateTeam(teamEntity);
            }
        }
        ScheduleResponse response = new ScheduleResponse();
        response.setScheduleDetailEntity(scheduleDetail);
        return response;
    }

    /**
     * 保存每日行程的信息
     * @param scheduleDetail
     * @return
     */
    @Override
    public ScheduleResponse saveScheduleDetail(TravelAgencyEntity travelEntity,ScheduleDetailRequest scheduleDetail) {
        if (scheduleDetail == null) return ScheduleResponse.createNotFoundResponse("没有行程详细");

        ScheduleDetailEntity scheduleDetailEntity =  scheduleDetail.getScheduleDetailEntity();
        //验证字段长度
        String desc = scheduleDetailEntity.getDesc();//当日行程
        if (!StringUtils.isEmpty(desc) && desc.length() > 20000){
            return ScheduleResponse.createInvalidParameterResponse("当日行程内容过长，请重新输入");
        }

        String flight1 = scheduleDetailEntity.getFlight1();//航班信息
        if (!StringUtils.isEmpty(flight1) && flight1.length() > 20000){
            return ScheduleResponse.createInvalidParameterResponse("航班信息内容过长，请重新输入");
        }

        String cateringInfo = scheduleDetailEntity.getCateringInfo();//餐饮说明
        if (!StringUtils.isEmpty(cateringInfo) && cateringInfo.length() > 20000){
            return ScheduleResponse.createInvalidParameterResponse("餐饮说明内容过长，请重新输入");
        }

        String newEntry = scheduleDetailEntity.getNewEntry();//新增标题
        if (!StringUtils.isEmpty(newEntry) && newEntry.length() > 500){
            return ScheduleResponse.createInvalidParameterResponse("新增标题内容过长，请重新输入");
        }

        String newContant = scheduleDetailEntity.getNewContant();//新增内容
        if (!StringUtils.isEmpty(newContant) && newContant.length() > 20000){
            return ScheduleResponse.createInvalidParameterResponse("新增内容内容过长，请重新输入");
        }

        // 保存每日行程信息
        addScheduleDetails(scheduleDetailEntity);

        //保存每日行程和景点关联信息
        List<ScheduleDetailScenicEntity> scheduleDetailScenicEntitys = scheduleDetail.getScheduleDetailScenicEntitys();
        if (scheduleDetailScenicEntitys != null){
            for (ScheduleDetailScenicEntity scheduleDetailScenicEntity:scheduleDetailScenicEntitys) {
                scheduleDetailScenicEntity.setScheduleDetailId(scheduleDetailEntity.getId());
                scheduleDetailScenicService.saveScheduleDetailScenicToTable(travelEntity,scheduleDetailScenicEntity);
            }
        }

        //保存每日行程和酒店关联信息
        HotelScheduleEntity hotelScheduleEntity = scheduleDetail.getHotelScheduleEntity();
        if(null!=hotelScheduleEntity){
            hotelScheduleEntity.setScheduleDetailId(scheduleDetailEntity.getId());
            hotelScheduleService.insert(hotelScheduleEntity);
        }

        //保存购物场所
       List<ShoppingScheduleEntity> shoppingScheduleList = scheduleDetail.getShoppingScheduleList();
        if (shoppingScheduleList != null){
            for (ShoppingScheduleEntity shoppingScheduleEntity:shoppingScheduleList) {
                shoppingScheduleEntity.setScheduleDetailId(scheduleDetailEntity.getId());
                shoppingScheduleService.insert(shoppingScheduleEntity);
            }
        }

        //保存自费项目
        List<OwnExpenseScheduleEntity> ownExpenseScheduleList = scheduleDetail.getOwnExpenseScheduleList();
        if (ownExpenseScheduleList != null){
            for (OwnExpenseScheduleEntity ownExpenseScheduleEntity:ownExpenseScheduleList) {
                ownExpenseScheduleEntity.setScheduleDetailId(scheduleDetailEntity.getId());
                ownExpenseScheduleService.insert(ownExpenseScheduleEntity);
            }
        }

        ScheduleResponse response = new ScheduleResponse();
        response.setScheduleDetailEntity(scheduleDetailEntity);
        return response;
    }


    private List<String> getDestinations(ScheduleDetailEntity scheduleDetail){
        List<String> list = new ArrayList<String>();
        if(!StringUtils.isEmpty(scheduleDetail.getDestination1())){
            list.add(scheduleDetail.getDestination1());
        }
        if(!StringUtils.isEmpty(scheduleDetail.getDestination2())){
            list.add(scheduleDetail.getDestination2());
        }
        if(!StringUtils.isEmpty(scheduleDetail.getDestination3())){
            list.add(scheduleDetail.getDestination3());
        }
        if(!StringUtils.isEmpty(scheduleDetail.getDestination4())){
            list.add(scheduleDetail.getDestination4());
        }
        return list;
    }

    @Override
    public ScheduleResponse updateDetails(ScheduleDetailEntity scheduleDetail) {
        if (scheduleDetail == null) return ScheduleResponse.createNotFoundResponse("没有行程详细");

        ScheduleDetailEntity originalDetail = getScheduleDetails(scheduleDetail.getId());
        Long originalLeaderId = originalDetail.getLeaderId();
        updateScheduleDetails(scheduleDetail,originalDetail);
        //保存目的地的时区，纬度度，城市和国家
//        List<String> destinationList = getDestinations(scheduleDetail);
//        destinationService.save(destinationList);

        deleteScenic(scheduleDetail.getId());
        saveScenic(scheduleDetail.getScenicList(), scheduleDetail.getId());

        //如果有导游，加入到团队成员表中
        updateScheduleDetailForGuide(originalLeaderId, scheduleDetail);
        ScheduleResponse response = new ScheduleResponse();
        response.setScheduleDetailEntity(scheduleDetail);
        return response;
    }

    @Override
    public ScheduleResponse updateScheduleDetailDetail(ScheduleDetailRequest scheduleDetailRequest) {

        ScheduleDetailEntity scheduleDetail = scheduleDetailRequest.getScheduleDetailEntity();
        if (scheduleDetail == null) return ScheduleResponse.createNotFoundResponse("没有行程详细");

        //验证字段长度
        String desc = scheduleDetail.getDesc();//当日行程
        if (!StringUtils.isEmpty(desc) && desc.length() > 20000){
            return ScheduleResponse.createInvalidParameterResponse("当日行程内容过长，请重新输入");
        }

        String flight1 = scheduleDetail.getFlight1();//航班信息
        if (!StringUtils.isEmpty(flight1) && flight1.length() > 20000){
            return ScheduleResponse.createInvalidParameterResponse("航班信息内容过长，请重新输入");
        }

        String cateringInfo = scheduleDetail.getCateringInfo();//餐饮说明
        if (!StringUtils.isEmpty(cateringInfo) && cateringInfo.length() > 20000){
            return ScheduleResponse.createInvalidParameterResponse("餐饮说明内容过长，请重新输入");
        }

        String newEntry = scheduleDetail.getNewEntry();//新增标题
        if (!StringUtils.isEmpty(newEntry) && newEntry.length() > 500){
            return ScheduleResponse.createInvalidParameterResponse("新增标题内容过长，请重新输入");
        }

        String newContant = scheduleDetail.getNewContant();//新增内容
        if (!StringUtils.isEmpty(newContant) && newContant.length() > 20000){
            return ScheduleResponse.createInvalidParameterResponse("新增内容内容过长，请重新输入");
        }

        ScheduleDetailEntity originalDetail = getScheduleDetails(scheduleDetail.getId());
        Long originalLeaderId = originalDetail.getLeaderId();
        updateScheduleDetails(scheduleDetail,originalDetail);

        //删除图片信息
        AlbumScheduleEntity albumScheduleEntity = new AlbumScheduleEntity();
        albumScheduleEntity.setScheduleDetailId(scheduleDetail.getId());
        albumScheduleService.deleteByContents(albumScheduleEntity);

        //修改每日行程酒店
        this.updateHotelSchedule(scheduleDetailRequest,scheduleDetail);

        //修改每日行程购物场所
        this.updateShoppingSchedule(scheduleDetailRequest,scheduleDetail);

        // 修改每日行程自费项目
        this.updateOwnExpenseSchedule(scheduleDetailRequest,scheduleDetail);

       /* deleteScenic(scheduleDetail.getId());
        saveScenic(scheduleDetail.getScenicList(), scheduleDetail.getId());*/

        //修改景点信息
        List<ScheduleDetailScenicEntity> scheduleDetailScenicEntities = scheduleDetailRequest.getScheduleDetailScenicEntitys();
        TravelAgencyEntity travelEntity = travelService.getCurrentTravel();
        //TODO  删除景点每日行程关联关系数据
        scheduleDetailScenicService.deleteScenicByScheduldId(scheduleDetail.getId());
        //保存每日行程景点信息
        if (scheduleDetailScenicEntities != null){
            for (ScheduleDetailScenicEntity cheduleDetailScenicEntity:scheduleDetailScenicEntities) {
                cheduleDetailScenicEntity.setScheduleDetailId(scheduleDetail.getId());
                scheduleDetailScenicService.saveScheduleDetailScenicToTable(travelEntity,cheduleDetailScenicEntity);
            }
        }

        //如果有导游，加入到团队成员表中
        updateScheduleDetailForGuide(originalLeaderId, scheduleDetail);
        ScheduleResponse response = new ScheduleResponse();
        response.setScheduleDetailEntity(scheduleDetail);
        return response;
    }


    /**
     * 据行程id查询行程详细
     * @param scheduleId
     * @return
     */
    @Override
    public List<ScheduleDetailEntity> selectScheduleDetailByScheduleId(Long scheduleId) {
        return scheduleDetailDao.selectScheduleDetailByScheduleId(scheduleId);
    }

    /**
     * 删除每日行程中景点
     */
    private void deleteScenic(Long scheduleDetailId) {
        scenicService.deleteByScheduleDetailId(scheduleDetailId);
    }

    /**
     * 保存每日行程中景点
     */
    private void saveScenic(List<ScenicEntity> scenicList, Long scheduleDetailId) {
        if (scenicList == null) return;
        for (ScenicEntity scenic : scenicList) {
            scenic.setScheduleDetailId(scheduleDetailId);
            scenicService.addScenic(scenic);
        }
    }

    //将指定导游加入成员表中。
    private void addMemberForGuide(ScheduleDetailEntity scheduleDetail, TeamEntity teamEntity) {
        MemberEntity member = new MemberEntity();
        member.setRealName(scheduleDetail.getName());
        member.setSex(scheduleDetail.getSex());
        member.setPhone(scheduleDetail.getPhone());
        member.settId(teamEntity.getId());//团队id
        member.setRole(2);//导游
        member.setRoleDescn("导游");
        member.setIsAdmin(0);//不是管理员
        member.setIsNewJoin(0);//是否为新加入（即为自己通过扫描填写资料加入）【0：否；1：是】
        try {
            if(member.getSex()!=null&&member.getSex()==1){
                member.setPhotoUrl(ConfigConstant.MALE_HEAD_URL);
            }else{
                member.setPhotoUrl(ConfigConstant.FEMALE_HEAD_URL);
            }
//            String photoUrl = PhotoHelper.createMemberImage(member.getRealName(),member.getSex());
//            member.setPhotoUrl(photoUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        memberService.addMember(member);

        UserEntity user = userService.getUserByPhone(member.getPhone());
        if(user != null) {//更新已有用户的新成员
            member.setUserId(user.getId());
            member.setJoinStatus(1);
            String txGroupId = teamEntity.getTxGroupid();
            member.setTeamId(txGroupId);
            memberService.updateMember(member);
            //将已有用户加入腾迅云群组中
            if(!StringUtils.isEmpty(txGroupId)){
                //将已有用户加入腾迅云群组中
                scheduleService.addTXGroup(member, user, txGroupId);
                //发送欢迎加入信息
                userService.sendInvitationMessage(member,teamEntity.getTxGroupid());
                //增加好友关系
                //List<MemberEntity> memberEntityList = memberService.getTouristList(txGroupId);
                //scheduleService.addFriend(user.getUserName(), memberEntityList);
            }
        }

        // 发送邀请加入短信
        try {
            userService.sendInvitationMessage(member.getId(), member.getPhone());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    private void deleteMemberForGuide(Long leaderId, Long teamId) {
        LeaderEntity leader = leaderService.getLeader(leaderId);
        MemberEntity member = memberService.getMemberForGuide(teamId, leader.getPhone());
        memberService.deleteMember(member);
    }

    private void updateScheduleDetailForGuide(Long originalLeaderId, ScheduleDetailEntity scheduleDetail) {
        TeamEntity teamEntity = teamService.selectByScheduleId(scheduleDetail.getJourId());
        if (originalLeaderId == null || "".equals(originalLeaderId)) {
            if (scheduleDetail.getLeaderId() != null && !"".equals(scheduleDetail.getLeaderId())) {
                boolean phoneIsRepeat = memberService.memberPhoneIsRepeat(scheduleDetail.getPhone(),teamEntity.getId());
                if (!phoneIsRepeat) {// 导游不在该团中，将导游加入member。
                    addMemberForGuide(scheduleDetail, teamEntity);
                    teamEntity.setTotalCount(teamEntity.getTotalCount() + 1);
                    teamService.updateTeam(teamEntity);
                }
            }
        }
//        else {
//            //团队中总人数不变，不更新团队表
//            if (scheduleDetail.getLeaderId() != null && !"".equals(scheduleDetail.getLeaderId())) {
//                deleteMemberForGuide(originalLeaderId, teamEntity.getId());
//                addMemberForGuide(scheduleDetail, teamEntity);
//            }
//        }
    }

    @Override
    public CommonResponse updateScheduleDetails(ScheduleDetailRequest request) {
        ScheduleDetailEntity detailEntity = getScheduleDetails(request.getId());
        if (detailEntity.getId() == null) {
            return CommonResponse.createNotFoundResponse("没有发现该行程详细");
        }
        //获取变更内容
        String changeContent = this.getChangeContent(detailEntity, request);

        detailEntity.setTrafficInfo(request.getTrafficInfo());//交通
        detailEntity.setLeaderId(request.getLeaderId());//导游ID
        detailEntity.setNewEntry(request.getNewEntry());//新增条目标题
        detailEntity.setNewContant(request.getNewContant());//新增条目内容
        detailEntity.setHotelInput(request.getHotelInput());//酒店
        detailEntity.setHotelConfirm(0);//手机端手动输入的视为不能确定。//酒店是否确认
        updateScheduleDetails(detailEntity);
        //查询领队
        List<MemberEntity> memberLeaderEntityList = memberService.selectMemberByScheduleDetailId(detailEntity.getId(),1);
        MemberEntity memberLeaderEntity = null;
        if (memberLeaderEntityList.size() > 0){
            memberLeaderEntity = memberLeaderEntityList.get(0);
        }

        //查询游客
        List<MemberEntity> memberEntityList = memberService.selectMemberByScheduleDetailId(detailEntity.getId(),3);
        for (MemberEntity memberEntity:memberEntityList) {
            //发送微信模板消息
            wxSendMessageService.sendChangeNoticeTemplateMessage(memberEntity,detailEntity,memberLeaderEntity,changeContent);
            //发送行程变更系统信息
            AppMessageResponse responseSchedule = getCustomScheduleMsg(memberEntity,detailEntity,memberLeaderEntity,changeContent);
            appSendMessageService.sendMessageToJoinInMember(responseSchedule, memberEntity.getUserEntity().getUserName());
        }

        return new CommonResponse();
    }


    @Override
    public ScheduleDetailEntity getScheduleDetails(long id) {
        ScheduleDetailEntity entity = scheduleDetailDao.select(id);
        if (entity == null) {
            entity = new ScheduleDetailEntity();
        }
        return entity;
    }


    //设置变更内容
    public String getChangeContent(ScheduleDetailEntity detailEntity, ScheduleDetailRequest request){
        String changeContent = "";
        //酒店
        String hotelInput = detailEntity.getHotelInput();
        String hotelInputRe = request.getHotelInput();
        if (StringUtils.isEmpty(hotelInput) && !StringUtils.isEmpty(hotelInputRe) && !hotelInputRe.equals(hotelInput)){
            changeContent = changeContent + "酒店变更为“" + hotelInputRe + "”";
        }else if (!StringUtils.isEmpty(hotelInput) && !StringUtils.isEmpty(hotelInputRe) && !hotelInputRe.equals(hotelInput)){
            changeContent = changeContent + "酒店由“"+hotelInput+"”变更为“" + hotelInputRe + "”";
        }
        //交通
        String trafficInfo = detailEntity.getTrafficInfo();
        String trafficInfoRe = request.getTrafficInfo();
        if (StringUtils.isEmpty(trafficInfo) && !StringUtils.isEmpty(trafficInfoRe)){
            if (StringUtils.isEmpty(changeContent)){
                changeContent = changeContent + "交通变更为“" + trafficInfoRe + "”";
            }else {
                changeContent = changeContent + "，交通变更为“" + trafficInfoRe + "”";
            }
        }else if (!StringUtils.isEmpty(trafficInfo) && !StringUtils.isEmpty(trafficInfoRe) && !trafficInfoRe.equals(trafficInfo)){
            if (StringUtils.isEmpty(changeContent)){
                changeContent = changeContent + "交通由“"+trafficInfo+"”变更为“" + trafficInfoRe + "”";
            }else {
                changeContent = changeContent + "，交通由“"+trafficInfo+"”变更为“" + trafficInfoRe + "”";
            }
        }
        //新增条目标题
        String newEntry = detailEntity.getNewEntry();
        String newEntryRe = request.getNewEntry();
        //新增条目内容
        String newContant = detailEntity.getNewContant();
        String newContantRe = request.getNewContant();
        if (!StringUtils.isEmpty(newContantRe) && !StringUtils.isEmpty(newEntryRe) && !newContantRe.equals(newContant)){
            if (StringUtils.isEmpty(changeContent)){
                changeContent = changeContent + "新增标题变更为标题“" + newEntryRe + "”内容“" + newContantRe+ "”";
            }else {
                changeContent = changeContent + "，新增标题变更为标题“" + newEntryRe + "”内容“" + newContantRe+ "”";
            }
        }
        //导游ID
        Long leaderId = detailEntity.getLeaderId();
        Long leaderIdRe = request.getLeaderId();
        if (leaderIdRe != null && leaderId != leaderIdRe){
            UserEntity userEntity = userService.getUserById(leaderIdRe);
            if (userEntity != null){
                String realName = userEntity.getRealName();
                if (StringUtils.isEmpty(changeContent)){
                    changeContent = changeContent + "导游变更为：“" + realName+ "”";
                }else {
                    changeContent = changeContent + "，导游变更为：“" + realName+ "”";
                }
            }
        }

        return changeContent;
    }


    //获取系统消息
    private AppMessageResponse getCustomScheduleMsg(MemberEntity memberEntity, ScheduleDetailEntity scheduleDetailEntity, MemberEntity memberLeaderEntity, String changeContent) {
        AppMessageResponse response = new AppMessageResponse();
        logger.info("行程变更安排：");
        List<CustomMsg> customMsgs = new ArrayList<>();

        CustomMsg msg1 = new CustomMsg();
        msg1.setTitle("变更行程日期：");
        msg1.setContent("第"+scheduleDetailEntity.getDayNum()+"天"+"（"+DateUtils.toString(scheduleDetailEntity.getScheduleDate(), "yyyy年MM月dd日")+")");
        customMsgs.add(msg1);

        CustomMsg msg2 = new CustomMsg();
        msg2.setTitle("变更内容：");
        msg2.setContent(changeContent);
        customMsgs.add(msg2);


        //参数
        List<ParameMsg> parameMsgs = new ArrayList<>();

        ParameMsg parameMsg1 = new ParameMsg();
        parameMsg1.setParameKey("teamId");
        parameMsg1.setParameValue(memberEntity.getTeamId());
        parameMsgs.add(parameMsg1);

        ParameMsg parameMsg2 = new ParameMsg();
        parameMsg2.setParameKey("scheduleDetaildId");
        parameMsg2.setParameValue(scheduleDetailEntity.getId().toString());
        parameMsgs.add(parameMsg2);

        ParameMsg parameMsg3 = new ParameMsg();
        parameMsg3.setParameKey("flag");
        parameMsg3.setParameValue("preview");
        parameMsgs.add(parameMsg3);

        //团队名称
        String name = memberEntity.gettName();
        if (!org.apache.commons.lang.StringUtils.isEmpty(name)){
            name = ELUtil.substring(name, 15);
        }
        // 设置响应对象
        response.setScheduleName(name);
        response.setTitle("行程变更通知");
        response.setDesc("尊敬的"+memberEntity.getRealName()+"您好，领队"+memberLeaderEntity.getRealName()+"修改了行程，请及时查看");
        response.setPictureUrl("http://letsgoimg-10049120.image.myqcloud.com/static_pic/waiter/schedule.png");
        response.setMsg(customMsgs);
        response.setBottom("立即查看");
        response.setTargetUrl(WxConstant.APP_SCHEDULE);
        response.setFlag("preview");
        response.setMsgType(3);
        response.setParameMsg(parameMsgs);
        response.setTeamId(memberEntity.getTeamId());
        response.setSubject("行程");

        logger.info(response);
        logger.info("封装行程变更安排消息完毕");
        return response;
    }

    /**
     * 获取某个团队中的明日行程
     * @param teamId
     * @return
     */
    public ScheduleDetailEntity getTomorrowScheduleDetail(Long teamId) {
        List<ScheduleDetailEntity> scheduleDetailList = this.getScheduleDetails(teamId, null);
        ScheduleDetailEntity scheduleDetail = null;
        if (CollectionUtils.isEmpty(scheduleDetailList)) {
            return scheduleDetail;
        }

        for (ScheduleDetailEntity scheduleDetailEntity: scheduleDetailList
                ) {
            scheduleDetailEntity = getScheduleDetailTimeZone(scheduleDetailEntity);

            Date scheduleDate = scheduleDetailEntity.getScheduleDate();
            TimeZone startTimeZone = TimeZone.getTimeZone(scheduleDetailEntity.getStartTimeZone());

            // 行程出发地当地时间
            Date timeZoneDate = TimeZoneUtil.getTimeZoneDate(new Date(), startTimeZone);
            // 根据行程出发地当地时间；判断是否明日行程
            Date tomorrowDate = DateUtils.addDay(timeZoneDate, 1);
            if (DateUtils.isSameDay(scheduleDate, tomorrowDate)){
                scheduleDetail = scheduleDetailEntity;
                break;
            }
        }

        //获取对应的天气
        if (scheduleDetail != null){
            scheduleDetail = this.getWeather(scheduleDetail);
        }
        return scheduleDetail;
    }

    /**
     * 获取某个团队中的今天行程
     * @param teamId
     * @return
     */
    public ScheduleDetailEntity getTodayScheduleDetail(Long teamId) {
        List<ScheduleDetailEntity> scheduleDetailList = this.getScheduleDetails(teamId, null);
        ScheduleDetailEntity scheduleDetail = null;
        if (CollectionUtils.isEmpty(scheduleDetailList)) {
            return scheduleDetail;
        }

        for (ScheduleDetailEntity scheduleDetailEntity: scheduleDetailList
                ) {
            scheduleDetailEntity = getScheduleDetailTimeZone(scheduleDetailEntity);

            Date scheduleDate = scheduleDetailEntity.getScheduleDate();
            //TimeZone destinationTimeZone = TimeZone.getTimeZone(scheduleDetailEntity.getDestinationTimeZone());

            // 行程目的地当地时间
            //Date timeZoneDate = TimeZoneUtil.getTimeZoneDate(new Date(), destinationTimeZone);

            // 根据行程目的地当地时间；判断是否今日行程
            if (DateUtils.isSameDay(scheduleDate, new Date())){
                scheduleDetail = scheduleDetailEntity;
                break;
            }
        }

        //获取对应的天气
        if (scheduleDetail != null){
            scheduleDetail = this.getWeather(scheduleDetail);
        }
        return scheduleDetail;
    }

    /**
     * 获取指定行程的时区信息
     * @param scheduleDetailEntity
     * @return
     */
    private ScheduleDetailEntity getScheduleDetailTimeZone(ScheduleDetailEntity scheduleDetailEntity) {
        Date scheduleDate = scheduleDetailEntity.getScheduleDate();

        // 获取出发地的时区和时间
        String startPlaceId = getStartPlaceId(scheduleDetailEntity);
        TimeZone startTimeZone = regionService.getTimeZoneByRegionId(startPlaceId);
        Date startTimeZoneDate = TimeZoneUtil.getSystemDateByTimeZone(scheduleDate, startTimeZone);
        scheduleDetailEntity.setStartTimeZone(startTimeZone.getID());
        scheduleDetailEntity.setStartTimeZoneDate(startTimeZoneDate);

        // 获取最后一个目的地的时区和时间
        String destId = getLastDestinationId(scheduleDetailEntity);
        TimeZone destTimeZone = regionService.getTimeZoneByRegionId(destId);
        Date destinationTimeZoneDate = TimeZoneUtil.getSystemDateByTimeZone(scheduleDate, destTimeZone);
        scheduleDetailEntity.setDestinationTimeZone(destTimeZone.getID());
        scheduleDetailEntity.setDestinationTimeZoneDate(destinationTimeZoneDate);

        return scheduleDetailEntity;
    }

    /**
     * 根据团队id获取当日目的地
     */
    public CurrentTimeZoneResponse getCurrentZoneInfo(Long teamId) {
        //获取当前日期的string格式
        String currentDate = DateUtils.getReqDate();

        ScheduleDetailEntity scheduleDetailEntity = getTodayScheduleDetail(teamId);
        if(scheduleDetailEntity == null || scheduleDetailEntity.getId() == null){
            throw new BusinessException(ErrorConstant.NOT_FOUND, "未找到对应行程!");
        }

        TimeZoneInfo timeZoneInfo = new TimeZoneInfo();
        timeZoneInfo.setDestination(getLastDest(scheduleDetailEntity) + "时间");
        timeZoneInfo.setTimeZoneId(scheduleDetailEntity.getDestinationTimeZone());

        CurrentTimeZoneResponse response = new CurrentTimeZoneResponse();
        response.setCurrentTimeZone(timeZoneInfo);
        return response;
    }

    /**
     * 获取行程目的地对应的天气
     * @param scheduleDetail
     * @return
     */
    public ScheduleDetailEntity getWeather(ScheduleDetailEntity scheduleDetail){
        // 获取最后一个目的地的天气
        //String city = getLastDest(scheduleDetail);
        //String lastCityWeather = this.getCityWeather(city, dateString);
        //scheduleDetail.setWeatherDescn(lastCityWeather);
        String dateString = DateUtils.f(scheduleDetail.getScheduleDate());

        // 获取最后一个目的地的天气
        String cityAreaId = getLastDestinationId(scheduleDetail);
        String lastCityWeather = this.getCityWeather(cityAreaId, dateString);
        scheduleDetail.setWeatherDescn(lastCityWeather);

        //获取第一个目的地的天气
        String firstCityID = this.getFirstDest(scheduleDetail);
        String firstCityWeather = this.getCityWeather(firstCityID, dateString);
        scheduleDetail.setFirstDayWeatherDescn(firstCityWeather);

        //获取出发地的天气
        String startCityID = this.getStartPlaceId(scheduleDetail);
        String startCityWeather = this.getCityWeather(startCityID, dateString);
        scheduleDetail.setStartPlaceWeatherDescn(startCityWeather);

        return  scheduleDetail;
    }




    /**
     * 根据城市获取天气
     * @param cityAreaID
     * @param dateString
     * @return
     */
    public String getCityWeather(String cityAreaID, String dateString){
        if(null==cityAreaID){
            return "暂无数据";
        }
        //RegionEntity regionEntity = regionService.getDestinationByDest(city);
        RegionEntity regionEntity = regionService.getById(cityAreaID);
        YahooWeather yahooWeather = new YahooWeather();
//        if (regionEntity.getId() != null) {
        String weather = "";
        if (regionEntity.getAreaid() != null) {
//            yahooWeather = yahooWeatherService.getWeather(destinationEntity.getCity(), dateString);
            yahooWeather = yahooWeatherService.getWeather(regionEntity.getAlias(), dateString);
            if (yahooWeather.getId() != null) {
                weather = yahooWeather.getDescn() + " " + yahooWeather.getLow() + "~" + yahooWeather.getHigh() + "℃";
            } else {
                weather = "暂无数据";
            }
        }
        return  weather;
    }



    /**
     * 获取每日行程中的最后一个目的地
     * @param scheduleDetail
     * @return
     */
    private String getLastDest(ScheduleDetailEntity scheduleDetail) {
        String city = "";
        if ( !StringUtils.isEmpty(scheduleDetail.getDestination4()) ) {
            city = scheduleDetail.getDestination4();
        } else if ( !StringUtils.isEmpty(scheduleDetail.getDestination3()) ) {
            city = scheduleDetail.getDestination3();
        } else if ( !StringUtils.isEmpty(scheduleDetail.getDestination2()) ) {
            city = scheduleDetail.getDestination2();
        } else {
            city = scheduleDetail.getDestination1();
        }

        return city;
    }

    /**
     * 获取每日行程中的最后一个目的地ID
     * @param scheduleDetail
     * @return 最后目的地的areaID
     */
    private String getLastDestinationId(ScheduleDetailEntity scheduleDetail) {
        String cityAreaId = "";
        if (!StringUtils.isEmpty(scheduleDetail.getDestination4())) {
            cityAreaId = scheduleDetail.getDestinationId4();

        } else if (!StringUtils.isEmpty(scheduleDetail.getDestination3())) {
            cityAreaId = scheduleDetail.getDestinationId3();
        } else if (!StringUtils.isEmpty(scheduleDetail.getDestination2())) {
            cityAreaId = scheduleDetail.getDestinationId2();
        } else {
            cityAreaId = scheduleDetail.getDestinationId1();
        }

        return cityAreaId;
    }


    /**
     * 获取每日行程中的第一个目的地
     * @param scheduleDetail
     * @return 返回行程中第一个目的地的ID
     */
    private String getFirstDest(ScheduleDetailEntity scheduleDetail) {
        String cityAreaId = null;
        if(!StringUtils.isEmpty(scheduleDetail.getDestinationId1())){
            cityAreaId = scheduleDetail.getDestinationId1();
        }
       // String city = scheduleDetail.getDestination1();
        return cityAreaId;
    }


    /**
     * 获取每日行程中的开始地
     * @param scheduleDetail
     * @return
     */
    private String getStartPlaceId(ScheduleDetailEntity scheduleDetail) {
        String cityAreaId = null;
        if(!StringUtils.isEmpty(scheduleDetail.getStartPlaceId())){
            cityAreaId = scheduleDetail.getStartPlaceId();
        }
        return cityAreaId;
    }


    /**
     * 获取某日行程中的交通信息
     *
     * @param teamId
     * @param date
     * @return
     */
    @Override
    public TrafficInfoResponse getTrafficInfo(String teamId, String date) {
        List<ScheduleDetailEntity> list = getScheduleDetails(teamId, date);
        TrafficInfoResponse response = new TrafficInfoResponse();
        if (list.isEmpty()) {
            response.setTraffic("");
        } else {
            if (StringUtils.isEmpty(list.get(0).getTrafficInfo())) {
                response.setTraffic("");
            } else {
                response.setTraffic(list.get(0).getTrafficInfo());
            }
        }
        return response;
    }

    /**
     * 获取目的地及时区ID
     *
     * @param teamId
     * @return
     */
    @Override
    public TimeZoneInfoResponse getDestinationInfo(String teamId, Double lat, Double lng) {
        // 获取当前时区
        TimeZoneInfo currentTimeZone = new TimeZoneInfo();
        if (lat != null && lng != null) {
            logger.info("当前定位的经纬度：" + lat + "," + lng);
            try {
                currentTimeZone.setDestination(TimeZoneUtil.getCityNameWithCoordinate(lat, lng) + "时间");
                TimeZone timeZone = TimeZoneUtil.getTimeZoneWithCoordinate(lat, lng);
                currentTimeZone.setTimeZoneId(timeZone.getID());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        List<ScheduleDetailEntity> list = getScheduleDetails(teamId, null);

        List<TimeZoneInfo> lastZoneInfos = new ArrayList<>();
        if (!list.isEmpty()) {
            // 获取时区列表
            List<String> destList = getDestinationIdList(list);
            lastZoneInfos = getTimeZoneInfos(destList);
        }

        TimeZoneInfoResponse response = new TimeZoneInfoResponse();
        //response.setCurrentTimeZone(currentTimeZone);
        response.setTimeZoneInfoList(lastZoneInfos);
        return response;
    }

    /**
     * 获取去重后的目的地列表
     * @param scheduleDetailEntities
     * @return
     */
    private List<String> getDestinationIdList(List<ScheduleDetailEntity> scheduleDetailEntities) {
        List<String> destinationIdList = new ArrayList<>();
        for (ScheduleDetailEntity scheduleDetailEntity : scheduleDetailEntities) {
            // 目的地1
            if (!StringUtils.isEmpty(scheduleDetailEntity.getDestination1())) {
                destinationIdList.add(scheduleDetailEntity.getDestinationId1());
            }
            // 目的地2
            if (!StringUtils.isEmpty(scheduleDetailEntity.getDestination2())) {
                destinationIdList.add(scheduleDetailEntity.getDestinationId2());
            }
            // 目的地3
            if (!StringUtils.isEmpty(scheduleDetailEntity.getDestination3())) {
                destinationIdList.add(scheduleDetailEntity.getDestinationId3());
            }
            // 目的地4
            if (!StringUtils.isEmpty(scheduleDetailEntity.getDestination4())) {
                destinationIdList.add(scheduleDetailEntity.getDestinationId4());
            }
        }

        return new ArrayList<>(new HashSet(destinationIdList));
    }

    /**
     * 根据行程的目的地列表，获取时区列表
     *
     * @param destIdList
     * @return
     */
    private List<TimeZoneInfo> getTimeZoneInfos(List<String> destIdList) {
        List<TimeZoneInfo> zoneInfos = new ArrayList<>();
        for (String destId : destIdList) {
            TimeZoneInfo timeZoneInfo = regionService.getTimeZoneInfoByRegionId(destId);
            zoneInfos.add(timeZoneInfo);
        }

        return zoneInfos;
    }

    public List<ScheduleDetailEntity> getScheduleDetails(String txGroupId, String date) {
        List<ScheduleDetailEntity> list = scheduleDetailDao.selectByTeamId(null, txGroupId, date);
        if (CollectionUtils.isEmpty(list)) {
            list = Collections.emptyList();
        }
        return list;
    }

    public List<ScheduleDetailEntity> getScheduleDetails(Long teamId, String date) {
        List<ScheduleDetailEntity> list = scheduleDetailDao.selectByTeamId(teamId, null, date);
        if (CollectionUtils.isEmpty(list)) {
            list = Collections.emptyList();
        }
        return list;
    }

    public ScheduleDetailResponse getCommentableDetail(String teamId, Long userID) {
        ScheduleDetailResponse response = new ScheduleDetailResponse();
        try {
            ScheduleDetailEntity scheduleDetailEntity = getCommentableScheduleDetail(teamId,userID);
            response.setScheduleDetailEntity(scheduleDetailEntity);
        } catch (Exception e) {
            e.printStackTrace();
            response = ScheduleDetailResponse.createNotFoundResponse(e.getMessage());
        }

        return response;
    }


    private ScheduleDetailEntity getCommentableScheduleDetail(String teamId, Long userID) {
        List<ScheduleDetailEntity> scheduleDetailList = this.getScheduleDetails(teamId, null);
        ScheduleDetailEntity scheduleDetail = new ScheduleDetailEntity();
        if (CollectionUtils.isEmpty(scheduleDetailList)) {
            return scheduleDetail;
        }

        for (ScheduleDetailEntity scheduleDetailEntity: scheduleDetailList
             ) {
            scheduleDetailEntity = getScheduleDetailTimeZone(scheduleDetailEntity);

            Date scheduleDate = scheduleDetailEntity.getScheduleDate();
            TimeZone destinationTimeZone = TimeZone.getTimeZone(scheduleDetailEntity.getDestinationTimeZone());

            // 行程目的地当地时间
            Date timeZoneDate = TimeZoneUtil.getTimeZoneDate(new Date(), destinationTimeZone);

            // 根据行程目的地当地时间；判断是否当前可点评行程
            // 行程当天12:00至次日12:00可以点评
            DateTime timeZoneDateTime = new DateTime(timeZoneDate);
            DateTime scheduleDateTime = new DateTime(scheduleDate);
            DateTime firstDate = scheduleDateTime.plusHours(12);
            DateTime secondDate = scheduleDateTime.plusHours(36);
            if (firstDate.isBefore(timeZoneDateTime)){
                if (userID != null){
                    // 获取行程明细点评
                    ScheduleDetailCommentEntity scheduleDetailCommentEntity = scheduleDetailCommentDao.selectComment(scheduleDetailEntity.getId(),userID);
                    if (scheduleDetailCommentEntity == null){
                        scheduleDetail = scheduleDetailEntity;
                        break;
                    }
                }
            }
        }

        if (scheduleDetail != null && scheduleDetail.getId() != null){
            Long scheduleDetailId = scheduleDetail.getId();
            List<ScenicEntity> scenicList = scenicService.getScenicListByScheduleDetailId(scheduleDetailId);
            scheduleDetail.setScenicList(scenicList);
        }

        return scheduleDetail;
    }

    /**
     * 根据出行时间查询行程详细和对应的用户id
     * @return
     */
    @Override
    public List<ScheduleDetailIdUserIdEntity> selectScheduleDetailByScheduleDate() {
        //获取昨天时间
        Calendar calendar = Calendar.getInstance();
        //calendar.add(Calendar.DATE,-1);
        //Date yesterday = calendar.getTime();
        calendar.add(Calendar.DATE, -2);
        Date theDayBefore = calendar.getTime();
        return scheduleDetailDao.selectScheduleDetailByScheduleDate(theDayBefore);
    }

    /*
       行程第几天
     */
    @Override
    public List<ScheduleDetailEntity> selectdayNum(ScheduleDetailRequest request) {
        List<ScheduleDetailEntity> scheduleDetailEntityList = scheduleDetailDao.selectdayNum(request);
        return scheduleDetailEntityList;
    }

    @Override
    public List<ScheduleDetailEntityVo> selectCountry(ScheduleDetailRequest request) {
        List<ScheduleDetailEntityVo> scheduleDetailList = scheduleDetailDao.selectCountry(request);
        return scheduleDetailList;
    }

    @Override
    public int selectCountryCount(ScheduleDetailRequest request) {

        return scheduleDetailDao.selectCountryCount(request);
    }

    @Override
    public ScheduleResponse updateDestinationId(ScheduleDetailEntity scheduleDetail) {
        scheduleDetail.setUpdateTime(new Date());
        scheduleDetailDao.updateDestinationId(scheduleDetail);
        return new ScheduleResponse();
    }



}
