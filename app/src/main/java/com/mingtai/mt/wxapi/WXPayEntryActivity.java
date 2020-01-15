package com.mingtai.mt.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mingtai.mt.R;
import com.mingtai.mt.interf.IWxResultListener;
import com.mingtai.mt.util.MingtaiUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;
    public static IWxResultListener iWxResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, MingtaiUtil.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
		Log.d("WX", "onPayFinish, errCode = " + resp.errCode);

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {
                iWxResult.setWxSuccess();
                finish();
            } else {
                iWxResult.setWxFail();
                finish();
            }
        } else {
            finish();
        }
    }


    public static void setPayListener(IWxResultListener iWxResultListener) {
        iWxResult = iWxResultListener;
    }

}