package com.userinterface.android.balloon;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by sarthakbabbar on 20/12/17.
 */
// Balloon class for animation of the balloon
public class Balloon extends ImageView implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {

    private ValueAnimator balloonAnimator;
    private BalloonListner touchListner;
    private boolean popped;
    private int intrensicBalloonColor;
    public Balloon(Context context) {
        super(context);
    }

    // creating balloon and adding the colors to the balloon
    public Balloon(Context context, int color, int rawHeight) {
        super(context);
        touchListner = (BalloonListner) context;
        this.setImageResource(R.drawable.balloon);
        this.setColorFilter(color);
        intrensicBalloonColor = color;
        int rawWidth = rawHeight/2;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(rawWidth, rawHeight);

    }
    // animation
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
        //put if else state to end the game if the animation ends for the balloon that is supposed to be popped
        if(!popped){
            touchListner.popBalloonn(this,false, intrensicBalloonColor);

        }
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }
    // stop the balloon animation on touch
    @Override
    public boolean onTouchEvent(MotionEvent event)


    {
        if (!popped && event.getAction() == MotionEvent.ACTION_DOWN){
            touchListner.popBalloonn(this,true, intrensicBalloonColor);
            int testColor = Color.argb(255,255,0,0);
            if (intrensicBalloonColor == Color.argb(255,255,0,0) )
            {
                popped = true;
                balloonAnimator.cancel();
                boolean returnValue = super.onTouchEvent(event);
                return returnValue;
            }


            /* if (PlayGame.levelNumber <= 10 && currentColor == Color.argb(255,255,0,0) ){
                popped = true;
                balloonAnimator.cancel();
            } else if (PlayGame.levelNumber > 10 && PlayGame.levelNumber <= 20 && (currentColor == Color.argb(255,255,0,0) || currentColor == Color.argb(255,0,255,0))){
                popped = true;
                balloonAnimator.cancel();

            }else if (PlayGame.levelNumber > 20 && PlayGame.levelNumber <= 30 && (currentColor == Color.argb(255,255,0,0) || currentColor == Color.argb(255,0,255,0) || currentColor == Color.argb(255,0,0,255))){
                popped = true;
                balloonAnimator.cancel();

            }else if (PlayGame.levelNumber > 30 && PlayGame.levelNumber <= 40 && (currentColor == Color.argb(255,255,0,0) || currentColor == Color.argb(255,0,255,0) || currentColor == Color.argb(255,0,0,255) || currentColor == Color.argb(255,130,125,0))){
                popped = true;
                balloonAnimator.cancel();

            } else if (PlayGame.levelNumber > 40 && PlayGame.levelNumber <= 50 ) {
                popped = true;
                balloonAnimator.cancel();
            } else if (PlayGame.levelNumber > 50){
                popped = true;
                balloonAnimator.cancel();
            } else  {
                return false;
                //put a dialog to end the game (how to return to the start page from here ?)
                //
            }*/
        }
        return !super.onTouchEvent(event);
    }
    // checks if the balloon is touched
    public interface BalloonListner{
        void popBalloonn(Balloon balloon, boolean userTouch, int currentColor);

    }
}
