package com.umessage.letsgo.service.impl.security;

//@RunWith(JMockit.class)
/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-datasource.xml",
		"classpath:spring/spring-mybatis.xml",
		"classpath:spring/spring-beans.xml" })
public class UserServiceImplTest {
	@Resource
	private UserServiceImpl userServiceImpl;
	@Resource
	private LocationServiceImpl locationService;

	@Injectable
	private UserDao userDao;
	
	@Injectable
	private IUserRoleService userRoleService;
	@Injectable
	private Mapper dozerBeanMapper = new DozerBeanMapper();
	@Injectable
	private IMemberService memberService;
	@Injectable
	private INoticeService noticeService;
	@Injectable
	private IAnnouncementService announcementService;
	

	@Test
	public void testGetUserByName() {
		
		//when(userDao.selectWithConditions(any(UserRequest.class))).thenReturn(userEntity); 
		//assertEquals(userEntity, userDao.selectWithConditions(new UserRequest()));
		
		new Expectations() {
			{
				final UserEntity userEntity = new UserEntity();
				userEntity.setUserName("zhajl");
				
				userDao.selectWithAccount((String) any);
				result = userEntity;
			}
		};
		
		UserResponse userResponse = userServiceImpl.getUserByLoginAccount("zhajl");
//		assertNotNull(userResponse);
//		assertNotNull(userResponse.getUserEntity());
//		assertEquals("zhajl", userResponse.getUserEntity().getUserName());
		
        new Verifications() {
            {
            	userDao.selectWithAccount((String) any);
                times = 1;
            }
        };
	}

	@Test
	public void testGetUserByPhone() {
		fail("Not yet implemented");
	}

	@Test
	public void testUserLogin() {
		fail("Not yet implemented");
	}

}*/
