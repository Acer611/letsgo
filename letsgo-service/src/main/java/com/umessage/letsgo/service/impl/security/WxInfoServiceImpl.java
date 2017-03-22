package com.umessage.letsgo.service.impl.security;

import com.umessage.letsgo.dao.security.WxInfoDao;
import com.umessage.letsgo.domain.po.security.WxInfoEntity;
import com.umessage.letsgo.service.api.security.IWxInfoService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zengguoqing on 2016/11/22.
 */

@Service
public class WxInfoServiceImpl implements IWxInfoService {

    private static Logger logger = Logger.getLogger(WxInfoServiceImpl.class);
    @Resource
    private WxInfoDao wxDao;


    @Override
    public boolean checkUserByUnionid(String unionId) {
        WxInfoEntity wxInfoEntity = wxDao.selectUserInfoByUnionID(unionId);
        if (null!=wxInfoEntity){
            return true;
        }
        return false;
    }
    @Override
    public WxInfoEntity findUserByUnionid(String unionId){

        return wxDao.selectUserInfoByUnionID(unionId);
    }

    @Override
    public int insertUserWxInfo(WxMpUser wxMpUser,String appID) {
        WxInfoEntity wxInfoEntity = new WxInfoEntity();
        wxInfoEntity.setOpenid(wxMpUser.getOpenId());
        wxInfoEntity.setNickname(wxMpUser.getNickname());
        wxInfoEntity.setCountry(wxMpUser.getCountry());
        wxInfoEntity.setProvince(wxMpUser.getProvince());
        wxInfoEntity.setCity(wxMpUser.getCity());
        //修改微信信息表中sex 字段的类型为string 和微信保持一致
        wxInfoEntity.setSex(wxMpUser.getSex());
        wxInfoEntity.setUnionID(wxMpUser.getUnionId());
        wxInfoEntity.setHeadImgUrl(wxMpUser.getHeadImgUrl());
        wxInfoEntity.setBinding(1);
        wxInfoEntity.setStatus(0);
        wxInfoEntity.setAppId(appID);

        return wxDao.insert(wxInfoEntity);
    }

    public int updateUserWxInfo(WxMpUser wxMpUser,String appID) {
        WxInfoEntity wxInfoEntity = new WxInfoEntity();
        wxInfoEntity.setOpenid(wxMpUser.getOpenId());
        wxInfoEntity.setNickname(wxMpUser.getNickname());
        wxInfoEntity.setCountry(wxMpUser.getCountry());
        wxInfoEntity.setProvince(wxMpUser.getProvince());
        wxInfoEntity.setCity(wxMpUser.getCity());
        wxInfoEntity.setSex(wxMpUser.getSex());
        wxInfoEntity.setUnionID(wxMpUser.getUnionId());
        wxInfoEntity.setHeadImgUrl(wxMpUser.getHeadImgUrl());
        wxInfoEntity.setBinding(0);
        wxInfoEntity.setStatus(0);
        wxInfoEntity.setAppId(appID);

        return wxDao.update(wxInfoEntity);
    }

    @Override
    public void subscribe(String unionId, WxMpUser wxMpUser, boolean flag,String appID) {

        if(!flag){
            String openId = wxMpUser.getOpenId();
            //检查是否是重新关注用户(已通过代码核查openID  发现重新关注openid 不会改变)
            boolean isReSubscribe = this.checkUserByOpenID(openId);
            if(isReSubscribe){
                // 逻辑1 删除原纪录重新插入新纪录
                //wxService.deleteUserByOpeID(openId) ;
                //wxService.insertUserWxInfo(wxMpUser,appID);

                //逻辑2 根据openID 跟新记录
                logger.info("重新关注用户修改微信信息表操作");
                this.updateUserWxInfo(wxMpUser,appID);
            }else{
                //插入微信信息到微信信息表
                logger.info("新关注用户插入微信信息表操作");
                this.insertUserWxInfo(wxMpUser,appID);
            }
        }
    }

    @Override
    public WxInfoEntity selectUserInfoByOpenID(String openId) {
        WxInfoEntity wxInfoEntity = wxDao.selectUserInfoByOpenID(openId);
        if(null != wxInfoEntity){
            return wxInfoEntity;
        }
        return null;
    }


    @Override
    public void unSubscribe(String openid) {
        wxDao.unSubscribeByOpenID(openid);
    }

    @Override
    public boolean checkUserByOpenID(String openid) {
        WxInfoEntity wxInfoEntity = wxDao.selectUserInfoByOpenID(openid);
        if (null!=wxInfoEntity){
            return true;
        }
        return false;
    }

    @Override
    public void deleteUserByOpeID(String openid) {
        wxDao.delete(openid);
    }



    /**
     * 根据用户ID查询微信信息
     * @param userId,openid
     * @return
     */
    @Override
    public WxInfoEntity selectWxInfoByUserId(Long userId, String appId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("appId",appId);
        return wxDao.selectWxInfoByUserId(map);
    }

    @Override
    public WxInfoEntity selectUserByUnionIDAndAppID(String unionId, String appID) {
        Map<String, Object> map = new HashMap<>();
        map.put("unionid",unionId);
        map.put("appId",appID);
        return wxDao.selectUserByUnionIDAndAppID(map);
    }

    @Override
    public void modifyWxUserInfo(WxMpUser wxMpUser,String appID,long id) {
        WxInfoEntity wxInfoEntity = new WxInfoEntity();
        wxInfoEntity.setAppId(appID);
        wxInfoEntity.setCity(wxMpUser.getCity());
        wxInfoEntity.setCountry(wxMpUser.getCountry());
        wxInfoEntity.setHeadImgUrl(wxMpUser.getHeadImgUrl());
        wxInfoEntity.setNickname(wxMpUser.getNickname());
        wxInfoEntity.setProvince(wxMpUser.getProvince());
        wxInfoEntity.setBinding(0);
        wxInfoEntity.setOpenid(wxMpUser.getOpenId());
        wxInfoEntity.setUnionID(wxMpUser.getUnionId());
        wxInfoEntity.setSex(wxMpUser.getSex());
        wxInfoEntity.setStatus(0);
        wxInfoEntity.setId(id);
        wxDao.update(wxInfoEntity);
    }

    @Override
    public WxInfoEntity selectWeixinInfoByOpenIDAndUnionID(String unionId,String openId) {
        WxInfoEntity wxInfoEntity = new WxInfoEntity();
        wxInfoEntity.setUnionID(unionId);
        wxInfoEntity.setOpenid(openId);
        return wxDao.selectWeixinInfoByOpenIDAndUnionID(wxInfoEntity);
    }
}
