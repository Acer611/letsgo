package com.umessage.letsgo.dao.security;

import com.umessage.letsgo.domain.po.security.ThirdPartyAccountEntity;

/**
 * Created by pw on 2016/11/23.
 */
public interface ThirdPartyAccountDao {

    /**
     * 添加第三方账号绑定信息
     * @param thirdPartyAccountEntity
     */
    int insert(ThirdPartyAccountEntity thirdPartyAccountEntity);
    /**
     * 删除第三方账号绑定信息
     * @param id
     */
    int delete(Long id);
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
     * 根据unionID查找第三方账号信息
     * @param unionid 微信唯一标识
     * @return
     */
    ThirdPartyAccountEntity selectThirdPartyInfoByUnionID(String unionid);

    /**
     * 根据unionID是否绑定信息
     * @param unionID
     * @return
     */
    ThirdPartyAccountEntity selectThirdPartyAccountByUnionid(String unionID);

    /**
     * 根据unionid 和userID 查询第三方实体信息
     * @return
     */
    ThirdPartyAccountEntity selectThirdPartyByUnionIDAndUserID(ThirdPartyAccountEntity thirdPartyAccountEntity);

    /**
     * 根据openid获取用户user信息
     * @param openId
     * @return
     */
    ThirdPartyAccountEntity selectUserInfoByOpenID(String openId);
}
