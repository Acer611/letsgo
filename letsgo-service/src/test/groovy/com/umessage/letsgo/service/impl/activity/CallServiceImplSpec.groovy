package com.umessage.letsgo.service.impl.activity

import com.umessage.letsgo.dao.activity.CallDao
import com.umessage.letsgo.domain.po.activity.CallDetailEntity
import com.umessage.letsgo.domain.po.activity.CallEntity
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse
import com.umessage.letsgo.service.api.activity.ICallDetailHistoryService
import com.umessage.letsgo.service.api.activity.ICallHistoryService
import org.dozer.Mapper
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by ZhaoYidong on 2016/5/7.
 */
class CallServiceImplSpec extends Specification{

    private CallDao callDao;

    private CallServiceImpl callService;
    private Mapper dozerBeanMapper;
    private ICallHistoryService callHistoryService;
    private ICallDetailHistoryService callDetailHistoryService;

    @Unroll("createCall #teamId")
    def "create Call test"(){
        given:
        CallEntity callEntity = new CallEntity(id:1,teamId: 1000L,status: 0,notarrivedCount:3,arrivedCount:0);
        CallDetailEntity callDetailEntity = new CallDetailEntity(id:1,callId: 1000L,memberId: 1,name: "name1",status: 0,callTime:"",latitude:0,longitude:0,createTime:new Date());

        when:
        then:
        where:
        ""
    }

    /**
     * 暂时不能测试通过。
     * 需要mock dozerBeanMapper、callHistoryService、callDetailHistoryService对象.
     * @return
     */
    @Unroll("finishCall #id")
    def "finish Call test"(){
        given:
        CallEntity callEntity = new CallEntity(id:1,teamId: 1000L,status: 0,notarrivedCount:3,arrivedCount:0);

        callDao = Mock(CallDao.class);
        callDao.selectCallWithId(1) >> callEntity ;

//        dozerBeanMapper = Mock(Mapper.class);
//        callHistoryService = Mock(CallHistoryServiceImpl.class);
//        callDetailHistoryService = Mock(CallDetailHistoryServiceImpl.class);
        callService = new CallServiceImpl(callMapper:callDao);

        when:
        CommonResponse commonResponse = callService.finishCall(id);
        then:
        commonResponse.retCode == retCode;
        where:
        id || retCode
        1  || 0
        3  || 7
    }

    @Unroll("getCallDetailList #id,#userId")
    def"get call detail list test"(){
        given:

        when:
        then:
        where:
        "";
    }
}
