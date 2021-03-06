package com.mingtai.mt.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LG on 2020/1/4.
 */
public class ChooseItemBean implements Parcelable {

    private String GoodsSn;
    private int num;
    private String GoodsId;

    public ChooseItemBean() {
    }

    protected ChooseItemBean(Parcel in) {
        GoodsSn = in.readString();
        num = in.readInt();
        GoodsId = in.readString();
    }

    public static final Creator<ChooseItemBean> CREATOR = new Creator<ChooseItemBean>() {
        @Override
        public ChooseItemBean createFromParcel(Parcel in) {
            return new ChooseItemBean(in);
        }

        @Override
        public ChooseItemBean[] newArray(int size) {
            return new ChooseItemBean[size];
        }
    };

    public String getGoodsId() {
        return GoodsId;
    }

    public void setGoodsId(String goodsId) {
        GoodsId = goodsId;
    }

    public String getGoodsSn() {
        return GoodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        GoodsSn = goodsSn;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(GoodsSn);
        dest.writeInt(num);
        dest.writeString(GoodsId);
    }
}