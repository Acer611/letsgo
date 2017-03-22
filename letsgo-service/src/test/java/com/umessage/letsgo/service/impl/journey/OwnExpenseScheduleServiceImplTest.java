/*

package com.umessage.letsgo.service.impl.journey;

import com.umessage.letsgo.domain.po.journey.OwnExpenseScheduleEntity;
import com.umessage.letsgo.service.api.journey.IOwnExpenseScheduleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

*/
/**
 * Created by ZhaoYidong on 2017/2/23.
 *//*


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-datasource.xml",
        "classpath:spring/spring-mybatis.xml",
        "classpath:spring/spring-beans.xml" })
public class OwnExpenseScheduleServiceImplTest {

    @Resource
    private IOwnExpenseScheduleService ownExpenseScheduleService;

    @Test
    public void testDelete() throws Exception {

    }

    @Test
    public void testInsert() throws Exception {
        OwnExpenseScheduleEntity ownExpenseScheduleEntity = new OwnExpenseScheduleEntity();
        ownExpenseScheduleEntity.setScheduleDetailId(1l);
        ownExpenseScheduleEntity.setItemName("登世界第一高楼");
        ownExpenseScheduleEntity.setBriefintroduction("哈利法塔");
        ownExpenseScheduleEntity.setItemCity("迪拜");
        ownExpenseScheduleEntity.setLimitNumber("限制为30人");
        ownExpenseScheduleEntity.setServiceItem("门票加特殊服务");
        ownExpenseScheduleEntity.setReferencePrice("成人1000迪拉姆，儿童500迪拉姆");
        List<String> photos = new ArrayList<>();
        photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487843121843&di=bd9b01289c313dd6c1a1cc3c3f73fce3&imgtype=0&src=http%3A%2F%2Fpic18.nipic.com%2F20120111%2F2004813_143703120000_2.jpg");
        photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487843122603&di=48600bd4dffdcd269159a8210269b091&imgtype=0&src=http%3A%2F%2Fwww.starwoodhotels.com%2Fpub%2Fmedia%2F724%2Fshe724ed.109521_xx.jpg");
        photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1487833092&di=9f0f7724fa0bbbd74f294b31566095f4&src=http://wiki.zhulong.com/upload/other/2013/05/13/fe71c1484013b6a714c212dcf53541fa.jpg");
        ownExpenseScheduleEntity.setPhotoUrls(photos);
        ownExpenseScheduleService.insert(ownExpenseScheduleEntity);
    }

    @Test
    public void testInsertBySchedule() throws Exception {
        OwnExpenseScheduleEntity ownExpenseScheduleEntity = new OwnExpenseScheduleEntity();
        ownExpenseScheduleEntity.setScheduleId(1l);
        ownExpenseScheduleEntity.setItemName("登巴黎铁塔");
        ownExpenseScheduleEntity.setBriefintroduction("巴黎铁塔");
        ownExpenseScheduleEntity.setItemCity("法国");
        ownExpenseScheduleEntity.setLimitNumber("限制为30人");
        ownExpenseScheduleEntity.setServiceItem("门票加特殊服务");
        ownExpenseScheduleEntity.setReferencePrice("成人1000法郎，儿童500法郎");
        List<String> photos = new ArrayList<>();
        photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487843121843&di=bd9b01289c313dd6c1a1cc3c3f73fce3&imgtype=0&src=http%3A%2F%2Fpic18.nipic.com%2F20120111%2F2004813_143703120000_2.jpg");
        photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487843122603&di=48600bd4dffdcd269159a8210269b091&imgtype=0&src=http%3A%2F%2Fwww.starwoodhotels.com%2Fpub%2Fmedia%2F724%2Fshe724ed.109521_xx.jpg");
        photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1487833092&di=9f0f7724fa0bbbd74f294b31566095f4&src=http://wiki.zhulong.com/upload/other/2013/05/13/fe71c1484013b6a714c212dcf53541fa.jpg");
        ownExpenseScheduleEntity.setPhotoUrls(photos);
        ownExpenseScheduleService.insertBySchedule(ownExpenseScheduleEntity);
    }

    @Test
    public void testSelect() throws Exception {
        OwnExpenseScheduleEntity ownExpenseScheduleEntity = ownExpenseScheduleService.select(1l);
        System.out.println(ownExpenseScheduleEntity);
    }

    @Test
    public void testUpdate() throws Exception {
        OwnExpenseScheduleEntity ownExpenseScheduleEntity = new OwnExpenseScheduleEntity();
        ownExpenseScheduleEntity.setId(1l);
        ownExpenseScheduleEntity.setScheduleDetailId(1l);
        ownExpenseScheduleEntity.setItemName("登世界第一高楼");
        ownExpenseScheduleEntity.setBriefintroduction("哈利法塔，原名迪拜塔，又称迪拜大厦或比斯迪拜塔，是世界第一高楼与人工构造物。");
        ownExpenseScheduleEntity.setItemCity("迪拜");
        ownExpenseScheduleEntity.setLimitNumber("限制为50人");
        ownExpenseScheduleEntity.setServiceItem("门票加特殊服务");
        ownExpenseScheduleEntity.setReferencePrice("成人1000迪拉姆，儿童500迪拉姆");
        List<String> photos = new ArrayList<>();
        photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487843121843&di=bd9b01289c313dd6c1a1cc3c3f73fce3&imgtype=0&src=http%3A%2F%2Fpic18.nipic.com%2F20120111%2F2004813_143703120000_2.jpg");
        photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487843122603&di=48600bd4dffdcd269159a8210269b091&imgtype=0&src=http%3A%2F%2Fwww.starwoodhotels.com%2Fpub%2Fmedia%2F724%2Fshe724ed.109521_xx.jpg");
        photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1487833092&di=9f0f7724fa0bbbd74f294b31566095f4&src=http://wiki.zhulong.com/upload/other/2013/05/13/fe71c1484013b6a714c212dcf53541fa.jpg");
        ownExpenseScheduleEntity.setPhotoUrls(photos);
        ownExpenseScheduleService.update(ownExpenseScheduleEntity);
    }

    @Test
    public void testUpdateBySchedule() throws Exception {
        OwnExpenseScheduleEntity ownExpenseScheduleEntity = new OwnExpenseScheduleEntity();
        ownExpenseScheduleEntity.setId(3l);
        ownExpenseScheduleEntity.setScheduleId(1l);
        ownExpenseScheduleEntity.setItemName("看凯旋门");
        ownExpenseScheduleEntity.setBriefintroduction("凯旋门");
        ownExpenseScheduleEntity.setItemCity("法国");
        ownExpenseScheduleEntity.setLimitNumber("限制为30人");
        ownExpenseScheduleEntity.setServiceItem("门票加特殊服务");
        ownExpenseScheduleEntity.setReferencePrice("成人1000法郎，儿童500法郎");
        List<String> photos = new ArrayList<>();
        photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487843121843&di=bd9b01289c313dd6c1a1cc3c3f73fce3&imgtype=0&src=http%3A%2F%2Fpic18.nipic.com%2F20120111%2F2004813_143703120000_2.jpg");
        photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487843122603&di=48600bd4dffdcd269159a8210269b091&imgtype=0&src=http%3A%2F%2Fwww.starwoodhotels.com%2Fpub%2Fmedia%2F724%2Fshe724ed.109521_xx.jpg");
        photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1487833092&di=9f0f7724fa0bbbd74f294b31566095f4&src=http://wiki.zhulong.com/upload/other/2013/05/13/fe71c1484013b6a714c212dcf53541fa.jpg");
        ownExpenseScheduleEntity.setPhotoUrls(photos);
        ownExpenseScheduleService.updateBySchedule(ownExpenseScheduleEntity);
    }

    @Test
    public void testSelectOwnExpenseScheduleAndAlbum() throws Exception {
        OwnExpenseScheduleEntity ownExpenseScheduleEntity = ownExpenseScheduleService.selectOwnExpenseScheduleAndAlbum(1l);
        System.out.println(ownExpenseScheduleEntity);
    }

    @Test
    public void testSelectOwnExpenseScheduleByScheduleId() throws Exception {
        List<OwnExpenseScheduleEntity> ownExpenseScheduleEntities = ownExpenseScheduleService.selectOwnExpenseScheduleByScheduleId(1l);
        System.out.println(ownExpenseScheduleEntities);
    }

    @Test
    public void deleteOwnExpenseScheduleByScheduleId() throws Exception {
        int i = ownExpenseScheduleService.deleteOwnExpenseScheduleByScheduleId(2l);
        System.out.println(i);
    }

    @Test
    public void deleteOwnExpenseScheduleByScheduleDetailId() throws Exception {
        int i = ownExpenseScheduleService.deleteOwnExpenseScheduleByScheduleDetailId(2l);
        System.out.println(i);
    }
}
*/
