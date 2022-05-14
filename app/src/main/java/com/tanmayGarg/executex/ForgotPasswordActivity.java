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

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ForgotPasswordActivity extends AppCompatActivity {

    //All the required member variables , mapped from layout: activity_forgot_password.xml
    //All are private adhering to Encapsulation (data hiding)
    private EditText mRecoverEmailEditTxt;
    private Button mRecoverPasswordBtn;
    private TextView mGoBackToLoginFromForgotPass;

    //Firebase authentication object to reset password of existing user
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //Hide the Action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        //Mapping the respective views to their ID's
        mRecoverEmailEditTxt = findViewById(R.id.recoverEmailEditTxt);
        mRecoverPasswordBtn = findViewById(R.id.recoverPasswordBtn);
        mGoBackToLoginFromForgotPass = findViewById(R.id.goBackToLoginFromForgotPass);

        //Getting the instance of FirebaseAuth
        mFirebaseAuth = FirebaseAuth.getInstance();//Returns an instance of this class corresponding to the default FirebaseApp instance

        //On click listener for recoverPasswordBtn which will then send a verification email to the user.
        mRecoverPasswordBtn.setOnClickListener(v -> {

            //Vibrates the android device using haptic feedback motor, for good user experience
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(50);

            //Get the emailID and perform required operations.
            String emailID = mRecoverEmailEditTxt.getText().toString().trim().toLowerCase();

            //Control flow for the Recover button, checks for validity of email and displays toast accordingly
            if (!Utilities.checkValidityEmail(emailID)) {
                Toast.makeText(getApplicationContext(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
            } else {
                //Recover the password for entered email
                mFirebaseAuth.sendPasswordResetEmail(emailID).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "We have sent an email to reset password for your email address", Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(ForgotPasswordActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "The email is not registered", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //On click listener which will then maneuver back to MainActivity
        mGoBackToLoginFromForgotPass.setOnClickListener(v -> {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(50);
            Intent mainIntent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
            startActivity(mainIntent);
        });

    }

}