package com.mingtai.mt.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductBean implements Parcelable {

    private String GoodsGG;
    private String GoodsId;
    private String GoodsImg;
    private String GoodsName;
    private int GoodsNum;
    private int IsPresell;
    private double GoodsPrice;
    private String GoodsSn;
    private String GoodsSpec;
    private String GoodsUnit;
    private String LgsName;
    private String LgsNumber;
    private String OrderId;
    private String OrderProductId;
    private String OrderProductStatus;
    private String OrderSn;
    private String ShippingDate;
    private String UserId;
    private String UserName;
    private String OrderProductStatusName;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public String getGoodsGG() {
        return GoodsGG;
    }

    public void setGoodsGG(String goodsGG) {
        GoodsGG = goodsGG;
    }

    public String getGoodsId() {
        return GoodsId;
    }

    public void setGoodsId(String goodsId) {
        GoodsId = goodsId;
    }

    public String getGoodsImg() {
        return GoodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        GoodsImg = goodsImg;
    }

    public String getGoodsName() {
        return GoodsName;
    }

    public void setGoodsName(String goodsName) {
        GoodsName = goodsName;
    }

    public int getGoodsNum() {
        return GoodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        GoodsNum = goodsNum;
    }

    public int getIsPresell() {
        return IsPresell;
    }

    public void setIsPresell(int isPresell) {
        IsPresell = isPresell;
    }

    public double getGoodsPrice() {
        return GoodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        GoodsPrice = goodsPrice;
    }

    public String getGoodsSn() {
        return GoodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        GoodsSn = goodsSn;
    }

    public String getGoodsSpec() {
        return GoodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        GoodsSpec = goodsSpec;
    }

    public String getGoodsUnit() {
        return GoodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        GoodsUnit = goodsUnit;
    }

    public String getLgsName() {
        return LgsName;
    }

    public void setLgsName(String lgsName) {
        LgsName = lgsName;
    }

    public String getLgsNumber() {
        return LgsNumber;
    }

    public void setLgsNumber(String lgsNumber) {
        LgsNumber = lgsNumber;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getOrderProductId() {
        return OrderProductId;
    }

    public void setOrderProductId(String orderProductId) {
        OrderProductId = orderProductId;
    }

    public String getOrderProductStatus() {
        return OrderProductStatus;
    }

    public void setOrderProductStatus(String orderProductStatus) {
        OrderProductStatus = orderProductStatus;
    }

    public String getOrderSn() {
        return OrderSn;
    }

    public void setOrderSn(String orderSn) {
        OrderSn = orderSn;
    }

    public String getShippingDate() {
        return ShippingDate;
    }

    public void setShippingDate(String shippingDate) {
        ShippingDate = shippingDate;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getOrderProductStatusName() {
        return OrderProductStatusName;
    }

    public void setOrderProductStatusName(String orderProductStatusName) {
        OrderProductStatusName = orderProductStatusName;
    }
}
