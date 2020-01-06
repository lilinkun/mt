package com.mingtai.mt.entity;

import java.io.Serializable;

/**
 * Created by LG on 2020/1/4.
 */
public class CustomPriceBean implements Serializable {

    private String goodsTotalPrice;
    private String shippingFreePrice;
    private String point;
    private String goodsPrice;
    private int deliveryMethod;
    private int goodsType;
    private int userlevel;

    public CustomPriceBean(String goodsTotalPrice, String shippingFreePrice,String point,String goodsPrice,int deliveryMethod,int goodsType,int userlevel) {
        this.goodsTotalPrice = goodsTotalPrice;
        this.shippingFreePrice = shippingFreePrice;
        this.point = point;
        this.goodsPrice = goodsPrice;
        this.deliveryMethod = deliveryMethod;
        this.goodsType = goodsType;
        this.userlevel = userlevel;
    }

    public String getGoodsTotalPrice() {
        return goodsTotalPrice;
    }

    public void setGoodsTotalPrice(String goodsTotalPrice) {
        this.goodsTotalPrice = goodsTotalPrice;
    }

    public String getShippingFreePrice() {
        return shippingFreePrice;
    }

    public void setShippingFreePrice(String shippingFreePrice) {
        this.shippingFreePrice = shippingFreePrice;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public int getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(int deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public int getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(int goodsType) {
        this.goodsType = goodsType;
    }

    public int getUserlevel() {
        return userlevel;
    }

    public void setUserlevel(int userlevel) {
        this.userlevel = userlevel;
    }
}
