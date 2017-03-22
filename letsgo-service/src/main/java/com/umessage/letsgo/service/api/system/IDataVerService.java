package com.umessage.letsgo.service.api.system;

import com.umessage.letsgo.domain.vo.system.request.LasterVerRequest;
import com.umessage.letsgo.domain.vo.system.respone.LasterVerResponse;

/**
 * Created by zhajl on 2016/5/25.
 */
public interface IDataVerService {
    public LasterVerResponse getLasterVerInfo(LasterVerRequest request);
}
