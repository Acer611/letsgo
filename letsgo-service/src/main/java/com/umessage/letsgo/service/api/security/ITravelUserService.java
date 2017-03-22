package com.umessage.letsgo.service.api.security;

import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.security.request.UserVo;
import com.umessage.letsgo.domain.vo.security.respone.UserListResponse;

/**
 * Created by ZhaoYidong on 2016/6/17.
 */
public interface ITravelUserService {

    public int addTravelUser(UserEntity userEntity);
    public CommonResponse updateTravelUser(UserEntity userEntity);
    public boolean accountIsRepeated(String account);
    public boolean checkCodeIsError(String checkCode,String phone);
	public CommonResponse saveOrUpdatenAccount(UserVo userVo)throws Exception;
	public UserListResponse getSonAccountList(Integer pageNo);
	public CommonResponse deleteAccount(Long accountId)throws Exception;
	public UserVo queryAccount(Long accountId);

    void updateAccount(Long accountId);


}
