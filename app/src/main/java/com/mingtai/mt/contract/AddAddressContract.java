package com.mingtai.mt.contract;

import com.mingtai.mt.entity.ProvinceBean;
import com.mingtai.mt.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2020/1/10.
 */
public interface AddAddressContract extends IView {
    public void getDataSuccess(ArrayList<ProvinceBean> provinceBeans, int id);

    public void getDataFail(String msg);

    public void getSaveSuccess();

    public void getSaveFail(String msg);

    public void modifySuccess();

    public void modifyFail(String msg);
}
