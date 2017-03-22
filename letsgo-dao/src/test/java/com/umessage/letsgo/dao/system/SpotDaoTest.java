package com.umessage.letsgo.dao.system;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.umessage.letsgo.domain.po.system.SpotEntity;
import com.umessage.letsgo.domain.vo.system.request.SpotRequest;
import org.junit.After;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by wendy on 2016/8/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-datasource.xml",
        "classpath:spring/spring-mybatis.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
@Transactional
public class SpotDaoTest {

    @Resource
    private SpotDao spotDao;

    /*@After
    public void tearDown() throws Exception {

    }*/

    @Test
    public void testSelectLike() throws Exception {

        /*SpotRequest request = new SpotRequest();
        request.setQuery("塔");
        List<String> list = new ArrayList<>();
        request.setCities(list);

        List<SpotEntity> spotEntityList = spotDao.selectLike(request);*/

//        Assert.assertEquals(0, spotEntityList.size());
    }
}