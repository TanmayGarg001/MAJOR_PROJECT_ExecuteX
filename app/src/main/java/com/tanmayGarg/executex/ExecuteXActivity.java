package com.tanmayGarg.executex;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class ExecuteXActivity extends AppCompatActivity {

    //Member variables
    private FloatingActionButton mAddNewTaskBtn;

    //Firebase authentication object to log-out current user
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_xactivity);

        mAddNewTaskBtn = findViewById(R.id.addNewTaskBtn);

        //creating a new instance of FirebaseAuth class
        firebaseAuth = FirebaseAuth.getInstance();

        //On click listener to add a new task which will maneuver to CreateTaskActivity
        mAddNewTaskBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ExecuteXActivity.this, CreateTaskActivity.class);
            startActivity(intent);
        });

    }

    //inflate the top-right menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_list, menu);
        return true;
    }

    //get the selected item
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logoutMenuOpt) {
            firebaseAuth.signOut();
            finish();
            Intent intent = new Intent(ExecuteXActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}