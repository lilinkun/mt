package com.mingtai.mt.contract;

import com.mingtai.mt.entity.BankBean;
import com.mingtai.mt.entity.UserBankBean;
import com.mingtai.mt.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2020/1/10.
 */
public interface BindCardContract extends IView {

    public void getBankInfoSuccess(ArrayList<BankBean> bankBeans);

    public void getBankInfoFail(String msg);

    public void upBankInfoSuccess(String info);

    public void upBankInfoFail(String msg);


    public void onSendVcodeSuccess();

    public void onSendVcodeFail(String str);


    public void getBankSuccess(UserBankBean userBankBean);

    public void getBankFail(String msg);
}
