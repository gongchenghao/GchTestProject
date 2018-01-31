package gcg.testproject.activity.ListViewDelete;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import gcg.testproject.R;

/**
 * @author blank
 * @time 下午4:01:07
 * @version V1.0
 */
public class SlideViewAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater layoutInflr;
	private int itemtype = 1;
	private OnRemoveListener mRemoveListener;

	public int getItemtype() {
		return itemtype;
	}

	public void setItemtype(int itemtype) {
		this.itemtype = itemtype;
	}

	public SlideViewAdapter(Context context) {
		this.mContext = context;
		this.layoutInflr = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public void setRemoveListener(OnRemoveListener removeListener) {
		this.mRemoveListener = removeListener;
	}

	class Viewlayout {
		private TextView mDelete;// 隐藏的侧滑删除按钮

	}

	@Override
	public int getCount() {
		return 10;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Viewlayout icom_view;
		if (convertView == null) {
			icom_view = new Viewlayout();
			convertView = layoutInflr.inflate(R.layout.item_listview_delete2,
					null);
			icom_view.mDelete = (TextView) convertView
					.findViewById(R.id.tvDelete);
			convertView.setTag(icom_view);
		} else {
			icom_view = (Viewlayout) convertView.getTag();
		}
		icom_view.mDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mRemoveListener != null)
					mRemoveListener.onRemoveItem(position);
			}
		});

		return convertView;
	}

	public interface OnRemoveListener {
		void onRemoveItem(int position);
	}

}
