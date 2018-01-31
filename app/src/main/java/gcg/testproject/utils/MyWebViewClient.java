package gcg.testproject.utils;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Administrator on 2017/6/26 0026.

 作用：让WebView加载的图片按照屏幕大小进行缩放
 用法：
 WebSettings settings = webView.getSettings();
 settings.setJavaScriptEnabled(true);

 webView.setWebViewClient(new MyWebViewClient(webView));
 */

public class MyWebViewClient extends WebViewClient {
    public MyWebViewClient(WebView webView) {
        this.webView = webView;
    }

    private WebView webView;

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        imgReset();//重置webview中img标签的图片大小
        // html加载完成之后，添加监听图片的点击js函数
        addImageClickListner();
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    /**
     * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
     **/
    private void imgReset() {
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
                "}" +
                "})()");
    }
    private void addImageClickListner() {
        // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistner.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");
    }
}
