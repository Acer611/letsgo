package com.umessage.letsgo.service.api.security;

import com.umessage.letsgo.domain.po.security.WxInfoEntity;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * Created by zengguoqing on 2016/11/22.
 */
public interface IWxInfoService {

    /**
     * 检查当前用户是否是微信公众号已关注用户
     * @param unionId 微信唯一标识
     * @return
     */
    public boolean checkUserByUnionid(String unionId);
    /**
     * 根据unionid 获取用户信息
     * @param unionId 微信唯一标识
     * @return
     */
    public WxInfoEntity findUserByUnionid(String unionId);

    /**
     * 添加微信公众号用户信息
     * @param wxMpUser 微信公众号获取的用户信息实体
     *  @param wxMpUser 微信应用ID
     * @return
     */
    public int insertUserWxInfo(WxMpUser wxMpUser,String appID);


    /**
     * 取消公众号关注
     * @param openid 微信的唯一标识boolean checkUserByOpenID(String );
     */
    public void unSubscribe(String openid);


    /**
     * 根据微信公众号的openID 检查用户是否存在
     * @param openId 用户公众号的OpenID
     * @return
     */
    boolean checkUserByOpenID(String openId);

    /**
     * 根据微信公众号的openID 删除用户信息 （真删除）
     * @param openId
     */
   void deleteUserByOpeID(String openId);

    /**
     * 根据微信公众号的openID修改微信信息
     * @param wxMpUser
     * @return
     */
    int updateUserWxInfo(WxMpUser wxMpUser,String appID);

    /**
     * 关注信公众号
     * @param unionId
     * @param wxMpUser
     * @param flag
     */
    void subscribe(String unionId, WxMpUser wxMpUser, boolean flag,String appID);

    WxInfoEntity selectUserInfoByOpenID(String openId);


    /**
     * 根据用户ID查询微信信息
     * @param userId,openid
     * @return
     */
    public WxInfoEntity selectWxInfoByUserId(Long userId, String appId);

    /**
     * 根据APPID 和unionID 获取用户信息
     * @param unionId
     * @param appID
     * @return
     */
    WxInfoEntity selectUserByUnionIDAndAppID(String unionId, String appID);

    /**
     * 根据unionid 修改微信信息
     * @param wxMpUser
     */
    void modifyWxUserInfo(WxMpUser wxMpUser,String appID,long id);

    /**
     *
     * 根据openid 和unionid 查找用户
     * @param unionId
     * @param openId
     * @return
     */
    WxInfoEntity selectWeixinInfoByOpenIDAndUnionID(String unionId,String openId);
}
