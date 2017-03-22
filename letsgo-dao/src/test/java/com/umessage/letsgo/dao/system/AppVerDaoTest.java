package com.umessage.letsgo.dao.system;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.umessage.letsgo.domain.po.system.AppVerEntity;
import com.umessage.letsgo.domain.vo.system.request.LasterVerRequest;
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

import static org.junit.Assert.*;

/**
 * Created by zhajl on 2016/5/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-datasource.xml",
        "classpath:spring/spring-mybatis.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
@Transactional
public class AppVerDaoTest {

    @Resource
    private AppVerDao appVerDao;

    @Test
    @DatabaseSetup("appVerData.xml")
    public void testSelectLasterVerInfo() throws Exception {
        LasterVerRequest req = new LasterVerRequest();
        req.setDeviceType("ios");
        req.setModule("tour_escort");

        AppVerEntity appVerEntity = appVerDao.selectLasterVerInfo(req);

        Assert.assertNotNull(appVerEntity);
        Assert.assertEquals(1001l, appVerEntity.getId().longValue());

        req.setDeviceType("android");
        req.setModule("traveller");

        appVerEntity = appVerDao.selectLasterVerInfo(req);

        Assert.assertNotNull(appVerEntity);
        Assert.assertEquals(1004l, appVerEntity.getId().longValue());
    }
}