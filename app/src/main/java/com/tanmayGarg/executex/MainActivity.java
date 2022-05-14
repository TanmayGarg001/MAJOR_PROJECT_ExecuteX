package com.tanmayGarg.executex;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    //All the required member variables, mapped from layout: activity_main.xml
    //All are private adhering to Encapsulation (data hiding)
    private EditText mLoginEmail;
    private EditText mLoginPassword;
    private TextView mForgotPassword;
    private Button mSignInBtn;
    private TextView mCreateNewAcc;
    private ProgressBar mProgressBar;

    //Firebase authentication object to login existing user
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Mapping the respective views to their ID's
        mLoginEmail = findViewById(R.id.loginEmail);
        mLoginPassword = findViewById(R.id.loginPassword);
        mForgotPassword = findViewById(R.id.forgotPassword);
        mSignInBtn = findViewById(R.id.signInBtn);
        mCreateNewAcc = findViewById(R.id.createNewAcc);
        mProgressBar = findViewById(R.id.progressBar);

        //Getting the instance of FirebaseAuth and current FireBaseUser
        mFirebaseAuth = FirebaseAuth.getInstance();//Returns an instance of this class corresponding to the default FirebaseApp instance
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            finish();
            startActivity(new Intent(MainActivity.this, ExecuteXActivity.class));//If the user has logged-in once then simple maneuver to the ExecuteXActivity
        }

        //On click listener which will sign-in the user if the information provided exists in the FirestoreDatabase.
        mSignInBtn.setOnClickListener(v -> {

            //Vibrates the android device using haptic feedback motor, for good user experience
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(50);

            //Get the emailId and password entered by the user and perform required operations.
            String emailId = mLoginEmail.getText().toString().trim().toLowerCase();
            String password = mLoginPassword.getText().toString();

            //Control flow for the Sign in button, checks if the data entered is fulfilling the constraints and displays toast accordingly
            if (emailId.isEmpty() || password.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please fill the required fields", Toast.LENGTH_SHORT).show();
            } else if (!Utilities.checkValidityEmail(emailId)) {
                Toast.makeText(getApplicationContext(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
            } else if (!Utilities.checkValidityPassword(password)) {
                Toast.makeText(getApplicationContext(), "Invalid password", Toast.LENGTH_SHORT).show();
            } else {
                //If everything is in order, then we Sign in the user else a toast is displayed.
                mProgressBar.setVisibility(View.VISIBLE);
                mFirebaseAuth.signInWithEmailAndPassword(emailId, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        emailVerification();
                    } else {
                        Toast.makeText(getApplicationContext(), "User does not exist or invalid username/password", Toast.LENGTH_LONG).show();
                    }
                    mProgressBar.setVisibility(View.INVISIBLE);
                });
            }
        });

        //On click listener which maneuvers to ForgotPasswordActivity
        mForgotPassword.setOnClickListener(v -> {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(50);
            Intent forgotPasswordIntent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
            startActivity(forgotPasswordIntent);
        });

        //On click listener which maneuvers to CreateNewAccountActivity
        mCreateNewAcc.setOnClickListener(v -> {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(50);
            Intent sigUpIntent = new Intent(MainActivity.this, CreateNewAccountActivity.class);
            startActivity(sigUpIntent);
        });


    }

    //Helper function which checks if the user has verified the entered email address
    private void emailVerification() {
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        assert firebaseUser != null;
        if (firebaseUser.isEmailVerified()) {
            finish();
            startActivity(new Intent(MainActivity.this, ExecuteXActivity.class));
        } else {
            Toast.makeText(getApplicationContext(), "Please verify the email address", Toast.LENGTH_SHORT).show();
            mFirebaseAuth.signOut();
        }
    }

}