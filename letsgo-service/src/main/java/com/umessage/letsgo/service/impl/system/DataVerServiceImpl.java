package com.umessage.letsgo.service.impl.system;

import com.umessage.letsgo.dao.system.AppVerDao;
import com.umessage.letsgo.dao.system.BaseDataVerDao;
import com.umessage.letsgo.domain.po.system.AppVerEntity;
import com.umessage.letsgo.domain.po.system.BaseDataVerEntity;
import com.umessage.letsgo.domain.vo.system.request.LasterVerRequest;
import com.umessage.letsgo.domain.vo.system.respone.LasterVerResponse;
import com.umessage.letsgo.service.api.system.IDataVerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhajl on 2016/5/25.
 */
@Service
public class DataVerServiceImpl implements IDataVerService {
    @Resource
    private AppVerDao appVerDao;
    @Resource
    private BaseDataVerDao baseDataVerDao;

    @Override
    public LasterVerResponse getLasterVerInfo(LasterVerRequest request) {
        AppVerEntity appVerEntity = appVerDao.selectLasterVerInfo(request);
        BaseDataVerEntity baseDataVerEntity = baseDataVerDao.selectLasterVerInfo(request);

        LasterVerResponse response = new LasterVerResponse();
        response.setAppVerEntity(appVerEntity);
        response.setBaseDataVerEntity(baseDataVerEntity);
        return response;
    }
}
