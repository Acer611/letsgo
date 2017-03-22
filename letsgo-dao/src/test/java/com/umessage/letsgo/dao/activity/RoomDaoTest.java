package com.umessage.letsgo.dao.activity;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;
import com.umessage.letsgo.domain.po.activity.RoomDetailEntity;
import com.umessage.letsgo.domain.po.activity.RoomEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-datasource.xml",
		"classpath:spring/spring-mybatis.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
	TransactionDbUnitTestExecutionListener.class })
@Transactional
//@DatabaseSetup("roomData.xml")
public class RoomDaoTest {
	
	@Resource
	private RoomDao roomDao;

	@Test
	public void testSelectRoomListByTeamId() {
//		fail("Not yet implemented");
		List<RoomEntity> list = roomDao.selectRoomListByTeamId("");

		for (RoomEntity roomEntity : list) {
			System.out.println(roomEntity.toString());
		}

//		Assert.assertNotNull(list);
//		Assert.assertEquals(2, list.size());
//		Assert.assertEquals(2, list.get(0).getRoomDetailList().size());
	}

	@Test
	public void testSelectRoomById() {
//		fail("Not yet implemented");

		RoomEntity roomEntity = roomDao.selectRoomById(1001l);

//		Assert.assertNotNull(roomEntity);
//		Assert.assertEquals(2, roomEntity.getRoomDetailList().size());
	}

}
