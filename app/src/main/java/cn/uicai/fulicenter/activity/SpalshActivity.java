package cn.uicai.fulicenter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.uicai.fulicenter.R;

public class SpalshActivity extends AppCompatActivity {

    private final long sleepTime=4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //拿到这个Activity 的启动时间
                long start = System.currentTimeMillis();
                //create db
                long costTime=System.currentTimeMillis() - start;//判断用户使用的时间
                //如果用户使用时间未超过2秒则让他继续等待
                if (sleepTime - costTime > 0)
                {
                    try {
                        Thread.sleep(sleepTime - costTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //跳转到MainActivity上
                startActivity(new Intent(SpalshActivity.this,MainActivity.class));
            }
        }).start();


    }
}
