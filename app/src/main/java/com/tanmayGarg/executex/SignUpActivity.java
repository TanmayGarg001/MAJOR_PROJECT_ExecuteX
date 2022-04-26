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
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    //Member variables
    private EditText mSignUpEmail;
    private EditText mSignUpPassword;
    private Button mCreateAccBtn;
    private TextView mGoBackToLoginFromSignUp;

    //Firebase authentication object for registration of new user
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mSignUpEmail = findViewById(R.id.signUpEmail);
        mSignUpPassword = findViewById(R.id.signUpPassword);
        mCreateAccBtn = findViewById(R.id.createAccBtn);
        mGoBackToLoginFromSignUp = findViewById(R.id.goBackToLoginFromSignUp);

        //creating a new instance of FirebaseAuth class
        firebaseAuth = FirebaseAuth.getInstance();

        mCreateAccBtn.setOnClickListener(v -> {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(50);
            String emailId = mSignUpEmail.getText().toString().trim().toLowerCase();
            String password = mSignUpPassword.getText().toString();
            if (Utilities.checkValidityEmail(emailId)) {
                //display a toast if user enters an invalid email
                Toast.makeText(getApplicationContext(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
            } else if (Utilities.checkValidityPassword(password)) {
                //display a toast if user enters an invalid password
                Toast.makeText(getApplicationContext(), "Password must contains minimum eight characters, " +
                        "at least one letter and one number", Toast.LENGTH_LONG).show();
            } else {
                //Add new account
                firebaseAuth.createUserWithEmailAndPassword(emailId, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Registration was successful", Toast.LENGTH_SHORT).show();
                        sendEmailVerification();
                    } else {
                        Toast.makeText(getApplicationContext(), "User with email id already exists", Toast.LENGTH_SHORT).show();
                    }
                });
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

    //sends email verification to the registered user
    private void sendEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(task -> {
                Toast.makeText(getApplicationContext(), "We have sent an email with a confirmation link to your email address", Toast.LENGTH_LONG).show();
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            });
        } else {
            Toast.makeText(getApplicationContext(), "Failed to send verification email", Toast.LENGTH_SHORT).show();
        }
    }

}