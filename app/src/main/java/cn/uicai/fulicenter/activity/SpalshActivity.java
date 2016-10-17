package cn.uicai.fulicenter.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.uicai.fulicenter.R;
import cn.uicai.fulicenter.utils.MFGT;

public class SpalshActivity extends AppCompatActivity {

    private final long sleepTime=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MFGT.gotoMainActivity(SpalshActivity.this);
                finish();
            }
        },sleepTime);

    }
}
