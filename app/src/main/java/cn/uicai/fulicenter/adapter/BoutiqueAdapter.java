package cn.uicai.fulicenter.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.uicai.fulicenter.R;
import cn.uicai.fulicenter.bean.BoutiqueBean;
import cn.uicai.fulicenter.utils.ImageLoader;

/**
 * Created by xiaomiao on 2016/10/19.
 */

public class BoutiqueAdapter extends Adapter<BoutiqueAdapter.BoutiqueViewHolder> {
    Context mContext;
    ArrayList<BoutiqueBean> mList;

    public BoutiqueAdapter(Context Context, ArrayList<BoutiqueBean> List) {
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
    public BoutiqueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BoutiqueViewHolder holder = new BoutiqueViewHolder(View.inflate(mContext, R.layout.item_boutique, null));

        return holder;
    }

    /**
     * 绑定数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(BoutiqueViewHolder holder, int position) {
        BoutiqueBean boutiqueBean = mList.get(position);
        ImageLoader.downloadImg(mContext,holder.ivBoutiqueImg,boutiqueBean.getImageurl());
        holder.tvBoutiqueTitle.setText(boutiqueBean.getTitle());
        holder.tvBoutiqueName.setText(boutiqueBean.getName());
        holder.tvBoutiqueDescription.setText(boutiqueBean.getDescription());
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


    public void initData(ArrayList<BoutiqueBean> list) {
        if (mList != null) {
            mList.clear();//清空数据
        }
        mList.addAll(list);
        notifyDataSetChanged();//刷新
    }


    class BoutiqueViewHolder extends ViewHolder {
        @BindView(R.id.ivBoutiqueImg)
        ImageView ivBoutiqueImg;
        @BindView(R.id.tvBoutiqueTitle)
        TextView tvBoutiqueTitle;
        @BindView(R.id.tvBoutiqueName)
        TextView tvBoutiqueName;
        @BindView(R.id.tvBoutiqueDescription)
        TextView tvBoutiqueDescription;
        @BindView(R.id.layout_boutique_item)
        RelativeLayout layoutBoutiqueItem;

        BoutiqueViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
