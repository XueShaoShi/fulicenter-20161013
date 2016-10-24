package cn.uicai.fulicenter.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.uicai.fulicenter.FuLiCenterApplication;
import cn.uicai.fulicenter.R;
import cn.uicai.fulicenter.fragment.BoutioueFragment;
import cn.uicai.fulicenter.fragment.CategoryFragment;
import cn.uicai.fulicenter.fragment.NewGoodsFragment;
import cn.uicai.fulicenter.fragment.PresonalCenterFragment;
import cn.uicai.fulicenter.utils.L;
import cn.uicai.fulicenter.utils.MFGT;

public class MainActivity extends BaseActivity {

    @BindView(R.id.layout_new_good)
    RadioButton mlayoutNewGood;
    @BindView(R.id.layout_boutique)
    RadioButton mlayoutBoutique;
    @BindView(R.id.layout_category)
    RadioButton mlayoutCategory;
    @BindView(R.id.layout_cart)
    RadioButton mlayoutCart;
    @BindView(R.id.tvCartHint)
    TextView mtvCartHint;
    @BindView(R.id.layout_personal_center)
    RadioButton mlayoutPersonalCenter;

    int indx;
    int currentIndx;
    RadioButton[] abs;
    Fragment[] mfragments;
    NewGoodsFragment mNewGoodsFragment;
    BoutioueFragment mBoutioueFragment;
    CategoryFragment mCategoryFragment;
    PresonalCenterFragment mpresonalCenterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        L.i("Mainactivity onCreate");
        super.onCreate(savedInstanceState);
    }

    private void initFragment() {
        mfragments = new Fragment[5];
        mNewGoodsFragment = new NewGoodsFragment();
        mBoutioueFragment = new BoutioueFragment();
        mCategoryFragment = new CategoryFragment();
        mpresonalCenterFragment = new PresonalCenterFragment();
        mfragments[0] = mNewGoodsFragment;
        mfragments[1] = mBoutioueFragment;
        mfragments[2] = mCategoryFragment;
        mfragments[4] = mpresonalCenterFragment;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, mNewGoodsFragment)
                .add(R.id.fragment_container, mBoutioueFragment)
                .add(R.id.fragment_container, mCategoryFragment)
                .hide(mBoutioueFragment)
                .hide(mCategoryFragment)
                .show(mNewGoodsFragment)
                .commit();
    }


    @Override
    protected void initView() {
        abs = new RadioButton[5];
        abs[0] = mlayoutNewGood;
        abs[1] = mlayoutBoutique;
        abs[2] = mlayoutCategory;
        abs[3] = mlayoutCart;
        abs[4] = mlayoutPersonalCenter;
    }

    @Override
    protected void initData() {
        initFragment();

    }

    @Override
    protected void setListener() {

    }

    public void onCheckedChange(View v) {
        switch (v.getId()) {
            case R.id.layout_new_good:
                indx=0;
                break;
            case R.id.layout_boutique:
                indx = 1;
                break;
            case R.id.layout_category:
                indx = 2;
                break;
            case R.id.layout_cart:
                indx = 3;
                break;
            case R.id.layout_personal_center:
                if (FuLiCenterApplication.getUser() == null) {
                    MFGT.gotoLogin(this);
                } else {
                    indx = 4;
                }
                break;
        }
        setFragment();
    }

    private void setFragment() {
        if (indx != currentIndx) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(mfragments[currentIndx]);
            L.e("indx="+indx);
            if (!mfragments[indx].isAdded()) {
                ft.add(R.id.fragment_container, mfragments[indx]);
            }
            ft.show(mfragments[indx]).commit();
        }
        setRadioButtonstate();
        currentIndx = indx;
    }

    private void setRadioButtonstate() {
        L.e("index="+indx);
        for (int i=0;i<abs.length;i++) {
           if (i == indx) {
               abs[i].setChecked(true);
           } else {
               abs[i].setChecked(false);
           }
       }
    }

    public void onBackPressed() {
     finish();
    }


}
