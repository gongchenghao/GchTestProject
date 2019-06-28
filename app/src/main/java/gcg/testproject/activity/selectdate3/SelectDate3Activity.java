package gcg.testproject.activity.selectdate3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.activity.selectdate3.pickerview.TimePickerDialog;
import gcg.testproject.activity.selectdate3.pickerview.data.Type;
import gcg.testproject.activity.selectdate3.pickerview.listener.OnDateSetListener;
import gcg.testproject.base.BaseActivity;

public class SelectDate3Activity extends BaseActivity implements View.OnClickListener, OnDateSetListener {

    @Bind(R.id.btn_all)
    Button mBtnAll;
    @Bind(R.id.btn_year_month_day)
    Button mBtnYearMonthDay;
    @Bind(R.id.btn_year_month)
    Button mBtnYearMonth;
    @Bind(R.id.btn_month_day_hour_minute)
    Button mBtnMonthDayHourMinute;
    @Bind(R.id.btn_hour_minute)
    Button mBtnHourMinute;
    @Bind(R.id.tv_time)
    TextView mTvTime;
    @Bind(R.id.btn_year_month_day_hour)
    Button mBtnYearMonthDayHour;
    @Bind(R.id.btn_year)
    Button mBtnYear;
    @Bind(R.id.btn_day_hour_minute)
    Button mBtnDayHourMinute;
    @Bind(R.id.btn_minute)
    Button mBtnMinute;

    TimePickerDialog mDialogAll;
    TimePickerDialog mDialogYearMonthHour;
    TimePickerDialog mDialogYearMonth;
    TimePickerDialog mDialogYear;
    TimePickerDialog mDialogYearMonthDay;
    TimePickerDialog mDialogMonthDayHourMinute;
    TimePickerDialog mDialogDayHourMinute;
    TimePickerDialog mDialogHourMinute;
    TimePickerDialog mDialogMinute;
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    long tenYears = 10L * 365 * 1000 * 60 * 60 * 24L;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date3);
        ButterKnife.bind(this);

        mBtnAll.setOnClickListener(this);
        mBtnYearMonthDay.setOnClickListener(this);
        mBtnYearMonth.setOnClickListener(this);
        mBtnMonthDayHourMinute.setOnClickListener(this);
        mBtnHourMinute.setOnClickListener(this);
        mBtnYearMonthDayHour.setOnClickListener(this);
        mBtnYear.setOnClickListener(this);
        mBtnDayHourMinute.setOnClickListener(this);
        mBtnMinute.setOnClickListener(this);

        mDialogAll = new TimePickerDialog.Builder()
                .setCallBack(this)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("TimePicker")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setHourText("时")
                .setMinuteText("分")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis())
                .setMaxMillseconds(System.currentTimeMillis() + tenYears)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.ALL)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(12)
                .build();

        mDialogYearMonth = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH)
                .setThemeColor(getResources().getColor(R.color.colorPrimary))
                .setCallBack(this)
                .build();
        mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
                .setCallBack(this)
                .build();
        mDialogMonthDayHourMinute = new TimePickerDialog.Builder()
                .setType(Type.MONTH_DAY_HOUR_MIN)
                .setCallBack(this)
                .build();
        mDialogHourMinute = new TimePickerDialog.Builder()
                .setType(Type.HOURS_MINS)
                .setCallBack(this)
                .build();
        mDialogYearMonthHour = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY_HOURS)
                .setCallBack(this)
                .build();
        mDialogYear = new TimePickerDialog.Builder()
                .setType(Type.YEAR)
                .setCallBack(this)
                .build();
        mDialogDayHourMinute = new TimePickerDialog.Builder()
                .setType(Type.DAY_HOUR_MIN)
                .setCallBack(this)
                .build();
        mDialogMinute = new TimePickerDialog.Builder()
                .setType(Type.MIN)
                .setCallBack(this)
                .build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_all://年月日时分
                mDialogAll.show(getSupportFragmentManager(), "all");
                break;
            case R.id.btn_year_month_day_hour: //年月日时
                mDialogYearMonthHour.show(getSupportFragmentManager(), "year_month_day_month");
                break;
            case R.id.btn_year_month_day: //年月日
                mDialogYearMonthDay.show(getSupportFragmentManager(), "year_month_day");
                break;
            case R.id.btn_year_month: //年月
                mDialogYearMonth.show(getSupportFragmentManager(), "year_month");
                break;
            case R.id.btn_year: //年
                mDialogYear.show(getSupportFragmentManager(), "year");
                break;
            case R.id.btn_month_day_hour_minute: //月日时分
                mDialogMonthDayHourMinute.show(getSupportFragmentManager(), "month_day_hour_minute");
                break;
            case R.id.btn_day_hour_minute: //日时分
                mDialogDayHourMinute.show(getSupportFragmentManager(), "day_hour_minute");
                break;
            case R.id.btn_hour_minute: //时分
                mDialogHourMinute.show(getSupportFragmentManager(), "hour_minute");
                break;
            case R.id.btn_minute: //分
                mDialogMinute.show(getSupportFragmentManager(), "minute");
                break;
        }
    }

    public String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        String text = getDateToString(millseconds);
        mTvTime.setText(text);
    }
}
