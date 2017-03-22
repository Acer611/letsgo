/*
package com.umessage.letsgo.service.impl.team;

import com.umessage.letsgo.domain.po.team.OwnExpenseAgencyEntity;
import com.umessage.letsgo.service.api.team.IOwnExpenseAgencyService;
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
public class OwnExpenseAgencyServiceImplTest {
    @Resource
    private IOwnExpenseAgencyService ownExpenseAgencyService;

    @Test
    public void testDelete() throws Exception {

    }

    @Test
    public void testInsert() throws Exception {
        OwnExpenseAgencyEntity ownExpenseAgencyEntity = new OwnExpenseAgencyEntity();
        ownExpenseAgencyEntity.setTravelId(1l);
        ownExpenseAgencyEntity.setItemName("攀登世界第一高峰");
        ownExpenseAgencyEntity.setBriefintroduction("珠穆朗玛峰");
        ownExpenseAgencyEntity.setItemCity("中国");
        ownExpenseAgencyEntity.setLimitNumber("限制为100人");
        ownExpenseAgencyEntity.setServiceItem("无需特殊服务");
        ownExpenseAgencyEntity.setReferencePrice("成人免费，儿童禁止");
        List<String> photos = new ArrayList<>();
        photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487859890803&di=66263afa95951d9f4985599f9353f93d&imgtype=0&src=http%3A%2F%2Fwww1.yj360.com%2Fup_files%2Fimages%2F2014-03-31%2F20140331115945-31877.jpg");
        photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487859890803&di=6d48c8b82bba15a1c450274cbaf127f5&imgtype=0&src=http%3A%2F%2Fimg1.tibet.cn%2Ftibet_cn%2Fholiday%2Fxxzx%2F201504%2FW020150421545495509443.jpg");
        photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487859890802&di=6f7ce8f2df00f078ff64ad72d126c3a7&imgtype=0&src=http%3A%2F%2Fpic.ffw.com.cn%2Fimage%2F1605%2F20160520151041_0.jpg");
        ownExpenseAgencyEntity.setPhotoUrls(photos);
        int insert = ownExpenseAgencyService.insert(ownExpenseAgencyEntity);
        System.out.println("插入"+insert+"条");
    }

    @Test
    public void testSelect() throws Exception {

    }

    @Test
    public void testUpdate() throws Exception {
        OwnExpenseAgencyEntity ownExpenseAgencyEntity = new OwnExpenseAgencyEntity();
        ownExpenseAgencyEntity.setId(2l);
        ownExpenseAgencyEntity.setTravelId(1l);
        ownExpenseAgencyEntity.setItemName("攀登世界第一高峰");
        ownExpenseAgencyEntity.setBriefintroduction("珠穆朗玛峰");
        ownExpenseAgencyEntity.setItemCity("中国");
        ownExpenseAgencyEntity.setLimitNumber("限制为200人");
        ownExpenseAgencyEntity.setServiceItem("无需特殊服务");
        ownExpenseAgencyEntity.setReferencePrice("成人免费，儿童禁止");
        List<String> photos = new ArrayList<>();
        photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487859890803&di=66263afa95951d9f4985599f9353f93d&imgtype=0&src=http%3A%2F%2Fwww1.yj360.com%2Fup_files%2Fimages%2F2014-03-31%2F20140331115945-31877.jpg");
        photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487859890803&di=6d48c8b82bba15a1c450274cbaf127f5&imgtype=0&src=http%3A%2F%2Fimg1.tibet.cn%2Ftibet_cn%2Fholiday%2Fxxzx%2F201504%2FW020150421545495509443.jpg");
        photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487859890802&di=6f7ce8f2df00f078ff64ad72d126c3a7&imgtype=0&src=http%3A%2F%2Fpic.ffw.com.cn%2Fimage%2F1605%2F20160520151041_0.jpg");
        ownExpenseAgencyEntity.setPhotoUrls(photos);
        int update = ownExpenseAgencyService.update(ownExpenseAgencyEntity);
        System.out.println("更新"+update+"条");
    }

    @Test
    public void testSelectOwnExpenseByName() throws Exception {
        OwnExpenseAgencyEntity ownExpenseAgencyEntity = new OwnExpenseAgencyEntity();
        ownExpenseAgencyEntity.setItemName("攀登世界第一高峰");
        ownExpenseAgencyEntity.setTravelId(1l);
        //ownExpenseAgencyEntity.setCities(ELUtil.strToList("中国"));
        List<OwnExpenseAgencyEntity> ownExpenseAgencyEntities = ownExpenseAgencyService.selectOwnExpenseByName(ownExpenseAgencyEntity);
        System.out.println(ownExpenseAgencyEntities);
    }

    @Test
    public void testSelectOwnExpenseBySameName() throws Exception {
        OwnExpenseAgencyEntity ownExpenseAgencyEntity = new OwnExpenseAgencyEntity();
        ownExpenseAgencyEntity.setItemName("攀登世界第一高峰");
        ownExpenseAgencyEntity.setItemCity("尼泊尔");
        ownExpenseAgencyEntity.setTravelId(1l);
        Boolean aBoolean = ownExpenseAgencyService.selectOwnExpenseBySameName(ownExpenseAgencyEntity);
        System.out.println(aBoolean);
    }

    @Test
    public void testSelectOwnExpenseAndAlbum() throws Exception {
        OwnExpenseAgencyEntity ownExpenseAgencyEntity = ownExpenseAgencyService.selectOwnExpenseAndAlbum(2l);
        System.out.println(ownExpenseAgencyEntity);
    }
}
*/
