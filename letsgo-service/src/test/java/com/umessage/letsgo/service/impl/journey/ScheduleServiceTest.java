package com.umessage.letsgo.service.impl.journey;

/**
 * Created by ZhaoYidong on 2016/7/7.
 */
/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-datasource.xml",
        "classpath:spring/spring-mybatis.xml",
        "classpath:spring/spring-beans.xml" })
public class ScheduleServiceTest {

    @Resource
    private IScheduleDetailsService detailsService;
    @Resource
    private IScheduleService service;
    @Resource
    private ScheduleDao dao;

    @Test
    public void descKeyWordTest(){
        ScheduleDetailEntity detailEntity = detailsService.getScheduleDetails(48L);
        assertNotNull(detailEntity);
        assertNotNull(detailEntity.getDesc());

        ScheduleEntity scheduleEntity = service.getSchedule(57);
        assertNotNull(scheduleEntity);
        List<ScheduleDetailEntity> list = scheduleEntity.getScheduleDetailList();
        if(list !=null){
            ScheduleDetailEntity detailEntity1 = list.get(0);
            System.out.println("desc=="+detailEntity1.getDesc());
        }
    }

    @Test
    public void relationshipQueryPageTest(){
        PageHelper.startPage(1,10);
        ScheduleRequest request = new ScheduleRequest();
        Page<Long> idList = dao.selectAllPages(request);
        assertTrue(idList.size()==10);
        System.out.println("aa"+idList);

        request.setScheduleIds(idList);
        List<ScheduleEntity> list = dao.selectAll(request);
        System.out.println("lists="+list.size()+list);
    }

}*/
