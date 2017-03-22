package com.umessage.letsgo.dao.security;

import com.umessage.letsgo.domain.po.security.WxInfoEntity;

import java.util.Map;

/**
 * Created by zengguoqing on 2016/11/22.
 */
public interface WxInfoDao {

    /**
     * 检查当前用户是否是微信公众号已关注用户
     * @param uninid 微信唯一标识
     * @return
     */
    WxInfoEntity selectUserInfoByUnionID(String uninid);

    /**
     * 添加微信公众号用户信息
     * @param wxInfoEntity 微信公众号用户信息实体
     * @return
     */
    int insert(WxInfoEntity wxInfoEntity);

    /**
     * 取消公众号关注，把这条记录的status 改为1 同时把unionID改为NULL
     * @param unionid
     */
    void unSubscribe(String unionid);

    /**
     * 取消公众号关注，把这条记录的status 改为1 同时把unionID改为NULL
     * @param openid
     */
    void unSubscribeByOpenID(String openid);

    /**
     * 根据微信公众号的openID 获取用户信息
     * @param openId
     * @return
     */
    WxInfoEntity selectUserInfoByOpenID(String openId);

    /**
     *
     * 根据openid 和unionid 查找用户
     * @param wxInfoEntity
     * @return
     */
    WxInfoEntity selectWeixinInfoByOpenIDAndUnionID(WxInfoEntity wxInfoEntity);

    /**
     * 根据微信公众号的openID 删除用户信息 （真删除）
     * @param openId
     * @return
     */
    int delete(String openId);

    /**
     * 根据微信公众号的openID修改微信信息
     * @param wxInfoEntity
     * @return
     */
    int update(WxInfoEntity wxInfoEntity);
    /**
     * 根据微信公众号的openID修改微信信息
     * @param wxInfoEntity
     * @return
     */
    int updateByID(WxInfoEntity wxInfoEntity);

    /**
     * 根据微信唯一标识 修改
     * @param unionid
     */
    void updateBindingStatus(String unionid);


    /**
     * 根据用户ID查询微信信息
     * @param map
     * @return
     */
    WxInfoEntity selectWxInfoByUserId(Map<String, Object> map);


    /**
     * 根据unionid 和AppId 查找用户
     * @param map
     * @return
     */
    WxInfoEntity selectUserByUnionIDAndAppID(Map<String, Object> map);
}
