package com.userinterface.android.balloon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor ;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StartPage extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        Intent intent = getIntent();
        String message = intent.getStringExtra("EXTRA_MESSAGE");
        Toast toast = Toast.makeText(getApplicationContext(), message, 5);
        toast.show();



    }

    public void Play(View view) {
        // Writing data to SharedPreferences
        SharedPreferences settings = getSharedPreferences("MyStorage", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("gameLevel", "0");
        editor.commit();

        //Sending the intent
        Intent intent = new Intent(this, PlayGame.class);
        Button button = (Button) findViewById(R.id.btnPlay);
        String message = button.getText().toString();
        intent.putExtra("EXTRA_MESSAGE", message);
        startActivity(intent);

    }
    public void ResumeGame(View view) {

        Intent intent = new Intent(this, PlayGame.class);
        Button button = (Button) findViewById(R.id.btnResume);
        String message = button.getText().toString();
        intent.putExtra("EXTRA_MESSAGE", message);
        startActivity(intent);

    }
}
