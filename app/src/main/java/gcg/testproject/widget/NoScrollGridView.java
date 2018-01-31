package gcg.testproject.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
* Created by IntelliJ IDEA.
* User: zhUser
* Date: 13-1-24
* Time: 下午6:53
*/
public class NoScrollGridView extends GridView {

     public NoScrollGridView(Context context, AttributeSet attrs){
          super(context, attrs);
     }
     
	public NoScrollGridView(Context context, AttributeSet attrs,
                            int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}



	public NoScrollGridView(Context context) {
		super(context);
	}

	@Override
     public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
          int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
          super.onMeasure(widthMeasureSpec, mExpandSpec);
     }
     @Override
     public boolean dispatchTouchEvent(MotionEvent ev) {
         //下面这句话是关键
     	if (ev.getAction()== MotionEvent.ACTION_MOVE) {
     		return true;
     	}
     	return super.dispatchTouchEvent(ev);//也有所不同哦
     }
}


