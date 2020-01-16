package com.mingtai.mt.activity;

import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.adapter.LevelAdapter;
import com.mingtai.mt.adressselectorlib.AddressPickerView;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.contract.SaleContract;
import com.mingtai.mt.entity.BankBean;
import com.mingtai.mt.entity.FriendsBean;
import com.mingtai.mt.entity.StoreInfoAddressBean;
import com.mingtai.mt.presenter.SalePresenter;
import com.mingtai.mt.util.ActivityUtil;
import com.mingtai.mt.util.Eyes;
import com.mingtai.mt.util.MingtaiUtil;
import com.mingtai.mt.util.UiHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;

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
    @BindView(R.id.tv_servicer_name)
    TextView tv_servicer_name;
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
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.ll_province)
    LinearLayout ll_province;
    @BindView(R.id.ll_update_level)
    LinearLayout ll_update_level;
    @BindView(R.id.tv_update_level)
    TextView tv_update_level;

    private String send_type_str;
    private int typeInt = 1;
    private int PersonalInt = 1;
    private int StoreInt = 2;
    private int userLevel = 0;
    private int type = 0;
    private StoreInfoAddressBean personalInfoBean;
    private StoreInfoAddressBean storeInfoAddressBean;
    private String personalStr;
    private String mProvinceCode;
    private String mCityCode;
    private String mAreaCode;
    private String mZipCode;
    private FriendsBean friendsBean;
    private AddressPickerView addressView;

    private SalePresenter salePresenter = new SalePresenter();


    @Override
    public int getLayoutId() {
        return R.layout.activity_sale;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));
        salePresenter.onCreate(this,this);
        /*if (ProApplication.mAccountBean.getIsSingleCenter() > 0) {
            salePresenter.getStoreAddress(ProApplication.mAccountBean.getStoreNo(),ProApplication.SESSIONID(this));
        }*/

        ActivityUtil.addHomeActivity(this);
        ActivityUtil.addActivity(this);

        if (ProApplication.mAccountBean.getStoreNo() != null && ProApplication.mAccountBean.getStoreNo().toString().trim().length() > 0) {
            et_business_name.setText(ProApplication.mAccountBean.getStoreNo());
            et_business_name.setFocusable(false);
        }else {
            tv_servicer_name.setFocusable(false);
            et_servicer_id.setFocusable(false);
            et_servicer_id.setText(ProApplication.mAccountBean.getUserName());
            tv_servicer_name.setText(ProApplication.mAccountBean.getSurName() + "  当前级别：" + ProApplication.mAccountBean.getUserLevelName());
        }

        type = getIntent().getBundleExtra(MingtaiUtil.TYPEID).getInt("type");

        if (type == MingtaiUtil.SALEINT){
            tv_detail.setText(R.string.declaration_sale);
        }else if (type == MingtaiUtil.UPDATEINT){
            tv_detail.setText(R.string.declaration_upgrade);
            if (getIntent().getBundleExtra(MingtaiUtil.TYPEID).getString("id") != null && getIntent().getBundleExtra(MingtaiUtil.TYPEID).getString("id").toString().trim().length() > 0){
                et_servicer_id.setText(getIntent().getBundleExtra(MingtaiUtil.TYPEID).getString("id"));
                et_servicer_id.setFocusable(false);
                salePresenter.queryName(et_servicer_id.getText().toString(), "20", ProApplication.SESSIONID(SaleActivity.this));
            }

        }else if (type == MingtaiUtil.TIAOBOINT){
            tv_detail.setText(R.string.declaration_tiaobo);
        }

        et_business_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    if (storeInfoAddressBean == null) {
                        if (typeInt == StoreInt && MingtaiUtil.editIsNotNull(et_business_name)) {
                            salePresenter.getStoreAddress(et_business_name.getText().toString(), ProApplication.SESSIONID(SaleActivity.this));
                        }
                    } else {
                        getStoreAddressSuccess(storeInfoAddressBean);
                    }
                }
            }
        });

        et_servicer_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (typeInt == PersonalInt) {
                        salePresenter.getPersonalAddress(et_servicer_id.getText().toString(), ProApplication.SESSIONID(SaleActivity.this));
                    }
                    salePresenter.queryName(et_servicer_id.getText().toString(), "20", ProApplication.SESSIONID(SaleActivity.this));
                }
            }
        });

    }

    @OnClick({R.id.ll_send_type,R.id.tv_next,R.id.ll_back,R.id.ll_province,R.id.main_view,R.id.ll_update_level})
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

//                setNumberPickerTextColor(numberPicker,getResources().getColor(R.color.white));

                setType(numberPicker,s);

                //关闭编辑模式
                numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    //当NunberPicker的值发生改变时，将会激发该方法
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        typeInt = newVal+1;
                        send_type_str = s[newVal];
                        tv_send_type.setText(send_type_str);
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


                if (!MingtaiUtil.editIsNotNull(et_servicer_id)) {
                    saleToast(R.string.input_service_id);
                }else if (!MingtaiUtil.editIsNotNull(tv_send_type)){
                    saleToast(R.string.choose_send_type);
                }else if (!MingtaiUtil.editIsNotNull(et_address)){
                    saleToast(R.string.hint_input_address);
                }else if (!MingtaiUtil.editIsNotNull(tv_province)){
                    saleToast(R.string.hint_choose_address);
                }else if (!MingtaiUtil.editIsNotNull(et_sale_mobile)){
                    saleToast(R.string.hint_input_mobile);
                }

                if (type == MingtaiUtil.UPDATEINT) {
                    if (!MingtaiUtil.editIsNotNull(tv_update_level)) {
                        saleToast(R.string.hint_input_update_level);
                    }

                    if (tv_update_level.getText().toString().trim().length() == 0){
                        return;
                    }
                }



                if (ProApplication.mAccountBean.getStoreNo() != null && ProApplication.mAccountBean.getStoreNo().toString().trim().length() > 0) {

                    if (MingtaiUtil.editIsNotNull(tv_servicer_name)){
                        if ((typeInt == PersonalInt && personalInfoBean != null) || (typeInt == StoreInt && storeInfoAddressBean != null)) {
                            salePresenter.saleNext(et_business_name.getText().toString(), et_servicer_id.getText().toString());
                        }
                    }else {
                        if (MingtaiUtil.editIsNotNull(et_servicer_id)) {
                            salePresenter.queryName(et_servicer_id.getText().toString(), "20", ProApplication.SESSIONID(SaleActivity.this));
                        }
                    }
                }else {

                    if ((typeInt == PersonalInt && personalInfoBean != null) || (typeInt == StoreInt && storeInfoAddressBean != null)) {

                       if (!MingtaiUtil.editIsNotNull(et_business_name)){
                            saleNextSuccess("");
                       }else {
                           salePresenter.saleNext(et_business_name.getText().toString(), et_servicer_id.getText().toString());
                       }
                    }

                    if (typeInt == StoreInt && storeInfoAddressBean == null && MingtaiUtil.editIsNotNull(et_business_name)){
                        salePresenter.getStoreAddress(et_business_name.getText().toString(), ProApplication.SESSIONID(SaleActivity.this));
                    }
                }
                break;


            case R.id.ll_back:

                finish();

                break;

            case R.id.ll_province:

                final PopupWindow popupWindow1 = new PopupWindow(this);
                View rootView1 = LayoutInflater.from(this).inflate(R.layout.pop_address_picker, null, false);
                addressView = rootView1.findViewById(R.id.apvAddress);
                addressView.setOnAddressPickerSure(new AddressPickerView.OnAddressPickerSureListener() {
                    @Override
                    public void onSureClick(String address, String provinceCode, String cityCode, String districtCode, String zipCode) {
                        tv_province.setText(address);
                        mProvinceCode = provinceCode;
                        mCityCode = cityCode;
                        mAreaCode = districtCode;
                        mZipCode = zipCode;
                        popupWindow1.dismiss();
                    }

                    @Override
                    public void onExit() {
                        popupWindow1.dismiss();
                    }
                });
                popupWindow1.setContentView(rootView1);
                popupWindow1.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow1.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow1.setBackgroundDrawable(new BitmapDrawable());
                popupWindow1.setFocusable(true);
                popupWindow1.setOutsideTouchable(true);
                popupWindow1.showAtLocation(findViewById(R.id.main_view), Gravity.CENTER, 0, 0);
                popupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        /*if (imageView != null && imageView.isShown()) {
                            imageView.setVisibility(View.GONE);
                        }*/
                    }
                });

                break;

            case R.id.main_view:
                if (et_servicer_id.getText().toString().trim().length() > 0) {
                    salePresenter.queryName(et_servicer_id.getText().toString(), "20", ProApplication.SESSIONID(SaleActivity.this));
                }
                break;

            case R.id.ll_update_level:

                salePresenter.chooseUpdateLevel(ProApplication.SESSIONID(this));

                break;
        }
    }

    private void setType(NumberPicker numberPicker,String[] s){
        if (typeInt == 1) {
            ll_store.setVisibility(View.VISIBLE);
            send_type_str = s[0];
            numberPicker.setValue(0);
            ll_province.setFocusable(true);
            ll_province.setFocusableInTouchMode(true);
            ll_province.setClickable(true);
            if (et_servicer_id.getText().toString() == null || et_servicer_id.getText().toString().trim().length() == 0){
                eartyEdit();
            }

            if (personalStr == null || !personalStr.equals(et_servicer_id.getText().toString()) ) {
                if (et_servicer_id.getText().toString().trim().length() > 0) {
                    personalStr = et_servicer_id.getText().toString();
                    salePresenter.getPersonalAddress(et_servicer_id.getText().toString(), ProApplication.SESSIONID(this));

                    if (ProApplication.mAccountBean.getStoreNo() == null || ProApplication.mAccountBean.getStoreNo().toString().trim().length() == 0) {
                        salePresenter.queryName(et_servicer_id.getText().toString(), "20", ProApplication.SESSIONID(SaleActivity.this));
                    }

                }
            }else {
                getPersonalAddressSuccess(personalInfoBean);
            }

        }else if (typeInt == 2){
            send_type_str = s[1];
            numberPicker.setValue(1);
            ll_store.setVisibility(View.VISIBLE);
            ll_province.setFocusable(false);
            ll_province.setClickable(false);
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
    public void getStoreAddressSuccess(StoreInfoAddressBean storeInfoAddressBean) {
        this.storeInfoAddressBean = storeInfoAddressBean;
        et_sale_name.setFocusable(false);
        et_sale_name.setText(storeInfoAddressBean.getName());
        et_sale_mobile.setFocusable(false);
        et_sale_mobile.setText(storeInfoAddressBean.getMobile());
        tv_province.setText(storeInfoAddressBean.getProvince_name() + " " + storeInfoAddressBean.getCity_name() + " " + storeInfoAddressBean.getArea_name());
        et_address.setFocusable(false);
        et_address.setText(storeInfoAddressBean.getAddress());
    }

    @Override
    public void getStoreAddressFail(String msg) {
        toast(msg);
    }

    @Override
    public void getPersonalAddressSuccess(StoreInfoAddressBean personalInfoBean) {
        this.personalInfoBean = personalInfoBean;
        et_sale_name.setFocusable(true);
        et_sale_name.setFocusableInTouchMode(true);
        et_sale_name.requestFocus();
        et_sale_name.setText(personalInfoBean.getName());
        et_sale_mobile.setFocusable(true);
        et_sale_mobile.setFocusableInTouchMode(true);
        et_sale_mobile.requestFocus();
        et_sale_mobile.setText(personalInfoBean.getMobile());
        tv_province.setText(personalInfoBean.getProvince_name() + " " + personalInfoBean.getCity_name() + " " + personalInfoBean.getArea_name());
        et_address.setFocusable(true);
        et_address.setFocusableInTouchMode(true);
        et_address.requestFocus();
        et_address.setText(personalInfoBean.getAddress());
    }

    @Override
    public void getPersonalAddressFail(String msg) {
        toast(msg);
    }

    @Override
    public void saleNextSuccess(String str) {
        Bundle bundle = new Bundle();
        bundle.putInt("GoodsType",type);
        if (type == MingtaiUtil.UPDATEINT) {
            bundle.putInt("UserLevel", userLevel);
        }else {
            bundle.putInt("UserLevel", friendsBean.getUserLevel());
        }
        bundle.putInt("deliveryMethod",typeInt);
        StoreInfoAddressBean addressBean;
        if (typeInt == PersonalInt){
            addressBean = personalInfoBean;

            if (mProvinceCode != null ) {
                addressBean.setProv(Integer.valueOf(mProvinceCode));
                addressBean.setCity(Integer.valueOf(mCityCode));
                addressBean.setArea(Integer.valueOf(mAreaCode));
                addressBean.setPost(mZipCode);
            }
        }else {
            addressBean = storeInfoAddressBean;
        }
        String name = et_sale_name.getText().toString();
        addressBean.setName(name);
        addressBean.setMobile(et_sale_mobile.getText().toString());
        addressBean.setUserName(et_servicer_id.getText().toString());
        ProApplication.STORENO = et_business_name.getText().toString();
        bundle.putSerializable("StoreInfoAddressBean",addressBean);
        UiHelper.launcherBundle(this,GoodsActivity.class,bundle);
    }

    @Override
    public void saleNextFail(String msg) {
        toast(msg);
    }

    @Override
    public void queryNameSuccess(FriendsBean friendsBean, String code) {
        this.friendsBean = friendsBean;
        tv_servicer_name.setText(friendsBean.getNickName() + "  当前级别:" + friendsBean.getUserLevelName());

        if (type == MingtaiUtil.SALEINT || type == MingtaiUtil.TIAOBOINT){
            if (friendsBean.getUserLevel() == 0){
                tv_next.setVisibility(View.GONE);
                toast("此用户不能购物");
            }else {
                tv_next.setVisibility(View.VISIBLE);
            }
        }

        if (type == MingtaiUtil.UPDATEINT){
            ll_update_level.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void queryNameFail(String msg) {
        toast(msg);
    }

    @Override
    public void getLevelInfoSuccess(ArrayList<BankBean> getLevelInfoSuccess) {
        final ArrayList<BankBean> arrayList = new ArrayList<>();
        if (friendsBean != null){
            int forLength = 0;
            if (ProApplication.mAccountBean.isAgreement()){
                forLength = getLevelInfoSuccess.size();
            }else {
                forLength = getLevelInfoSuccess.size() - 2;
            }
            for (int i = 0; i < forLength;i++) {
                if(friendsBean.getUserLevel() <= getLevelInfoSuccess.get(i).getId()){

                    arrayList.add(getLevelInfoSuccess.get(i));
                }
            }
        }

        View contentView1 = LayoutInflater.from(this).inflate(R.layout.popup_list, null);

        ListView listView1 = (ListView) contentView1.findViewById(R.id.rv_list);
        listView1.setVisibility(View.VISIBLE);
        listView1.setAdapter(new LevelAdapter(this,arrayList));

        final PopupWindow popupWindow1 = new PopupWindow(contentView1,
                LinearLayout.LayoutParams.MATCH_PARENT,  LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow1.setContentView(contentView1);
        popupWindow1.setOutsideTouchable(true);
        popupWindow1.setBackgroundDrawable(new BitmapDrawable());
        popupWindow1.showAsDropDown(ll_update_level);
        popupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv_update_level.setText(arrayList.get(position).getBankName());
                userLevel = arrayList.get(position).getId();
                popupWindow1.dismiss();

            }
        });
    }

    @Override
    public void getLevelInfoFail(String msg) {

    }

    public void eartyEdit(){
        et_sale_name.setText("");
        et_sale_mobile.setText("");
        tv_province.setText("");
        et_address.setText("");
    }



    private void saleToast(int msg){
        toast(msg);
        return;
    }

    public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color) {
        boolean result = false;
        final int count = numberPicker.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = numberPicker.getChildAt(i);
            if (child instanceof EditText) {
                try {
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint) selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText) child).setTextColor(color);
                    numberPicker.invalidate();
                    result = true;
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}
