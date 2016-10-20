package cn.uicai.fulicenter.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import cn.uicai.fulicenter.I;
import cn.uicai.fulicenter.R;
import cn.uicai.fulicenter.activity.BaseActivity;
import cn.uicai.fulicenter.activity.BoutiqueChildActivity;
import cn.uicai.fulicenter.activity.CategoryChildActivity;
import cn.uicai.fulicenter.activity.GoodsDetailActivity;
import cn.uicai.fulicenter.activity.MainActivity;
import cn.uicai.fulicenter.bean.BoutiqueBean;
import cn.uicai.fulicenter.bean.CategoryChildBean;

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
        startActivity(context,intent);
    }

    /**
     * 实现从商品跳转到商品详细(二级界面)的一个跳转，并设置动画
     * @param context
     * @param goodsId
     */
    public static void gotoGoodsDetailActivity(Context context, int goodsId){
        Intent intent = new Intent();
        intent.setClass(context, GoodsDetailActivity.class);
        intent.putExtra(I.GoodsDetails.KEY_GOODS_ID, goodsId);
        startActivity(context,intent);
    }

    public static void startActivity(Context context, Intent intent) {
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }

    /**
     * 实现从精选跳转到精选详情（二级界面）的跳转，并设置动画
     * @param context
     * @param bean
     */
    public static void gotoBoutiqueChildActivity(Context context, BoutiqueBean bean){
        Intent intent = new Intent();
        intent.setClass(context,BoutiqueChildActivity.class);
        intent.putExtra(I.Boutique.CAT_ID, bean);
        startActivity(context,intent);
    }

    /**
     * 实现从分类跳转到分类商品详细(二级界面)的一个跳转，并设置动画
     * @param context
     * @param catId
     */
    public static void gotoCategoryChildActivity(Context context, int catId, String groupName, ArrayList<CategoryChildBean> list){
        Intent intent = new Intent();
        intent.setClass(context,CategoryChildActivity.class);
            intent.putExtra(I.CategoryChild.CAT_ID, catId);
            intent.putExtra(I.CategoryGroup.NAME, groupName);
            intent.putExtra(I.CategoryChild.ID,list);
        startActivity(context,intent);
    }

}
