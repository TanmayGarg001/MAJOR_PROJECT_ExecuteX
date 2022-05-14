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

public class CreateNewAccountActivity extends AppCompatActivity {

    //All the required member variables, mapped from layout: activity_create_new_account.xml
    //All are private adhering to Encapsulation (data hiding)
    private EditText mSignUpEmail;
    private EditText mSignUpPassword;
    private Button mCreateAccountButton;
    private TextView mGoBackToLoginFromSignUp;

    //Firebase authentication object for registration of new user
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);

        //Mapping the respective views to their ID's
        mSignUpEmail = findViewById(R.id.signUpEmail);
        mSignUpPassword = findViewById(R.id.signUpPassword);
        mCreateAccountButton = findViewById(R.id.createAccountButton);
        mGoBackToLoginFromSignUp = findViewById(R.id.goBackToLoginFromSignUp);

        //Getting the instance of FirebaseAuth
        mFirebaseAuth = FirebaseAuth.getInstance();//Returns an instance of this class corresponding to the default FirebaseApp instance

        //On click listener which will create a new user in FirestoreDatabase if the information provided fulfills the constraints.
        mCreateAccountButton.setOnClickListener(v -> {

            //Vibrates the android device using haptic feedback motor, for good user experience
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(50);

            //Get the emailID and password entered by the user and perform required operations.
            String emailID = mSignUpEmail.getText().toString().trim().toLowerCase();
            String password = mSignUpPassword.getText().toString();

            //Control flow for the Create Account button, also checks if the entered data is valid in accordance to the constrains
            if (emailID.isEmpty() || password.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please fill the required fields", Toast.LENGTH_SHORT).show();
            }
            if (!Utilities.checkValidityEmail(emailID)) {
                Toast.makeText(getApplicationContext(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
            } else if (!Utilities.checkValidityPassword(password)) {
                Toast.makeText(getApplicationContext(), "Password must contains minimum eight characters, " +
                        "at least one letter and one number", Toast.LENGTH_LONG).show();
            } else {
                //If everything is in order, then we make a new user account with provided credentials else a toast is displayed.
                mFirebaseAuth.createUserWithEmailAndPassword(emailID, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Registration was successful", Toast.LENGTH_SHORT).show();
                        sendEmailVerification();
                    } else {
                        Toast.makeText(getApplicationContext(), "User with email id already exists", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //On click listener which will then maneuver back to MainActivity
        mGoBackToLoginFromSignUp.setOnClickListener(v -> {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(50);
            Intent mainIntent = new Intent(CreateNewAccountActivity.this, MainActivity.class);
            startActivity(mainIntent);
        });

    }

    //Sends email verification message to the registered user emailID only if it is fist-time user, else displays a toast.
    private void sendEmailVerification() {
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(task -> {
                Toast.makeText(getApplicationContext(), "We have sent an email with a confirmation link to your email address", Toast.LENGTH_LONG).show();
                mFirebaseAuth.signOut();
                finish();
                startActivity(new Intent(CreateNewAccountActivity.this, MainActivity.class));
            });
        } else {
            //User-Id probably already exists.
            Toast.makeText(getApplicationContext(), "Failed to send verification email", Toast.LENGTH_SHORT).show();
        }
    }

}