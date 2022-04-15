package com.tanmayGarg.executex;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //member variables
    private EditText mLoginEmail;
    private EditText mLoginPassword;
    private TextView mForgotPassword;
    private Button mSignInBtn;
    private TextView mCreateNewAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoginEmail = findViewById(R.id.loginEmail);
        mLoginPassword = findViewById(R.id.loginPassword);
        mForgotPassword = findViewById(R.id.forgotPassword);
        mSignInBtn = findViewById(R.id.signInBtn);
        mCreateNewAcc = findViewById(R.id.createNewAcc);

        //On click listener which signs-in the user if the information provided exists in the database.
        mSignInBtn.setOnClickListener(v -> {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(50);
            String emailId = mLoginEmail.getText().toString().trim().toLowerCase();
            String password = mLoginPassword.getText().toString();
            //control flow
        });

        //On click listener which maneuvers to Forgot password acitivity {ForgotPasswordAcitivity}
        mForgotPassword.setOnClickListener(v -> {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(50);
            Intent forgotPasswordIntent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
            startActivity(forgotPasswordIntent);
        });

        //On click listener which maneuvers to New Account creation activity {SignUpAcitivity}
        mCreateNewAcc.setOnClickListener(v -> {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(50);
            Intent sigUpIntent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(sigUpIntent);
        });


    }

}