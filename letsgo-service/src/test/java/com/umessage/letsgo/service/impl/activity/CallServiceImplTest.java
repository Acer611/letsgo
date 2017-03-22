package com.umessage.letsgo.service.impl.activity;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:spring/spring-datasource.xml",
//		"classpath:spring/spring-mybatis.xml",
//		"classpath:spring/spring-beans.xml" })
//public class CallServiceImplTest {
//
//	@Resource
//	private CallServiceImpl callServiceImpl;
//
//	@Resource
//	private Mapper dozerBeanMapper;
//
//	@Test
//	public void testAddCall() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testUpdateCall() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetCall() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testCreateCall() throws Exception {
////		fail("Not yet implemented");
//
////		CommonResponse response = callServiceImpl.createCall(1l,new UserEntity(),new LocationEntity());
////
////		Assert.assertEquals(0, (int)response.getRetCode());
//	}
//
//	@Test
//	public void testFinishCall() {
////		fail("Not yet implemented");
//		CommonResponse commonResponse = callServiceImpl.finishCall(1l);
//
//		Assert.assertEquals((int)commonResponse.getRetCode(), 0);
//
////		CallEntity callEntity = callServiceImpl.getCall(1l);
//
////		Assert.assertEquals((int)callEntity.getStatus(), 1);
//	}
//
//	@Test
//	public void testGetCallDetailList() {
////		fail("Not yet implemented");
//
////		CallResponse response = callServiceImpl.getCallDetailList(1l);
//
////		Assert.assertNotNull(response);
//
//	}
//	@Test
//	public void IntegerTest(){
//		Integer integer = 1;
//		assertTrue(integer == 1);
//	}
//
//	@Test
//	public void helpPageTest(){
//		PageHelper.startPage(1,10);
//		Page<CallEntity> page = callServiceImpl.selectAll();
//		List<CallEntity> callEntitiyList = page.getResult();
//		System.out.println("callList.size()="+callEntitiyList.size());
//		System.out.println("当前页数："+page.getPageNum());
//		System.out.println("每页条数："+page.getPageSize());
//		System.out.println("总条数："+page.getTotal());
//		System.out.println("总页数："+page.getPages());
//
//		Page<CallEntity> secondList = callServiceImpl.selectAll();
//		System.out.println("第二次查询后，callList.size()="+secondList.size());
//
//		PageHelper.startPage(0,0);
//		Page<CallEntity> threeList = callServiceImpl.selectAll();
//		System.out.println("第三次查询后，callList.size()="+threeList.size());
//	}
//
//	@Test
//	public void testSign(){
//
//		String a ="localhost:8080/openapi/web/schedule/getScheduleShow?flag=@TGS#1RHYZ2AEE&teamId=10010&client_id=BG9CJ9WRB&ver=1.0&timestamp=1465870433521&sign=E21944EC56999023A5927FF5FA78B3D4&access_token=de56920a-08a8-4620-ab63-264645879717&client_secret=ABD76132-141E-4651-BF63-17A71C555988";
//		Map<String,String> map = new HashMap<String,String>();
//		map.put("flag","introduce");
//		map.put("teamId","@TGS#1RHYZ2AEE");
////		map.put("id","1145");
////		map.put("lng","116.378");
////		map.put("lat","40.035");
////		map.put("scheduleDetaildId","8");
////		map.put("id",String.valueOf(1259));
//		map.put(Constant.CLIENT_ID,"F8F3BNKNQ");
//		map.put(Constant.CLIENT_VER,"1.0");
//		map.put(Constant.TIMESTAMP,"1467796904");
//		map.put(Constant.ACCESS_TOKEN,"de56920a-08a8-4620-ab63-264645879717");
//		Map<String,String> signMap = SignUtil.sign(map,"DAB7A4AB-BCA5-4C1A-A0CD-4B5135C67B73");
//		map.put(Constant.CLIENT_SIGN,signMap.get("appSign"));
//		System.out.println("sign:"+signMap.get("appSign"));
//		SignVo vo =dozerBeanMapper.map(map, SignVo.class);
//		System.out.println(vo);
////		localhost:8080/openapi/web/schedule/getScheduleShow?flag=introduce&teamId=@TGS#1RHYZ2AEE&client_id=F8F3BNKNQ&ver=1.0&timestamp=1467796904&sign=0B961D9355E5667B95911C57485E201B&access_token=de56920a-08a8-4620-ab63-264645879717&client_secret=DAB7A4AB-BCA5-4C1A-A0CD-4B5135C67B73
////		SignVo{client_id='BG9CJ9WRB', timestamp=1465870433521, sign='EEDEDF45C260959EB9ECD3FF68203529', ver='1.0', access_token='ddb9bb84-d392-42a2-8d37-c58d6fc863e5'}
//		String s ="http://localhost:8080/openapi/web/schedule/getScheduleShow?flag=introduce&teamId=@TGS#1RHYZ2AEE&client_id=F8F3BNKNQ&ver=1.0&timestamp=1467796904&sign=0B961D9355E5667B95911C57485E201B&access_token=de56920a-08a8-4620-ab63-264645879717&client_secret=DAB7A4AB-BCA5-4C1A-A0CD-4B5135C67B73";
//		try {
//			System.out.println("==="+URLEncoder.encode("@TGS#1RHYZ2AEE","UTF-8"));
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//	}
//}
//%40TGS%231RHYZ2AEE
