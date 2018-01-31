package gcg.testproject.activity.SelectVideo;

import android.database.Cursor;
import android.provider.MediaStore.Video.Media;

import java.io.Serializable;

/**
 * Created by dzl on 2016/9/27.
 */
public class VideoItem implements Serializable {

    // Media._ID, Media.TITLE, Media.SIZE, Media.DURATION, Media.DATA
    public String title;
    /** 视频的保存路径 */
    public String data;
    public long size;
    public long duration;

    /**
     * 把Cursor转换为一个视频JavaBean
     * @param cursor
     * @return
     */
    public static VideoItem fromCursor(Cursor cursor) {
        VideoItem item = new VideoItem();
        item.title = cursor.getString(cursor.getColumnIndex(Media.TITLE));
        item.data = cursor.getString(cursor.getColumnIndex(Media.DATA));
        item.size = cursor.getLong(cursor.getColumnIndex(Media.SIZE));
        item.duration = cursor.getLong(cursor.getColumnIndex(Media.DURATION));
        return item;
    }
}
