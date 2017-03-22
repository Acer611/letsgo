package com.umessage.letsgo.domain.vo.journey.response;

import com.umessage.letsgo.core.errorhandler.constant.ErrorConstant;
import com.umessage.letsgo.domain.po.journey.ScheduleDescEntity;
import com.umessage.letsgo.domain.po.journey.ScheduleDetailEntity;
import com.umessage.letsgo.domain.po.journey.ScheduleEntity;
import com.umessage.letsgo.domain.po.journey.SurveyEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.response.vo.*;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoYidong on 2016/5/31.
 *
 */
public class ScheduleResponse extends CommonResponse {

    @ApiModelProperty(value="行程概述实体")
    private ScheduleEntity scheduleEntity;
    @ApiModelProperty(value = "每日行程实体")
    private ScheduleDetailEntity scheduleDetailEntity;
    @ApiModelProperty(value = "其他说明实体")
    private ScheduleDescEntity scheduleDescEntity;

    @ApiModelProperty(value="费用信息")
    private CostInfoVo costInfoVo;
    @ApiModelProperty(value="重要提示")
    private ImportantInfoVo importantInfoVo;
    @ApiModelProperty(value="风险须知及安全提示")
    private SafeInfoVo safeInfoVo;
    @ApiModelProperty(value="购物场所")
    private ShopPlaceVo shopPlaceVo;
    @ApiModelProperty(value="地接社信息")
    private TravelAgencyInfoVo travelAgencyInfoVo;
    @ApiModelProperty(value = "是否可编辑")
    private String isEditable;
    @ApiModelProperty(value = "teamId")
    private String teamId;
    @ApiModelProperty(value = "destinationId1")
    private String destinationId1;
    @ApiModelProperty(value = "destinationId2")
    private String destinationId2;
    @ApiModelProperty(value = "destinationId3")
    private String destinationId3;

    @ApiModelProperty(value = "问卷调查")
    private SurveyEntity surveyEntity;

    @ApiModelProperty(value = "游客列表")
    private List<MemberEntity> touristList;

    @ApiModelProperty(value = "行程ID")
    private Long scheduleId;

    @ApiModelProperty(value = "是否微信")
    private String isWeChat;

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public List<MemberEntity> getTouristList() {
        return touristList;
    }

    public void setTouristList(List<MemberEntity> touristList) {
        this.touristList = touristList;
    }

    public String getDestinationId1() {
        return destinationId1;
    }

    public void setDestinationId1(String destinationId1) {
        this.destinationId1 = destinationId1;
    }

    public String getDestinationId2() {
        return destinationId2;
    }

    public void setDestinationId2(String destinationId2) {
        this.destinationId2 = destinationId2;
    }

    public String getDestinationId3() {
        return destinationId3;
    }

    public void setDestinationId3(String destinationId3) {
        this.destinationId3 = destinationId3;
    }

    public String getDestinationId4() {
        return destinationId4;
    }

    public void setDestinationId4(String destinationId4) {
        this.destinationId4 = destinationId4;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    @ApiModelProperty(value = "destinationId4")
    private String destinationId4;
    @ApiModelProperty(value = "hotelId")
    private String hotelId;
    @ApiModelProperty(value = "浏览器各链接的签名")
    private Map<String,SignVo> signMap;

    public ScheduleEntity getScheduleEntity() {
        return scheduleEntity;
    }

    public void setScheduleEntity(ScheduleEntity scheduleEntity) {
        this.scheduleEntity = scheduleEntity;
    }

    public static ScheduleResponse createNotFoundResponse(String retMsg){
        class NotFoundResponse extends ScheduleResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.NOT_FOUND;
            }
        }

        ScheduleResponse response = new NotFoundResponse();
        response.setRetMsg(retMsg);
        return response;
    }

    public static ScheduleResponse createUserNotLoginResponse(){
        class UserNotLoginResponse extends ScheduleResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.USER_NOT_LOGIN;
            }

            @Override
            public String getRetMsg() {
                return "用户未登录或登录信息过期";
            }
        }

        return new UserNotLoginResponse();
    }

    public static ScheduleResponse createEmptyParameterResponse(String retMsg){
        class EmptyParameterResponse extends ScheduleResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.EMPTY_PARAMETER;
            }
        }

        ScheduleResponse response = new EmptyParameterResponse();
        response.setRetMsg(retMsg);
        return response;
    }

    public static ScheduleResponse createInvalidParameterResponse(String retMsg){
        class EmptyParameterResponse extends ScheduleResponse {

            @Override
            public Integer getRetCode() {
                return ErrorConstant.INVALID_PARAMETER;
            }
        }

        ScheduleResponse response = new EmptyParameterResponse();
        response.setRetMsg(retMsg);
        return response;
    }

    public ScheduleDetailEntity getScheduleDetailEntity() {
        return scheduleDetailEntity;
    }

    public void setScheduleDetailEntity(ScheduleDetailEntity scheduleDetailEntity) {
        this.scheduleDetailEntity = scheduleDetailEntity;
    }

    public CostInfoVo getCostInfoVo() {
        return costInfoVo;
    }

    public void setCostInfoVo(CostInfoVo costInfoVo) {
        this.costInfoVo = costInfoVo;
    }

    public ImportantInfoVo getImportantInfoVo() {
        return importantInfoVo;
    }

    public void setImportantInfoVo(ImportantInfoVo importantInfoVo) {
        this.importantInfoVo = importantInfoVo;
    }

    public SafeInfoVo getSafeInfoVo() {
        return safeInfoVo;
    }

    public void setSafeInfoVo(SafeInfoVo safeInfoVo) {
        this.safeInfoVo = safeInfoVo;
    }

    public ShopPlaceVo getShopPlaceVo() {
        return shopPlaceVo;
    }

    public void setShopPlaceVo(ShopPlaceVo shopPlaceVo) {
        this.shopPlaceVo = shopPlaceVo;
    }

    public TravelAgencyInfoVo getTravelAgencyInfoVo() {
        return travelAgencyInfoVo;
    }

    public void setTravelAgencyInfoVo(TravelAgencyInfoVo travelAgencyInfoVo) {
        this.travelAgencyInfoVo = travelAgencyInfoVo;
    }

    public String getIsEditable() {
        return isEditable;
    }

    public void setIsEditable(String isEditable) {
        this.isEditable = isEditable;
    }

    public Map<String, SignVo> getSignMap() {
        return signMap;
    }

    public void setSignMap(Map<String, SignVo> signMap) {
        this.signMap = signMap;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public SurveyEntity getSurveyEntity() {
        return surveyEntity;
    }

    public void setSurveyEntity(SurveyEntity surveyEntity) {
        this.surveyEntity = surveyEntity;
    }

    public String getIsWeChat() {
        return isWeChat;
    }

    public void setIsWeChat(String isWeChat) {
        this.isWeChat = isWeChat;
    }

    public ScheduleDescEntity getScheduleDescEntity() {
        return scheduleDescEntity;
    }

    public void setScheduleDescEntity(ScheduleDescEntity scheduleDescEntity) {
        this.scheduleDescEntity = scheduleDescEntity;
    }

    @Override
    public String toString() {
        return "ScheduleResponse{" +
                "scheduleEntity=" + scheduleEntity +
                ", scheduleDetailEntity=" + scheduleDetailEntity +
                ", costInfoVo=" + costInfoVo +
                ", importantInfoVo=" + importantInfoVo +
                ", safeInfoVo=" + safeInfoVo +
                ", shopPlaceVo=" + shopPlaceVo +
                ", travelAgencyInfoVo=" + travelAgencyInfoVo +
                ", isEditable=" + isEditable +
                ", signMap=" + signMap +
                '}';
    }
}
