package cn.uicai.fulicenter.net;

import android.content.Context;

import cn.uicai.fulicenter.I;
import cn.uicai.fulicenter.bean.NewGoodsBean;

/**
 * Created by xiaomiao on 2016/10/17.
 */

public class NetDao {
    public static void downloadnewGoods(Context context, int pageId, OkHttpUtils.OnCompleteListener<NewGoodsBean[]> lsitener) {
        OkHttpUtils utils = new OkHttpUtils(context);
        utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID, String.valueOf(I.CAT_ID))
                .addParam(I.PAGE_ID, String.valueOf(I.PAGE_ID))
                .addParam(I.PAGE_SIZE, String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(NewGoodsBean[].class)
                .execute(lsitener);
    }
}
