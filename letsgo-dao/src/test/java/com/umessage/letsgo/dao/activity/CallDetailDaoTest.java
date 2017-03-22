package com.umessage.letsgo.dao.activity;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.umessage.letsgo.domain.po.activity.CallDetailEntity;
import com.umessage.letsgo.domain.vo.activity.request.CallDetailRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/5/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-datasource.xml",
        "classpath:spring/spring-mybatis.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
@Transactional
@DatabaseSetup("callData.xml")
public class CallDetailDaoTest {
    @Resource
    private CallDetailDao callDetailDao;

    @Test
    public void testSelectCallDetailListWithConditions() throws Exception {
        CallDetailRequest request = new CallDetailRequest();
        request.setCallId(1001l);
        request.setStatus(1);
        request.setSort_updateTime("desc");
        List<CallDetailEntity> callDetails = callDetailDao.selectCallDetailListWithConditions(request);

        Assert.assertNotNull(callDetails);
    }
}