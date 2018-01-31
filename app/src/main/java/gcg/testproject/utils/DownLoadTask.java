package gcg.testproject.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

import gcg.testproject.R;

public class DownLoadTask extends AsyncTask<String, String, Integer> {

    private File file;
    private String path;
    private Context context;
    private TextView size, progress;
    private ProgressBar progressBar;
    private boolean can_download;
    private AlertDialog dialog;
    private HttpURLConnection conn;
    private boolean is_download = true;

    public DownLoadTask(String path, Context context) {
        this.path = path;
        this.context = context;
        Log.i("test111","DownLoadTask");
    }

    @Override
    protected Integer doInBackground (String... params) {
        // TODO Auto-generated method stub
        try {
            if (can_download) {
                final URL url = new URL (path);
                conn = (HttpURLConnection) url.openConnection ();
                conn.setRequestMethod ("GET");
                conn.setConnectTimeout (30 * 1000);
                conn.setDoInput (true);
                conn.connect ();
                final InputStream is = conn.getInputStream ();
                final int total = conn.getContentLength ();
                final FileOutputStream fos = new FileOutputStream (file);
                int percent = 0;
                final byte[] read = new byte[ 1024 ];
                final int unit = 1024 * 1024;
                int total_read = 0;
                final DecimalFormat df = new DecimalFormat ("0.#");
                final String total_size = df.format ((double) total / unit) + "M";
                while ((percent = is.read (read)) != - 1 && is_download) {
                    total_read += percent;
                    publishProgress (total_size, df.format ((double) total_read / unit) + "M", String.valueOf ((int) (((double) total_read / total) * 100)));
                    fos.write (read, 0, percent);
                }
                fos.close ();
                return 0;
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace ();
            return - 1;
        }
        return - 1;
    }

    @Override
    protected void onPostExecute (Integer result) {
        // TODO Auto-generated method stub
        super.onPostExecute (result);
        Log.i("test111","onPostExecute");
        dialog.dismiss ();
        Log.i("test111","result："+result);
        if (result == 0) {
            Intent intent = new Intent (Intent.ACTION_VIEW);
            intent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType (Uri.fromFile (file), "application/vnd.android.package-archive");
            context.startActivity (intent);
        } else {
            Toast.makeText (context, "下载失败", Toast.LENGTH_SHORT).show ();
        }
    }

    @Override
    protected void onProgressUpdate (String... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate (values);
        Log.i("test111","onProgressUpdate——values[ 2 ]："+values[2]);
        size.setText (values[ 1 ] + "/" + values[ 0 ]);
        progress.setText (values[ 2 ] + "%");
        progressBar.setProgress (Integer.parseInt (values[ 2 ]));
    }

    @Override
    protected void onPreExecute () {
        // TODO Auto-generated method stub
        super.onPreExecute ();
        if (Environment.getExternalStorageState ().equals (Environment.MEDIA_MOUNTED)) {
            can_download = true;
            Log.i("test111","can_download："+can_download);
            showDialog ();
            File fileDir = new File (Environment.getExternalStorageDirectory ().getPath () + "/xinyi8091");
            if (! fileDir.exists ()) {
                fileDir.mkdir ();
            }
//			this.file = new File(Environment.getExternalStorageDirectory().getPath(), this.path.substring(this.path.lastIndexOf("/"), this.path.length()));
            file = new File (Environment.getExternalStorageDirectory ().getPath () + "/xinyi8091", path.substring (path.lastIndexOf ("/"), path.length ()));
        } else {
            Toast.makeText (context, "SD卡不可用", Toast.LENGTH_SHORT).show ();
            can_download = false;
        }
    }

    private void showDialog () {
        Log.i("test111","showDialog");
        if (dialog == null) {
            Log.i("test111","dialog == null");
            View pb = LayoutInflater.from (context).inflate (R.layout.publish_version, null);
            size = (TextView) pb.findViewById (R.id.size);
            progress = (TextView) pb.findViewById (R.id.progress);
            progressBar = (ProgressBar) pb.findViewById (R.id.pb);
//		dialog=new AlertDialog.Builder(new ContextThemeWrapper(context,R.style.apk_loading_dialog))

            dialog = new AlertDialog.Builder (context,AlertDialog.THEME_HOLO_LIGHT)
                    .setTitle ("下载安装包").setCancelable (false)
                    .setView (pb).setPositiveButton ("取消", new DialogInterface.OnClickListener () {

                        @Override
                        public void onClick (DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            is_download = false;
                            //cancel(true);
                            conn.disconnect ();
                            file.delete ();
                        }
                    }).create ();
//            dialog.getWindow ().setContentView (pb);
        }
        dialog.show ();
    }
}

