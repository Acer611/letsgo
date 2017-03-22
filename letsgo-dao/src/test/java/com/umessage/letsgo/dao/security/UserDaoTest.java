package com.umessage.letsgo.dao.security;

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
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.umessage.letsgo.domain.po.security.UserEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-datasource.xml", "classpath:spring/spring-mybatis.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
	TransactionDbUnitTestExecutionListener.class })
@Transactional
public class UserDaoTest {
	@Resource
	private UserDao userDao;
	
	@Test
	@DatabaseSetup("userData.xml") 
	public void testSelectWithConditions() {
		UserEntity userEntity = userDao.selectWithAccount("13488786266");
		Assert.assertNotNull(userEntity);
		Assert.assertEquals("13488786266", userEntity.getPhone());
	}
	
	@Test
	@DatabaseSetup("userData.xml") 
	@ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "userExpectedData.xml")
	public void testUpdate() {
		UserEntity userEntity = userDao.select(1001L);
		Assert.assertNotNull(userEntity);
		userEntity.setNickName("Bob");
		userDao.update(userEntity);
	}
}
