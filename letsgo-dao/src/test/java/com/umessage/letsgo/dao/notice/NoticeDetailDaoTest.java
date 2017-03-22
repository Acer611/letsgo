package com.umessage.letsgo.dao.notice;

import static org.junit.Assert.*;

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
import com.umessage.letsgo.domain.po.notice.NoticeDetailEntity;
import com.umessage.letsgo.domain.vo.notice.request.DetailParamRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-datasource.xml",
		"classpath:spring/spring-mybatis.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
	TransactionDbUnitTestExecutionListener.class })
@Transactional
public class NoticeDetailDaoTest {
	
	@Resource
	private NoticeDetailDao noticeDetailDao;

	@Test
	@DatabaseSetup("noticeData.xml")
	public void testSelectNoticeDetailWithConditions() {
//		fail("Not yet implemented");
		DetailParamRequest request = new DetailParamRequest();
		request.setMemberId(1002l);
		request.setNoticeId(1001l);
		NoticeDetailEntity noticeDetailEntity = noticeDetailDao.selectNoticeDetailWithConditions(request);
		
		Assert.assertNotNull(noticeDetailEntity);
		Assert.assertEquals(0, (int)noticeDetailEntity.getActiveStatus());
	}

}
