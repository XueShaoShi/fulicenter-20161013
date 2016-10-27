package cn.uicai.fulicenter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.uicai.fulicenter.FuLiCenterApplication;
import cn.uicai.fulicenter.R;
import cn.uicai.fulicenter.activity.MainActivity;
import cn.uicai.fulicenter.adapter.CartAdapter;
import cn.uicai.fulicenter.bean.CartBean;
import cn.uicai.fulicenter.bean.User;
import cn.uicai.fulicenter.net.NetDao;
import cn.uicai.fulicenter.net.OkHttpUtils;
import cn.uicai.fulicenter.utils.CommonUtils;
import cn.uicai.fulicenter.utils.ConvertUtils;
import cn.uicai.fulicenter.utils.L;
import cn.uicai.fulicenter.view.SpaceItemDecoration;

/**
 * Created by xiaomiao on 2016/10/19.
 */

public class CartFragment extends BaseFragment {
    private static final String TAG = CartFragment.class.getCanonicalName();

    @BindView(R.id.tv_rfresh)
    TextView tvRfresh;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    LinearLayoutManager llm;
    MainActivity mContext;
    CartAdapter mAdapter;
    ArrayList<CartBean> mlist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_newgoods, container, false);
        ButterKnife.bind(this, layout);
        mlist = new ArrayList<>();
        mContext = (MainActivity) getContext();
        mAdapter = new CartAdapter(mContext, mlist);
        super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }

    @Override
    protected void setListener() {
        setPullDownListener();//下拉刷新
    }

    private void setPullDownListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(true);//显示下拉刷新的提示
                tvRfresh.setVisibility(View.VISIBLE);
                downloadCart();
            }
        });
    }
    @Override
    protected void initData() {
        downloadCart();
    }

    private void downloadCart() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            NetDao.downloadCart(mContext,user.getMuserName(),new OkHttpUtils.OnCompleteListener<CartBean[]>() {
                @Override
                public void onSuccess(CartBean[] result) {
                    L.e(TAG,"resule="+result);
                    srl.setRefreshing(false);//设置刷新为False  不在显示
                    tvRfresh.setVisibility(getView().GONE);//设置提示为不可见
                    if (result != null && result.length > 0) {
                        ArrayList<CartBean> list = ConvertUtils.array2List(result);
                        mAdapter.initData(list);
                    }
                }

                @Override
                public void onError(String error) {
                    srl.setRefreshing(false);//设置刷新为False  不在显示
                    tvRfresh.setVisibility(getView().GONE);//设置提示为不可见
                    CommonUtils.showLongToast(error);
                    L.e("error"+error);
                }
            });
        }
    }
    @Override
    protected void initView() {
        srl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        llm = new LinearLayoutManager(mContext);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        rv.setAdapter(mAdapter);
        rv.addItemDecoration(new SpaceItemDecoration(20));
    }
}


