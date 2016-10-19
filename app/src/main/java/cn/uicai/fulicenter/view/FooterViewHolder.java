package cn.uicai.fulicenter.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.uicai.fulicenter.R;

/**
 * Created by xiaomiao on 2016/10/19.
 */

public class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvFooter)
        public TextView mtvFooter;

        public FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

