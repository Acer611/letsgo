package com.umessage.letsgo.service.api.team;

import com.umessage.letsgo.domain.po.team.ShoppingAgencyEntity;

import java.util.List;


/**
 * Created by zengguoqing on 2016/9/7.
 */
public interface IShoppingAgencyService {

    /**
     * Description:
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * Description:
     * @param shoppingAgencyEntity
     * @return int
     */
    int insert(ShoppingAgencyEntity shoppingAgencyEntity);

    /**
     * Description:
     * @param id
     * @return com.umessage.letsgo.domain.po.team.ShoppingAgencyEntity
     */
    ShoppingAgencyEntity select(Long id);

    /**
     * Description:
     * @return java.util.List<com.umessage.letsgo.domain.po.team.ShoppingAgencyEntity>
     */
    List<ShoppingAgencyEntity> selectAll();

    /**
     * Description:
     * @param shoppingAgencyEntity
     * @return int
     */
    int update(ShoppingAgencyEntity shoppingAgencyEntity);

    /**
     * 根据私有购物场所名称模糊查询
     * @param shoppingAgencyEntity
     * @return
     */
    List<ShoppingAgencyEntity> selectShoppingAgencyByName(ShoppingAgencyEntity shoppingAgencyEntity);

    /**
     * 检查是否有同名的购物场所
     * @param shoppingAgencyEntity
     * @return
     */
    Boolean selectHotelAgencyBySameName(ShoppingAgencyEntity shoppingAgencyEntity);

}
