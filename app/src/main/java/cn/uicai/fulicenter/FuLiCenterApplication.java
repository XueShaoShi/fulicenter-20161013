package cn.uicai.fulicenter;

import android.app.Application;

/**
 * Created by xiaomiao on 2016/10/17.
 */

public class FuLiCenterApplication extends Application {
    private static FuLiCenterApplication instance;

    public FuLiCenterApplication() {
        instance = this;
    }

    public static FuLiCenterApplication getInstance() {
        if (instance == null) {
            instance = new FuLiCenterApplication();
        }
        return instance;
    }
}