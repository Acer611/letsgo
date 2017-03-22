package com.umessage.letsgo.dao.notice;

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

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.umessage.letsgo.domain.po.notice.AnnouncementEntity;
import com.umessage.letsgo.domain.vo.notice.request.AnnouRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-datasource.xml",
		"classpath:spring/spring-mybatis.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
	TransactionDbUnitTestExecutionListener.class })
@Transactional
public class AnnouncementDaoTest {
	
	@Resource
	private AnnouncementDao announcementDao;

	@Test
//	@DatabaseSetup("announcementData.xml")
	public void testSelectAnnouncementListWithConditions() {
//		fail("Not yet implemented");
		AnnouRequest request = new AnnouRequest();
//		request.setTeamId(1001l);
		List<AnnouncementEntity> list = announcementDao.selectAnnouncementListWithConditions(request);
		
//		Assert.assertNotNull(list);
//		Assert.assertEquals(3, list.size());
	}

}
