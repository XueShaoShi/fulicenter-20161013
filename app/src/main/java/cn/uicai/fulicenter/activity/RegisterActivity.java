package cn.uicai.fulicenter.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.uicai.fulicenter.I;
import cn.uicai.fulicenter.R;
import cn.uicai.fulicenter.bean.Result;
import cn.uicai.fulicenter.net.NetDao;
import cn.uicai.fulicenter.net.OkHttpUtils;
import cn.uicai.fulicenter.utils.CommonUtils;
import cn.uicai.fulicenter.utils.L;
import cn.uicai.fulicenter.utils.MFGT;
import cn.uicai.fulicenter.view.DisplayUtils;

public class RegisterActivity extends BaseActivity {
    private static final String TAG = RegisterActivity.class.getSimpleName();

    @BindView(R.id.username)
    EditText musername;
    @BindView(R.id.nick)
    EditText mnick;
    @BindView(R.id.password)
    EditText mpassword;
    @BindView(R.id.confirm_password)
    EditText mconfirmPassword;
    @BindView(R.id.btn_register)
    Button mbtnRegister;


    String username;
    String nickname;
    String password;
    String confirmPwd;
    RegisterActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mContext = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(this, "账号注册");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.btn_register)
    public void checkedInput() {

        username = musername.getText().toString();
        nickname = mnick.getText().toString();
        password = mpassword.getText().toString();
        confirmPwd = mconfirmPassword.getText().toString();
        if (TextUtils.isEmpty(username)) {
            CommonUtils.showShortToast(R.string.user_name_connot_be_empty);
            musername.requestFocus();
            return;
        } else if (!username.matches("[a-zA-Z]\\w{5,15}")) {
            CommonUtils.showShortToast(R.string.illegal_user_name);
            musername.requestFocus();
            return;
        } else if (TextUtils.isEmpty(nickname)) {
            CommonUtils.showShortToast(R.string.nick_name_connot_be_empty);
            mnick.requestFocus();
            return;
        } else if (TextUtils.isEmpty(password)) {
            CommonUtils.showShortToast(R.string.password_connot_be_empty);
            mpassword.requestFocus();
            return;
        } else if (TextUtils.isEmpty(confirmPwd)) {
            CommonUtils.showShortToast(R.string.confirm_password_connot_be_empty);
            mconfirmPassword.requestFocus();
            return;
        } else if (!password.equals(confirmPwd)) {
            CommonUtils.showShortToast(R.string.two_input_password);
            mconfirmPassword.requestFocus();
            return;
        }
        register();
    }

    private void register() {
        final ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage(getResources().getString(R.string.registering));
        pd.show();
        NetDao.register(mContext, username, nickname, password, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                pd.dismiss();
                if(result==null){
                    CommonUtils.showShortToast(R.string.register_fail);
                }else{
                    if(result.isRetMsg()){
                        CommonUtils.showLongToast(R.string.register_success);
                        setResult(RESULT_OK,new Intent().putExtra(I.User.USER_NAME,username));
                        MFGT.finish(mContext);
                    }else{
                        CommonUtils.showLongToast(R.string.register_fail_exists);
                        musername.requestFocus();
                    }
                }
            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                CommonUtils.showShortToast(error);
            }
        });
    }
}
