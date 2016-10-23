package cn.uicai.fulicenter.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.uicai.fulicenter.R;
import cn.uicai.fulicenter.view.DisplayUtils;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.nick)
    EditText nick;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.confirm_password)
    EditText confirmPassword;
    @BindView(R.id.btn_register)
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(this,"账号注册");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }
}
