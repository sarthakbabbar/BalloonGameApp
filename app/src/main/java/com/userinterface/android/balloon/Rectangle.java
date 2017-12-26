package com.userinterface.android.balloon;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by sarthakbabbar on 22/12/17.
 */

public class Rectangle extends ImageView {
    public Rectangle(Context context, int color, int screenHeight,int screenWidth) {
        super(context);
        this.setImageResource(R.drawable.rectangle);
        this.setColorFilter(color);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(screenWidth, screenHeight);

    }

}
