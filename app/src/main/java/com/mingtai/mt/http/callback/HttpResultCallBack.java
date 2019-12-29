package com.mingtai.mt.http.callback;

import android.util.Log;

import com.google.gson.Gson;
import com.mingtai.mt.entity.ResultBean;
import com.mingtai.mt.http.factory.ResultException;
import com.mingtai.mt.util.MingtaiUtil;

import rx.Subscriber;

public abstract class HttpResultCallBack<M, T> extends Subscriber<ResultBean<M, T>> {

    /**
     * 请求返回
     */
    public abstract void onResponse(M m, String status, T page);

    public abstract void onErr(String msg, String status);

    /**
     * 请求完成
     */
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (e != null) {
            if (e instanceof ResultException) {
                ResultException err = (ResultException) e;
//                onErr(err.getErrMsg(), WlmUtil.RESULT_FAIL);
            } else {
//                onErr(e.getMessage(), WlmUtil.RESULT_FAIL);
                Log.d("HttpResultCallBack", "解析失败==：" + e.getMessage());
            }
        }
        onCompleted();
    }

    /**
     * Http请求失败
     */
    private void onHttpFail(String msg, String status) {
        onErr(msg, status);
    }

    @Override
    public void onNext(ResultBean<M, T> result) {
        String jsonResponse = new Gson().toJson(result);
        Log.d("HttpResultCallBack", "返回ok==：" + jsonResponse);
        if (result.getStatus().equals(MingtaiUtil.RESULT_SUCCESS)) {
            if (result.getData() == null) {
                onResponse(result.getData(), result.getCode(), result.getPage());
            } else {
                onResponse(result.getData(), MingtaiUtil.RESULT_SUCCESS, result.getPage());
            }
        } else {
            onHttpFail(result.getDesc(), MingtaiUtil.RESULT_FAIL + result.getCode());
        }
    }
}
