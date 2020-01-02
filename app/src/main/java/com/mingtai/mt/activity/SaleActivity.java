package com.mingtai.mt.activity;

import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.contract.SaleContract;
import com.mingtai.mt.entity.PersonalInfoBean;
import com.mingtai.mt.entity.StoreInfoAddressBean;
import com.mingtai.mt.presenter.SalePresenter;
import com.mingtai.mt.util.MingtaiUtil;
import com.mingtai.mt.util.UiHelper;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lilinkun
 * on 2019/12/29
 */
public class SaleActivity extends BaseActivity implements SaleContract {

    @BindView(R.id.tv_send_type)
    TextView tv_send_type;
    @BindView(R.id.ll_personal)
    LinearLayout ll_personal;
    @BindView(R.id.ll_store)
    LinearLayout ll_store;
    @BindView(R.id.tv_detail)
    TextView tv_detail;
    @BindView(R.id.et_servicer_id)
    EditText et_servicer_id;
    @BindView(R.id.et_servicer_name)
    TextView et_servicer_name;
    @BindView(R.id.et_business_name)
    EditText et_business_name;
    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.et_sale_name)
    EditText et_sale_name;
    @BindView(R.id.et_sale_mobile)
    EditText et_sale_mobile;
    @BindView(R.id.tv_province)
    TextView tv_province;
    @BindView(R.id.tv_address)
    TextView tv_address;

    private String send_type_str;
    private int typeInt = 0;
    private int PersonalInt = 0;
    private int StoreInt = 1;
    private StoreInfoAddressBean personalInfoBean;
    private StoreInfoAddressBean storeInfoAddressBean;
    private String personalStr;

    private SalePresenter salePresenter = new SalePresenter();


    @Override
    public int getLayoutId() {
        return R.layout.activity_sale;
    }

    @Override
    public void initEventAndData() {

        salePresenter.onCreate(this,this);
        /*if (ProApplication.mAccountBean.getIsSingleCenter() > 0) {
            salePresenter.getStoreAddress(ProApplication.mAccountBean.getStoreNo(),ProApplication.SESSIONID(this));
        }*/

        int type = getIntent().getBundleExtra(MingtaiUtil.TYPEID).getInt("type");

        if (type == 1){
            tv_detail.setText("保单详情(消费-经销商)");
            et_servicer_id.setText(ProApplication.mAccountBean.getUserName());
            et_servicer_name.setText(ProApplication.mAccountBean.getNickName());
        }else if (type == 2){
            tv_detail.setText("消费订单(升级-经销商)");
            if (getIntent().getBundleExtra(MingtaiUtil.TYPEID).getString("id") != null && getIntent().getBundleExtra(MingtaiUtil.TYPEID).getString("id").toString().trim().length() > 0){
                et_servicer_id.setText(getIntent().getBundleExtra(MingtaiUtil.TYPEID).getString("id"));
                et_servicer_id.setFocusable(false);
            }

        }else if (type == 3){
            tv_detail.setText("保单详情(业绩调拨-经销商)");
            et_servicer_id.setText(ProApplication.mAccountBean.getUserName());
            et_servicer_name.setText(ProApplication.mAccountBean.getNickName());
        }

        et_business_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (storeInfoAddressBean == null) {
                    salePresenter.getStoreAddress(et_business_name.getText().toString(), ProApplication.SESSIONID(SaleActivity.this));
                }else {
                    getStoreAddressSuccess(storeInfoAddressBean);
                }
            }
        });

        et_servicer_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (typeInt == PersonalInt) {
                    salePresenter.getPersonalAddress(et_servicer_id.getText().toString(), ProApplication.SESSIONID(SaleActivity.this));
                }
            }
        });

    }

    @OnClick({R.id.ll_send_type,R.id.tv_next,R.id.ll_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_send_type:
                ll_personal.setVisibility(View.VISIBLE);
                final String[] s = new String[]{"配送到个人","配送到商铺"};

                final PopupWindow popupWindow = new PopupWindow(this);
                View rootView = LayoutInflater.from(this).inflate(R.layout.pop_sale,null,false);
                final NumberPicker numberPicker = rootView.findViewById(R.id.np_sale);
                TextView tv_over = rootView.findViewById(R.id.tv_over);
                numberPicker.setDisplayedValues(s);
                numberPicker.setMaxValue(s.length-1);
                numberPicker.setMinValue(0);

                setType(numberPicker,s);

                //关闭编辑模式
                numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    //当NunberPicker的值发生改变时，将会激发该方法
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        typeInt = newVal;
                        send_type_str = s[newVal];
                        setType(numberPicker,s);
                    }
                });
                tv_over.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tv_send_type.setText(send_type_str);
                        popupWindow.dismiss();
                    }
                });
                popupWindow.setContentView(rootView);
                popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.showAtLocation(findViewById(R.id.main_view), Gravity.CENTER, 0, 0);

                break;

            case R.id.tv_next:

                UiHelper.launcher(this,GoodsActivity.class);

                break;


            case R.id.ll_back:

                finish();

                break;
        }
    }

    private void setType(NumberPicker numberPicker,String[] s){
        if (typeInt == 0) {
            ll_store.setVisibility(View.GONE);
            send_type_str = s[0];
            numberPicker.setValue(0);

            if (et_servicer_id.getText().toString() == null || et_servicer_id.getText().toString().trim().length() == 0){
                eartyEdit();
            }

            if (personalStr == null || !personalStr.equals(et_servicer_id.getText().toString()) ) {
                if (et_servicer_id.getText().toString().trim().length() > 0) {
                    personalStr = et_servicer_id.getText().toString();
                    salePresenter.getPersonalAddress(et_servicer_id.getText().toString(), ProApplication.SESSIONID(this));
                }
            }

        }else if (typeInt == 1){
            send_type_str = s[1];
            numberPicker.setValue(1);
            ll_store.setVisibility(View.VISIBLE);
            et_business_name.setFocusable(false);
            if (ProApplication.mAccountBean.getIsSingleCenter() > 0) {
                et_business_name.setText(ProApplication.mAccountBean.getStoreNo());
            }

            if (storeInfoAddressBean == null) {
                if (et_business_name.getText().toString().trim().length() > 0) {
                    salePresenter.getStoreAddress(et_business_name.getText().toString(), ProApplication.SESSIONID(this));
                }
            }else {
                getStoreAddressSuccess(storeInfoAddressBean);
            }

        }
    }

    @Override
    public void getDataSuccess() {

    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void getStoreAddressSuccess(StoreInfoAddressBean storeInfoAddressBean) {
        this.storeInfoAddressBean = storeInfoAddressBean;
        et_sale_name.setFocusable(false);
        et_sale_name.setText(storeInfoAddressBean.getName());
        et_sale_mobile.setFocusable(false);
        et_sale_mobile.setText(storeInfoAddressBean.getPhone());
        tv_province.setFocusable(false);
        tv_province.setText(storeInfoAddressBean.getProvince_name() + " " + storeInfoAddressBean.getCity_name() + " " + storeInfoAddressBean.getArea_name());
        tv_address.setFocusable(false);
        tv_address.setText(storeInfoAddressBean.getAddress());
    }

    @Override
    public void getStoreAddressFail(String msg) {
        toast(msg);
    }

    @Override
    public void getPersonalAddressSuccess(StoreInfoAddressBean personalInfoBean) {
        this.personalInfoBean = personalInfoBean;
        et_sale_name.setFocusable(true);
        et_sale_name.setText(personalInfoBean.getName());
        et_sale_mobile.setFocusable(true);
        et_sale_mobile.setText(storeInfoAddressBean.getPhone());
        tv_province.setFocusable(true);
        tv_province.setText(storeInfoAddressBean.getProvince_name() + " " + storeInfoAddressBean.getCity_name() + " " + storeInfoAddressBean.getArea_name());
        tv_address.setFocusable(true);
        tv_address.setText(storeInfoAddressBean.getAddress());
    }

    @Override
    public void getPersonalAddressFail(String msg) {
        toast(msg);
    }

    public void eartyEdit(){
        et_sale_name.setText("");
        et_sale_mobile.setText("");
        tv_province.setText("");
        tv_address.setText("");
    }
}
