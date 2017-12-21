package com.userinterface.android.balloon;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

public class PlayGame extends AppCompatActivity {

    private ViewGroup mContentView;
    private int[] BalloonColors = new int[3];
    private int NextColor, screenWidth,screenHeight;

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

        mContentView .setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                    Balloon b = new Balloon(PlayGame.this, BalloonColors[NextColor],100);
                    b.setX(motionEvent.getX());
                    b.setY(screenHeight);
                    mContentView.addView(b);
                    b.releaseBalloon(screenHeight, 3000);

                    if (NextColor+1 == BalloonColors.length){
                        NextColor = 0;

                    } else {
                        NextColor ++;

                    }

                return false;
            }
        });
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



}
