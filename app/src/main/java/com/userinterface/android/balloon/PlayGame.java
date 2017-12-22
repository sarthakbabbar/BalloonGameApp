package com.userinterface.android.balloon;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import java.util.Date;
import java.util.Random;

public class PlayGame extends AppCompatActivity
    implements Balloon.BalloonListner{

    private ViewGroup mContentView;
    private int[] BalloonColors = new int[3];
    private int NextColor, screenWidth,screenHeight;
    private int levelNumber;

    public static final int min_delay = 500;
    public static final int max_delay = 1500;
    public static final int min_duration = 1000;
    public static final int max_duration = 8000;
    private int myScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(new MyCircle(this));
        setContentView(R.layout.activity_play_game);

        BalloonColors[0] = Color.argb(255,255,0,0);
        BalloonColors[1] = Color.argb(255,0,255,0);
        BalloonColors[2] = Color.argb(255,0,0,255);

        mContentView = (ViewGroup) findViewById(R.id.activity_play_game);

        ViewTreeObserver viewTreeObserver = mContentView.getViewTreeObserver();
        if (viewTreeObserver.isAlive()){
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mContentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    screenWidth = mContentView.getWidth();
                    screenHeight = mContentView.getHeight();
                }
            });


        }

        Intent intent = getIntent();
        String message = intent.getStringExtra("EXTRA_MESSAGE");
        Toast toast = Toast.makeText(getApplicationContext(), message, 5);
        toast.show();


        // Reading from SharedPreferences
        SharedPreferences settings = getSharedPreferences("MyStorage", MODE_PRIVATE);
        String gameLevel = settings.getString("gameLevel", "");
        Toast toast1 = Toast.makeText(getApplicationContext(), gameLevel, 5);
        toast1.show();




    }

    public void End(View view) {


        Intent intent = new Intent(this, StartPage.class);
        Button button = (Button) findViewById(R.id.btnEnd);
        String message = button.getText().toString();
        intent.putExtra("EXTRA_MESSAGE", message);
        startActivity(intent);

    }

    public void Next(View view) {
        //Reading level data
        SharedPreferences settings = getSharedPreferences("MyStorage", MODE_PRIVATE);
        String gameLevel = settings.getString("gameLevel", "");

        int levelNumber = Integer.parseInt(gameLevel);
        levelNumber++;
        String levelString = Integer.toString(levelNumber);

        // Writing data to SharedPreferences
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("gameLevel", levelString);
        editor.commit();

        Toast toast1 = Toast.makeText(getApplicationContext(), levelString, 5);
        toast1.show();

        startgame();


    }

    public boolean onTouchEvent(MotionEvent event) {
        String x = Float.toString(event.getX());
        String y = Float.toString(event.getY());
        String coordinates = "Value of X : " + x + " ; Value of Y : " + y;
        //int y = (int)event.getY();

        Toast pos = Toast.makeText(getApplicationContext(), coordinates, 5);
        pos.show();
        return false;
    }

    private void startgame(){
        levelNumber++;
        BalloonLauncher launcher = new BalloonLauncher();
        launcher.execute(levelNumber);
    }

    @Override
    public void popBalloonn(Balloon balloon, boolean userTouch) {
        mContentView.removeView(balloon);
        if (userTouch) {
            myScore ++;
            updateDisplay();
        }
    }

    private void updateDisplay() {
        // TODO : // Update the display
    }

    private class BalloonLauncher extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... params) {

            if (params.length != 1) {
                throw new AssertionError(
                        "Expected 1 param for current level");
            }

            int level = params[0];
            int maxDelay = Math.max(min_delay,
                    (max_delay - ((level - 1) * 500)));
            int minDelay = maxDelay / 2;

            int balloonsLaunched = 0;
            while (balloonsLaunched < 3) {

//              Get a random horizontal position for the next balloon
                Random random = new Random(new Date().getTime());
                int xPosition = random.nextInt(screenWidth - 200);
                publishProgress(xPosition);
                balloonsLaunched++;

//              Wait a random number of milliseconds before looping
                int delay = random.nextInt(minDelay) + minDelay;
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        return null;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int xPosition = values[0];
            launchBalloon(xPosition);
        }

    }

    private void launchBalloon(int x) {

        Balloon balloon = new Balloon(this, BalloonColors[NextColor], 150);

        if (NextColor + 1 == BalloonColors.length) {
            NextColor = 0;
        } else {
            NextColor++;
        }

//      Set balloon vertical position and dimensions, add to container
        balloon.setX(x);
        balloon.setY(screenHeight + balloon.getHeight());
        mContentView.addView(balloon);

//      Let 'er fly
        int duration = Math.max(min_duration, max_duration- (levelNumber * 1000));
        balloon.releaseBalloon(screenHeight, duration);

    }


}
