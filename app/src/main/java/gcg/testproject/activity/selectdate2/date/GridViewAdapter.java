package gcg.testproject.activity.selectdate2.date;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import gcg.testproject.R;

public class GridViewAdapter extends BaseAdapter{
	private final static String TAG = GridViewAdapter.class.getSimpleName();

	private Context _context;
	private List<GridViewData> _dataList;
	private VDate todayVdate;
	private boolean _isSelectCheckIn = false;//是否选择了入住日期，如果是，则把之前选的的日期都去掉
	private VDate 	_checkInVDate;

	public GridViewAdapter(Context context, VDate checkInVDate, List<GridViewData> dataList) {
		this._context = context;
		this._dataList = dataList;
		this._checkInVDate = checkInVDate;
		Date date = new Date(CalendarUtil.getYear()-1900, 
				CalendarUtil.getMonth()-1, CalendarUtil.getDate());
		todayVdate = new VDate(date);
	}
	@Override
	public int getCount() {
		return _dataList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return _dataList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}


    public void setSelected(){
        _isSelectCheckIn = true;

    }
	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		
		View itemView = null;
		if (null == view) {
			itemView = ((Activity)_context).getLayoutInflater().inflate(R.layout.item_date_gridview, null);
			view = itemView;
			view.setTag(itemView);
		}else{
			itemView = (View)(view.getTag());
		}
		TextView day = (TextView)itemView.findViewById(R.id.item_date_day);
		TextView type = (TextView)itemView.findViewById(R.id.item_date_type);
//		final ImageView cancel = (ImageView)itemView.findViewById(R.id.item_date_cancel);
		RelativeLayout layout = (RelativeLayout)itemView.findViewById(R.id.item_date_layout);
		
		layout.setVisibility(View.VISIBLE);
//		cancel.setVisibility(View.GONE);
		
		GridViewData data = _dataList.get(position);
		
		//周末字体用橘色
		if (position == 0 || (position%7 == 0 || (position+1)%7 == 0)) {
			day.setTextColor(_context.getResources().getColor(R.color.main_service_normal));
		}else{
			day.setTextColor(_context.getResources().getColor(R.color.font_1));
		}
		//在过去的日子使用灰色字体
		if (null != data.getvDate() && todayVdate.compare(data.getvDate()) > 0) {
			day.setTextColor(_context.getResources().getColor(R.color.font_3));
		}
		//在入住之前的日子使用灰色字体
		if (null != _checkInVDate && data.getvDate() != null && _checkInVDate.compare(data.getvDate()) > 0) {
			day.setTextColor(_context.getResources().getColor(R.color.font_3));
		}
		
//		cancel.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				_dataList.get(position).setCheckType(GridViewData.CHECK_NORAML);
//				_checkInVDate = null;
//				cancel.setVisibility(View.GONE);
//				notifyDataSetChanged();
//			}
//		});

        day.setBackgroundResource(R.color.transparent);
		if (data.getDay() == -1) {
			day.setText(" ");
			type.setText("入住");
			type.setVisibility(View.INVISIBLE);
		}else{
			day.setText(""+data.getDay());
			switch (data.getCheckType()) {
			case GridViewData.CHECK_IN:
				type.setVisibility(View.VISIBLE);
				type.setText("入住");
//				cancel.setVisibility(View.VISIBLE);
				day.setTextColor(_context.getResources().getColor(R.color.white));
				type.setTextColor(_context.getResources().getColor(R.color.font_blue_normal));
				day.setBackgroundResource(R.drawable.shape_btn_blue_round_solid_date);
//				layout.setBackgroundColor(_context.getResources().getColor(R.color.head_blue));
				break;
			case GridViewData.CHECK_OUT:
				type.setVisibility(View.VISIBLE);
				type.setText("退房");
				day.setTextColor(_context.getResources().getColor(R.color.white));
				type.setTextColor(_context.getResources().getColor(R.color.font_blue_normal));
				day.setBackgroundResource(R.drawable.shape_btn_blue_round_solid_date);
//				layout.setBackgroundColor(_context.getResources().getColor(R.color.head_blue));
				break;
			case GridViewData.CHECK_ING:
				day.setTextColor(_context.getResources().getColor(R.color.font_blue_normal));
				day.setBackgroundResource(R.drawable.shape_btn_blue_round_stroke_date);
//				layout.setBackgroundColor(_context.getResources().getColor(R.color.head_blue));
				break;
			case GridViewData.CHECK_NORAML:
				type.setVisibility(View.INVISIBLE);
				day.setBackgroundResource(R.color.transparent);
//				layout.setBackgroundColor(_context.getResources().getColor(R.color.white));
				//判断是否是今天
				if (data.isToday()) {
//					layout.setVisibility(View.INVISIBLE);
//					today.setVisibility(View.VISIBLE);
                    day.setText("今天");
                    day.setTextColor(_context.getResources().getColor(R.color.font_blue_normal));
				}
				break;
			case GridViewData.CHECK_IN_ED:
                if (!_isSelectCheckIn){
                    day.setTextColor(_context.getResources().getColor(R.color.font_2));
                    day.setBackgroundResource(R.drawable.shape_btn_gray_round_stroke_date);
                    type.setVisibility(View.VISIBLE);
                    type.setText("入住");
                    type.setTextColor(_context.getResources().getColor(R.color.font_2));
                }else{
                    type.setVisibility(View.INVISIBLE);
                    day.setBackgroundResource(R.color.transparent);
                }

				break;
			case GridViewData.CHECK_OUT_ED:
                if (!_isSelectCheckIn){
                    day.setTextColor(_context.getResources().getColor(R.color.font_2));
                    day.setBackgroundResource(R.drawable.shape_btn_gray_round_stroke_date);
                    type.setVisibility(View.VISIBLE);
                    type.setText("退房");
                    type.setTextColor(_context.getResources().getColor(R.color.font_2));
                }else{
                    type.setVisibility(View.INVISIBLE);
                    day.setBackgroundResource(R.color.transparent);
                }

				break;
			case GridViewData.CHECK_ED_ING:
                if (!_isSelectCheckIn){
                    day.setTextColor(_context.getResources().getColor(R.color.font_2));
                    day.setBackgroundResource(R.drawable.shape_btn_gray_round_stroke_date);
                    type.setVisibility(View.INVISIBLE);
                }else{
                    day.setBackgroundResource(R.color.transparent);
                }
				break;
			}
		}
		return view;
	}
	
	public void setCheckInVdate(VDate checkInVDate) {
		this._checkInVDate = checkInVDate;
	}

}
