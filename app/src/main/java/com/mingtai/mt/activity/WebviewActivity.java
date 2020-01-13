package com.mingtai.mt.activity;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.util.MingtaiUtil;

import butterknife.BindView;

/**
 * Created by LG on 2020/1/13.
 */
public class WebviewActivity extends BaseActivity {

    @BindView(R.id.wv_home)
    WebView webView;
    @BindView(R.id.tv_home_webview)
    TextView tv_home_webview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void initEventAndData() {

        String categoryName = getIntent().getBundleExtra(MingtaiUtil.TYPEID).getString("CategoryName");
        String url = getIntent().getBundleExtra(MingtaiUtil.TYPEID).getString("URL");

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
        webView.loadUrl(url);

    }
}
