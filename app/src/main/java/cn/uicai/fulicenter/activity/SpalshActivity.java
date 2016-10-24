package cn.uicai.fulicenter.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.uicai.fulicenter.FuLiCenterApplication;
import cn.uicai.fulicenter.R;
import cn.uicai.fulicenter.bean.User;
import cn.uicai.fulicenter.dao.SharePrefrenceUtils;
import cn.uicai.fulicenter.dao.UserDao;
import cn.uicai.fulicenter.utils.L;
import cn.uicai.fulicenter.utils.MFGT;

public class SpalshActivity extends AppCompatActivity {
    private static final String TAG=SpalshActivity.class.getCanonicalName();
    SpalshActivity mContext;
    private final long sleepTime=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);
        mContext = this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                User user = FuLiCenterApplication.getUser();
                L.e(TAG,"fulicenter,user="+user);
                String username = SharePrefrenceUtils.getInstence(mContext).getUser();
                L.e(TAG,"fulicenter,username="+username);
                if (user == null && username != null) {
                    UserDao dao = new UserDao(mContext);
                    user = dao.getUser(username);
                    L.e(TAG, "user=" + user);
                    if (user != null) {
                        FuLiCenterApplication.setUser(user);
                    }
                }
                MFGT.gotoMainActivity(SpalshActivity.this);
                finish();
            }
        },sleepTime);
    }
}
