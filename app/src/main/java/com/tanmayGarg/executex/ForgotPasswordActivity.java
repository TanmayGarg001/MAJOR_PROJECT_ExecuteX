package com.tanmayGarg.executex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //On click listener to maneuver back to login page i.e. MainActivity
        TextView goBackToLogin = findViewById(R.id.goBackToLogin2);
        goBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });

    }
}