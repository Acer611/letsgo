/*

package com.umessage.letsgo.service.impl.journey;

import com.umessage.letsgo.domain.po.journey.ShoppingScheduleEntity;
import com.umessage.letsgo.service.api.journey.IShoppingScheduleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

*/
/**
 * Created by ZhaoYidong on 2017/2/20.
 *//*


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-datasource.xml",
        "classpath:spring/spring-mybatis.xml",
        "classpath:spring/spring-beans.xml" })
public class ShoppingScheduleServiceImplTest {

    @Resource
    private IShoppingScheduleService shoppingScheduleService;

    @Test
    public void testDelete() throws Exception {
        int delete = shoppingScheduleService.delete(1l);
        System.out.println(delete);
    }

    @Test
    public void testInsert() throws Exception {
        ShoppingScheduleEntity shoppingScheduleEntity = new ShoppingScheduleEntity();
        shoppingScheduleEntity.setScheduleId(1l);
        shoppingScheduleEntity.setShoppingName("明星购物广场");
        shoppingScheduleEntity.setShoppingCity("香港");
        shoppingScheduleEntity.setSellProducts("明星首饰");
        shoppingScheduleEntity.setResidenceTime("两个小时");
        shoppingScheduleService.insert(shoppingScheduleEntity);
    }

    @Test
    public void testSelect() throws Exception {

    }

    @Test
    public void testUpdate() throws Exception {
        ShoppingScheduleEntity shoppingScheduleEntity = new ShoppingScheduleEntity();
        shoppingScheduleEntity.setId(1l);
        shoppingScheduleEntity.setScheduleId(1l);
        shoppingScheduleEntity.setShoppingName("新加坡国际购物广场");
        shoppingScheduleEntity.setShoppingCity("新加坡");
        shoppingScheduleEntity.setSellProducts("金条珠宝");
        shoppingScheduleEntity.setResidenceTime("两个小时");
        shoppingScheduleService.update(shoppingScheduleEntity);
    }

    @Test
    public void testSelectShoppingByScheduleDetailId() throws Exception {
        List<ShoppingScheduleEntity> shoppingScheduleEntities = shoppingScheduleService.selectShoppingByScheduleDetailId(1l);
        System.out.println(shoppingScheduleEntities);
    }

    @Test
    public void testSelectShoppingByScheduleId() throws Exception {
        List<ShoppingScheduleEntity> shoppingScheduleEntities = shoppingScheduleService.selectShoppingByScheduleId(1l);
        System.out.println(shoppingScheduleEntities);
    }

    @Test
    public void deleteShoppingByScheduleDetailId() throws Exception {
        int i = shoppingScheduleService.deleteShoppingByScheduleDetailId(2l);
        System.out.println(i);
    }

    @Test
    public void deleteShoppingByScheduleId() throws Exception {
        int i = shoppingScheduleService.deleteShoppingByScheduleId(2l);
        System.out.println(i);
    }

}
*/
