package com.tanmayGarg.executex;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class TaskReadOnlyActivity extends AppCompatActivity {

    //All the required member variables, mapped from layout: activity_task_read_only.xml
    //All are private adhering to Encapsulation (data hiding)
    TextView mTaskTitleReadOnly;
    TextView mTaskDescriptionReadOnly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_read_only);

        //Adds a back arrow at Action bar
        Toolbar toolbar = findViewById(R.id.readOnlyTaskToolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //Mapping the respective views to their ID's
        mTaskTitleReadOnly = findViewById(R.id.taskTitleReadOnly);
        mTaskDescriptionReadOnly = findViewById(R.id.taskDescriptionReadOnly);

        //Getting the intent that started this activity and setting the title and description of the clicked task
        Intent data = getIntent();
        mTaskTitleReadOnly.setText(data.getStringExtra("title"));
        mTaskDescriptionReadOnly.setText(data.getStringExtra("description"));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();//Called when the activity has detected the user's press of the back key
        }
        return super.onOptionsItemSelected(item);
    }

}