package gcg.testproject.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/6/21 0021.
 */

public class DownLoadUtils extends AsyncTask<String, Integer, String> {
    private Context context;
    private PowerManager.WakeLock mWakeLock;
//    private String path = Constants.CACHE_DIR + "/download/"+System.currentTimeMillis();
    private String path ;
    private TextView textView;
    private long len;

    public DownLoadUtils(Context context, String fileName, TextView textView,long len) {
        Log.i("test1","DownLoadUtils2");

        this.context = context;
        path = Environment.getExternalStorageDirectory().toString() + "/download/"+fileName;
        this.len = len;
        this.textView = textView;
    }
    @Override
    protected String doInBackground(String... sUrl) {
        Log.i("test1","doInBackground");

        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            Log.i("test1","try");

            Log.i("test1","sUrl[0]:"+sUrl[0]);

            URL url = new URL(sUrl[0]);

            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage();
            }
            // this will be useful to display download percentage
            // might be -1: server did not report the length
//            int fileLength = connection.getContentLength();

            // download the file
            input = connection.getInputStream();
            output = new FileOutputStream(path);
            Log.i("test1","path"+path);

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                // allow canceling with back button
                if (isCancelled()) {
                    input.close();
                    return null;
                }
                total += count;
                Log.i("test1","count:"+count);

                Log.i("test1","total:"+total);
                Message msg = new Message();


                // publishing the progress....
                if (len > 0) // only if length is known
                {
                    msg.obj = total*100/len +"%";
                    handler.sendMessage(msg);
                }
                output.write(data, 0, count);
            }
        } catch (Exception e) {
            Log.e("cuowu",e.getMessage());
            return e.toString();
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }
            if (connection != null)
                connection.disconnect();
        }
        return null;
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String pro = (String) msg.obj;
            Log.i("test1","pro:"+pro);
//            if(pro.equals("100%"))
//            {
//                textView.setText("下载完成");
//            }else {
                textView.setText(pro);
//            }
        }
    };
}
