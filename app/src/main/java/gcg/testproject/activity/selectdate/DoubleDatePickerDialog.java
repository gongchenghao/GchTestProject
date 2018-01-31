/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gcg.testproject.activity.selectdate;

import java.lang.reflect.Field;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import gcg.testproject.R;
import gcg.testproject.utils.LogUtils;

/**
 * A simple dialog containing an {@link DatePicker}.
 *
 * <p>
 * See the <a href="{@docRoot}guide/topics/ui/controls/pickers.html">Pickers</a>
 * guide.
 * </p>AlertDialog
 */
public class DoubleDatePickerDialog extends AlertDialog implements OnClickListener, OnDateChangedListener {

	private static final String START_YEAR = "start_year";
	private static final String END_YEAR = "end_year";
	private static final String START_MONTH = "start_month";
	private static final String END_MONTH = "end_month";
	private static final String START_DAY = "start_day";
	private static final String END_DAY = "end_day";

	private final DatePicker mDatePicker_start;
	private final DatePicker mDatePicker_end;
	private final OnDateSetListener mCallBack;
	private final Button btn_cancle;
	private final Button btn_ok;
	/**
	 * The callback used to indicate the user is done filling in the date.
	 */
	public interface OnDateSetListener {

		void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth,
					   DatePicker endDatePicker, int endYear, int endMonthOfYear, int endDayOfMonth);
	}

	public DoubleDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
		this(context, 0, callBack, year, monthOfYear, dayOfMonth);
	}

	public DoubleDatePickerDialog(Context context, int theme, OnDateSetListener callBack, int year, int monthOfYear,
			int dayOfMonth) {
		this(context, 0, callBack, year, monthOfYear, dayOfMonth, true);
	}

	public DoubleDatePickerDialog(Context context, int theme, final OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth, boolean isDayVisible) {
		super(context, theme);

		mCallBack = callBack;

		Context themeContext = getContext();

		LayoutInflater inflater = (LayoutInflater) themeContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.date_picker_dialog, null);
		setView(view);
		mDatePicker_start = (DatePicker) view.findViewById(R.id.datePickerStart);
		mDatePicker_end = (DatePicker) view.findViewById(R.id.datePickerEnd);

		//找到自定义的确定按钮和取消按钮，并设置点击事件
		btn_cancle = (Button) view.findViewById(R.id.btn_cancle);
		btn_ok = (Button) view.findViewById(R.id.btn_ok);
		btn_cancle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		btn_ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				tryNotifyDateSet();
				dismiss();
			}
		});

		//设置年月日控件的宽度
		setWidth(mDatePicker_start);
		setWidth(mDatePicker_end);

		mDatePicker_start.init(year, monthOfYear, dayOfMonth, this);
		mDatePicker_end.init(year, monthOfYear, dayOfMonth, this);

		if (!isDayVisible) {
			hidDay(mDatePicker_start);
			hidDay(mDatePicker_end);
		}
	}

	//设置年月日的宽度
	private void setWidth(DatePicker dataPicker) {
		LinearLayout dpContainer = (LinearLayout)dataPicker.getChildAt(0)   ;   // LinearLayout
		LinearLayout dpSpinner = (LinearLayout)dpContainer.getChildAt(0);       // 0 : LinearLayout; 1 : CalendarView
		for(int i = 0; i < dpSpinner.getChildCount(); i ++) {
			//注意：在5.0之上，如果显示为日历样式，就将NumberPicker替换成TextView
			NumberPicker numPicker = (NumberPicker) dpSpinner.getChildAt(i);     // 0-2 : NumberPicker
			LinearLayout.LayoutParams params1 = null;
			if (i == 0) //年
			{
				params1 = new LinearLayout.LayoutParams(90, LinearLayout.LayoutParams.WRAP_CONTENT);

			}else if (i == 1) //月
			{
				params1 = new LinearLayout.LayoutParams(70, LinearLayout.LayoutParams.WRAP_CONTENT);
			} else //日
			{
				params1 = new LinearLayout.LayoutParams(70, LinearLayout.LayoutParams.WRAP_CONTENT);
			}
			params1.leftMargin = 0;
			params1.rightMargin = 30;
			numPicker.setLayoutParams(params1);
		}
	}


	/**
	 *
	 * @param mDatePicker
	 */
	private void hidDay(DatePicker mDatePicker) {
		Field[] datePickerfFields = mDatePicker.getClass().getDeclaredFields();
		for (Field datePickerField : datePickerfFields) {
			if ("mDaySpinner".equals(datePickerField.getName())) {
				datePickerField.setAccessible(true);
				Object dayPicker = new Object();
				try {
					dayPicker = datePickerField.get(mDatePicker);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
				// datePicker.getCalendarView().setVisibility(View.GONE);
				((View) dayPicker).setVisibility(View.GONE);
			}
		}
	}

	public void onClick(DialogInterface dialog, int which) {
		if (which == BUTTON_POSITIVE)
			tryNotifyDateSet();
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int month, int day) {
		if (view.getId() == R.id.datePickerStart)
			mDatePicker_start.init(year, month, day, this);
		if (view.getId() == R.id.datePickerEnd)
			mDatePicker_end.init(year, month, day, this);
	}

	/**
	 *
	 * @return The calendar view.
	 */
	public DatePicker getDatePickerStart() {
		return mDatePicker_start;
	}

	/**
	 *
	 * @return The calendar view.
	 */
	public DatePicker getDatePickerEnd() {
		return mDatePicker_end;
	}

	/**
	 * Sets the start date.
	 *
	 * @param year
	 *            The date year.
	 * @param monthOfYear
	 *            The date month.
	 * @param dayOfMonth
	 *            The date day of month.
	 */
	public void updateStartDate(int year, int monthOfYear, int dayOfMonth) {
		mDatePicker_start.updateDate(year, monthOfYear, dayOfMonth);
	}

	/**
	 * Sets the end date.
	 *
	 * @param year
	 *            The date year.
	 * @param monthOfYear
	 *            The date month.
	 * @param dayOfMonth
	 *            The date day of month.
	 */
	public void updateEndDate(int year, int monthOfYear, int dayOfMonth) {
		mDatePicker_end.updateDate(year, monthOfYear, dayOfMonth);
	}

	private void tryNotifyDateSet() {
		if (mCallBack != null) {
			mDatePicker_start.clearFocus();
			mDatePicker_end.clearFocus();
			mCallBack.onDateSet(mDatePicker_start, mDatePicker_start.getYear(), mDatePicker_start.getMonth(),
					mDatePicker_start.getDayOfMonth(), mDatePicker_end, mDatePicker_end.getYear(),
					mDatePicker_end.getMonth(), mDatePicker_end.getDayOfMonth());
		}
	}

	@Override
	protected void onStop() {
		// tryNotifyDateSet();
		super.onStop();
	}

	@Override
	public Bundle onSaveInstanceState() {
		Bundle state = super.onSaveInstanceState();
		state.putInt(START_YEAR, mDatePicker_start.getYear());
		state.putInt(START_MONTH, mDatePicker_start.getMonth());
		state.putInt(START_DAY, mDatePicker_start.getDayOfMonth());
		state.putInt(END_YEAR, mDatePicker_end.getYear());
		state.putInt(END_MONTH, mDatePicker_end.getMonth());
		state.putInt(END_DAY, mDatePicker_end.getDayOfMonth());
		return state;
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		int start_year = savedInstanceState.getInt(START_YEAR);
		int start_month = savedInstanceState.getInt(START_MONTH);
		int start_day = savedInstanceState.getInt(START_DAY);
		mDatePicker_start.init(start_year, start_month, start_day, this);

		int end_year = savedInstanceState.getInt(END_YEAR);
		int end_month = savedInstanceState.getInt(END_MONTH);
		int end_day = savedInstanceState.getInt(END_DAY);
		mDatePicker_end.init(end_year, end_month, end_day, this);

	}
}
