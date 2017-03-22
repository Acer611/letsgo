package com.umessage.letsgo.dao.security;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-datasource.xml",
		"classpath:spring/spring-mybatis.xml" })
public class RoleDaoTest {
	
	@Resource
	private RoleDao roleDao;

	@Test
	public void testSelectRoleWithName() {
//		fail("Not yet implemented");
		roleDao.selectRoleWithName("");
	}

}
