package com.tanmayGarg.executex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //On click listener to maneuver back to login page i.e. MainActivity
        TextView goBackToLogin = findViewById(R.id.goBackToLogin);
        goBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });

    }


}