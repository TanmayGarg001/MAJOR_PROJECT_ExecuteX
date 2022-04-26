package com.tanmayGarg.executex;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class CreateTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Add a task");//change title of Action bar.



    }
}