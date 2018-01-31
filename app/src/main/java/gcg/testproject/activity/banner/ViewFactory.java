package gcg.testproject.activity.banner;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import gcg.testproject.R;

/**
 * ImageView创建工厂
 */
public class ViewFactory {

	/**
	 * 获取ImageView视图的同时加载显示url
	 * 
	 * @param url
	 * @return
	 */
	public static ImageView getImageView(Context context, String url) {
		ImageView imageView = (ImageView)LayoutInflater.from(context).inflate(R.layout.item_view_banner, null);
		Glide.with(context).load(url).into(imageView);
		return imageView;
	}
}
