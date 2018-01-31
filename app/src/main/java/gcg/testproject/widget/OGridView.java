package gcg.testproject.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author liuxiu 自定义gridview，为了解决在ScrollView中嵌套gridview时显示不全的问题
 * 
 */
public class OGridView extends GridView {
	public OGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public OGridView(Context context) {
		super(context);
	}

	public OGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
