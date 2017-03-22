package com.umessage.letsgo.dao.team;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.umessage.letsgo.domain.po.team.MemberEntity;
import com.umessage.letsgo.domain.vo.team.requset.MemberRequest;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-datasource.xml",
		"classpath:spring/spring-mybatis.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
	TransactionDbUnitTestExecutionListener.class })
@Transactional
//@DatabaseSetup("teamData.xml")
public class MemberDaoTest {
	@Resource
	private MemberDao memberDao;

	@Test
	public void testSelectMemberListWithConditons() {
//		fail("Not yet implemented");
		MemberRequest request = new MemberRequest();
//		request.setTeamId(1001l);
//		request.setIsAdmin(1);
		
//		List<Long> memberIds = new ArrayList<Long>();
//		memberIds.add(1001l);
//		memberIds.add(1002l);
//		memberIds.add(1003l);
//		request.setMemberIds(memberIds);
		
//		request.setGroupId(1001l);
//		request.setRole("lt3");
		request.setPhone("13763435434");
		request.setSort_isLeader("asc");

		List<MemberEntity> list = memberDao.selectMemberListWithConditons(request);
		
//		Assert.assertNotNull(list);
//		Assert.assertEquals(1, list.size());
//		Assert.assertEquals(1003, (long)list.get(0).getId());
//		Assert.assertEquals("杨丞琳", list.get(0).getRealName());
	}

	@Test
	public void testSelectMemberWithConditons() {
//		fail("Not yet implemented");
		MemberRequest request = new MemberRequest();
		request.setUserId(1001l);
//		request.setTeamId(1001l);
		MemberEntity memberEntity = memberDao.selectMemberWithConditons(request);
		
//		Assert.assertNotNull(memberEntity);
//		Assert.assertEquals("杨导", memberEntity.getRealName());
	}

	@Test
	public void testSelectTeamIdsByUserId() {
//		fail("Not yet implemented");
//		List<Long> list = memberDao.selectTeamIdsByUserId(1001l);
		
//		Assert.assertNotNull(list);
//		Assert.assertEquals(1, list.size());
	}

}
