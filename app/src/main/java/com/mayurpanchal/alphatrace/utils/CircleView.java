package com.mayurpanchal.alphatrace.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by rsousa on 27/06/15.
 */
public class CircleView extends View {
    public CircleView(Context mContext) {
        super(mContext);
    }
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(15f);
        canvas.drawColor(Color.WHITE);
        paint.setColor(Color.BLUE);
        canvas.drawCircle(500, 500, 100, paint);
    }
}
