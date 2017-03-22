/*


package com.umessage.letsgo.service.impl.team;

import com.umessage.letsgo.domain.po.journey.ScheduleDescEntity;
import com.umessage.letsgo.domain.po.team.HotelAgencyEntity;
import com.umessage.letsgo.domain.vo.common.respone.CommonResponse;
import com.umessage.letsgo.domain.vo.journey.response.ScheduleResponse;
import com.umessage.letsgo.service.api.journey.IScheduleDescService;
import com.umessage.letsgo.service.api.journey.IWebScheduleService;
import com.umessage.letsgo.service.api.team.IHotelAgencyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-datasource.xml",
        "classpath:spring/spring-mybatis.xml",
        "classpath:spring/spring-beans.xml" })
public class HotelAgencyServiceImplTest {
    @Resource
    private IHotelAgencyService agencyService;
    @Resource
    private IWebScheduleService webScheduleService;
    @Resource
    private IScheduleDescService scheduleDescService;

    @Test
    public void testInsert() throws Exception {
        HotelAgencyEntity hotelAgencyEntity = new HotelAgencyEntity();
        hotelAgencyEntity.setTravelId(1l);
        hotelAgencyEntity.setHotelName("北京国际大酒店");
        hotelAgencyEntity.setCity("北京");
        hotelAgencyEntity.setBriefintroduction("康师傅肯定是分解落实到防静电");
        hotelAgencyEntity.setStarLv("7");
        List<String> photos = new ArrayList<>();
        photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487249744402&di=91e8b6c6efae91a6d16ccf81467e4660&imgtype=0&src=http%3A%2F%2Fwww.sxdaily.com.cn%2FNMediaFile%2F2014%2F0517%2FSXRB201405171448000328178255613.jpg");
        photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487249778401&di=ff9b321d8deed5602eab358254491d3a&imgtype=0&src=http%3A%2F%2Fimages4.c-ctrip.com%2Ftarget%2Ft1%2Ftuangou%2F979%2F918%2F891%2F099e704a86e74519ac481e05c5b83230_720_480_s.jpg");
        photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487249807329&di=c45e683779076806eb50289f6144aecf&imgtype=0&src=http%3A%2F%2Fwww.sznews.com%2Fphoto%2Fimages%2Fattachement%2Fjpg%2Fsite3%2F20150921%2F4439c4524395176a27085d.jpg");
        hotelAgencyEntity.setPhotoUrls(photos);
        agencyService.insert(hotelAgencyEntity);
    }

    @Test
    public void testUpdate() throws Exception {
        HotelAgencyEntity hotelAgencyEntity = new HotelAgencyEntity();
        hotelAgencyEntity.setId(1l);
        hotelAgencyEntity.setTravelId(1l);
        hotelAgencyEntity.setHotelName("北京七星级国际大酒店");
        hotelAgencyEntity.setCity("北京");
        hotelAgencyEntity.setBriefintroduction("著名的国际酒店");
        hotelAgencyEntity.setStarLv("7");
        agencyService.update(hotelAgencyEntity);
    }

    @Test
    public void testSelectHotelAgencyByName() throws Exception {
        String hotelname = "赛";
        String cityname = "北京";
        Long travelid = 1l;
        List<HotelAgencyEntity> hotelAgencyEntities = agencyService.selectHotelAgencyByName(hotelname,cityname,travelid);
        System.out.println(hotelAgencyEntities.size());
        System.out.println(hotelAgencyEntities);
    }

    @Test
    public void testSelectHotelAgencyBySameName() throws Exception {
        HotelAgencyEntity hotelAgencyEntity = new HotelAgencyEntity();
        hotelAgencyEntity.setHotelName("北京七星级国际大酒店");
        hotelAgencyEntity.setCity("天津");
        hotelAgencyEntity.setTravelId(1l);
        Boolean aBoolean = agencyService.selectHotelAgencyBySameName(hotelAgencyEntity);
        System.out.println(aBoolean);
    }

    @Test
    public void getScheduleShowByTeamId() throws Exception {
        ScheduleResponse scheduleShowByTeamId = webScheduleService.getScheduleShowByTeamId("@TGS#1JBK2MMEG", 2226l,  null);
    }

    @Test
    public void createScheduleDesc() throws Exception {
        List<ScheduleDescEntity> scheduleDescEntities = new ArrayList<>();
        ScheduleDescEntity scheduleDescEntity = new ScheduleDescEntity();
        scheduleDescEntity.setTitle("其他说明1");
        scheduleDescEntity.setContent("非常详细");
        scheduleDescEntity.setScheduleId(1111l);
        ScheduleDescEntity scheduleDescEntity2 = new ScheduleDescEntity();
        scheduleDescEntity2.setTitle("其他说明2");
        scheduleDescEntity2.setContent("非常详细");
        scheduleDescEntity2.setScheduleId(1111l);
        scheduleDescEntities.add(scheduleDescEntity);
        scheduleDescEntities.add(scheduleDescEntity2);
        CommonResponse scheduleDesc = scheduleDescService.createScheduleDesc(scheduleDescEntities);
    }
    @Test
    public void deleteByScheduleId() throws Exception {
        int i = scheduleDescService.deleteByScheduleId(1111l);
        System.out.println(i);
    }
}
*/





