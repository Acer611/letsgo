package com.umessage.letsgo.domain.vo.journey.response.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by ZhaoYidong on 2016/6/7.
 */
public class ShopPlaceVo implements Serializable {
    /**
     * 购物场所
     */
    @ApiModelProperty(value="购物场所")
    private String shoppingPlace;

    public String getShoppingPlace() {
        return shoppingPlace;
    }

    public void setShoppingPlace(String shoppingPlace) {
        this.shoppingPlace = shoppingPlace;
    }
}
