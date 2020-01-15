package com.mingtai.mt.contract;

import com.mingtai.mt.entity.BankBean;
import com.mingtai.mt.entity.FriendsBean;
import com.mingtai.mt.entity.PageBean;
import com.mingtai.mt.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2019/12/25.
 */
public interface RegisterContract extends IView {
    public void setDataSuccess(String msg);
    public void setDataFail(String msg);

    public void queryNameSuccess(FriendsBean friendsBean,String code,int type);
    public void queryNameFail(String msg);

    public void getRefereesNameSuccess(String str,String code);
    public void getRefereesNameFail(String msg);


    public void getBankInfoSuccess(ArrayList<BankBean> bankBeans);
    public void getBankInfoFail(String msg);
}
