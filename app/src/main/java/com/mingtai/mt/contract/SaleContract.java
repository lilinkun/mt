package com.mingtai.mt.contract;

import com.mingtai.mt.entity.PersonalInfoBean;
import com.mingtai.mt.entity.StoreInfoAddressBean;
import com.mingtai.mt.mvp.IView;

/**
 * Created by LG on 2020/1/2.
 */
public interface SaleContract extends IView {
    public void getDataSuccess();
    public void getDataFail(String msg);

    public void getStoreAddressSuccess(StoreInfoAddressBean storeInfoAddressBean);
    public void getStoreAddressFail(String msg);

    public void getPersonalAddressSuccess(StoreInfoAddressBean personalInfoBean);
    public void getPersonalAddressFail(String msg);
}
