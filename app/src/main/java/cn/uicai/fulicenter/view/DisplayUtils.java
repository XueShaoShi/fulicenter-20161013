package cn.uicai.fulicenter.view;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import cn.uicai.fulicenter.R;
import cn.uicai.fulicenter.utils.MFGT;

/**
 * Created by xiaomiao on 2016/10/23.
 */

public class DisplayUtils {
    public static void initBack(final Activity activity){
        activity.findViewById(R.id.backClickArea).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                activity.finish();
                MFGT.finish(activity);
            }
        });
    }

    public static void initBackWithTitle(final Activity activity, final String title){
        initBack(activity);
        ((TextView)activity.findViewById(R.id.tv_common_title)).setText(title);
    }
}
