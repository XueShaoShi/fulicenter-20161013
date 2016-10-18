package cn.uicai.fulicenter.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.uicai.fulicenter.R;
import cn.uicai.fulicenter.fragment.NewGoodsFragment;
import cn.uicai.fulicenter.utils.L;

public class MainActivity extends AppCompatActivity {

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
    RadioButton[] abs;
    Fragment[] mfragments;
    NewGoodsFragment mNewGoodsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        L.i("Mainactivity onCreate");
        initView();
        initFragment();
    }

    private void initFragment() {
        mfragments = new Fragment[5];
        mNewGoodsFragment = new NewGoodsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, mNewGoodsFragment)
                .show(mNewGoodsFragment)
                .commit();

    }

    private void initView() {
        abs = new RadioButton[5];
        abs[0] = mlayoutNewGood;
        abs[1] = mlayoutBoutique;
        abs[2] = mlayoutCategory;
        abs[3] = mlayoutCart;
        abs[4] = mlayoutPersonalCenter;

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
                indx = 4;
                break;
        }
        setRadioButtonstate();
    }

    private void setRadioButtonstate() {
       for (int i=0;i<abs.length;i++) {
           if (i == indx) {
               abs[i].setChecked(true);
           } else {
               abs[i].setChecked(false);
           }
       }
    }
}
