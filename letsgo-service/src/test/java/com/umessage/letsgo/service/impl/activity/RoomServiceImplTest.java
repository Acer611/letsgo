package com.umessage.letsgo.service.impl.activity;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-datasource.xml",
		"classpath:spring/spring-mybatis.xml",
		"classpath:spring/spring-beans.xml" })
public class RoomServiceImplTest {
	@Resource
	private RoomServiceImpl roomServiceImpl;
	private RoomRequest roomRequest;
	private RoomEntity roomEntity;
	private RoomDetailEntity roomDetailEntity;
	private RoomDetailEntity roomDetailEntity2;

	@Test
	public void testAddRoom() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteRoom() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateRoom() {
		fail("Not yet implemented");
	}

	@Before
	public void paperData(){
		roomRequest = new RoomRequest();

		List<RoomEntity> roomList = new ArrayList<RoomEntity>();
		roomEntity = new RoomEntity();
//		roomEntity.setTeamId(1l);
		roomEntity.setRoomNum("1001");

		List<RoomDetailEntity> roomDetailList = new ArrayList<RoomDetailEntity>();
		roomDetailEntity = new RoomDetailEntity();
		roomDetailEntity.setMemberId(4l);
		roomDetailEntity.setRoomId(1l);
		roomDetailEntity.setGroupId(-1l);
		roomDetailList.add(roomDetailEntity);

		roomDetailEntity2 = new RoomDetailEntity();
		roomDetailEntity2.setMemberId(5l);
		roomDetailEntity2.setRoomId(1l);
		roomDetailEntity2.setGroupId(3l);
		roomDetailList.add(roomDetailEntity2);

		roomEntity.setRoomDetailList(roomDetailList);
		roomList.add(roomEntity);
//		roomRequest.setRoomList(roomList);
	}
	*//**
	 * 增加房间测试
	 *//*
	@Test
	public void testCreateRoom() {
//		CommonResponse response = roomServiceImpl.createRoom(roomRequest);
//		assertEquals(0,(int)response.getRetCode());
	}

	*//**
	 * 更新房间测试
	 * id 为数据库中值，测试时得修改
	 *//*
	@Test
	public void testCreateRoom2(){
		roomEntity.setId(1020L);
		roomEntity.setRoomNum("007");

		roomDetailEntity.setId(1038L);

		roomDetailEntity2.setId(1039L);

//		CommonResponse response = roomServiceImpl.createRoom(roomRequest);
//		assertEquals(0,(int)response.getRetCode());
	}


	@Test
	public void testGetTeamRoomDetailList() {
//		fail("Not yet implemented");
		
//		RoomResponse roomResponse = (RoomResponse) roomServiceImpl.getTeamRoomDetailList(1l);
//
//		Assert.assertNotNull(roomResponse);
//		Assert.assertEquals("1001", roomResponse.getRoomNum());
	}
	@Test
	public void testMethod(){

	}

	public CommonResponse getResponse(){
		RoomResponse roomResponse = new RoomResponse();
		roomResponse.setRetCode(1);
		roomResponse.setRetMsg("123");

		RoomEntity room = new RoomEntity();
		room.setRoomNum("roomNum");
		room.setRoomType(1);
		List<RoomEntity> list = new ArrayList<RoomEntity>();
		list.add(room);

		roomResponse.setRoomList(list);
		return roomResponse;
	}

}*/
