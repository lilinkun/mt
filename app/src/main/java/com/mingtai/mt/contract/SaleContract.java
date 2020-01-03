package com.mingtai.mt.contract;

import com.mingtai.mt.entity.BankBean;
import com.mingtai.mt.entity.FriendsBean;
import com.mingtai.mt.entity.StoreInfoAddressBean;
import com.mingtai.mt.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2020/1/2.
 */
public interface SaleContract extends IView {

    public void getStoreAddressSuccess(StoreInfoAddressBean storeInfoAddressBean);
    public void getStoreAddressFail(String msg);

    public void getPersonalAddressSuccess(StoreInfoAddressBean personalInfoBean);
    public void getPersonalAddressFail(String msg);

    public void saleNextSuccess(String str);
    public void saleNextFail(String msg);

    public void queryNameSuccess(FriendsBean friendsBean, String code);
    public void queryNameFail(String msg);

    public void getLevelInfoSuccess(ArrayList<BankBean> getLevelInfoSuccess);
    public void getLevelInfoFail(String msg);
}
