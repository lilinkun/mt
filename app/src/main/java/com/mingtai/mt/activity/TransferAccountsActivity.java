package com.mingtai.mt.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.contract.TransferAccountsContract;
import com.mingtai.mt.entity.FriendsBean;
import com.mingtai.mt.presenter.TransferAccountsPresenter;
import com.mingtai.mt.util.ActivityUtil;
import com.mingtai.mt.util.Eyes;
import com.mingtai.mt.util.MingtaiUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2020/1/10.
 */
public class TransferAccountsActivity extends BaseActivity implements TransferAccountsContract {

    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_sender)
    TextView tv_sender;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.et_no)
    EditText et_no;
    @BindView(R.id.et_price)
    EditText et_price;
    @BindView(R.id.et_psd)
    EditText et_psd;
    @BindView(R.id.tv_receive_name)
    TextView tv_receive_name;

    private TransferAccountsPresenter transferAccountsPresenter = new TransferAccountsPresenter();
    String queryName = "";
    String receiveName = "";
    boolean isName = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_transfer_accounts;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this, getResources().getColor(R.color.white));

        ActivityUtil.addAllActivity(this);
        String mtCoin = getIntent().getBundleExtra(MingtaiUtil.TYPEID).getString(MingtaiUtil.COIN);

        transferAccountsPresenter.onCreate(this,this);

        tv_price.setText(mtCoin);

        tv_sender.setText(ProApplication.mAccountBean.getUserName());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        tv_date.setText(simpleDateFormat.format(new Date()));

        et_no.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (et_no.getText().toString().trim().length() > 0) {
                        receiveName = et_no.getText().toString();
                        transferAccountsPresenter.queryName(et_no.getText().toString(), "0", ProApplication.SESSIONID(TransferAccountsActivity.this));
                    }else {
                        toast("请输入受让方编号");
                    }
                }
            }
        });
    }

    @OnClick({R.id.ll_back,R.id.right_now_transfer})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_back:

                setResult(RESULT_OK);
                finish();

                break;

            case R.id.right_now_transfer:

                if (!MingtaiUtil.editIsNotNull(et_no)){
                    toast("请填写受让方编号");
                    return;
                }
                if (!MingtaiUtil.editIsNotNull(et_price)){
                    toast("金额不能为空");
                    return;
                }

                if (!MingtaiUtil.editIsNotNull(et_psd)){
                    toast("密码不能为空");
                    return;
                }


                if (tv_receive_name.getText().toString().trim().length() > 0) {
                    isName = false;
                    transferAccountsPresenter.getTransferAccounts(et_price.getText().toString(),et_psd.getText().toString(),et_no.getText().toString(),"5",ProApplication.SESSIONID(this));
                }else{
                    isName = true;
                    transferAccountsPresenter.queryName(et_no.getText().toString(), "0", ProApplication.SESSIONID(this));
                }


                break;
        }
    }

    @Override
    public void getTransferAccountsSuccess(String msg) {
        toast(msg);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void getTransferAccountsFail(String msg) {
        toast(msg);
    }

    @Override
    public void queryNameSuccess(FriendsBean friendsBean, String code) {
        queryName = friendsBean.getNickName();
        tv_receive_name.setText(queryName);
        if (isName){
            isName = false;
            transferAccountsPresenter.getTransferAccounts(et_price.getText().toString(),et_psd.getText().toString(),et_no.getText().toString(),"5",ProApplication.SESSIONID(this));
        }
    }

    @Override
    public void queryNameFail(String msg) {
        toast(msg);
        queryName = "";
        tv_receive_name.setText("");
    }
}
