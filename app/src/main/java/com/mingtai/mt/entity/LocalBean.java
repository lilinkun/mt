package com.mingtai.mt.entity;

/**
 * Created by kai
 * on 2019/12/29
 */
public enum  LocalBean {
    SERVER("开新服务区"),
    HUALUO("自动滑落");


    private String localStr;

    LocalBean(String localStr) {
        this.localStr = localStr;
    }

    public String getLocalStr() {
        return localStr;
    }

    public void setLocalStr(String localStr) {
        this.localStr = localStr;
    }
}
