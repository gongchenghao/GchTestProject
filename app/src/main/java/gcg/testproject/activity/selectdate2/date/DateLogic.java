package gcg.testproject.activity.selectdate2.date;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DateLogic {
	private final static String TAG = DateLogic.class.getSimpleName();
	
	public List<GridViewData> getDateList(int year, int month, final VDate checkInDate_ed, final VDate checkOutDate_ed) {
		List<GridViewData> dataList = new ArrayList<GridViewData>();
		int monthDayNumber = CalendarUtil.getMonthMaxDay(year, month);
		int firstDayOfWeek = CalendarUtil.getTheFirstDayOfWeek(year, month);
		
		for (int i = 0; i < 42; i++) {
			GridViewData data = new GridViewData();
			int day = i - (firstDayOfWeek - 2);
			if (day <= 0 || day > monthDayNumber) {
				day = -1;
			}
			data.setDay(day);
			if (year == CalendarUtil.getYear()
					&& month == CalendarUtil.getMonth()
					&& day == CalendarUtil.getDate()) {
				data.setToday(true);
			}
			
			if (i > 7 && day == -1) {
				if (42 - i >= 7) {
					break;
				}
			}

			if (day != -1) {
				Date date = new Date(year-1900, month-1, day);
				VDate vDate = new VDate(date);
				data.setvDate(vDate);
                //1 when large,-1,when small,0 when equal
                switch (vDate.compare(checkInDate_ed)){
                    case 1:
                        if (vDate.compare(checkOutDate_ed) == 0){
                            data.setCheckType(GridViewData.CHECK_OUT_ED);
                        }else if (vDate.compare(checkOutDate_ed) == -1){
                            data.setCheckType(GridViewData.CHECK_ED_ING);
                        }

                        break;
                    case 0:
                        data.setCheckType(GridViewData.CHECK_IN_ED);
                        break;

                }
			}
			dataList.add(data);
		}
		return dataList;

	}

}
