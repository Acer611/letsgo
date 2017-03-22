package com.umessage.letsgo.service.impl.security;

import com.umessage.letsgo.dao.security.WxTeamDao;
import com.umessage.letsgo.domain.po.security.WxTeamEntity;
import com.umessage.letsgo.service.api.security.IWxTeamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by pw on 2016/11/23.
 */

@Service
public class WxTeamServiceImpl implements IWxTeamService {
    @Resource
    private WxTeamDao wxTeamDao;

    @Override
    public int create(WxTeamEntity wxTeamEntity) {
        return wxTeamDao.insert(wxTeamEntity);
    }

    @Override
    public int delete(WxTeamEntity wxTeamEntity) {
        return wxTeamDao.delete(wxTeamEntity.getId());
    }

    @Override
    public int update(WxTeamEntity wxTeamEntity) {
        return wxTeamDao.update(wxTeamEntity);
    }

    @Override
    public List<WxTeamEntity> selectWxTeamByUnionid(String unionid) {
        List<WxTeamEntity> wxTeamEntity = wxTeamDao.selectWxTeamByUnionid(unionid);
        if(null != wxTeamEntity){
            return  wxTeamEntity;
        }
        return null;
    }

    @Override
    public List<WxTeamEntity> selectWxTeamInfoListByUnionid(String unionID) {
        return wxTeamDao.selectWxTeamInfoListByUnionid(unionID);
    }
}
