package gcg.testproject.activity.xuliehua;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;

import java.io.Serializable;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.base.BaseActivity;
import gcg.testproject.utils.NetUtils;

public class XuLieHua2Activity extends BaseActivity {

    @Bind(R.id.tv_date)
    TextView mTvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xu_lie_hua2);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        PersonBean1 personBean1 = (PersonBean1) intent.getSerializableExtra("PersonBean1");
        if (personBean1 != null)
        {
            mTvDate.setText("通过Serializable获取到的数据："+personBean1.toString());
        }

        PersonBean2 personBean2 = intent.getParcelableExtra("PersonBean2");
        if (personBean2 != null)
        {
            mTvDate.setText("通过Parcelable获取到的数据："+personBean2.toString());
        }
    }
}
