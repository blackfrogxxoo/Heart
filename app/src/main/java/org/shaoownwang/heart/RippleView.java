package org.shaoownwang.heart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wxc on 2016/5/7.
 */
public class RippleView extends View {

    private float width;
    private float height;

    private float density;
    private Paint paint;
    private RadialGradient shader;
    private int startColor = getResources().getColor(R.color.colorAccent);
    private int endColor = getResources().getColor(R.color.color_light_red);

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    private float progress = 75;

    public RippleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        density = getResources().getDisplayMetrics().density;

        paint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float cx = width/2;
        float cy = height/2;
        shader = new RadialGradient(width / 2, height / 2,
                calculateRippleRadius(), startColor, endColor, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        canvas.drawCircle(cx, cy, calculateRippleRadius(), paint);
    }

    private float calculateRadius() {
        return (100-progress/5) * width / 200;
    }


    private float calculateRippleRadius() {
        return (100-progress/5) * height / 200;
    }

    public void setStartColor(int startColor) {
        this.startColor = startColor;
        invalidate();
    }



    public void setEndColor(int endColor) {
        this.endColor = endColor;
        invalidate();
    }
}
