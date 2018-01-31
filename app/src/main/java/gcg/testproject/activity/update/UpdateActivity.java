package gcg.testproject.activity.update;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.base.BaseActivity;
import gcg.testproject.utils.UpDataUtils;

/**
 * app版本更新更新
 *
 * @ClassName:UpdateActivity
 * @PackageName:gcg.testproject.activity.update
 * @Create On 2018/1/22   11:12
 * @Site:http://www.handongkeji.com
 * @author:gongchenghao
 * @Copyrights 2018/1/22 handongkeji All rights reserved.
 */

public class UpdateActivity extends BaseActivity {

    @Bind(R.id.tv_current)
    TextView tvCurrent;
    @Bind(R.id.tv_lastest)
    TextView tvLastest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);

//        使用UpDataUtils类进行更新
        new UpDataUtils(this);
    }
}
