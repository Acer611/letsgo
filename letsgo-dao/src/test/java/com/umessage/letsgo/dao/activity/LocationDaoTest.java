package com.umessage.letsgo.dao.activity;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.umessage.letsgo.domain.po.activity.LocationEntity;
import com.umessage.letsgo.domain.vo.activity.request.LocationRequest;
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
 * Created by ZhaoYidong on 2016/5/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-datasource.xml",
        "classpath:spring/spring-mybatis.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
@Transactional
//@DatabaseSetup("locationData.xml")
public class LocationDaoTest {

    private  double EARTH_RADIUS = 6378.137;//地球半径
    @Resource
    private LocationDao locationDao;

    @Test
    public void selectLocationWithUserIdTest(){
        LocationEntity entity = locationDao.selectLocationWithUserId(1L);
//        assertNotNull(entity);
//        assertEquals(3L,(long)entity.getId());
    }

    @Test
    public void tsetDistance(){
        double d =GetDistance(116.403,39.914147, 116.403,39.914);
        System.out.println(d);
    }

    public double GetDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = Math.toRadians(lat1);
        double radLat2 = Math.toRadians(lat2);
        double a = radLat1 - radLat2;
        double b = Math.toRadians(lng1) - Math.toRadians(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
                Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) * 0.0001d * 1000;
        return s;
    }

    @Test
    public void testmappingTest(){
        LocationRequest request = new LocationRequest();
//        request.setTeamId(10010L);
        request.setRole("eq3");

        List<LocationEntity> locationEntityList = locationDao.selectLocationsByTeamId(request);
        System.out.println(locationEntityList);
    }
}
