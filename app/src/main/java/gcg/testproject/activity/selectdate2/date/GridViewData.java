package gcg.testproject.activity.selectdate2.date;

public class GridViewData {
	
	public final static int CHECK_NORAML = 0;
	public final static int CHECK_IN = 1;
	public final static int CHECK_OUT = 2;
	public final static int CHECK_ING = 3;
	public final static int CHECK_IN_ED = 4;
	public final static int CHECK_OUT_ED = 5;
	public final static int CHECK_ED_ING = 6;
	
	private boolean isToday = false;//�Ƿ��ǵ���
	private int day;//����,�����ǰû������Ϊ-1
	/*
	 * �Ƿ���ס����꣬��סΪcheck_in�����Ϊcheck_out,��ͨΪcheck_normal,������סΪcheck_ing,
	 * ֮ǰѡ����סΪcheck_in_ed,֮ǰѡ�����Ϊcheck_out_ed,֮ǰѡ��������סΪcheck_ed_ing;
	 */
	private int checkType;
	private VDate vDate;
	
	
	
	public VDate getvDate() {
		return vDate;
	}
	public void setvDate(VDate vDate) {
		this.vDate = vDate;
	}
	public int getCheckType() {
		return checkType;
	}
	public void setCheckType(int checkType) {
		this.checkType = checkType;
	}
	public boolean isToday() {
		return isToday;
	}
	public void setToday(boolean isToday) {
		this.isToday = isToday;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	
	

}
