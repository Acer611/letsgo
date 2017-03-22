package com.umessage.letsgo.service.api.system;

import com.umessage.letsgo.domain.po.system.LogManage;
import com.umessage.letsgo.domain.vo.system.request.LogManageRequest;
import com.umessage.letsgo.domain.vo.system.respone.LogManageResponse;

/**
 * Created by pw on 2016/9/8.
 */
public interface ILogManageService {
    int add(LogManage logManage);
    //获取日志列表
    LogManageResponse getLogList(LogManageRequest request);
}
