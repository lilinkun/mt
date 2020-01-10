package com.mingtai.mt.contract;

import com.mingtai.mt.entity.AddressBean;
import com.mingtai.mt.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2020/1/10.
 */
public interface ChooseAddressContract extends IView {
    public void setDataSuccess(ArrayList<AddressBean> addressBeanArrayList);

    public void setDataFail(String msg);

    public void deleteSuccess();

    public void deleteFail(String msg);

    public void isDefaultSuccess(String isDefaultStr);

    public void isDefaultFail(String msg);
}
