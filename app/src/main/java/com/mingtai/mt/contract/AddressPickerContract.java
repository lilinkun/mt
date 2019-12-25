package com.mingtai.mt.contract;


import com.mingtai.mt.entity.ProvinceBean;
import com.mingtai.mt.mvp.IView;

import java.util.ArrayList;

public interface AddressPickerContract extends IView {
    public void getDataSuccess(ArrayList<ProvinceBean> provinceBeans, int id);
    public void getDataFail(String msg);
}
