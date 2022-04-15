package com.tanmayGarg.executex;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    //Member variables
    private EditText mSignUpEmail;
    private EditText mSignUpPassword;
    private Button mCreateAccBtn;
    private TextView mGoBackToLoginFromSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mSignUpEmail = findViewById(R.id.signUpEmail);
        mSignUpPassword = findViewById(R.id.signUpPassword);
        mCreateAccBtn = findViewById(R.id.createAccBtn);
        mGoBackToLoginFromSignUp = findViewById(R.id.goBackToLoginFromSignUp);

        mCreateAccBtn.setOnClickListener(v -> {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(50);
            String emailId = mSignUpEmail.getText().toString().trim().toLowerCase();
            String password = mSignUpPassword.getText().toString();
            if (Utilities.checkValidityEmail(emailId)) {
                //display a toast if user enters an invalid email
                Toast.makeText(getApplicationContext(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
            }
            if (Utilities.checkValidityPassword(password)) {
                //display a toast if user enters an invalid password
                Toast.makeText(getApplicationContext(), "Password must contains minimum eight characters, " +
                        "at least one letter and one number", Toast.LENGTH_SHORT).show();
            } else {
                //Add new account
                Toast.makeText(getApplicationContext(), "New account...", Toast.LENGTH_SHORT).show();
            }
        });

        //On click listener for goBackToLogin text which will then maneuver back to login page i.e. MainActivity
        mGoBackToLoginFromSignUp.setOnClickListener(v -> {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(50);
            Intent mainIntent = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(mainIntent);
        });

    }

}