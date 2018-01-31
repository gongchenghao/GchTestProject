package gcg.testproject.activity.SelectVideo;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore.Video.Thumbnails;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import gcg.testproject.R;


/**
 * Created by Administrator on 2017/5/22 0022.
 */

public class MyVideoThumbLoader {
    // 创建cache
    public LruCache<String, Bitmap> lruCache;
    private Context context;

    public MyVideoThumbLoader(Context context)
    {
        this.context = context;
        int maxMemory = (int) Runtime.getRuntime().maxMemory();// 获取最大的运行内存
        int maxSize = maxMemory / 4;
        // 拿到缓存的内存大小
        lruCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                // 这个方法会在每次存入缓存的时候调用
                return value.getByteCount();
            }
        };
    }

    public void addVideoThumbToCache(String path, Bitmap bitmap) {
        if (getVideoThumbToCache(path) == null) {
            // 当前地址没有缓存时，就添加

            lruCache.put(path, bitmap);
        }
    }

    public Bitmap getVideoThumbToCache(String path) {

        return lruCache.get(path);

    }

//    public void showThumbByAsynctack(String path, MyImageView imgview) {
    public void showThumbByAsynctack(String path, ImageView imgview) {
        if (getVideoThumbToCache(path) == null) {
            // 异步加载
            new MyBobAsynctack(imgview, path).execute(path);
        } else {
            imgview.setImageBitmap(getVideoThumbToCache(path));
        }

    }

    class MyBobAsynctack extends AsyncTask<String, Void, Bitmap> {
//        private MyImageView imgView;
        private ImageView imgView;
        private String path;

//        public MyBobAsynctack(MyImageView imageView, String path) {
        public MyBobAsynctack(ImageView imageView, String path) {
            this.imgView = imageView;
            this.path = path;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
                bitmap = getVideoThumb2(params[0]);
                if (bitmap == null) {  //如果没有获取到缩略图，就使用默认的
                    bitmap = android.graphics.BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
                }
                Log.i("test2","bitmap000:"+bitmap);


                // //直接对Bitmap进行缩略操作，最后一个参数定义为OPTIONS_RECYCLE_INPUT ，来回收资源
//                Bitmap bitmap2 = tu.extractThumbnail(bitmap, 100, 100, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
//                Log.i("test1","path:bitmap2: " + bitmap2);
                // 加入缓存中
                if (getVideoThumbToCache(params[0]) == null) {
                    addVideoThumbToCache(path, bitmap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            Log.i("test2","onPostExecute:");

//            Log.i("test1","imgView.getTag():"+imgView.getTag());
//            Log.i("test1","path:"+path);

//            if (imgView.getTag().equals(path)) {// 通过 Tag可以绑定 图片地址和imageView，这是解决Listview加载图片错位的解决办法之一
                imgView.setImageBitmap(bitmap);
//            }
        }
    }

    /**
     * 获取视频文件缩略图 API>=8(2.2)
     *
     * @param path 视频文件的路径
     * @param kind 缩略图的分辨率：MINI_KIND、MICRO_KIND、FULL_SCREEN_KIND
     * @return Bitmap 返回获取的Bitmap
     */
    public static Bitmap getVideoThumb2(String path, int kind) {
        return ThumbnailUtils.createVideoThumbnail(path, kind);
    }
    public static Bitmap getVideoThumb2(String path) {
        return getVideoThumb2(path, Thumbnails.MINI_KIND);
    }

    /**
     * Bitmap保存成File
     *
     * @param bitmap input bitmap
     * @param name output file's name
     * @return String output file's path
     */
    public static String bitmap2File(Bitmap bitmap, String name) {
        File f = new File(Environment.getExternalStorageDirectory() + name + ".jpg");
        if (f.exists())
        {
            f.delete();
        }

        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            return null;
        }
        return f.getAbsolutePath();
    }

}
