package gcg.testproject.utils;

import android.annotation.TargetApi;
import android.graphics.Paint;
import android.os.Build;
import android.view.ViewTreeObserver;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/2/15 0015.
 */

public class TextViewUtils {
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void textSet(final TextView textView, String text)
    {
        textView.setText(text);
        ViewTreeObserver observer = textView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        textView.setText(autoSplitText(textView));
                    }
                });
        observer.removeOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

            }
        });
    }


    public static String autoSplitText(TextView tv) {
        if (tv == null)
        {
            return null;
        }
        String rawText = tv.getText().toString(); //原始文本
        Paint tvPaint = tv.getPaint(); //paint，包含字体等信息
        float tvWidth = tv.getWidth() - tv.getPaddingLeft() - tv.getPaddingRight(); //控件可用宽度

        //将原始文本按行拆分
        String[] rawTextLines = rawText.replaceAll("\r", "").split("\n");
        StringBuilder sbNewText = new StringBuilder();
        for (String rawTextLine : rawTextLines) {
            if (tvPaint.measureText(rawTextLine) <= tvWidth) {
                //如果整行宽度在控件可用宽度之内，就不处理了
                sbNewText.append(rawTextLine);
            } else {
                //如果整行宽度超过控件可用宽度，则按字符测量，在超过可用宽度的前一个字符处手动换行
                float lineWidth = 0;
                for (int cnt = 0; cnt != rawTextLine.length(); ++cnt) {
                    char ch = rawTextLine.charAt(cnt);
                    lineWidth += tvPaint.measureText(String.valueOf(ch));
                    if (lineWidth <= tvWidth) {
                        sbNewText.append(ch);
                    } else {
                        sbNewText.append("\n");
                        lineWidth = 0;
                        --cnt;
                    }
                }
            }
            sbNewText.append("\n");
        }
        //把结尾多余的\n去掉
        if (!rawText.endsWith("\n")) {
            sbNewText.deleteCharAt(sbNewText.length() - 1);
        }
        return sbNewText.toString();
    }

}
