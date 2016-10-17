package cn.uicai.fulicenter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.uicai.fulicenter.I;
import cn.uicai.fulicenter.R;
import cn.uicai.fulicenter.activity.MainActivity;
import cn.uicai.fulicenter.adapter.GoodsAdapter;
import cn.uicai.fulicenter.bean.NewGoodsBean;
import cn.uicai.fulicenter.net.NetDao;
import cn.uicai.fulicenter.net.OkHttpUtils;

/**
 * Created by xiaomiao on 2016/10/17.
 */

public class NewGoodsFragment extends Fragment {

    @BindView(R.id.tv_rfresh)
    TextView mtvRfresh;
    @BindView(R.id.rv)
    RecyclerView mrv;
    @BindView(R.id.srl)
    SwipeRefreshLayout msrl;

    MainActivity mContext;
    GoodsAdapter mAdapter;
    ArrayList<NewGoodsBean> mList;
    int pageId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_newgoods, container, false);
        ButterKnife.bind(this, layout);
        mContext = (MainActivity) getContext();
        mList = new ArrayList<>();
        mAdapter = new GoodsAdapter(mContext,mList);
        initView();
        initData();
        return layout;
    }

    private void initData() {
        NetDao.downloadnewGoods(mContext, pageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {

            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void initView() {
        msrl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        GridLayoutManager glm = new GridLayoutManager(mContext, I.COLUM_NUM);
        mrv.setLayoutManager(glm);
        mrv.setHasFixedSize(true);
        mrv.setAdapter(mAdapter);
    }

}
