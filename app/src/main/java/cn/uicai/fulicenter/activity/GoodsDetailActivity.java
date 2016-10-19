package cn.uicai.fulicenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.uicai.fulicenter.I;
import cn.uicai.fulicenter.R;
import cn.uicai.fulicenter.bean.AlbumsBean;
import cn.uicai.fulicenter.bean.GoodsDetailsBean;
import cn.uicai.fulicenter.net.NetDao;
import cn.uicai.fulicenter.net.OkHttpUtils;
import cn.uicai.fulicenter.utils.CommonUtils;
import cn.uicai.fulicenter.utils.L;
import cn.uicai.fulicenter.utils.MFGT;
import cn.uicai.fulicenter.view.FlowIndicator;
import cn.uicai.fulicenter.view.SlideAutoLoopView;

public class GoodsDetailActivity extends BaseActivity {


    int goodsId;
    GoodsDetailActivity mContext;
    @BindView(R.id.backClickArea)
    LinearLayout backClickArea;
    @BindView(R.id.tv_good_name_english)
    TextView tvGoodNameEnglish;
    @BindView(R.id.tv_good_name)
    TextView tvGoodName;
    @BindView(R.id.tv_good_price_shop)
    TextView tvGoodPriceShop;
    @BindView(R.id.tv_good_price_current)
    TextView tvGoodPriceCurrent;
    @BindView(R.id.salv)
    SlideAutoLoopView salv;
    @BindView(R.id.indicator)
    FlowIndicator indicator;
    @BindView(R.id.wv_good_brief)
    WebView wvGoodBrief;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        goodsId = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        L.i("details", "gooodsId=" + goodsId);
        if (goodsId == 0) {
            finish();
        }
        mContext = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        NetDao.downloadGoodsDetail(mContext, goodsId, new OkHttpUtils.OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                L.i("details=" + result);
                if (result != null) {
                    showGoodDetails(result);
                } else {
                    finish();
                }
            }

            @Override
            public void onError(String error) {
                finish();
                L.e("details,error" + error);
                CommonUtils.showShortToast(error);
            }
        });
    }

    private void showGoodDetails(GoodsDetailsBean details) {
        tvGoodNameEnglish.setText(details.getGoodsEnglishName());
        tvGoodName.setText(details.getGoodsName());
        tvGoodPriceCurrent.setText(details.getCurrencyPrice());
        tvGoodPriceShop.setText(details.getShopPrice());
        salv.startPlayLoop(indicator, getAlbumImUrl(details), getAlbumImgCount(details));
        wvGoodBrief.loadDataWithBaseURL(null, details.getGoodsBrief(), I.TEXT_HTML, I.UTF_8, null);

    }

    private int getAlbumImgCount(GoodsDetailsBean details) {
        if (details.getPromotePrice() != null && details.getPromotePrice().length() > 0) {
            return details.getProperties()[0].getAlbums().length;
        }
        return 0;
    }

    private String[] getAlbumImUrl(GoodsDetailsBean details) {
        String[] url = new String[]{};
        if (details.getPromotePrice() != null && details.getPromotePrice().length() > 0) {
            AlbumsBean[] albums = details.getProperties()[0].getAlbums();
            url = new String[albums.length];
            for (int i = 0; i < albums.length; i++) {
                url[i] = albums[i].getImgUrl();
            }
        }
        return url;
    }

    @Override
    protected void initView() {

    }

    @OnClick(R.id.backClickArea)
    public void onBackClick() {
        MFGT.finish(this);
    }
}
