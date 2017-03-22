package com.umessage.letsgo.service.impl.activity;

import com.umessage.letsgo.core.annotation.dataitem.DataItem;
import com.umessage.letsgo.core.extensions.springsecurity.utils.SignUtil;
import com.umessage.letsgo.domain.po.activity.LocationEntity;
import com.umessage.letsgo.domain.po.security.UserEntity;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.vo.activity.response.LocationListResponse;
import com.umessage.letsgo.domain.vo.journey.response.vo.SignVo;
import com.umessage.letsgo.service.api.activity.ILocationService;
import com.umessage.letsgo.service.api.activity.IWebLocationService;
import com.umessage.letsgo.service.common.constant.Constant;
import org.dozer.Mapper;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoYidong on 2016/6/12.
 */
@Service
public class WebLocationServiceImpl implements IWebLocationService {

    @Resource
    private ILocationService locationService;
    @Resource
    private ClientDetailsService clientDetailsService;
    @Resource
    private Mapper dozerBeanMapper;

    @Override
    @DataItem
    public List<LocationEntity> getLocationList(String teamId, Long userId, boolean isPushed) throws Exception {
        List<LocationEntity> locationList = locationService.getLocationList(teamId, userId, isPushed);
        for (LocationEntity locationEntity:locationList) {
            MemberEntity memberEntity = locationEntity.getMemberEntity();
            //同步头像
            memberEntity = this.changePhotoUrl(memberEntity);
        }
        return locationList;
    }

    @Override
    public LocationListResponse getLocationSign(String teamId, LocationListResponse respone, SignVo signVo) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("teamId", teamId);
        map.put(Constant.CLIENT_ID, signVo.getClient_id());
        map.put(Constant.CLIENT_VER, signVo.getVer());
        map.put(Constant.TIMESTAMP, String.valueOf(new Date().getTime()));
        map.put(Constant.ACCESS_TOKEN, signVo.getAccess_token());

        Map<String, String> appSignMap = SignUtil.sign(map, clientDetailsService.loadClientByClientId(signVo.getClient_id()).getClientSecret());
        map.put(Constant.CLIENT_SIGN, appSignMap.get("appSign"));
        SignVo signVo1 = dozerBeanMapper.map(map, SignVo.class);

        Map<String, SignVo> signMap = new HashMap<String, SignVo>();
        signMap.put("refreshMemberLocation", signVo1);
        respone.setSignMap(signMap);
        return respone;
    }
    //获取围观位置
    @Override
    @DataItem
    public List<LocationEntity> getOnlookLocation(String teamId, Long userId, boolean isPushed) throws Exception {
        return locationService.getOnlookLocation(teamId, userId, isPushed);
    }


    /**
     * 设置团队头像地址为用户头像地址 lizhen
     * @param memberEntity
     * @return
     */
    private MemberEntity changePhotoUrl(MemberEntity memberEntity){
        if(null == memberEntity){
            return memberEntity;
        }
        UserEntity userEntity = memberEntity.getUserEntity();
        if (userEntity != null){
            String photoUrl = userEntity.getPhotoUrl();
            //判断关联用户的头像地址是否为空，不为空设置团队头像地址为用户头像地址
            if (!StringUtils.isEmpty(photoUrl)){
                memberEntity.setPhotoUrl(photoUrl);
            }
        }

        return memberEntity;
    }

}
