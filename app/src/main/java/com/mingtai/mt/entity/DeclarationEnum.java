package com.mingtai.mt.entity;

import com.mingtai.mt.R;

/**
 * Created by LG on 2020/1/2.
 */
public enum DeclarationEnum {
    REGISTER(R.mipmap.ic_declaration_register,R.string.declaration_register),
    UPDATE(R.mipmap.ic_declaration_update,R.string.declaration_upgrade),
    SALE(R.mipmap.ic_declaration_sale,R.string.declaration_sale),
    TIAOBO(R.mipmap.ic_declaration_tiaobo,R.string.declaration_tiaobo);

    private int ImgSrc;
    private int DeclarationStr;

    DeclarationEnum(int ImgSrc,int declarationStr){
        this.ImgSrc = ImgSrc;
        this.DeclarationStr = declarationStr;
    }

    public int getImgSrc() {
        return ImgSrc;
    }

    public void setImgSrc(int imgSrc) {
        ImgSrc = imgSrc;
    }

    public int getDeclarationStr() {
        return DeclarationStr;
    }

    public void setDeclarationStr(int declarationStr) {
        DeclarationStr = declarationStr;
    }
}
