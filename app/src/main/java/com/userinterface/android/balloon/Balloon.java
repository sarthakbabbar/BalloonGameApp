package com.userinterface.android.balloon;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

/**
 * Created by sarthakbabbar on 20/12/17.
 */

public class Balloon extends ImageView implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {

    private ValueAnimator balloonAnimator;
    public Balloon(Context context) {
        super(context);
    }

    public Balloon(Context context, int color, int rawHeight) {
        super(context);
        this.setImageResource(R.drawable.balloon);
        this.setColorFilter(color);

        int rawWidth = rawHeight/2;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(rawWidth, rawHeight);

    }

    public void releaseBalloon(int screenHeight, int duration){
        balloonAnimator = new ValueAnimator();
        balloonAnimator.setDuration(duration);
        balloonAnimator.setFloatValues(screenHeight, 0f);
        balloonAnimator.setInterpolator(new LinearInterpolator());
        balloonAnimator.setTarget(this);
        balloonAnimator.addListener(this);
        balloonAnimator.addUpdateListener(this);
        balloonAnimator.start();



    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        setY((Float) valueAnimator.getAnimatedValue());

    }

    @Override
    public void onAnimationStart(Animator animator) {

    }

    @Override
    public void onAnimationEnd(Animator animator) {

    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }
}
