package com.userinterface.android.balloon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by sarthakbabbar on 18/12/17.
 */

public class MyCircle extends View
{
    public MyCircle(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int x = getWidth();
        int y = getHeight();
        int radius = 100;

        Paint paint = new Paint();

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);

        canvas.drawPaint(paint);
        paint.setColor(Color.parseColor("#CD5c5c"));

        canvas.drawCircle(x / 2, y / 2, radius, paint);

    }
}
