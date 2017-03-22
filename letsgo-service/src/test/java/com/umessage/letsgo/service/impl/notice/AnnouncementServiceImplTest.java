package com.umessage.letsgo.service.impl.notice;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-datasource.xml",
		"classpath:spring/spring-mybatis.xml",
		"classpath:spring/spring-beans.xml" })
public class AnnouncementServiceImplTest {
	
	@Resource
	private AnnouncementServiceImpl announcementServiceImpl;
	
	@Resource
	private AnnouncementDao announcementDao;

	@Test
	public void testAddAnnouncementEntity() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAnnouncementListAnnouRequest() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveAnnouncement() {
//		fail("Not yet implemented");
		
		AnnouncementRequest request = new AnnouncementRequest();
		
//		request.setTeamId(1l);
		request.setUserId(1l);
		request.setTitle("泰国五日游注意事项");
		request.setContent("乘车注意事项：应按编号排队上车，各车领队负责清点人数，到齐后按时发车，如有意外及时报告领队；看护及照顾好小孩，严禁把头手伸出窗外；遵守公共卫生，垃 圾一律扔进垃圾袋内；如有晕车提前预防；乘坐车时需防途中睡觉受凉感冒，备好适量衣物/食品/饮料，提前上卫生间方便；返程按指定路线，无特殊情况途中不 得停车。为使大家度过一个轻松、快乐、安全的旅游假日，请转告亲友本次旅游行程安排，以免担心和做好接应准备。");
		try {
			announcementServiceImpl.saveAnnouncement(request);
		} catch (Exception e) {
			e.printStackTrace();
		}

		AnnouncementEntity actual = announcementDao.select(4l);
		
		Assert.assertEquals(0, (long)actual.getId());
		
	}

	@Test
	public void testGetAnnouncementList() {
//		fail("Not yet implemented");
		
		AnnouncementResponse response = announcementServiceImpl.getAnnouncementList(null, 1l);
		
		Assert.assertNotNull(response);
		
		Assert.assertEquals(0, response.getAnnouncementList().size());
	}

}*/
