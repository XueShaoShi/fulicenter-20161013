package cn.uicai.fulicenter.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.uicai.fulicenter.I;
import cn.uicai.fulicenter.R;
import cn.uicai.fulicenter.adapter.GoodsAdapter;
import cn.uicai.fulicenter.bean.BoutiqueBean;
import cn.uicai.fulicenter.bean.NewGoodsBean;
import cn.uicai.fulicenter.net.NetDao;
import cn.uicai.fulicenter.net.OkHttpUtils;
import cn.uicai.fulicenter.utils.CommonUtils;
import cn.uicai.fulicenter.utils.ConvertUtils;
import cn.uicai.fulicenter.utils.L;
import cn.uicai.fulicenter.utils.MFGT;
import cn.uicai.fulicenter.view.SpaceItemDecoration;

public class BoutiqueChildActivity extends BaseActivity {

    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.tv_rfresh)
    TextView tvRfresh;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    GridLayoutManager glm;
    GoodsAdapter mAdapter;
    BoutiqueChildActivity mContext;
    ArrayList<NewGoodsBean> mList;
    int pageId=1;
    BoutiqueBean ben;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_boutique_child);
        ButterKnife.bind(this);
        ben = (BoutiqueBean) getIntent().getSerializableExtra(I.Boutique.CAT_ID);
        if (ben == null) {
            finish();
        }
        mContext = this;
        mList = new ArrayList<>();
        mAdapter = new GoodsAdapter(mContext, mList);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        srl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        glm = new GridLayoutManager(mContext, I.COLUM_NUM);
        rv.setLayoutManager(glm);
        rv.setHasFixedSize(true);
        rv.setAdapter(mAdapter);
        rv.addItemDecoration(new SpaceItemDecoration(20));
        tvCommonTitle.setText(ben.getTitle());
    }

    @Override
    protected void initData() {
        downloadnewGoods(I.ACTION_DOWNLOAD);
    }

    @Override
    protected void setListener() {
        setPullUpListener();//上拉加载
        setPullDownListener();//下拉刷新
    }

    private void setPullUpListener() {
        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
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
                srl.setEnabled(firstPosition==0);
            }
        });

    }

    private void setPullDownListener() {
        //下拉刷新的方法
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(true);//显示下拉刷新的提示
                tvRfresh.setVisibility(View.VISIBLE);
                pageId=1;
                downloadnewGoods(I.ACTION_PULL_DOWN);
            }
        });
    }
    //下载数据
    private void downloadnewGoods(final int action) {
        NetDao.downloadNewGoods(mContext,ben.getId(),pageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                srl.setRefreshing(false);//设置刷新为False  不在显示
                tvRfresh.setVisibility(View.GONE);//设置提示为不可见
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
                srl.setRefreshing(false);//设置刷新为False  不在显示
                tvRfresh.setVisibility(View.GONE);//设置提示为不可见
                mAdapter.setMore(false);
                CommonUtils.showLongToast(error);
            }
        });

    }



    @OnClick(R.id.backClickArea)
    public void onClick() {
        MFGT.finish(this);
    }
}
