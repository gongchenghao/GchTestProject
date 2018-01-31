package gcg.testproject.activity.selectdate2.date;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import gcg.testproject.R;


public class DateActivity extends Activity implements OnClickListener {

	private final static String TAG = DateActivity.class.getSimpleName();

	private CustomGridView _gridView1, _gridView2, _gridView3;
	private TextView _month1, _month2, _month3;
	private GridViewAdapter _adapter1, _adapter2, _adapter3;
	private List<GridViewData> _dataList1, _dataList2, _dataList3;

	private VDate _checkInDate, _checkOutDate;
    private VDate _checkInDate_ed, _checkOutDate_ed;

	private int _clickPosition;//点击第几个月，0、1、2

	private DateLogic _logic;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_date);

		_logic = new DateLogic();
		init();
	}

	private void init() {
        _checkInDate_ed = (VDate)getIntent().getSerializableExtra("checkInDate");
        _checkOutDate_ed = (VDate)getIntent().getSerializableExtra("checkOutDate");
		initViews();
		initDatas();
	}

	private void initViews() {
		((ImageView)findViewById(R.id.date_head_back)).setOnClickListener(this);

		_dataList1 = new ArrayList<GridViewData>();
		_dataList2 = new ArrayList<GridViewData>();
		_dataList3 = new ArrayList<GridViewData>();

		_adapter1 = new GridViewAdapter(this, _checkInDate, _dataList1);
		_adapter2 = new GridViewAdapter(this, _checkInDate,  _dataList2);
		_adapter3 = new GridViewAdapter(this, _checkInDate,  _dataList3);

		View include1 = (View)findViewById(R.id.date_include1);
		View include2 = (View)findViewById(R.id.date_include2);
		View include3 = (View)findViewById(R.id.date_include3);

		_gridView1 = (CustomGridView)include1.findViewById(R.id.include_date_gridview);
		_gridView2 = (CustomGridView)include2.findViewById(R.id.include_date_gridview);
		_gridView3 = (CustomGridView)include3.findViewById(R.id.include_date_gridview);

		_gridView1.setAdapter(_adapter1);
		_gridView2.setAdapter(_adapter2);
		_gridView3.setAdapter(_adapter3);

		_month1 = (TextView)include1.findViewById(R.id.include_date_month);
		_month2 = (TextView)include2.findViewById(R.id.include_date_month);
		_month3 = (TextView)include3.findViewById(R.id.include_date_month);
	}

	private void initDatas() {
		int year = CalendarUtil.getYear();
		int month = CalendarUtil.getMonth();
		_month1.setText(year+"年"+ month+"月");

		_dataList1.addAll(_logic.getDateList(CalendarUtil.getYear(), CalendarUtil.getMonth(),_checkInDate_ed, _checkOutDate_ed));
		if (month == 11) {
			_month2.setText(year+"年"+(month+1)+"月");
			_month3.setText(year+1+"年"+1+"月");
			_dataList2.addAll(_logic.getDateList(year, month+1,_checkInDate_ed, _checkOutDate_ed));
			_dataList3.addAll(_logic.getDateList(year+1, 1,_checkInDate_ed, _checkOutDate_ed));
		}else if(month == 12){
			_month2.setText(year+1+"年"+1+"月");
			_month3.setText(year+1+"年"+2+"月");
			_dataList2.addAll(_logic.getDateList(year+1, 1,_checkInDate_ed, _checkOutDate_ed));
			_dataList3.addAll(_logic.getDateList(year+1, 2,_checkInDate_ed, _checkOutDate_ed));
		}else{
			_month2.setText(year+"年"+(month+1)+"月");
			_month3.setText(year+"年"+(month+2)+"月");
			_dataList2.addAll(_logic.getDateList(year, month+1,_checkInDate_ed, _checkOutDate_ed));
			_dataList3.addAll(_logic.getDateList(year, month+2,_checkInDate_ed, _checkOutDate_ed));
		}

		_adapter1.notifyDataSetChanged();

		_adapter2.notifyDataSetChanged();

		_adapter3.notifyDataSetChanged();

		_gridView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				_clickPosition = 0;
				judgeDate(_dataList1.get(position).getDay(), position, _dataList1);

			}

		});

		_gridView2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				_clickPosition = 1;
				judgeDate(_dataList2.get(position).getDay(), position, _dataList2);
			}

		});

		_gridView3.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				_clickPosition = 3;
				judgeDate(_dataList3.get(position).getDay(), position, _dataList3);
			}

		});
	}

    private void setSelectCheckIn(){
        _adapter1.setSelected();
        _adapter2.setSelected();
        _adapter3.setSelected();
    }

	private void judgeDate(int day, int position, List<GridViewData> dataList ) {
		if (day == -1) {
			return; 
		}

		VDate vDate = dataList.get(position).getvDate();
		if (null != vDate) {
			judgeDate(vDate, dataList, position);
		}else{
		}

		_adapter1.notifyDataSetChanged();
		_adapter2.notifyDataSetChanged();
		_adapter3.notifyDataSetChanged();
		if (null != _checkOutDate) {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					Intent intent = new Intent();
					intent.putExtra("checkInDate", _checkInDate);
					intent.putExtra("checkOutDate", _checkOutDate);
					DateActivity.this.setResult(Activity.RESULT_OK, intent);
					DateActivity.this.finish();
				}
			}, 1000);
		}
	}

	private void judgeDate(VDate clickDate, List<GridViewData> dataList, int position) {

		if (null == _checkInDate) {
//			_isSelectCheckIn = true;
			//设置入住日期
			Date currDate = new Date(CalendarUtil.getYear()-1900, 
					CalendarUtil.getMonth()-1, CalendarUtil.getDate());
			VDate vDate = new VDate(currDate) ;
			if (clickDate.compare(vDate) < 0) {
				_checkInDate = null;
				return;//在当天之前不允许点击
			}
            setSelectCheckIn();
			_checkInDate = clickDate;

			setDataCheckType(dataList, position, GridViewData.CHECK_IN);

			_adapter1.setCheckInVdate( _checkInDate);
			_adapter2.setCheckInVdate( _checkInDate);
			_adapter3.setCheckInVdate( _checkInDate);

		}else{
			//设置离店日期
			if (_checkInDate.compare(clickDate) > 0) {
				Toast.makeText(this, "退房日期必须比入住日期大~", Toast.LENGTH_LONG).show();
				return;
			}else if (_checkInDate.compare(clickDate) == 0) {
				setDataCheckType(dataList, position, GridViewData.CHECK_NORAML);
				_checkInDate = null;
				_adapter1.setCheckInVdate( null);
				_adapter2.setCheckInVdate( null);
				_adapter3.setCheckInVdate( null);
//				_isSelectCheckIn = false;
				return;
			}
			_checkOutDate = clickDate;
			setDataCheckType(dataList, position, GridViewData.CHECK_OUT);

			//把中间设为check_ing
			boolean isCheck_in = false;
			boolean isCheck_out = false;
			for (int i = 0; i < _dataList1.size(); i++) {
				if (i > 0 &&_dataList1.get(i-1).getCheckType() == GridViewData.CHECK_IN) {
					isCheck_in = true;
				}
				if (isCheck_in && !isCheck_out) {
					_dataList1.get(i).setCheckType(GridViewData.CHECK_ING);
				}
				if (i< _dataList1.size()-1 && _dataList1.get(i+1).getCheckType() == GridViewData.CHECK_OUT) {
					isCheck_out = true;
					isCheck_in = false;
				}
			}
			for (int i = 0; i < _dataList2.size(); i++) {
				if (i > 0 &&_dataList2.get(i-1).getCheckType() == GridViewData.CHECK_IN) {
					isCheck_in = true;
				}
				if (isCheck_in && !isCheck_out) {
					_dataList2.get(i).setCheckType(GridViewData.CHECK_ING);

				}
				if (i< _dataList2.size()-1 && _dataList2.get(i+1).getCheckType() == GridViewData.CHECK_OUT) {
					isCheck_out = true;
					isCheck_in = false;
				}

			}
			for (int i = 0; i < _dataList3.size(); i++) {
				if (i > 0 &&_dataList3.get(i-1).getCheckType() == GridViewData.CHECK_IN) {
					isCheck_in = true;
				}
				if (isCheck_in && !isCheck_out) {
					_dataList3.get(i).setCheckType(GridViewData.CHECK_ING);
				}
				if (i< _dataList3.size()-1 && _dataList3.get(i+1).getCheckType() == GridViewData.CHECK_OUT) {
					isCheck_out = true;
					isCheck_in = false;
				}
			}

		}
	}

	private void setDataCheckType(List<GridViewData> dataList, int position, int checkType ) {
		dataList.get(position).setCheckType(checkType);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.date_head_back:
			finish();
			break;
		default:
			break;
		}

	}


}
