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
import cn.uicai.fulicenter.utils.CommonUtils;
import cn.uicai.fulicenter.utils.ConvertUtils;
import cn.uicai.fulicenter.utils.L;
import cn.uicai.fulicenter.view.SpaceItemDecoration;

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
    int pageId=1;
    GridLayoutManager glm;



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
        setListener();
        return layout;
    }

    private void setListener() {
        setPullUpListener();//上拉加载
        setPullDownListener();//下拉刷新

    }

    private void setPullUpListener() {
        mrv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPostion = glm.findFirstCompletelyVisibleItemPosition();//拿到最后一条数据
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastPostion == mAdapter.getItemCount() - 1
                        && mAdapter.isMore()) {
                    pageId++;
                    downloadnewGoods(I.ACTION_PULL_UP);

                }


            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstPosition = glm.findFirstCompletelyVisibleItemPosition();
                msrl.setEnabled(firstPosition==0);
            }
        });

    }

    private void setPullDownListener() {
        //下拉刷新的方法
        msrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                msrl.setRefreshing(true);//显示下拉刷新的提示
                mtvRfresh.setVisibility(View.VISIBLE);
                pageId=1;
                downloadnewGoods(I.ACTION_PULL_DOWN);
            }
        });
    }
        //下载数据
    private void downloadnewGoods(final int action) {
        NetDao.downloadnewGoods(mContext, pageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                msrl.setRefreshing(false);//设置刷新为False  不在显示
                mtvRfresh.setVisibility(getView().GONE);//设置提示为不可见
                mAdapter.setMore(true);
                if (result != null && result.length > 0) {
                    ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                    if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                        mAdapter.initData(list);
                    } else {
                        mAdapter.addData(list);
                    }

                    if (list.size() < I.PAGE_SIZE_DEFAULT) {
                        mAdapter.setMore(false);//显示没有更多数据
                    }
                } else {
                    mAdapter.setMore(false);//显示没有更多数据
                }
            }

            @Override
            public void onError(String error) {
                msrl.setRefreshing(false);//设置刷新为False  不在显示
                mtvRfresh.setVisibility(getView().GONE);//设置提示为不可见
                mAdapter.setMore(false);
                CommonUtils.showLongToast(error);
                L.e("error"+error);
            }
        });

    }


    private void initData() {
        downloadnewGoods(I.ACTION_DOWNLOAD);
    }



    private void initView() {
        msrl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        glm = new GridLayoutManager(mContext, I.COLUM_NUM);
        mrv.setLayoutManager(glm);
        mrv.setHasFixedSize(true);
        mrv.setAdapter(mAdapter);
        mrv.addItemDecoration(new SpaceItemDecoration(20));
    }

}
