package com.mingtai.mt.contract;

import com.mingtai.mt.entity.ChangeIsWdBean;
import com.mingtai.mt.mvp.IView;

/**
 * Created by LG on 2020/1/14.
 */
public interface WebviewContract extends IView {

    public void webviewSuccess(String msg);
    public void webviewFail(String msg);

    public void changeIsWdSuccess(String changeIsWd);
    public void changeIsWdFail(String msg);

}
