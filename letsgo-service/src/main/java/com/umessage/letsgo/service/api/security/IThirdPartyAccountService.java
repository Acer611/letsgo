package com.umessage.letsgo.service.api.security;

import com.umessage.letsgo.domain.po.security.ThirdPartyAccountEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;

/**
 * Created by pw on 2016/11/23.
 */
public interface IThirdPartyAccountService {

    /**
     * 添加第三方账号绑定信息
     * @param thirdPartyAccountEntity
     */
    int create(ThirdPartyAccountEntity thirdPartyAccountEntity);
    /**
     * 删除第三方账号绑定信息
     * @param thirdPartyAccountEntity
     */
    int delete(ThirdPartyAccountEntity thirdPartyAccountEntity);
    /**
     * 修改第三方账号绑定信息
     * @param thirdPartyAccountEntity
     */
    int update(ThirdPartyAccountEntity thirdPartyAccountEntity);

    /**
     * 通过unionID查询第三方账号绑定信息
     * @param unionID
     */
    ThirdPartyAccountEntity selectByUnionID(String unionID);

    /**
     * 根据微信的unionid查找用户是否存在
     * @param  unionID 微信唯一标识
     * @return
     */
     boolean checkIsBindingUser(String unionID);

    /**
     * 绑定用户
     * 分为两步，1.绑定微信用户到跟上用户
     * 2.检查微信用户下是否有团信息和行程信息若存在 将微信名下的团信息绑定到跟上用户名下
     * @param unionID 微信唯一标识
     * @param phoneNumber 手机号
     * @param teamID 团信息
     * @return
     */
    boolean bindingUser(String unionID,String phoneNumber,long teamID, String txGroupid );

    /**
     * 绑定微信用户到跟上用户（单独的用户绑定不含团绑定）
     * @param unionID 微信的唯一标识
     * @param userEntity 用户实体信息
     */
    void bindingWxUserToOurUser(String unionID , UserEntity userEntity);

    /**
     * Binding 团信息和行程信息(当前设计为只绑定出行中和即将出行的团)
     * @param userEntity 用户信息实体
     * @param unionID 微信的唯一标识
     * @param txGroupid 腾讯组ID
     * @param teamID 团ID
     */
    void bindingTeamInfo(UserEntity userEntity, String unionID, long teamID,String txGroupid);



    /**
     * 根据unionID是否绑定信息
     * @param unionID
     * @return
     */
    ThirdPartyAccountEntity selectThirdPartyAccountByUnionid(String unionID);

    /**
     * 根据openID获取第三方账号信息
     * @param openId
     * @return
     */
    ThirdPartyAccountEntity findUserInfoByOpenID(String openId);
}
