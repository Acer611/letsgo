package com.umessage.letsgo.service.impl.team;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-datasource.xml",
		"classpath:spring/spring-mybatis.xml",
		"classpath:spring/spring-beans.xml" })
public class MemberServiceImplTest {
	
	@Resource
	private MemberServiceImpl memberServiceImpl;

	@Test
	public void testAddMember() {
//		fail("Not yet implemented");
		MemberEntity memberEntity = new MemberEntity();
		memberEntity.setRealName("杨丞琳");
		memberEntity.setSex(0);
		memberEntity.setType(1);
		memberEntity.setRole(3);
		memberEntity.setIsAdmin(0);
//		memberEntity.setTeamId(1l);

		memberServiceImpl.addMember(memberEntity);
		
//		Assert.assertNotNull(memberEntity);
	}

	@Test
	public void testDeleteMember() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateMember() {
//		fail("Not yet implemented");
		MemberEntity memberEntity = memberServiceImpl.getMember(3l);

		int result = memberServiceImpl.updateMember(memberEntity);
		
//		Assert.assertNotEquals(1, result);
		
	}

	@Test
	public void testGetMemberList() {
//		fail("Not yet implemented");
		
		MemberRequest request = new MemberRequest();
//		request.setTeamId(1l);
//		request.setIsAdmin(0);
		request.setGroupId(1l);
		
		List<MemberEntity> list = memberServiceImpl.getMemberList(request);
		
//		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testGetMember() {
//		fail("Not yet implemented");
		
		MemberEntity memberEntity = memberServiceImpl.getMember(3l);
		
//		Assert.assertEquals(3, (long)memberEntity.getId());
	}

	@Test
	public void testGetMemberWithTeamIdAndUserId() {
//		fail("Not yet implemented");
		
//		MemberEntity memberEntity = memberServiceImpl.getMemberWithTeamIdAndUserId(1l, 1l);
		
//		Assert.assertEquals(3, (long)memberEntity.getId());
	}

	@Test
	public void testGetTeamIdsByUserId() {
//		fail("Not yet implemented");
		
//		List<Long> list = memberServiceImpl.getTeamIdsByUserId(1l);
		
//		Assert.assertEquals(null, list);
	}

	@Test
	public void testSetAdministrator() {
//		fail("Not yet implemented");
		MemberEntity memberEntity = memberServiceImpl.getMember(1l);
		int before = memberEntity.getIsAdmin();
		
//		memberServiceImpl.setAdministrator(1l, 1);
		memberServiceImpl.setAdministrator(null, 0);
		
//		Assert.assertEquals(before, (int)memberEntity.getIsAdmin());
	}

	@Test
	public void testGetMemberDetail() {
//		fail("Not yet implemented");
		
		MemberResponse memberResponse = memberServiceImpl.getMemberDetail(1l, 1l);
		
//		Assert.assertNull(memberResponse);
	}

	@Test
	public void testSetMarkPhone() {
//		fail("Not yet implemented");
		
		memberServiceImpl.setMarkPhone(4l, "");
		
		MemberEntity memberEntity = memberServiceImpl.getMember(4l);
		
//		Assert.assertEquals("+1569636", memberEntity.getMarkPhone());
	}

	@Test
	public void testGetTouristList() {
//		fail("Not yet implemented");
		
//		List<MemberEntity> list = memberServiceImpl.getTouristList(1l, 1);
//		List<MemberEntity> list = memberServiceImpl.getTouristList(1l);
		
//		Assert.assertNull(list);
		
//		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testGetGroupMembertList() {
//		fail("Not yet implemented");
		
		List<MemberEntity> list = memberServiceImpl.getGroupMembertList(2l);
		
//		Assert.assertNull(list);
	}

	@Test
	public void getMemberListByTIdTest(){
		MemberRequest memberRequest = new MemberRequest();
		memberRequest.settId(10069L);
		List<MemberEntity> memberList = memberServiceImpl.getMemberList(memberRequest);
//		assertTrue(memberList.size()>0);
	}

	@Test
	public void getTeamMember(){
		List<MemberEntity> memberEntityList = memberServiceImpl.getTouristList(10296L);
		System.out.println("member:"+memberEntityList.size());
	}

}*/
