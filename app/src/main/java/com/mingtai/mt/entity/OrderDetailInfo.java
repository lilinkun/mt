package com.mingtai.mt.entity;

public class OrderDetailInfo {

    private double MinCash;
    private OrderDetailBean Order;

    public double getMinCash() {
        return MinCash;
    }

    public void setMinCash(double minCash) {
        MinCash = minCash;
    }

    public OrderDetailBean getOrder() {
        return Order;
    }

    public void setOrder(OrderDetailBean order) {
        Order = order;
    }
}
