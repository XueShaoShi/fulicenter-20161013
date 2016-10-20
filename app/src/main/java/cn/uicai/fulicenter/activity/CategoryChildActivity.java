package cn.uicai.fulicenter.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.uicai.fulicenter.I;
import cn.uicai.fulicenter.R;
import cn.uicai.fulicenter.adapter.GoodsAdapter;
import cn.uicai.fulicenter.bean.NewGoodsBean;
import cn.uicai.fulicenter.net.NetDao;
import cn.uicai.fulicenter.net.OkHttpUtils;
import cn.uicai.fulicenter.utils.CommonUtils;
import cn.uicai.fulicenter.utils.ConvertUtils;
import cn.uicai.fulicenter.utils.L;
import cn.uicai.fulicenter.utils.MFGT;
import cn.uicai.fulicenter.view.SpaceItemDecoration;

public class CategoryChildActivity extends BaseActivity {

    @BindView(R.id.tv_rfresh)
    TextView tvRfresh;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    GridLayoutManager glm;
    GoodsAdapter mAdapter;
    CategoryChildActivity mContext;
    ArrayList<NewGoodsBean> mList;
    int pageId = 1;
    int catId;
    boolean addTimeAsc=false;
    boolean priceAsc=false;
    int sortBy = I.SORT_BY_ADDTIME_DESC;

    @BindView(R.id.btn_sort_price)
    Button btnSortPrice;
    @BindView(R.id.btn_sort_addtime)
    Button btnSortAddtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_category_child);
        ButterKnife.bind(this);
        mContext = this;
        mList = new ArrayList<>();
        mAdapter = new GoodsAdapter(mContext, mList);
        catId = getIntent().getIntExtra(I.CategoryChild.CAT_ID, 0);
        if (catId == 0) {
            finish();
        }
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
    }

    @Override
    protected void initData() {
        downloadCategoryGoods(I.ACTION_DOWNLOAD);
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
                    downloadCategoryGoods(I.ACTION_PULL_UP);

                }


            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstPosition = glm.findFirstCompletelyVisibleItemPosition();
                srl.setEnabled(firstPosition == 0);
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
                pageId = 1;
                downloadCategoryGoods(I.ACTION_PULL_DOWN);
            }
        });
    }

    //下载数据
    private void downloadCategoryGoods(final int action) {
        NetDao.downloadCategoryGoods(mContext, catId, pageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
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
                L.e("error" + error);
            }
        });

    }


    @OnClick(R.id.backClickArea)
    public void onClick() {
        MFGT.finish(this);
    }

    @OnClick({R.id.btn_sort_price, R.id.btn_sort_addtime})
    public void onClick(View view) {
        Drawable right;
        switch (view.getId()) {
            case R.id.btn_sort_price:
                if (priceAsc) {
                    sortBy = I.SORT_BY_PRICE_ASC;
                    right = getResources().getDrawable(R.mipmap.arrow_order_up);
                } else {
                    sortBy = I.SORT_BY_PRICE_DESC;
                    right = getResources().getDrawable(R.mipmap.arrow_order_down);
                }
                right.setBounds(0,0,right.getIntrinsicWidth(),right.getIntrinsicHeight());
                btnSortPrice.setCompoundDrawablesWithIntrinsicBounds(null,null,right,null);
                priceAsc = !priceAsc;
                break;
            case R.id.btn_sort_addtime:
                if (addTimeAsc) {
                    sortBy = I.SORT_BY_ADDTIME_ASC;
                    right = getResources().getDrawable(R.mipmap.arrow_order_up);
                } else {
                    sortBy = I.SORT_BY_ADDTIME_DESC;
                    right = getResources().getDrawable(R.mipmap.arrow_order_down);
                }
                right.setBounds(0,0,right.getIntrinsicWidth(),right.getIntrinsicHeight());
                btnSortAddtime.setCompoundDrawablesWithIntrinsicBounds(null,null,right,null);
                addTimeAsc = !addTimeAsc;
                break;
        }
        mAdapter.setSoryBy(sortBy);
    }
}
