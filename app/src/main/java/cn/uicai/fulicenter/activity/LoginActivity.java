package cn.uicai.fulicenter.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.uicai.fulicenter.I;
import cn.uicai.fulicenter.R;
import cn.uicai.fulicenter.bean.Result;
import cn.uicai.fulicenter.bean.User;
import cn.uicai.fulicenter.net.NetDao;
import cn.uicai.fulicenter.net.OkHttpUtils;
import cn.uicai.fulicenter.utils.CommonUtils;
import cn.uicai.fulicenter.utils.L;
import cn.uicai.fulicenter.utils.MFGT;
import cn.uicai.fulicenter.utils.ResultUtils;

public class LoginActivity extends BaseActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.username)
    EditText musername;
    @BindView(R.id.password)
    EditText mpassword;

    String username;
    String password;
    LoginActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mContext = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                checkInput();
                break;
            case R.id.btn_register:
                MFGT.gotoRegister(this);
                break;
        }
    }

    private void checkInput() {
        username = musername.getText().toString().trim();
        password = mpassword.getText().toString().trim();
        if(TextUtils.isEmpty(username)){
            CommonUtils.showLongToast(R.string.user_name_connot_be_empty);
            musername.requestFocus();
            return;
        }else if(TextUtils.isEmpty(password)){
            CommonUtils.showLongToast(R.string.password_connot_be_empty);
            mpassword.requestFocus();
            return;
        }

        login();
    }

    private void login() {
        final ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage(getResources().getString(R.string.logining));
        pd.show();
        L.e(TAG,"username="+username+",password="+password);
        NetDao.Login(mContext, username, password, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
               Result result=ResultUtils.getResultFromJson(s,User.class);
                pd.dismiss();
                L.e(TAG,"result="+result);
                if (result == null) {
                    CommonUtils.showShortToast(R.string.login_fail);
                } else {
                    if (result.isRetMsg()) {
                        User user = (User) result.getRetData();
                        L.e(TAG,"user="+user);
                        MFGT.finish(mContext);
                    } else {
                        if (result.getRetCode() == I.MSG_LOGIN_UNKNOW_USER) {
                            CommonUtils.showLongToast(R.string.login_fail_unknow_user);
                        } else if (result.getRetCode() == I.MSG_LOGIN_ERROR_PASSWORD) {
                            CommonUtils.showLongToast(R.string.login_fail_error_password);
                        } else {
                            CommonUtils.showShortToast(R.string.login_fail);
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                CommonUtils.showLongToast(error);
                L.e(TAG,"error="+error);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == I.REQUEST_CODE_REGISTER){
            String name = data.getStringExtra(I.User.USER_NAME);
            musername.setText(name);
        }
    }
}
