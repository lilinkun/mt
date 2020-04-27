package com.mingtai.mt.entity;

import java.util.ArrayList;

/**
 * Created by LG on 2020/1/6.
 */
public class OrderDetailBean {

    private String OrderId;
    private String OrderSn;
    private String UserName;
    private int OrderStatus;
    private String OrderStatusName;
    private String Consignee;
    private String OrderTypeName;
    //升级2 消费 4  业绩调拨 8
    private int OrderType;
    private int Integral;
    private String Address;
    private String AddressName;
    private int deliveryMethod;
    private String deliveryMethodName;
    private String Mobile;
    private double PayFee;
    private double Money1;
    private double Money2;
    private double Money5;
    private double GoodsAmount;
    private String LgsName;
    private String LgsNumber;
    private double OrderAmount;
    private double ShippingFree;
    private String CreateDate;
    private String PayDate;
    private String BestTime;
    private int Province;
    private int City;
    private int Road;
    private int District;
    private String SingleCenterName;
    private String DrawName;
    private String Zip;
    private ArrayList<SelfOrderInfoBean> orderDetail;
    private ArrayList<ProductBean> listProduct;
    private String FreezeId;
    private int SendStatus;

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getOrderSn() {
        return OrderSn;
    }

    public void setOrderSn(String orderSn) {
        OrderSn = orderSn;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        OrderStatus = orderStatus;
    }

    public String getOrderStatusName() {
        return OrderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        OrderStatusName = orderStatusName;
    }

    public String getConsignee() {
        return Consignee;
    }

    public void setConsignee(String consignee) {
        Consignee = consignee;
    }

    public String getOrderTypeName() {
        return OrderTypeName;
    }

    public void setOrderTypeName(String orderTypeName) {
        OrderTypeName = orderTypeName;
    }

    public int getOrderType() {
        return OrderType;
    }

    public void setOrderType(int orderType) {
        OrderType = orderType;
    }

    public int getIntegral() {
        return Integral;
    }

    public void setIntegral(int integral) {
        Integral = integral;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getAddressName() {
        return AddressName;
    }

    public void setAddressName(String addressName) {
        AddressName = addressName;
    }

    public int getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(int deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getDeliveryMethodName() {
        return deliveryMethodName;
    }

    public void setDeliveryMethodName(String deliveryMethodName) {
        this.deliveryMethodName = deliveryMethodName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public double getPayFee() {
        return PayFee;
    }

    public void setPayFee(double payFee) {
        PayFee = payFee;
    }

    public double getMoney1() {
        return Money1;
    }

    public void setMoney1(double money1) {
        Money1 = money1;
    }

    public double getMoney2() {
        return Money2;
    }

    public void setMoney2(double money2) {
        Money2 = money2;
    }

    public double getMoney5() {
        return Money5;
    }

    public void setMoney5(double money5) {
        Money5 = money5;
    }

    public double getGoodsAmount() {
        return GoodsAmount;
    }

    public void setGoodsAmount(double goodsAmount) {
        GoodsAmount = goodsAmount;
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

    public double getOrderAmount() {
        return OrderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        OrderAmount = orderAmount;
    }

    public double getShippingFree() {
        return ShippingFree;
    }

    public void setShippingFree(double shippingFree) {
        ShippingFree = shippingFree;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getPayDate() {
        return PayDate;
    }

    public void setPayDate(String payDate) {
        PayDate = payDate;
    }

    public String getBestTime() {
        return BestTime;
    }

    public void setBestTime(String bestTime) {
        BestTime = bestTime;
    }

    public int getProvince() {
        return Province;
    }

    public void setProvince(int province) {
        Province = province;
    }

    public int getCity() {
        return City;
    }

    public void setCity(int city) {
        City = city;
    }

    public int getRoad() {
        return Road;
    }

    public void setRoad(int road) {
        Road = road;
    }

    public int getDistrict() {
        return District;
    }

    public void setDistrict(int district) {
        District = district;
    }

    public String getSingleCenterName() {
        return SingleCenterName;
    }

    public void setSingleCenterName(String singleCenterName) {
        SingleCenterName = singleCenterName;
    }

    public String getDrawName() {
        return DrawName;
    }

    public void setDrawName(String drawName) {
        DrawName = drawName;
    }

    public String getZip() {
        return Zip;
    }

    public void setZip(String zip) {
        Zip = zip;
    }

    public ArrayList<SelfOrderInfoBean> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(ArrayList<SelfOrderInfoBean> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public String getFreezeId() {
        return FreezeId;
    }

    public void setFreezeId(String freezeId) {
        FreezeId = freezeId;
    }

    public ArrayList<ProductBean> getListProduct() {
        return listProduct;
    }

    public void setListProduct(ArrayList<ProductBean> listProduct) {
        this.listProduct = listProduct;
    }

    public int getSendStatus() {
        return SendStatus;
    }

    public void setSendStatus(int sendStatus) {
        SendStatus = sendStatus;
    }
}
