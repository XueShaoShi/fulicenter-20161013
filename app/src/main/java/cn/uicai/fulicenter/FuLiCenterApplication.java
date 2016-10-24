package cn.uicai.fulicenter;

import android.app.Application;

import cn.uicai.fulicenter.bean.User;

/**
 * Created by xiaomiao on 2016/10/17.
 */

public class FuLiCenterApplication extends Application {
    private static FuLiCenterApplication instance;
    private static String username;

    private static User user;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        FuLiCenterApplication.username = username;
    }

    public FuLiCenterApplication() {
        instance = this;
    }

    public static FuLiCenterApplication getInstance() {
        if (instance == null) {
            instance = new FuLiCenterApplication();
        }
        return instance;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        FuLiCenterApplication.user = user;
    }
}
