/*


package com.umessage.letsgo.service.impl.journey;

import com.umessage.letsgo.domain.po.journey.HotelScheduleEntity;
import com.umessage.letsgo.domain.vo.journey.response.ScheduleResponse;
import com.umessage.letsgo.service.api.journey.IHotelScheduleService;
import com.umessage.letsgo.service.api.journey.IWebScheduleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;




*/
/**
 * Created by ZhaoYidong on 2017/2/16.
 *//*





@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-datasource.xml",
        "classpath:spring/spring-mybatis.xml",
        "classpath:spring/spring-beans.xml" })
public class HotelScheduleServiceImplTest {
    @Resource
    private IHotelScheduleService hotelScheduleService;
    @Resource
    private IWebScheduleService webScheduleService;

    @Test
    public void testSaveHotelSchedule() throws Exception {
        HotelScheduleEntity hotelScheduleEntity1 = new HotelScheduleEntity();
        hotelScheduleEntity1.setScheduleDetailId(1111l);
        hotelScheduleEntity1.setHotelName("北京大酒店");
        //hotelScheduleEntity1.setBriefintroduction("宽带缴费看得见覅打飞机");
        //hotelScheduleEntity1.setCity("北京");
        //hotelScheduleEntity1.setSameLevel(1);
        List<String> photos = new ArrayList<>();
        photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487249744402&di=91e8b6c6efae91a6d16ccf81467e4660&imgtype=0&src=http%3A%2F%2Fwww.sxdaily.com.cn%2FNMediaFile%2F2014%2F0517%2FSXRB201405171448000328178255613.jpg");
        photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487249778401&di=ff9b321d8deed5602eab358254491d3a&imgtype=0&src=http%3A%2F%2Fimages4.c-ctrip.com%2Ftarget%2Ft1%2Ftuangou%2F979%2F918%2F891%2F099e704a86e74519ac481e05c5b83230_720_480_s.jpg");
        photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487249807329&di=c45e683779076806eb50289f6144aecf&imgtype=0&src=http%3A%2F%2Fwww.sznews.com%2Fphoto%2Fimages%2Fattachement%2Fjpg%2Fsite3%2F20150921%2F4439c4524395176a27085d.jpg");
        //hotelScheduleEntity1.setPhotoUrls(photos);
        hotelScheduleService.insert(hotelScheduleEntity1);
    }

    @Test
    public void selectByScheduleDetailId() throws Exception {
        HotelScheduleEntity hotelScheduleEntity = hotelScheduleService.selectByScheduleDetailId(2359l);
        System.out.println(hotelScheduleEntity);
    }

    @Test
    public void selectHotelScheduleAndAlbum() throws Exception {
        HotelScheduleEntity hotelScheduleEntity = hotelScheduleService.selectHotelScheduleAndAlbum(1l);
        System.out.println(hotelScheduleEntity);
    }

    @Test
    public void getScheduleShowByTeamId() throws Exception {
        ScheduleResponse scheduleResponse = webScheduleService.getScheduleShowByTeamId("@TGS#1GM35TNEB", 2461l, null,null);
        System.out.println("完成");
    }

}
*/





