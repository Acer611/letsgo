package com.umessage.letsgo.dao.team;

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
import com.umessage.letsgo.domain.po.team.TeamEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-datasource.xml",
		"classpath:spring/spring-mybatis.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
	TransactionDbUnitTestExecutionListener.class })
@Transactional
public class TeamDaoTest {
	
	@Resource
	private TeamDao teamDao;

	@Test
//	@DatabaseSetup("teamData.xml")
	public void testSelectTeamListWithIDs() {
//		fail("Not yet implemented");
		List<String> teamIds = new ArrayList<String>();
		teamIds.add("");
		List<TeamEntity> list = teamDao.selectTeamListWithIDs(teamIds);
		
//		Assert.assertNotNull(list);
//		Assert.assertEquals(1, list.size());
	}

}
