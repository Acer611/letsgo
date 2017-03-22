package com.umessage.letsgo.dao.security;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.umessage.letsgo.domain.po.security.UserRoleEntity;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-datasource.xml", "classpath:spring/spring-mybatis.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionDbUnitTestExecutionListener.class })
@Transactional
public class UserRoleDaoTest {
	
	@Resource
	private UserRoleDao userRoleDao;

	@Test
	@DatabaseSetup("userData.xml")
	public void testSelectUserRoleWithConditions() {
//		fail("Not yet implemented");
		UserRoleEntity userRoleEntity = new UserRoleEntity();
		userRoleEntity.setUserId(1001l);
		userRoleEntity.setRoleId(1l);
		List<UserRoleEntity> userRoleEntityList = userRoleDao.selectUserRoleWithConditions(userRoleEntity);

		Assert.assertNotNull(userRoleEntity);
//		Assert.assertEquals(1l, (long)userRoleEntityList.get(0).getRoleId());
		Assert.assertNotNull(userRoleEntityList.get(0).getRole());
		Assert.assertEquals("领队", userRoleEntityList.get(0).getRole().getDescn());
	}

}
