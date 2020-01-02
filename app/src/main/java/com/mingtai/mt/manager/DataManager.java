package com.mingtai.mt.manager;

import android.content.Context;

import com.mingtai.mt.entity.AccountBean;
import com.mingtai.mt.entity.BankBean;
import com.mingtai.mt.entity.FriendsBean;
import com.mingtai.mt.entity.HomeBean;
import com.mingtai.mt.entity.PageBean;
import com.mingtai.mt.entity.PersonalInfoBean;
import com.mingtai.mt.entity.ProvinceBean;
import com.mingtai.mt.entity.ResultBean;
import com.mingtai.mt.entity.StoreInfoAddressBean;
import com.mingtai.mt.http.RetrofitHelper;
import com.mingtai.mt.http.RetrofitService;

import java.util.ArrayList;
import java.util.HashMap;

import rx.Observable;


/**
 * Created by LG on 2018/11/27.
 */
public class DataManager {
    private RetrofitService mRetrofitService;

    public DataManager(Context context){
        this.mRetrofitService = RetrofitHelper.getInstance(context).getServer();
    }

    public Observable<ResultBean<String, Object>> register(HashMap<String,String> hashMap){
        return mRetrofitService.register(hashMap);
    }


    /**
     * 点击亲友人获取信息
     */
    public Observable<ResultBean<FriendsBean, Object>> queryName(HashMap<String,String> hashMap){
        return mRetrofitService.queryName(hashMap);
    }


    /**
     * 登录
     */
    public Observable<ResultBean<AccountBean,Object>> login(HashMap<String,String> mHashMap){
        return mRetrofitService.login(mHashMap);
    }


    /**
     * 点击服务人获取信息
     */
    public Observable<ResultBean<String, Object>> getRefereesName(HashMap<String,String> hashMap){
        return mRetrofitService.getRefereesName(hashMap);
    }


    /**
     * 获取收货区域
     */
    public Observable<ResultBean<ArrayList<ProvinceBean>,Object>> getLocalData(HashMap<String,String> mHashMap){
        return mRetrofitService.getLocalData(mHashMap);
    }


    /**
     * 获取银行信息
     */
    public Observable<ResultBean<ArrayList<BankBean>, Object>> getBankInfo(HashMap<String, String> mHashMap) {
        return mRetrofitService.getBankInfo(mHashMap);
    }

    /**
     * 获取全局设置
     */
    public Observable<ResultBean<HomeBean, Object>> getHomeSettingData(HashMap<String, String> mHashMap) {
        return mRetrofitService.getHomeSettingData(mHashMap);
    }


    /**
     * 获取全局设置
     */
    public Observable<ResultBean<HomeBean, Object>> getHomeData(HashMap<String, String> mHashMap) {
        return mRetrofitService.getHomeData(mHashMap);
    }

    /**
     * 获取店铺收货地址
     */
    public Observable<ResultBean<StoreInfoAddressBean, Object>> getStoreAddress(HashMap<String, String> mHashMap) {
        return mRetrofitService.getStoreAddress(mHashMap);
    }

    /**
     * 获取个人收货地址
     */
    public Observable<ResultBean<StoreInfoAddressBean, Object>> getPersonalAddress(HashMap<String, String> mHashMap) {
        return mRetrofitService.getPersonalAddress(mHashMap);
    }


}
