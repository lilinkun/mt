package com.mingtai.mt.entity;

import com.mingtai.mt.R;

/**
 * Created by LG on 2020/1/2.
 */
public enum DeclarationEnum {
    REGISTER(R.mipmap.ic_register,"会员注册"),
    SALE(R.mipmap.ic_register,"会员消费"),
    UPDATE(R.mipmap.ic_register,"会员升级"),
    TIAOBO(R.mipmap.ic_register,"业绩调拨");

    private int ImgSrc;
    private String DeclarationStr;

    DeclarationEnum(int ImgSrc,String declarationStr){
        this.ImgSrc = ImgSrc;
        this.DeclarationStr = declarationStr;
    }

    public int getImgSrc() {
        return ImgSrc;
    }

    public void setImgSrc(int imgSrc) {
        ImgSrc = imgSrc;
    }

    public String getDeclarationStr() {
        return DeclarationStr;
    }

    public void setDeclarationStr(String declarationStr) {
        DeclarationStr = declarationStr;
    }
}
