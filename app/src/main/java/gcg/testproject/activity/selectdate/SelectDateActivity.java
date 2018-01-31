package gcg.testproject.activity.selectdate;

import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.base.BaseActivity;

/**
 * 日期选择器
 *
 * @ClassName:SelectDateActivity
 * @PackageName:gcg.testproject.activity.selectdate
 * @Create On 2018/1/15   14:48
 * @Site:http://www.handongkeji.com
 * @author:gongchenghao
 * @Copyrights 2018/1/15 handongkeji All rights reserved.
 */

public class SelectDateActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.tv_start_time)
    TextView tvStartTime;
    @Bind(R.id.tv_end_time)
    TextView tvEndTime;
    private Calendar c;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);
        ButterKnife.bind(this);

        initClickEvent(); //初始化控件的点击事件

        c = Calendar.getInstance();
    }

    private void initClickEvent() {
        tvStartTime.setOnClickListener(this);
        tvEndTime.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_start_time:
                showDateDialog();
                break;
            case R.id.tv_end_time:
                showDateDialog();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showDateDialog() {
        new DoubleDatePickerDialog(SelectDateActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker startDatePicker,
                                  int startYear, int startMonthOfYear, int startDayOfMonth,
                                  DatePicker endDatePicker,
                                  int endYear, int endMonthOfYear, int endDayOfMonth) {

                tvStartTime.setText(startYear+"-"+(startMonthOfYear + 1)+"-"+startDayOfMonth);
                tvEndTime.setText(endYear+"-"+(endMonthOfYear + 1)+"-"+endDayOfMonth);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show(); //最后一个参数传true，表示可以显示日
    }
}
