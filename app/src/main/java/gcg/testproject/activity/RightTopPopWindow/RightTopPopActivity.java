package gcg.testproject.activity.RightTopPopWindow;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.base.BaseActivity;

public class RightTopPopActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.tv_popwindow)
    TextView tvPopwindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_right_top_pop);
        ButterKnife.bind(this);

        initClickEvent(); //初始化控件的点击事件
    }

    private void initClickEvent() {
        tvPopwindow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_popwindow:
                RightTopPopWindowUtils utils = new RightTopPopWindowUtils(RightTopPopActivity.this);
                utils.showPop(tvPopwindow,2);
                break;
        }
    }
}
