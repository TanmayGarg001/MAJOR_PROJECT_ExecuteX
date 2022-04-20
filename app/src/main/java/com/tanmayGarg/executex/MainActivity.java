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

public class MainActivity extends AppCompatActivity {

    //member variables
    private EditText mLoginEmail;
    private EditText mLoginPassword;
    private TextView mForgotPassword;
    private Button mSignInBtn;
    private TextView mCreateNewAcc;

    //Firebase authentication object to login existing user
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoginEmail = findViewById(R.id.loginEmail);
        mLoginPassword = findViewById(R.id.loginPassword);
        mForgotPassword = findViewById(R.id.forgotPassword);
        mSignInBtn = findViewById(R.id.signInBtn);
        mCreateNewAcc = findViewById(R.id.createNewAcc);

        //creating a new instance of FirebaseAuth class
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();//if user has logged-in once then simple move to the ExecuteX activity
        if (firebaseUser != null) {
            finish();
            startActivity(new Intent(MainActivity.this, ExecuteXActivity.class));
        }

        //On click listener which signs-in the user if the information provided exists in the database.
        mSignInBtn.setOnClickListener(v -> {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(50);
            String emailId = mLoginEmail.getText().toString().trim().toLowerCase();
            String password = mLoginPassword.getText().toString();
            if (emailId.isEmpty() || password.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please fill the required fields", Toast.LENGTH_SHORT).show();
            }
            else if (Utilities.checkValidityEmail(emailId)) {
                Toast.makeText(getApplicationContext(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
            }
            else if (Utilities.checkValidityPassword(password)) {
                Toast.makeText(getApplicationContext(), "Invalid password", Toast.LENGTH_SHORT).show();
            } else {
                firebaseAuth.signInWithEmailAndPassword(emailId, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        checkCredentials();
                    } else {
                        Toast.makeText(getApplicationContext(), "User does not exist or invalid username/password", Toast.LENGTH_LONG).show();
                    }
                });
            }
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

    private void checkCredentials() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        assert firebaseUser != null;
        if (firebaseUser.isEmailVerified()) {
            finish();
            startActivity(new Intent(MainActivity.this, ExecuteXActivity.class));
        } else {
            Toast.makeText(getApplicationContext(), "Please verify the email address", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }

}