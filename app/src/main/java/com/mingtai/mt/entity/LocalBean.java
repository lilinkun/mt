package com.mingtai.mt.entity;

/**
 * Created by lilinkun
 * on 2019/12/29
 */
public enum LocalBean {
    SERVER(1,"开新服务区"),
    HUALUO(0,"自动滑落");


    private int id;
    private String localStr;

    LocalBean(int id,String localStr)
    {
        this.id = id;
        this.localStr = localStr;
    }

    public String getLocalStr() {
        return localStr;
    }

    public void setLocalStr(String localStr) {
        this.localStr = localStr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
