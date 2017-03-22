package com.weixin.handler;

import com.umessage.letsgo.core.utils.HttpClientUtil;
import com.umessage.letsgo.domain.po.activity.LocationEntity;
import com.umessage.letsgo.domain.po.activity.LocationHistoryEntity;
import com.umessage.letsgo.domain.po.security.ThirdPartyAccountEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.vo.activity.response.LocationRespone;
import com.umessage.letsgo.service.api.activity.ILocationHistoryService;
import com.umessage.letsgo.service.api.activity.ILocationService;
import com.umessage.letsgo.service.api.security.IThirdPartyAccountService;
import com.umessage.letsgo.service.api.security.IUserService;
import com.umessage.letsgo.service.common.constant.SmsConstant;
import com.weixin.service.ICoreService;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutImageMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * Created by gaofei on 2017/1/18.
 */

@Component
public class LocationHandler extends AbstractHandler {

    @Autowired
    private ICoreService coreService;

    @Autowired
    private IThirdPartyAccountService thirdPartyAccountService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ILocationService locationService;

    @Autowired
    private ILocationHistoryService locationHistoryService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
        this.logger.info("\n接收到请求消息，内容：【{}】", wxMessage.toString());
        //获取微信的地理位置信息
        String openId = wxMessage.getFromUser();
        //地理位置纬度
        Double latidutude = wxMessage.getLatitude();
        //地理位置经度
        Double longitude = wxMessage.getLongitude();
        //地理位置精度
        Double precision = wxMessage.getPrecision();

        String baseURL = SmsConstant.BASE_MAP_URL;
        String locationValue = longitude  + "," + latidutude;
        String mapKeyUrl = SmsConstant.MAP_KEY_URL;
        String  mapKeyValue = SmsConstant.MAP_KEY;

        //利用高德API 处理坐标问题
        JSONObject json = HttpClientUtil.httpGet(baseURL + locationValue + mapKeyUrl + mapKeyValue);
        String lacations = null;
        if(json.get("status").equals("1")){
            lacations = json.get("locations").toString();
        }

        if(null!=lacations){
            String [] location = lacations.split(",");
            longitude = Double.parseDouble(location[0]);
            latidutude = Double.parseDouble(location[1]);
        }


        //获取用户信息
        ThirdPartyAccountEntity  thirdPartyAccountEntity = thirdPartyAccountService.findUserInfoByOpenID(openId);
        UserEntity userEntity = thirdPartyAccountEntity.getUserEntity();

        if(null != userEntity){
            locationService.createLocation(userEntity.getId(),longitude,latidutude);
        }
        /*//根据用户ID查询当前用户是否已经有位置信息
        LocationEntity locationEntity = locationService.getLocationByUserId(userEntity.getId());

        //组装地理位置信息
        LocationEntity locationInfo = new LocationEntity();
        locationInfo.setUserId(userEntity.getId());
        locationInfo.setLongitude(longitude);
        locationInfo.setLatitude(latidutude);
        locationInfo.setFinishTime(new Date());

        locationService.createLocation()

        boolean flag = false;
        //插入或修改a_location 表中的信息
        if(null!=locationEntity.getId()){
            locationInfo.setId(locationEntity.getId());
            locationInfo.setVersion(locationEntity.getVersion() + 1);
            int num = locationService.updateLocation(locationInfo);
            locationService.createLocation()
            if(num>0){
                flag = true;
            }
        }else{
            locationInfo.setVersion(1L);
            locationService.addLocation(locationInfo);
            if(locationInfo.getId()>0){
                flag = true;
            }
        }

        if(flag){
            //组装历史地理位置信息
            LocationHistoryEntity locationHistoryEntity =  new LocationHistoryEntity();
            locationHistoryEntity.setUserId(userEntity.getId());
            locationHistoryEntity.setLongitude(longitude);
            locationHistoryEntity.setLatitude(latidutude);
            locationHistoryEntity.setFinishTime(new Date());
            locationHistoryEntity.setVersion(1L);
            //向a_location_history表中插入数据
            locationHistoryService.addLocationHistory(locationHistoryEntity);
        }*/

        WxMpXmlOutTextMessage m
                = WxMpXmlOutMessage.TEXT()
                .content("您好！")
                .fromUser(wxMessage.getToUser())
                .toUser(wxMessage.getFromUser())
                .build();
        return m;
    }
}
