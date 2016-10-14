package cn.uicai.fulicenter.utils;

import android.app.Activity;
import android.content.Intent;

import cn.uicai.fulicenter.R;
import cn.uicai.fulicenter.activity.MainActivity;

//辅助统一跳转的风格,以及简化跳转的逻辑
public class MFGT {
    /**
     * 设置动画
     * @param activity
     */
    public static void finish(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }

    /**
     * 跳转到主页
     * @param context
     */
    public static void gotoMainActivity(Activity context){
        startActivity(context, MainActivity.class);
    }

    /**
     *实现intent的跳转 并设置Context的动画
     * @param context
     * @param cls
     */
    public static void startActivity(Activity context,Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(context,cls);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }
}
