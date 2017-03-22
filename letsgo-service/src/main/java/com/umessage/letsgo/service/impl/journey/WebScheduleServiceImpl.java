package com.umessage.letsgo.service.impl.journey;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.core.exception.BusinessException;
import com.umessage.letsgo.core.extensions.springsecurity.utils.SignUtil;
import com.umessage.letsgo.dao.journey.AlbumScheduleDao;
import com.umessage.letsgo.dao.journey.ScheduleDao;
import com.umessage.letsgo.domain.po.journey.*;
import com.umessage.letsgo.domain.po.team.LeaderEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.vo.journey.request.ScheduleShowRequest;
import com.umessage.letsgo.domain.vo.journey.response.ScheduleResponse;
import com.umessage.letsgo.domain.vo.journey.response.vo.*;
import com.umessage.letsgo.service.api.journey.*;
import com.umessage.letsgo.service.api.system.IRegionService;
import com.umessage.letsgo.service.api.system.IYahooWeatherService;
import com.umessage.letsgo.service.api.team.IMemberService;
import com.umessage.letsgo.service.common.constant.Constant;
import org.dozer.Mapper;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by ZhaoYidong on 2016/6/8.
 */
@Service
public class WebScheduleServiceImpl implements IWebScheduleService {

    @Resource
    private IScheduleDetailsService scheduleDetailsService;
    @Resource
    private ClientDetailsService clientDetailsService;
    @Resource
    private IPromptInfoService promptService;
    @Resource
    private Mapper dozerBeanMapper;
    @Resource
    private ScheduleDao scheduleDao;
    @Resource
    private IYahooWeatherService yahooWeatherService;
    @Resource
    private IDestinationService destinationService;
    @Resource
    private IMemberService memberService;
    @Resource
    private IScheduleService scheduleService;
    @Resource
    private IRegionService regionService;
    @Resource
    private AlbumScheduleDao albumScheduleDao;
    @Resource
    private IScheduleDescService scheduleDescService;
    @Resource
    private IAlbumScheduleService albumScheduleService;

    @Resource
    private IScheduleDetailScenicService scheduleDetailScenicService;

    @Override
    public ScheduleResponse getScheduleShowByTeamId(String teamId, Long scheduleDetaildId,Long scheduleId, Long descId) {
        ScheduleEntity scheduleEntity =null;
        if (teamId != null) {
            scheduleEntity = getSchedule(teamId);

            if (scheduleEntity != null){
                List<ScheduleDetailEntity> detailList = scheduleEntity.getScheduleDetailList();
                if (detailList != null){

                    for (ScheduleDetailEntity scheduleDetailEntity : detailList) {
                        List<ScheduleDetailScenicEntity> scheduleDetailScenicList = scheduleDetailEntity.getScheduleDetailScenicEntitys();
                        if (scheduleDetailScenicList != null){
                            for (ScheduleDetailScenicEntity scheduleDetailScenicEntity:scheduleDetailScenicList) {
                                AlbumScheduleEntity request =  new AlbumScheduleEntity();
                                request.setScheduleDetailId(scheduleDetailScenicEntity.getScheduleDetailId());
                                request.setObjectId(scheduleDetailScenicEntity.getId());
                                request.setType(1);
                                List<AlbumScheduleEntity> albumScheduleList = albumScheduleDao.getAlbumSchedule(request);
                                List<String> imageList = new ArrayList<>();
                                if(null!=albumScheduleList){
                                    for (AlbumScheduleEntity albumSchedule:albumScheduleList) {
                                        imageList.add("\""+albumSchedule.getPhotoUrl()+"\"");
                                    }
                                }
                                scheduleDetailScenicEntity.setAlbumScheduleEntities(albumScheduleList);
                                scheduleDetailScenicEntity.setImageList(imageList);
                            }
                        }

                        HotelScheduleEntity hotelSchedule = scheduleDetailEntity.getHotelSchedule();
                        if (hotelSchedule != null){
                            List<String> photoUrls = new ArrayList<>();
                            List<AlbumScheduleEntity> albumSchedules = albumScheduleService.getAlbumSchedule(hotelSchedule.getScheduleDetailId(), hotelSchedule.getId(), 2);
                            if (albumSchedules != null){
                                for (AlbumScheduleEntity albumScheduleEntity:albumSchedules) {
                                    photoUrls.add("\""+albumScheduleEntity.getPhotoUrl()+"\"");
                                }
                                hotelSchedule.setAlbumScheduleList(albumSchedules);
                            }
                            hotelSchedule.setPhotoUrls(photoUrls);
                        }

                        //增加每日行程对应的自费项目及相册
                        List<OwnExpenseScheduleEntity> ownExpenseScheduleList = scheduleDetailEntity.getOwnExpenseScheduleList();
                        if (ownExpenseScheduleList != null){
                            for (OwnExpenseScheduleEntity ownExpenseSchedule:ownExpenseScheduleList) {
                                AlbumScheduleEntity albumScheduleEntity =  new AlbumScheduleEntity();
                                albumScheduleEntity.setScheduleDetailId(ownExpenseSchedule.getScheduleDetailId());
                                albumScheduleEntity.setObjectId(ownExpenseSchedule.getId());
                                albumScheduleEntity.setType(3);
                                List<AlbumScheduleEntity> albumScheduleList = albumScheduleDao.getAlbumSchedule(albumScheduleEntity);
                                if (albumScheduleList != null){
                                    List<String> images = new ArrayList<>();
                                    for (AlbumScheduleEntity albumSchedule:albumScheduleList) {
                                        images.add("\""+albumSchedule.getPhotoUrl()+"\"");
                                    }
                                    ownExpenseSchedule.setPhotoUrls(images);
                                    ownExpenseSchedule.setAlbumScheduleList(albumScheduleList);
                                }
                            }
                        }
                    }

                }
            }
        }
        if(scheduleEntity == null && scheduleId!=null){
            scheduleEntity = scheduleService.getSchedule(scheduleId);
        }
        if (scheduleEntity == null) {
            throw new BusinessException(ErrorConstant.NOT_FOUND, "没有发现行程");
        }


        ScheduleResponse response = new ScheduleResponse();
        response = getpromptInfo(response, scheduleEntity.getId());
        if (scheduleDetaildId != null) {
            List<ScheduleDetailEntity> detailList = scheduleEntity.getScheduleDetailList();
            if (detailList != null) {
                for (ScheduleDetailEntity detailEntity : detailList) {
                    if (scheduleDetaildId.equals(detailEntity.getId())) {
                        // 获取行程对应的天气
                        detailEntity = scheduleDetailsService.getWeather(detailEntity);

                        response.setScheduleDetailEntity(detailEntity);
                        break;
                    }
                }
            }
        }

        if (descId != null){
            List<ScheduleDescEntity> scheduleDescEntities = scheduleEntity.getScheduleDescEntities();
            if (scheduleDescEntities != null){
                for (ScheduleDescEntity scheduleDescEntity:scheduleDescEntities) {
                    if (descId == scheduleDescEntity.getId()){
                        response.setScheduleDescEntity(scheduleDescEntity);
                        break;
                    }
                }
            }
        }

        //增加行程对应的自费项目及相册
        List<OwnExpenseScheduleEntity> ownExpenseScheduleList = scheduleEntity.getOwnExpenseSchedules();
        if (ownExpenseScheduleList != null){
            for (OwnExpenseScheduleEntity ownExpenseSchedule:ownExpenseScheduleList) {
                List<AlbumScheduleEntity> albumScheduleList = albumScheduleService.getAlbumScheduleByScheduleId(ownExpenseSchedule.getScheduleId(), ownExpenseSchedule.getId(), 3);
                List<String> images = new ArrayList<>();
                if (albumScheduleList != null){
                    for (AlbumScheduleEntity albumSchedule:albumScheduleList) {
                        images.add("\""+albumSchedule.getPhotoUrl()+"\"");
                    }
                    ownExpenseSchedule.setAlbumScheduleList(albumScheduleList);
                }
                ownExpenseSchedule.setPhotoUrls(images);
            }
        }
        response.setScheduleEntity(scheduleEntity);
        return response;
    }

    private ScheduleEntity getSchedule(String teamId) {
        //ScheduleEntity s = scheduleDao.selectScheduleInfo(teamId);
        ScheduleEntity scheduleEntity = scheduleDao.selectByTeamId(teamId);
        System.out.println(scheduleEntity);
        return scheduleEntity;
    }

    /**
     * 获取提示信息
     */
    private ScheduleResponse getpromptInfo(ScheduleResponse response, Long scheduleId) {
        PromptInfoEntity promptInfoEntity = promptService.getByScheduleId(scheduleId);
        if (promptInfoEntity != null) {
            CostInfoVo costInfoVo = dozerBeanMapper.map(promptInfoEntity, CostInfoVo.class);
            ImportantInfoVo importantInfoVo = dozerBeanMapper.map(promptInfoEntity, ImportantInfoVo.class);
            SafeInfoVo safeInfoVo = dozerBeanMapper.map(promptInfoEntity, SafeInfoVo.class);
            ShopPlaceVo shopPlaceVo = dozerBeanMapper.map(promptInfoEntity, ShopPlaceVo.class);
            TravelAgencyInfoVo travelAgencyInfoVo = dozerBeanMapper.map(promptInfoEntity, TravelAgencyInfoVo.class);

            response.setCostInfoVo(costInfoVo);
            response.setImportantInfoVo(importantInfoVo);
            response.setSafeInfoVo(safeInfoVo);
            response.setShopPlaceVo(shopPlaceVo);
            response.setTravelAgencyInfoVo(travelAgencyInfoVo);
        }
        return response;
    }

    @Override
    public ScheduleResponse getScheduleSigns(ScheduleShowRequest showRequest, ScheduleResponse response, SignVo signVo) {
        if (response.getScheduleEntity() == null) return response;

        Map<String, SignVo> previewSignMap = getPreviewAndEditSign(showRequest.getTeamId(), response.getScheduleEntity(), signVo,showRequest.getScheduleId());
        Map<String, SignVo> scheduleAndPromptSignMap = getScheduleAndPromptSign(showRequest.getTeamId(), signVo,showRequest.getScheduleId());
        previewSignMap.putAll(scheduleAndPromptSignMap);

        SignVo guidesSign = getGuidesSign(showRequest.getTeamId(), signVo,showRequest.getScheduleId());
        previewSignMap.put("guidesSign", guidesSign);

        SignVo updateDetailSign = getUpdateScheduleDetailSign(signVo,showRequest.getScheduleId());
        previewSignMap.put("updateDetailSign", updateDetailSign);

        Map<String, SignVo> scheduleDescSign = getScheduleDescSign(showRequest.getTeamId(),"scheduleDesc",response.getScheduleEntity(), signVo,showRequest.getScheduleId());
        previewSignMap.putAll(scheduleDescSign);

        response.setSignMap(previewSignMap);
        return response;
    }

    private Map<String, SignVo> getPreviewAndEditSign(String teamId, ScheduleEntity scheduleEntity, SignVo signVo,Long scheduleId) {
        Map<String, SignVo> previewSignMap = getPreviewSign(teamId, "preview", scheduleEntity, signVo,scheduleId);
        Map<String, SignVo> editSignMap = getPreviewSign(teamId, "edit", scheduleEntity, signVo,scheduleId);
        previewSignMap.putAll(editSignMap);
        return previewSignMap;
    }


    /**
     * 行程其他说明的签名
     * @param scheduleEntity
     * @param flag
     * @param signVo
     * @return
     */
    private Map<String, SignVo> getScheduleDescSign(String teamId, String flag, ScheduleEntity scheduleEntity, SignVo signVo, Long scheduleId) {
        List<ScheduleDescEntity> scheduleDescEntities = scheduleEntity.getScheduleDescEntities();
        Map<String, SignVo> signMap = new HashMap<String, SignVo>();
        if (scheduleDescEntities != null){
            for (ScheduleDescEntity scheduleDescEntity:scheduleDescEntities) {
                Map<String, String> map = new HashMap<String, String>();
                if (teamId != null) {
                    map.put("teamId", teamId);
                }
                if (scheduleId != null) {
                    map.put("scheduleId", scheduleId.toString());
                }
                if (scheduleDescEntity.getId() != null) {
                    map.put("descId", String.valueOf(scheduleDescEntity.getId()));
                }
                if (flag != null) {
                    map.put("flag", flag);
                }
                map.put(Constant.CLIENT_ID, signVo.getClient_id());
                map.put(Constant.CLIENT_VER, signVo.getVer());
                map.put(Constant.TIMESTAMP, String.valueOf(new Date().getTime()));
                map.put(Constant.ACCESS_TOKEN, signVo.getAccess_token());

                Map<String, String> appSignMap = SignUtil.sign(map, clientDetailsService.loadClientByClientId(signVo.getClient_id()).getClientSecret());
                map.put(Constant.CLIENT_SIGN, appSignMap.get("appSign"));

                SignVo vo = dozerBeanMapper.map(map, SignVo.class);
                signMap.put(String.valueOf(scheduleDescEntity.getId() + flag), vo);
            }
        }
        return signMap;
    }



    /**
     * 获取每日行程查看签名
     */
    private Map<String, SignVo> getPreviewSign(String teamId, String flag, ScheduleEntity scheduleEntity, SignVo signVo,Long scheduleId) {
        List<ScheduleDetailEntity> scheduleDetailList = scheduleEntity.getScheduleDetailList();
        Map<String, SignVo> signMap = new HashMap<String, SignVo>();
        for (ScheduleDetailEntity detail : scheduleDetailList) {
            Map<String, String> map = new HashMap<String, String>();
            if (teamId != null) {
                map.put("teamId", teamId);
            }
            if (scheduleId != null) {
                map.put("scheduleId", scheduleId.toString());
            }

            map.put("scheduleDetaildId", String.valueOf(detail.getId()));
            map.put("flag", flag);
            map.put(Constant.CLIENT_ID, signVo.getClient_id());
            map.put(Constant.CLIENT_VER, signVo.getVer());
            map.put(Constant.TIMESTAMP, String.valueOf(new Date().getTime()));
            map.put(Constant.ACCESS_TOKEN, signVo.getAccess_token());

            Map<String, String> appSignMap = SignUtil.sign(map, clientDetailsService.loadClientByClientId(signVo.getClient_id()).getClientSecret());
            map.put(Constant.CLIENT_SIGN, appSignMap.get("appSign"));

            SignVo vo = dozerBeanMapper.map(map, SignVo.class);
            signMap.put(String.valueOf(detail.getId() + flag), vo);
        }

        return signMap;
    }

    //获取导游请求的签名
    private SignVo getGuidesSign(String teamId, SignVo signVo,Long scheduleId) {
        return getScheduleSign(teamId, null, signVo,scheduleId);
    }

    //获取更新每日行程请求的签名
    private SignVo getUpdateScheduleDetailSign(SignVo signVo,Long scheduleId) {
        return getScheduleSign(null, null, signVo,scheduleId);
    }

    /**
     * 获取行程信息和提示信息签名
     */
    private Map<String, SignVo> getScheduleAndPromptSign(String teamId, SignVo signVo,Long scheduleId) {
        Map<String, SignVo> signMap = new HashMap<String, SignVo>();
        SignVo introduceSign = getScheduleSign(teamId, "introduce", signVo,scheduleId);
        signMap.put("introduce", introduceSign);

        SignVo overViewSign = getScheduleSign(teamId, "overView", signVo,scheduleId);
        signMap.put("overView", overViewSign);

        SignVo costSign = getScheduleSign(teamId, "cost", signVo,scheduleId);
        signMap.put("cost", costSign);

        SignVo shopSign = getScheduleSign(teamId, "shop", signVo,scheduleId);
        signMap.put("shop", shopSign);

        SignVo promptSign = getScheduleSign(teamId, "prompt", signVo,scheduleId);
        signMap.put("prompt", promptSign);

        SignVo travelAgencySign = getScheduleSign(teamId, "travelAgency", signVo,scheduleId);
        signMap.put("travelAgency", travelAgencySign);

        SignVo surveySign = getScheduleSign(teamId, "survey", signVo,scheduleId);
        signMap.put("survey", surveySign);

        SignVo touristSign = getScheduleSign(teamId, "tourist", signVo,scheduleId);
        signMap.put("tourist", touristSign);

        return signMap;
    }

    /**
     * 计算行程概述和提示信息签名
     */
    private SignVo getScheduleSign(String teamId, String flag, SignVo signVo,Long scheduleId) {
        Map<String, String> map = new HashMap<String, String>();
        if (teamId != null) {
            map.put("teamId", teamId);
        }
        if (flag != null) {
            map.put("flag", flag);
        }
        if (scheduleId != null) {
            map.put("scheduleId", scheduleId.toString());
        }
        map.put(Constant.CLIENT_ID, signVo.getClient_id());
        map.put(Constant.CLIENT_VER, signVo.getVer());
        map.put(Constant.TIMESTAMP, String.valueOf(new Date().getTime()));
        map.put(Constant.ACCESS_TOKEN, signVo.getAccess_token());

        Map<String, String> appSignMap = SignUtil.sign(map, clientDetailsService.loadClientByClientId(signVo.getClient_id()).getClientSecret());
        map.put(Constant.CLIENT_SIGN, appSignMap.get("appSign"));

        return dozerBeanMapper.map(map, SignVo.class);
    }

    @Override
    public List<LeaderEntity> getScheduleGuides(String teamId) {
        ScheduleEntity scheduleEntity = scheduleDao.selectByTeamId(teamId);
        List<LeaderEntity> leaderList = new ArrayList<LeaderEntity>();
        if (scheduleEntity != null) {
            List<ScheduleDetailEntity> scheduleDetailList = scheduleEntity.getScheduleDetailList();
            for (ScheduleDetailEntity detail : scheduleDetailList) {
                if (detail.getLeaderId() != null) {
                    LeaderEntity leaderEntity = new LeaderEntity();
                    leaderEntity.setId(detail.getLeaderId());
                    leaderEntity.setName(detail.getName());
                    leaderEntity.setSex(detail.getSex());
                    leaderEntity.setPhone(detail.getPhone());
                    leaderList.add(leaderEntity);
                }

            }
        }
        return leaderList;
    }
    // 获取游客列表
   public List<MemberEntity> getTouristList(Long scheduleId){
      ScheduleEntity schedule= scheduleService.getSchedule(scheduleId);
       if(schedule.getTeamId()==null){
                   throw new BusinessException(ErrorConstant.EMPTY_PARAMETER, "团队id不能为空");
               }
       return memberService.getTouristList(schedule.getTeamId());
    }
}
