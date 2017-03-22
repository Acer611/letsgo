package com.umessage.letsgo.dao.team;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.umessage.letsgo.domain.po.team.HotelEntity;
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
 * Created by zengguoqing on 2016/8/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-datasource.xml",
        "classpath:spring/spring-mybatis.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
@Transactional
public class HotelDaoTest {
   // @Resource
    //private HotelDao hotelDao;

    @Test
    public void testGetHotel() throws Exception {
        /*List<HotelEntity> h=hotelDao.getHotel(null);
        System.out.print("============"+h.size());*/
    }
}