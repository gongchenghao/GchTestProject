package gcg.testproject.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @author liuxiu 自定义listview，为了解决在ScrollView中嵌套listview时显示不全的问题
 * 
 */
public class OListView extends ListView {
	public OListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public OListView(Context context) {
		super(context);
	}

	public OListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
