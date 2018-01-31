package gcg.testproject.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2017/2/23 0023.
 */

public class ORecycleView extends RecyclerView {

    public ORecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ORecycleView(Context context) {
        super(context);
    }

    public ORecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, expandSpec);
    }
}
