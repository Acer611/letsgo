package com.umessage.letsgo.service.impl.team;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-datasource.xml",
		"classpath:spring/spring-mybatis.xml",
		"classpath:spring/spring-beans.xml" })
public class TeamServiceImplTest {
	
	@Resource
	private TeamServiceImpl teamServiceImpl;
	@Resource
	private TeamDao teamDao;

	@Test
	public void testAddTeam() {
//		fail("Not yet implemented");
		TeamEntity teamEntity = new TeamEntity();
		teamEntity.setName("德意法三国超值豪华游");
		teamEntity.setTeamNum("7863");
		int result = teamServiceImpl.addTeam(teamEntity);
		
//		Assert.assertEquals(4, (long)teamEntity.getId());
	}

	@Test
	public void testDeleteTeam() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateTeam() {
//		fail("Not yet implemented");
		TeamEntity teamEntity = teamDao.select(4l);
		teamEntity.setTeamNum("6767");
		int result = teamServiceImpl.updateTeam(teamEntity);
		
//		Assert.assertEquals(1, result);
	}

	@Test
	public void getTeamListTest() {
		TeamListResponse respone = teamServiceImpl.getTeamList(1L, null);
		Assert.assertEquals(5, respone.getTeamList().size());

		TeamListResponse respone2 = teamServiceImpl.getTeamList(100L, 1);
//		assertEquals(7,(int)respone2.getRetCode());
//		assertNull(respone2.getTeamList());
	}

	@Test
	public void getTeamDetailTest() {
//		TeamRespone team = teamServiceImpl.getTeamDetail(100L);
//		assertEquals(7,(int)team.getRetCode());
//		assertNull(team.getTeamEntity());
//
//		TeamRespone team2 = teamServiceImpl.getTeamDetail(10001L);
//		assertNotNull(team2.getTeamEntity());
//		assertEquals("中青旅",team2.getTeamEntity().getTravelName());

	}

	@Test
	public void getTeamMemberListTest() {
//		TeamMemberResponse response = teamServiceImpl.getTeamMemberList(10001l);
//		assertEquals(1,response.getAdministratorList().size());
//		assertEquals(1,response.getGroupList().size());
//		assertEquals(1,response.getGroupList().get(0).getMemberList().size());
//		assertEquals(1,response.getPersonalList().size());
//
//		TeamMemberResponse response2 = teamServiceImpl.getTeamMemberList(10004l);
//		assertEquals(0,response2.getAdministratorList().size());
//		assertEquals(0,response2.getGroupList().size());
//		assertEquals(0,response2.getGroupList().size());
//		assertEquals(1,response2.getPersonalList().size());
//
//		TeamMemberResponse response3 = teamServiceImpl.getTeamMemberList(10005l);
//		assertEquals(1,response3.getAdministratorList().size());
//		assertEquals(0,response3.getGroupList().size());
//		assertEquals(0,response3.getGroupList().size());
//		assertEquals(0,response3.getPersonalList().size());
//
//		TeamMemberResponse response4 = teamServiceImpl.getTeamMemberList(10006l);
//		assertEquals(0,response4.getAdministratorList().size());
//		assertEquals(1,response4.getGroupList().size());
//		assertEquals(0,response4.getPersonalList().size());
	}

	@Test
	public void testAddTourGuide() {
//		fail("Not yet implemented");
		
		GuideMemberRequest request = new GuideMemberRequest();
		request.setRealName("Muss导");
//		request.setTeamId(2l);
		request.setPhone("15677354578");
		request.setSex(0);
		CommonResponse response = teamServiceImpl.addTourGuide(request);
		
//		Assert.assertEquals(0, (int)response.getRetCode());
		
	}

}*/
