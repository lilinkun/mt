package com.mingtai.mt.activity;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Build;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.contract.GoodsDetailContract;
import com.mingtai.mt.entity.GoodsBean;
import com.mingtai.mt.presenter.GoodsDetailPresenter;
import com.mingtai.mt.ui.CustomBannerView;
import com.mingtai.mt.util.ActivityUtil;
import com.mingtai.mt.util.Eyes;
import com.mingtai.mt.util.MingtaiUtil;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2020/1/10.
 */
public class GoodsDetailActivity extends BaseActivity implements GoodsDetailContract {

    @BindView(R.id.tv_goods_price)
    TextView tv_goods_price;
    @BindView(R.id.tv_goods_old_price)
    TextView tv_goods_old_price;
    @BindView(R.id.tv_number)
    TextView tv_number;
    @BindView(R.id.tv_goods_name)
    TextView tv_goods_name;
    @BindView(R.id.img_good_pic)
    Banner banner;
    @BindView(R.id.wv_goods_detail)
    WebView wv_goods_detail;

    private GoodsDetailPresenter goodsDetailPresenter = new GoodsDetailPresenter();

    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_detail;
    }

    @Override
    public void initEventAndData() {
        Eyes.translucentStatusBar(this, false);

        ActivityUtil.addAllActivity(this);
        goodsDetailPresenter.onCreate(this,this);

        String str = getIntent().getBundleExtra(MingtaiUtil.TYPEID).getString("GOODSID");

        goodsDetailPresenter.getGoodsDetail(str, ProApplication.SESSIONID(this));

        wv_goods_detail.getSettings().setJavaScriptEnabled(true);
        wv_goods_detail.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //
        wv_goods_detail.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH); //
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wv_goods_detail.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
//        wv_goods_detail.setVisibility(View.GONE);
        wv_goods_detail.setWebViewClient(new WebViewClient() {
            //覆盖shouldOverrideUrlLoading 方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        wv_goods_detail.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

            }
        });

    }

    @Override
    public void getDataSuccess(GoodsBean goodsBean) {
        tv_goods_price.setText(goodsBean.getPrice()+"");
        tv_goods_old_price.setText(goodsBean.getMarketPrice()+"");
        tv_goods_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tv_number.setText(goodsBean.getIntegral() + "");
        tv_goods_name.setText(goodsBean.getGoodsName());

        ArrayList<String> list_path = new ArrayList<>();
        String[] strings;
        if (goodsBean.getGoodsImgList().contains(",")) {
            strings = goodsBean.getGoodsImgList().split(",");
        } else {
            strings = new String[1];
            strings[0] = goodsBean.getGoodsImgList();
        }

        for (int i = 0; i < strings.length; i++) {
            list_path.add(strings[i]);
        }
        CustomBannerView.startB(list_path, banner, this, true);

        String mobileDesc = goodsBean.getMobileDesc();

        wv_goods_detail.loadUrl(mobileDesc);
    }

    @Override
    public void getDataFail(String msg) {

    }

    @OnClick({R.id.ll_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_back:

                finish();

                break;
        }
    }
}
