package com.mingtai.mt.activity;


import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.adapter.BankAdapter;
import com.mingtai.mt.adapter.ServerLocalAdapter;
import com.mingtai.mt.adressselectorlib.AddressPickerView;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.contract.RegisterContract;
import com.mingtai.mt.entity.BankBean;
import com.mingtai.mt.entity.FriendsBean;
import com.mingtai.mt.entity.LocalBean;
import com.mingtai.mt.entity.PageBean;
import com.mingtai.mt.presenter.RegisterPresenter;
import com.mingtai.mt.util.UiHelper;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/12/12.
 */
public class RegisterActivity extends BaseActivity implements RegisterContract {

    @BindView(R.id.tv_province)
    TextView tv_province;
    @BindView(R.id.et_friends_id)
    EditText et_friends_id;
    @BindView(R.id.tv_register)
    TextView tv_register;
    @BindView(R.id.et_register_name)
    EditText et_register_name;
    @BindView(R.id.et_nickname)
    EditText et_nickname;
    @BindView(R.id.tv_friends_name)
    TextView tv_friends_name;
    @BindView(R.id.ll_local_server)
    LinearLayout ll_local_server;
    @BindView(R.id.ll_server_local)
    LinearLayout ll_server_local;
    @BindView(R.id.tv_server_local)
    TextView tv_server_local;
    @BindView(R.id.et_serverer_id)
    EditText et_serverer_id;
    @BindView(R.id.rl_server_local)
    RelativeLayout rl_server_local;
    @BindView(R.id.et_reigster_idcard)
    EditText et_reigster_idcard;
    @BindView(R.id.tv_reigster_bank)
    TextView tv_reigster_bank;
    @BindView(R.id.et_register_bank_account)
    EditText et_register_bank_account;
    @BindView(R.id.et_register_mobile)
    EditText et_register_mobile;
    @BindView(R.id.ll_bank)
    LinearLayout ll_bank;
    @BindView(R.id.et_local_server)
    EditText et_local_server;
    @BindView(R.id.ll_server_id)
    LinearLayout ll_server_id;
    @BindView(R.id.tv_serverer)
    TextView tv_serverer;
    @BindView(R.id.et_address)
    EditText et_address;

    private AddressPickerView addressView;
    private String localStr;
    private String mProvinceCode;
    private String mCityCode;
    private String mAreaCode;
    private String mZipCode;
    private BankBean bankBean;
    private LocalBean localBean;
    private PopupWindow bankPopupWindow;
    private ArrayList<EditText> editTexts = new ArrayList<>();
    private boolean isRegister = true;
    private String code = "0";
    RegisterPresenter registerPresenter = new RegisterPresenter();

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initEventAndData() {

        registerPresenter.onCreate(this,this);

        setEditString(et_register_name);
        setEditString(et_friends_id);
        setEditString(et_reigster_idcard);
        setEditString(et_register_bank_account);
        setEditString(et_register_mobile);
        setEditString(et_address);

        et_register_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if (et_register_name.getText().toString().trim().length() == 0){
                        toast("请输入姓名");
                    }
                }
            }
        });

        et_friends_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if (et_friends_id.getText().toString().trim().length() > 0) {
                        registerPresenter.queryName(et_friends_id.getText().toString(), "0", ProApplication.SESSIONID(RegisterActivity.this));
                    }else {
                        toast("请输入亲友人编号");
                    }
                }
            }
        });

        et_local_server.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if (et_local_server.getText().toString().trim().length() > 0) {
                        registerPresenter.getRefereesName(et_local_server.getText().toString(),"0", ProApplication.SESSIONID(RegisterActivity.this));
                    }else {
                        toast("请输入服务区域编号");
                    }
                }
            }
        });
    }

    @Override
    public void setDataSuccess(String msg) {
        Bundle bundle = new Bundle();
        bundle.putInt("type",2);
        bundle.putString("id",et_serverer_id.getText().toString());
        UiHelper.launcherBundle(this,SaleActivity.class,bundle);
        finish();
    }

    @Override
    public void setDataFail(String msg) {
        toast(msg);
    }

    @Override
    public void queryNameSuccess(FriendsBean friendsBean,String code) {
        toast(friendsBean.getNickName());
        tv_friends_name.setText(friendsBean.getNickName());
        ll_local_server.setVisibility(View.VISIBLE);
        ll_server_local.setVisibility(View.VISIBLE);
        ll_server_id.setVisibility(View.VISIBLE);
        this.code = code;

        if (code.equals("-1")){
            localBean = LocalBean.HUALUO;
            tv_server_local.setText(LocalBean.HUALUO.getLocalStr());
            et_serverer_id.setText("空");
            et_serverer_id.setFocusable(false);
            et_serverer_id.setClickable(false);
            rl_server_local.setClickable(false);
            et_local_server.setFocusable(false);
            et_local_server.setText("空");
            registerPresenter.getRefereesName(et_friends_id.getText().toString(),"0", ProApplication.SESSIONID(RegisterActivity.this));
        }else if (code.equals("0")){
            localBean = LocalBean.SERVER;
            et_serverer_id.setText(et_friends_id.getText().toString());
            tv_server_local.setText(LocalBean.SERVER.getLocalStr());
            tv_serverer.setText(friendsBean.getNickName());
            rl_server_local.setClickable(false);
            et_serverer_id.setFocusable(false);
            et_local_server.setText("空");
            et_local_server.setFocusable(false);
        }else {
            rl_server_local.setClickable(true);
            et_serverer_id.setFocusable(true);
            et_local_server.setFocusable(true);
            et_serverer_id.setClickable(true);
        }
    }

    @Override
    public void queryNameFail(String msg) {
        toast(msg);
    }

    @Override
    public void getRefereesNameSuccess(String friendsBean, String code) {
        et_serverer_id.setText(friendsBean);
    }

    @Override
    public void getRefereesNameFail(String msg) {
        toast(msg);
    }

    @Override
    public void getBankInfoSuccess(ArrayList<BankBean> bankBeans) {
        initPop(bankBeans);
    }

    @Override
    public void getBankInfoFail(String msg) {
        toast(msg);
    }

    @OnClick({R.id.ll_province,R.id.rl_server_local,R.id.ll_back,R.id.ll_bank,R.id.tv_register})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_province:
                final PopupWindow popupWindow = new PopupWindow(this);
                View rootView = LayoutInflater.from(this).inflate(R.layout.pop_address_picker, null, false);
                addressView = rootView.findViewById(R.id.apvAddress);
                addressView.setOnAddressPickerSure(new AddressPickerView.OnAddressPickerSureListener() {
                    @Override
                    public void onSureClick(String address, String provinceCode, String cityCode, String districtCode, String zipCode) {
                        tv_province.setText(address);
                        mProvinceCode = provinceCode;
                        mCityCode = cityCode;
                        mAreaCode = districtCode;
                        mZipCode = zipCode;
                        popupWindow.dismiss();
                    }

                    @Override
                    public void onExit() {
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
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        /*if (imageView != null && imageView.isShown()) {
                            imageView.setVisibility(View.GONE);
                        }*/
                    }
                });
                break;

            case R.id.rl_server_local:

                if (!code.equals("0") && !code.equals("-1")) {
                    registerPresenter.getRefereesName(et_local_server.getText().toString(), "0", ProApplication.SESSIONID(RegisterActivity.this));
                }

                View contentView1 = LayoutInflater.from(this).inflate(R.layout.popup_list, null);

                ListView listView1 = (ListView) contentView1.findViewById(R.id.rv_list);
                listView1.setVisibility(View.VISIBLE);
                listView1.setAdapter(new ServerLocalAdapter(this));

                final PopupWindow popupWindow1 = new PopupWindow(contentView1,
                        LinearLayout.LayoutParams.MATCH_PARENT,  LinearLayout.LayoutParams.WRAP_CONTENT, true);
                popupWindow1.setContentView(contentView1);
                popupWindow1.setOutsideTouchable(true);
                popupWindow1.setBackgroundDrawable(new BitmapDrawable());
                popupWindow1.showAsDropDown(ll_server_local);
                popupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        tv_server_local.setText(localStr);
                    }
                });
                listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        localStr = LocalBean.values()[position].getLocalStr();
                        localBean = LocalBean.values()[position];
                        popupWindow1.dismiss();

                    }
                });

                break;

            case R.id.ll_back:

                finish();

                break;

            case R.id.ll_bank:

                registerPresenter.getBankInfo(ProApplication.SESSIONID(this));

                break;

            case R.id.tv_register:

                registerPresenter.register(et_register_name.getText().toString(),et_nickname.getText().toString(),"1",et_local_server.getText().toString(),
                        et_friends_id.getText().toString(),et_nickname.getText().toString(),et_register_name.getText().toString(),et_reigster_idcard.getText().toString(),
                        "0",bankBean.getId()+"",et_register_bank_account.getText().toString(),localBean.getId()+"",et_serverer_id.getText().toString(),
                        mProvinceCode,mCityCode,mAreaCode,et_address.getText().toString(),mZipCode,ProApplication.SESSIONID(this));

                break;
        }
    }


    String editStr = "";
    public String setEditString(EditText editString){
        editTexts.add(editString);
        editString.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editStr = s.toString();
                isRegister = true;
                for (int i = 0;i < editTexts.size(); i++){
                    if(editTexts.get(i).getText().toString().trim().length() == 0){
                        isRegister = false;
                    }
                }
                if (tv_reigster_bank.getText().toString().trim().length() == 0 || tv_province.getText().toString().trim().length() == 0){
                    isRegister = false;
                }

                if (isRegister){
                    tv_register.setBackgroundColor(getResources().getColor(R.color.green));
                    tv_register.setClickable(true);
                }else {
                    tv_register.setBackgroundColor(getResources().getColor(R.color.line));
                    tv_register.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return editStr;
    }

    public void initPop(final ArrayList<BankBean> bankBeans) {
        View rootview = LayoutInflater.from(this).inflate(R.layout.pop_layout, null);
        RecyclerView recyclerView = rootview.findViewById(R.id.rv_bank);
        RelativeLayout relativeLayout = rootview.findViewById(R.id.rl_choose_bank);
        BankAdapter bankAdapter = new BankAdapter(this, bankBeans);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bankPopupWindow != null) {
                    bankPopupWindow.dismiss();
                }
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
        });

        recyclerView.setAdapter(bankAdapter);

        bankPopupWindow = new PopupWindow(rootview,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);

        bankPopupWindow.setOutsideTouchable(true);
        bankPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        bankAdapter.setOnItemClick(new BankAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                tv_reigster_bank.setText(bankBeans.get(position).getBankName());
                bankBean = bankBeans.get(position);
                bankPopupWindow.dismiss();
            }
        });

        bankPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                /*if (iv_pop_show.isShown()) {
                    iv_pop_show.setVisibility(View.GONE);
                }*/
            }
        });

        bankPopupWindow.showAtLocation(findViewById(R.id.main_view), Gravity.CENTER, 0, 0);

//           new OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//
//                tv_bank_name.setText(GrouponType.values()[position].getTypeName());
//
//                popupWindow.dismiss();
//            }
//        });
    }

}
