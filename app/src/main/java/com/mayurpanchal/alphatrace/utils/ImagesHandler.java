package com.mayurpanchal.alphatrace.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by rsousa on 18/07/15.
 */
public final class ImagesHandler {
    public static Bitmap getBitmapFromView (View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable viewBg = view.getBackground();
        if (viewBg != null)
            viewBg.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }
}
