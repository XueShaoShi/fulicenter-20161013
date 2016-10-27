package cn.uicai.fulicenter.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.uicai.fulicenter.R;
import cn.uicai.fulicenter.bean.CartBean;

/**
 * Created by xiaomiao on 2016/10/19.
 */

public class CartAdapter extends Adapter<CartAdapter.CartViewHolder> {
    Context mContext;
    ArrayList<CartBean> mList;

    public CartAdapter(Context Context, ArrayList<CartBean> List) {
        this.mContext = Context;
        this.mList = new ArrayList<>();
        mList.addAll(List);
    }


    /**
     * 绑定布局
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CartViewHolder holder = new CartViewHolder(View.inflate(mContext,
                R.layout.item_cart, null));

        return holder;
    }

    /**
     * 绑定数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        CartBean cartBean = mList.get(position);
      /*  ImageLoader.downloadImg(mContext,holder.ivBoutiqueImg,boutiqueBean.getImageurl());
        holder.tvBoutiqueTitle.setText(boutiqueBean.getTitle());
        holder.tvBoutiqueName.setText(boutiqueBean.getName());
        holder.tvBoutiqueDescription.setText(boutiqueBean.getDescription());
        holder.layoutBoutiqueItem.setTag(boutiqueBean);*/
    }

    /**
     * 获取要显示的总量
     *
     * @return
     */
    @Override
    public int getItemCount() {

        return mList != null ? mList.size() : 0;
    }


    public void initData(ArrayList<CartBean> list) {
        if (mList != null) {
            mList.clear();//清空数据
        }
        mList.addAll(list);
        notifyDataSetChanged();//刷新
    }


     class CartViewHolder extends ViewHolder {
        @BindView(R.id.cb_cart_selected)
        CheckBox mCbCartSelected;
        @BindView(R.id.iv_cart_thumb)
        ImageView mIvCartThumb;
        @BindView(R.id.tv_cart_good_name)
        TextView mTvCartGoodName;
        @BindView(R.id.iv_cart_add)
        ImageView mIvCartAdd;
        @BindView(R.id.iv_cart_count)
        TextView mIvCartCount;
        @BindView(R.id.iv_cart_del)
        ImageView mIvCartDel;
        @BindView(R.id.tv_cart_price)
        TextView mTvCartPrice;

         CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}