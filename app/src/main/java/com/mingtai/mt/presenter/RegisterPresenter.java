package com.mingtai.mt.presenter;
import android.app.ProgressDialog;
import android.content.Context;

import com.mingtai.mt.base.BasePresenter;
import com.mingtai.mt.contract.RegisterContract;
import com.mingtai.mt.entity.BankBean;
import com.mingtai.mt.entity.FriendsBean;
import com.mingtai.mt.entity.ResultBean;
import com.mingtai.mt.http.callback.HttpResultCallBack;
import com.mingtai.mt.manager.DataManager;
import com.mingtai.mt.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2019/12/25.
 */
public class RegisterPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private RegisterContract registerContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        registerContract = (RegisterContract) view;
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    /**
     * 注册
     * @param UserName
     * @param Mobile
     * @param PDTypeId
     * @param Resettlement
     * @param Referees
     * @param NickName
     * @param SurName
     * @param UserCode
     * @param UserType
     * @param BankName
     * @param BankNo
     * @param Subarea
     * @param ActivationPerson
     * @param prov
     * @param city
     * @param area
     * @param Address
     * @param SessionId
     */
    public void register(String UserName, String Mobile, String PDTypeId,String Resettlement,String Referees,String NickName, String SurName, String UserCode,
                         String UserType,String BankName,String BankNo,String Subarea,String ActivationPerson,String prov,String city,String area,
                         String Address,String Zip,String SessionId){
        final ProgressDialog progressDialog = ProgressDialog.show(mContext, "请稍等...", "注册中...", true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "UserBaseCreate");
        params.put("UserName", UserName);
        params.put("Mobile", Mobile);
        params.put("UserLevel", "0");
        params.put("PDTypeId", PDTypeId);
        if (Resettlement.equals("空")){
            Resettlement = "";
        }
        params.put("Resettlement", Resettlement);
        params.put("Referees", Referees);
        params.put("NickName", NickName);
        params.put("SurName", SurName);
        params.put("UserCode", UserCode);
        params.put("UserType", UserType);
        params.put("BankName", BankName);
        params.put("BankNo", BankNo);
        params.put("Subarea", Subarea);
        if (ActivationPerson.equals("空")){
            ActivationPerson = "";
        }
        params.put("ActivationPerson", ActivationPerson);
        params.put("prov", prov);
        params.put("city", city);
        params.put("area", area);
        params.put("Address", Address);
        params.put("Zip", Zip);
        params.put("SessionId", SessionId);

        mCompositeSubscription.add(manager.register(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String, Object>() {
                    @Override
                    public void onResponse(String goodsListBeans, String status, ResultBean<String,Object> page) {
                        registerContract.setDataSuccess(page.getDesc());

                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        registerContract.setDataFail(msg);

                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }));
    }

    /**
     * 点击亲友人获取信息
     * @param UserName
     * @param UserType
     * @param SessionId
     */
    public void queryName(String UserName, String UserType, String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "UserBaseQueryNameGet");
        params.put("UserName", UserName);
        params.put("UserType", UserType);
        params.put("SessionId", SessionId);

        mCompositeSubscription.add(manager.queryName(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<FriendsBean, Object>() {
                    @Override
                    public void onResponse(FriendsBean friendsBean, String status, ResultBean<FriendsBean, Object> o) {
                        registerContract.queryNameSuccess(friendsBean,status);

                    }

                    @Override
                    public void onErr(String msg, String status) {
                        registerContract.queryNameFail(msg);

                    }
                }));
    }

    /**
     * 点击服务人获取信息
     * @param UserName
     * @param UserType
     * @param SessionId
     */
    public void getRefereesName(String UserName, String UserType, String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "GetRefereesLeftSubarea");
        params.put("UserName", UserName);
        params.put("UserType", UserType);
        params.put("SessionId", SessionId);

        mCompositeSubscription.add(manager.getRefereesName(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String, Object>() {
                    @Override
                    public void onResponse(String str, String status, ResultBean<String,Object> o) {
                        registerContract.getRefereesNameSuccess(str,status);

                    }

                    @Override
                    public void onErr(String msg, String status) {
                        registerContract.getRefereesNameFail(msg);

                    }
                }));
    }

    public void getBankInfo(String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "BankNameList");
        params.put("SessionId", SessionId);

        mCompositeSubscription.add(manager.getBankInfo(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<BankBean>, Object>() {

                    @Override
                    public void onResponse(ArrayList<BankBean> addressBeans, String status, ResultBean<ArrayList<BankBean>,Object> page) {
                        registerContract.getBankInfoSuccess(addressBeans);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        registerContract.getBankInfoFail(msg);
                    }
                })
        );
    }
}
