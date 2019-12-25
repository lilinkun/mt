package com.mingtai.mt.http;


import com.mingtai.mt.entity.PageBean;
import com.mingtai.mt.entity.ResultBean;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by LG on 2018/11/7.
 */

public interface RetrofitService {

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String, PageBean>> homelist(@FieldMap Map<String, String> map);


}
