package com.umessage.letsgo.service.impl.team;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-datasource.xml",
		"classpath:spring/spring-mybatis.xml",
		"classpath:spring/spring-beans.xml" })
public class GroupServiceImplTest {
	
	@Resource
	private GroupServiceImpl groupServiceImpl;

	@Test
	public void testAddGroup() {
//		fail("Not yet implemented");
		GroupEntity groupEntity = new GroupEntity();
		groupEntity.setName("一号家庭");
//		groupEntity.setTeamId(1l);
		groupEntity.setTotalCount(2);

		groupServiceImpl.addGroup(groupEntity);
		
//		Assert.assertEquals(0, (long)groupEntity.getId());
	}

	@Test
	public void testDeleteGroup() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateGroup() {
//		fail("Not yet implemented");
		
		GroupEntity groupEntity = groupServiceImpl.getGroup(1l);
		groupEntity.setName("二号家庭");
		groupServiceImpl.updateGroup(groupEntity);
		
//		Assert.assertNotEquals("二号家庭", groupEntity.getName());
	}

	@Test
	public void testGetGroup() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetGroupListByTeamId() {
//		fail("Not yet implemented");
//		List<GroupEntity> groupEntities = groupServiceImpl.getGroupListByTeamId(10001l);
		
//		Assert.assertNotNull(groupEntities);
	}

	@Test
	public void testCreateGroup() {
		GroupRequest groupRequest = new GroupRequest();
		groupRequest.setName("泰国传奇");
//		groupRequest.setTeamId(1l);
		groupRequest.setLeaderId(1010l);
		
		List<Long> memberIds = new ArrayList<Long>();
		memberIds.add(1011l);
		groupRequest.setMemberIds(memberIds);
		
		CommonResponse response = groupServiceImpl.createGroup(groupRequest);
		
//		Assert.assertEquals((int)response.getRetCode(), 0);
	}

	@Test
	public void testDelGroup() {
//		fail("Not yet implemented");
		
		groupServiceImpl.delGroup(2l);
		GroupEntity g = groupServiceImpl.getGroup(2l);
		
		Assert.assertNull(g);
	}

	@Test
	public void testModifyGroup() {
//		fail("Not yet implemented");
		
		GroupRequest groupRequest = new GroupRequest();
		groupRequest.setGroupId(1025L);
		groupRequest.setName("泰国组合");
//		groupRequest.setTeamId(1l);
		groupRequest.setLeaderId(1011l);

		List<Long> list = new ArrayList<Long>();
		list.add(1010l);
		groupRequest.setMemberIds(list);
		
		CommonResponse response = groupServiceImpl.modifyGroup(groupRequest);
		
//		Assert.assertEquals((int)response.getRetCode(), 0);
	}

//	@Test
//	public void getGroupMemberList(){
//		GroupResponse groupResponse = groupServiceImpl.getGroupMemberList(1025L);
//		assertNotNull(groupResponse.getGroupEntity());
//		assertEquals(1025L,(long)groupResponse.getGroupEntity().getId());
//		assertEquals(2,groupResponse.getGroupEntity().getMemberList().size());
//	}
}*/
