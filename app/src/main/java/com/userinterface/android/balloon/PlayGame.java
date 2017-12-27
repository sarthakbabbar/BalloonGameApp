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
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.Random;

public class PlayGame extends AppCompatActivity
    implements Balloon.BalloonListner{

    private ViewGroup mContentView;
    private int[] BalloonColors = new int[5];
    private int NextColor, screenWidth,screenHeight;
    public  static int levelNumber ;
    private int levelBalloonSpeed;
    TextView levelDisplay;

    public static final int min_delay = 500;
    public static final int max_delay = 1500;
    public static final int min_duration = 1000;
    public static final int max_duration = 8000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(new MyCircle(this));
        setContentView(R.layout.activity_play_game);

        //creating colors
        BalloonColors[0] = Color.argb(255,255,0,0);
        BalloonColors[1] = Color.argb(255,0,255,0);
        BalloonColors[2] = Color.argb(255,0,0,255);
        BalloonColors[3] = Color.argb(255,130,125,0);
        BalloonColors[4] = Color.argb(255,0,125,130);

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
        //getting the intent from the button click from start page
        Intent intent = getIntent();
        String message = intent.getStringExtra("EXTRA_MESSAGE");
        Toast toast = Toast.makeText(getApplicationContext(), message, 5);
        toast.show();


        // Reading from SharedPreferences
        SharedPreferences settings = getSharedPreferences("MyStorage", MODE_PRIVATE);
        String gameLevel = settings.getString("gameLevel", "");
        Toast toast1 = Toast.makeText(getApplicationContext(), gameLevel, 5);
        toast1.show();
        levelNumber = Integer.parseInt(gameLevel);


        //adding rectangle
        if (levelNumber <= 10 ) {
            placeReactangle(1400, 0, 0);
            placeReactangle(1400, 200, 0);
            placeReactangle(1400, 400, 0);
            placeReactangle(1400, 600, 0);
            placeReactangle(1400, 800, 0);
        }
        levelDisplay = findViewById(R.id.textLevelNumber);
        updateDisplay();




    }
    // on touch event for the button end
    public void End(View view) {


        Intent intent = new Intent(this, StartPage.class);
        Button button = (Button) findViewById(R.id.btnEnd);
        String message = button.getText().toString();
        intent.putExtra("EXTRA_MESSAGE", message);
        startActivity(intent);

    }
    //on touch event for the button next
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

        startgame();

        if (levelNumber <= 10 ){
            placeReactangle(1400,0, 0);
            placeReactangle(1400,200, 0);
            placeReactangle(1400,400, 0);
            placeReactangle(1400,600,0);
            placeReactangle(1400,800,0);
        } else if (levelNumber > 10 && levelNumber <= 20 ) {
            placeReactangle(1400,0, 0);
            placeReactangle(1400,200, 1);
            placeReactangle(1400,400, 0);
            placeReactangle(1400,600,1);
            placeReactangle(1400,800,0);
        } else if (levelNumber > 20 && levelNumber <= 30 ) {
            placeReactangle(1400,0, 0);
            placeReactangle(1400,200, 1);
            placeReactangle(1400,400, 2);
            placeReactangle(1400,600,0);
            placeReactangle(1400,800,1);
        } else if (levelNumber > 30 && levelNumber <= 40 ) {
            placeReactangle(1400,0, 0);
            placeReactangle(1400,200, 1);
            placeReactangle(1400,400, 2);
            placeReactangle(1400,600,3);
            placeReactangle(1400,800,0);
        }   else if (levelNumber > 40 && levelNumber <= 50 ) {
            placeReactangle(1400,0, 0);
            placeReactangle(1400,200, 1);
            placeReactangle(1400,400, 2);
            placeReactangle(1400,600,3);
            placeReactangle(1400,800,4);
        }


    }
    // coordinates of x and y
    /*public boolean onTouchEvent(MotionEvent event) {
        String x = Float.toString(event.getX());
        String y = Float.toString(event.getY());
        String coordinates = "Value of X : " + x + " ; Value of Y : " + y;
        //int y = (int)event.getY();

        Toast pos = Toast.makeText(getApplicationContext(), coordinates, 5);
        pos.show();
        return false;
    }*/
    // starting the animation
    private void startgame(){
        levelBalloonSpeed++;
        levelNumber++;
        updateDisplay();
        BalloonLauncher launcher = new BalloonLauncher();
        launcher.execute(levelNumber);
    }
    //popping balloon on touch
    @Override
    public void popBalloonn(Balloon balloon, boolean userTouch, int currentColor) {
      if (userTouch) {
          if (levelNumber <= 10) {
              if (currentColor == BalloonColors[0]) {
                  mContentView.removeView(balloon);
                  updateDisplay();
              }else {
                  Toast gameOver = Toast.makeText(getApplicationContext(), "The game is over", 10);
                  gameOver.show();
              }
          } else if (levelNumber > 10 && levelNumber <= 20) {
              if (currentColor == BalloonColors[0] || currentColor == BalloonColors[1]) {
                  mContentView.removeView(balloon);
                  updateDisplay();
              } else {
                  Toast gameOver = Toast.makeText(getApplicationContext(), "The game is over", 10);
                  gameOver.show();
              }
          }else if (levelNumber > 20 && levelNumber <= 30) {
              if (currentColor == BalloonColors[0] || currentColor == BalloonColors[1] || currentColor == BalloonColors[2]) {
                  mContentView.removeView(balloon);
                  updateDisplay();
              } else {
                  Toast gameOver = Toast.makeText(getApplicationContext(), "The game is over", 10);
                  gameOver.show();
              }
          }else if (levelNumber > 30 && levelNumber <= 40) {
              if (currentColor == BalloonColors[0] || currentColor == BalloonColors[1] || currentColor == BalloonColors[2] || currentColor == BalloonColors[3]) {
                  mContentView.removeView(balloon);
                  updateDisplay();
              } else {
                  Toast gameOver = Toast.makeText(getApplicationContext(), "The game is over", 10);
                  gameOver.show();
              }
          }else if (levelNumber > 40 && levelNumber <= 50) {
              if (currentColor == BalloonColors[0] || currentColor == BalloonColors[1] || currentColor == BalloonColors[2] || currentColor == BalloonColors[3] || currentColor == BalloonColors[4]) {
                  mContentView.removeView(balloon);
                  updateDisplay();
              } else {
                  Toast gameOver = Toast.makeText(getApplicationContext(), "The game is over", 10);
                  gameOver.show();
              }
          }
      } else {
          if (levelNumber <= 10) {
              if (currentColor == BalloonColors[0]) {
                  Toast gameOver = Toast.makeText(getApplicationContext(), "The game is over", 10);
                  gameOver.show();
                  mContentView.removeView(balloon);
              }else {
                  mContentView.removeView(balloon);
              }
          }else if (levelNumber > 10 && levelNumber <= 20 ){
              if (currentColor == BalloonColors[0] || currentColor == BalloonColors[1]) {
                  Toast gameOver = Toast.makeText(getApplicationContext(), "The game is over", 10);
                  gameOver.show();
                  mContentView.removeView(balloon);
              }else {
                  mContentView.removeView(balloon);
              }
          }else if (levelNumber > 20 && levelNumber <= 30 ){
              if (currentColor == BalloonColors[0] || currentColor == BalloonColors[1] || currentColor == BalloonColors[2]) {
                  Toast gameOver = Toast.makeText(getApplicationContext(), "The game is over", 10);
                  gameOver.show();
                  mContentView.removeView(balloon);
              }else {
                  mContentView.removeView(balloon);
              }
          }else if (levelNumber > 30 && levelNumber <= 40 ){
                  if (currentColor == BalloonColors[0] || currentColor == BalloonColors[1] || currentColor == BalloonColors[2] || currentColor == BalloonColors[3]) {
                      Toast gameOver = Toast.makeText(getApplicationContext(), "The game is over", 10);
                      gameOver.show();
                      mContentView.removeView(balloon);
                  }else {
                      mContentView.removeView(balloon);
                  }
          }else if (levelNumber > 40 && levelNumber <= 50 ){
              if (currentColor == BalloonColors[0] || currentColor == BalloonColors[1] || currentColor == BalloonColors[2] || currentColor == BalloonColors[3] || currentColor == BalloonColors[4]) {
                  Toast gameOver = Toast.makeText(getApplicationContext(), "The game is over", 10);
                  gameOver.show();
                  mContentView.removeView(balloon);
              }else {
                  mContentView.removeView(balloon);
              }
          }
      }
    }

    private void updateDisplay() {
        levelDisplay.setText(String.valueOf(levelNumber));
    }

    private class BalloonLauncher extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... params) {

            if (params.length != 1) {
                throw new AssertionError(
                        "Expected 1 param for current level");
            }
            //game logic
            int level = params[0];
            int maxDelay = Math.max(GlobalElements.ConfigData.MIN_DELAY,
                    (GlobalElements.ConfigData.MAX_DELAY - ((level - 1) * 500)));
            int minDelay = maxDelay / 2;

            int balloonsLaunched = 0;
            while (balloonsLaunched < 5) {

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
        //adding colors to the balloon
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

//     Balloon animation begins
        if (levelNumber > 10 && levelNumber <= 20){
            levelBalloonSpeed = levelNumber - 10;
        } else if (levelNumber > 20 && levelNumber <=30){
            levelBalloonSpeed = levelNumber - 20;
        } else if (levelNumber > 30 && levelNumber <=40){
            levelBalloonSpeed = levelNumber - 30;
        } else if (levelNumber > 40 && levelNumber <=50){
            levelBalloonSpeed = levelNumber - 40;
        }

        int duration = Math.max(min_duration, max_duration- (levelBalloonSpeed * 1000));
        balloon.releaseBalloon(screenHeight, duration);

    }
    private void placeReactangle(int height, int width,int color){
        Rectangle rectangle = new Rectangle(this, BalloonColors[color],200,100);
        rectangle.setX(width);
        rectangle.setY(height);
        mContentView.addView(rectangle);
    }

}
