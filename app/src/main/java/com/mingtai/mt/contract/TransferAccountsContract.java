package com.mingtai.mt.contract;

import com.mingtai.mt.entity.FriendsBean;
import com.mingtai.mt.mvp.IView;

/**
 * Created by LG on 2020/1/10.
 */
public interface TransferAccountsContract extends IView {

    public void getTransferAccountsSuccess(String msg);
    public void getTransferAccountsFail(String msg);



    public void queryNameSuccess(FriendsBean friendsBean, String code);
    public void queryNameFail(String msg);
}
