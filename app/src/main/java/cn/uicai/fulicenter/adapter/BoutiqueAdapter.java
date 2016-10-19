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
import cn.uicai.fulicenter.I;
import cn.uicai.fulicenter.R;
import cn.uicai.fulicenter.bean.BoutiqueBean;
import cn.uicai.fulicenter.utils.ImageLoader;
import cn.uicai.fulicenter.view.FooterViewHolder;

/**
 * Created by xiaomiao on 2016/10/19.
 */

public class BoutiqueAdapter extends Adapter {
    Context mContext;
    ArrayList<BoutiqueBean> mList;
    boolean isMore;

    public BoutiqueAdapter(Context Context, ArrayList<BoutiqueBean> List) {
        mContext = Context;
        mList = new ArrayList<>();
        mList.addAll(List);
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    /**
     * 绑定布局
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        if (viewType == I.TYPE_FOOTER) {
            holder = new FooterViewHolder(View.inflate(mContext, R.layout.item_footer, null));
        } else {
            holder = new BoutiqueViewHolder(View.inflate(mContext, R.layout.item_boutique, null));
        }

        return null;
    }

    /**
     * 绑定数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            ((FooterViewHolder) holder).mtvFooter.setText(getFooterString());

        }
        if (holder instanceof BoutiqueViewHolder) {
            BoutiqueBean boutiqueBean = mList.get(position);
            ImageLoader.downloadImg(mContext, ((BoutiqueViewHolder) holder).ivBoutiqueImg, boutiqueBean.getImageurl());
            ((BoutiqueViewHolder) holder).tvBoutiqueTitle.setText(boutiqueBean.getTitle());
            ((BoutiqueViewHolder) holder).tvBoutiqueName.setText(boutiqueBean.getName());
            ((BoutiqueViewHolder) holder).tvBoutiqueDescription.setText(boutiqueBean.getDescription());
        }

    }

    /**
     * 获取要显示的总量
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mList != null ? mList.size() + 1 : 1;
    }

    /**
     * 判断要返回布局的类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

    public int getFooterString() {
        return isMore ? R.string.load_more : R.string.no_more;
    }

    public void initData(ArrayList<BoutiqueBean> list) {
        if (mList != null) {
            mList.clear();//清空数据
        }
        mList.addAll(list);
        notifyDataSetChanged();//刷新
    }

    public void addData(ArrayList<BoutiqueBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
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
