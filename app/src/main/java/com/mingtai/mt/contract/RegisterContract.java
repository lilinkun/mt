package com.mingtai.mt.contract;

import com.mingtai.mt.entity.FriendsBean;
import com.mingtai.mt.entity.PageBean;
import com.mingtai.mt.mvp.IView;

/**
 * Created by LG on 2019/12/25.
 */
public interface RegisterContract extends IView {
    public void setDataSuccess(String msg, PageBean pageBean);
    public void setDataFail(String msg);

    public void queryNameSuccess(FriendsBean friendsBean);
    public void queryNameFail(String msg);
}
