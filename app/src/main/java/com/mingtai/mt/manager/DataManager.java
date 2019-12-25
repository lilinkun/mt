package com.mingtai.mt.manager;

import android.content.Context;

import com.mingtai.mt.entity.PageBean;
import com.mingtai.mt.entity.ResultBean;
import com.mingtai.mt.http.RetrofitHelper;
import com.mingtai.mt.http.RetrofitService;

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

}
