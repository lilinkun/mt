package com.mingtai.mt.manager;

import android.content.Context;

import com.mingtai.mt.entity.AccountBean;
import com.mingtai.mt.entity.FriendsBean;
import com.mingtai.mt.entity.PageBean;
import com.mingtai.mt.entity.ProvinceBean;
import com.mingtai.mt.entity.ResultBean;
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

    public Observable<ResultBean<String, PageBean>> homelist(HashMap<String,String> hashMap){
        return mRetrofitService.homelist(hashMap);
    }

    public Observable<ResultBean<FriendsBean, Object>> queryName(HashMap<String,String> hashMap){
        return mRetrofitService.queryName(hashMap);
    }

    public Observable<ResultBean<AccountBean,Object>> login(HashMap<String,String> mHashMap){
        return mRetrofitService.login(mHashMap);
    }

    /**
     * 获取收货区域
     */
    public Observable<ResultBean<ArrayList<ProvinceBean>,Object>> getLocalData(HashMap<String,String> mHashMap){
        return mRetrofitService.getLocalData(mHashMap);
    }


}
