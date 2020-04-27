package com.mingtai.mt.activity;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.contract.WebviewContract;
import com.mingtai.mt.entity.ChangeIsWdBean;
import com.mingtai.mt.http.RetrofitHelper;
import com.mingtai.mt.presenter.WebviewPresenter;
import com.mingtai.mt.util.ActivityUtil;
import com.mingtai.mt.util.Eyes;
import com.mingtai.mt.util.MingtaiUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2020/1/13.
 */
public class WebviewActivity extends BaseActivity implements WebviewContract {

    @BindView(R.id.wv_home)
    WebView webView;
    @BindView(R.id.tv_home_webview)
    TextView tv_home_webview;
    @BindView(R.id.ll_add_bottom)
    LinearLayout ll_add_bottom;

    private WebviewPresenter webviewPresenter = new WebviewPresenter();
    private String type;

    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarWhiteColor(this, getResources().getColor(R.color.white));

        ActivityUtil.addAllActivity(this);
        webviewPresenter.onCreate(this,this);

        String categoryName = getIntent().getBundleExtra(MingtaiUtil.TYPEID).getString("CategoryName");
        String url = getIntent().getBundleExtra(MingtaiUtil.TYPEID).getString("URL");
        type = getIntent().getBundleExtra(MingtaiUtil.TYPEID).getString("type");

        tv_home_webview.setText(categoryName);

        if (type.equals("register")){
            ll_add_bottom.setVisibility(View.VISIBLE);
        }

        if (type.equals("agreement")){
            if (ProApplication.mAccountBean.getIsWd() == 0) {
                ll_add_bottom.setVisibility(View.VISIBLE);
            }
        }



        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient()
        {
            public boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView, String paramAnonymousString)
            {
                return false;
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient()
        {
            public boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView, String paramAnonymousString)
            {
                return false;
            }
        });


        if (type.equals("lgs")){
            url = RetrofitHelper.BASE_U + "/Order/LogisticsQuery/" + getIntent().getBundleExtra(MingtaiUtil.TYPEID).getString("ordersn");
        }

        if (type.equals("product")){
            url = RetrofitHelper.BASE_U + "/Order/LogisticsQuery/"+ getIntent().getBundleExtra(MingtaiUtil.TYPEID).getString("ordersn")
                    + "?OrderProductId=" + getIntent().getBundleExtra(MingtaiUtil.TYPEID).getString("OrderProductId");
        }
        webView.loadUrl(url);

    }

    @OnClick({R.id.tv_agree,R.id.ll_back})
    public void onClick(View view){
        switch (view.getId()) {

            case R.id.tv_agree:

                if (type.equals("register")) {
                    webviewPresenter.getWebview(ProApplication.SESSIONID(this));
                }else if (type.equals("agreement")){
                    webviewPresenter.changeIsWd(ProApplication.SESSIONID(this));
                }

                break;

            case R.id.ll_back:

                finish();

                break;
        }
    }

    @Override
    public void webviewSuccess(String msg) {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void webviewFail(String msg) {
        toast(msg);
    }

    @Override
    public void changeIsWdSuccess(String changeIsWd) {
        ProApplication.mAccountBean.setIsWd(2);
        toast(changeIsWd);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void changeIsWdFail(String msg) {
        toast(msg);
    }
}
