package com.mingtai.mt.contract;

import com.mingtai.mt.entity.HomeBean;
import com.mingtai.mt.entity.HomeMobileBean;
import com.mingtai.mt.mvp.IView;

/**
 * Created by LG on 2019/12/25.
 */
public interface HomeContract extends IView {


    public void getHomeSuccess(HomeMobileBean homeMobileBean);
    public void getHomeFail(String msg);
}
