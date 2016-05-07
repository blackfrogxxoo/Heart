package org.shaoownwang.heart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wxc on 2016/5/7.
 */
public class HeartView extends View {

    private float density;
    private Paint paint;
    private int offset = -20;
    private double a = 75;
    private int heartColor = getResources().getColor(R.color.colorAccent);
    private Path path1;
    private Path path2;

    public HeartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        density = getResources().getDisplayMetrics().density;

        paint = new Paint();
        paint.setStrokeWidth(2 * density);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(heartColor);

        float width = getMeasuredWidth();
        float height = getMeasuredHeight();
        // 公式： x=a*(2*cos(t)-cos(2*t))
        //       y=a*(2*sin(t)-sin(2*t))
        int n = 600;
        for(int i=offset; i<n-offset; i++) {
            double t = i * Math.PI / n;
            double ty = (i-offset) * Math.PI / n;
            float y = height/2 - (float) (a*(2*Math.cos(t) - Math.cos(2 * t))) - density * 16;
            float x1 = width/2 - (float) (a*(2*Math.sin(ty) - Math.sin(2 * ty)));
            float x2 = width/2 + (float) (a*(2*Math.sin(ty) - Math.sin(2 * ty)));
            if(i==offset) {
                path1 = new Path();
                path2 = new Path();
                path1.moveTo(x1, y);
                path2.moveTo(x2, y);
                continue;
            } else {
                if(x1<=x2) {
                    path1.lineTo(x1, y);
                    path2.lineTo(x2, y);
                }
            }
        }

        path1.close();
        path2.close();
        canvas.drawPath(path1, paint);
        canvas.drawPath(path2, paint);
    }

    public void setoffset(int offset) {
        this.offset = offset;
        invalidate();
    }

    public void setA(double a) {
        this.a = a;
        invalidate();
    }

    public void setHeartColor(int heartColor) {
        this.heartColor = heartColor;
        invalidate();
    }
}
