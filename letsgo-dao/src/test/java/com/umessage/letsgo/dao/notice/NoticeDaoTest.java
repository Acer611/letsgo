package com.umessage.letsgo.dao.notice;

import static org.junit.Assert.*;

import java.util.ArrayList;
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
import com.umessage.letsgo.domain.po.notice.NoticeEntity;
import com.umessage.letsgo.domain.vo.notice.request.NoticeParamRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-datasource.xml",
		"classpath:spring/spring-mybatis.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
	TransactionDbUnitTestExecutionListener.class })
@Transactional
@DatabaseSetup("noticeData.xml")
public class NoticeDaoTest {
	
	@Resource
	private NoticeDao noticeDao;

	@Test
	public void testSelectNoticeListWithConditions() {
//		fail("Not yet implemented");
		NoticeParamRequest request = new NoticeParamRequest();
//		request.setTeamId(1001l);
//		request.setMemberId(1003l);
//		request.setType(1);

		List<Long> teamIds = new ArrayList<Long>();
		List<Long> memberIds = new ArrayList<Long>();
		teamIds.add(new Long(1001));
		memberIds.add(new Long(1003));

//		request.setTeamIds(teamIds);
		request.setMemberIds(memberIds);
		request.setType(1);

		List<NoticeEntity> list = noticeDao.selectNoticeListWithConditions(request);
		
		Assert.assertNotNull(list);
//		Assert.assertEquals(2, list.size());
//		Assert.assertEquals(1003, (long)list.get(0).getId());
//		Assert.assertEquals(2, list.get(0).getNoticeReplyList().size());
//		Assert.assertEquals(1005, (long)list.get(0).getNoticeReplyList().get(0).getMemberId());
//		Assert.assertEquals(0, (long)list.get(0).getNoticeDetailList().get(0).getActiveStatus());
//		Assert.assertEquals(1002, (long)list.get(1).getNoticeDetailList().get(1).getMemberId());
	}

}
