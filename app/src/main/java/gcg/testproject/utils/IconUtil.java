package gcg.testproject.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import gcg.testproject.R;

/**
 * @ClassName:IconUtil
 * @PackageName:com.shengwugou.utils
 * @Created by xuchuanting
 * @on 2016/12/28 0028.
 * @Site:http://www.handongkeji.com
 * @Copyrights 2016/12/28 0028 handongkeji All rights reserved.
 */
public class IconUtil {
    public static void setUserIcon1(final Context context, String url, final ImageView iv) {
        int imgid = 0;

        DrawableTypeRequest<String> load = Glide.with(context)
                .load(url);
        load.crossFade();
        load.asBitmap()
                .centerCrop()
                .placeholder(imgid)
                .error(R.mipmap.zanwuwangluo)
                .into(new BitmapImageViewTarget(iv) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        iv.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    public static void setUserIcon(boolean isRound, final Context context, String url, final ImageView iv) {
        int imgid = 0;

        if ("".equals(url) || "null".equals(url) || url == null) {
            iv.setImageResource(imgid);
        } else {
            DrawableTypeRequest<String> load = Glide.with(context)
                    .load(url);
            load.crossFade();
            load.asBitmap()
                    .centerCrop()
                    .placeholder(imgid)
                    .error(imgid)
                    .into(iv);
        }

    }
}
