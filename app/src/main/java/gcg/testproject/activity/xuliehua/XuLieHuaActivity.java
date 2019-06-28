package gcg.testproject.activity.xuliehua;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.base.BaseActivity;

//参考链接：https://www.cnblogs.com/JarvisHuang/p/5550109.html
public class XuLieHuaActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.tv_tiao_zhuan)
    TextView mTvTiaoZhuan;
    @Bind(R.id.tv_tiao_zhuan2)
    TextView mTvTiaoZhuan2;

    private PersonBean1 mPersonBean1;
    private PersonBean2 mPersonBean2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xu_lie_hua);
        ButterKnife.bind(this);

        mPersonBean1 = new PersonBean1();
        mPersonBean1.setAge(18);
        mPersonBean1.setName("张三");
        mPersonBean1.setSex(1);

        mPersonBean2 = new PersonBean2(Parcel.obtain());
        mPersonBean2.setAge(188);
        mPersonBean2.setName("张三三");
        mPersonBean2.setSex(11);

        mTvTiaoZhuan.setOnClickListener(this);
        mTvTiaoZhuan2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_tiao_zhuan:
                Intent intent = new Intent(XuLieHuaActivity.this, XuLieHua2Activity.class);
                intent.putExtra("PersonBean1", mPersonBean1);
                startActivity(intent);
                break;
            case R.id.tv_tiao_zhuan2:
                Intent intent1 = new Intent(XuLieHuaActivity.this, XuLieHua2Activity.class);
                intent1.putExtra("PersonBean2", mPersonBean2);
                startActivity(intent1);
                break;
        }
    }
}
