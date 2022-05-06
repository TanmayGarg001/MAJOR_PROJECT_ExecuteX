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

    //member variables
    TextView mTaskTitleReadOnly;
    TextView mTaskDescriptionReadOnly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_read_only);

        Toolbar toolbar = findViewById(R.id.readOnlyTaskToolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mTaskTitleReadOnly = findViewById(R.id.taskTitleReadOnly);
        mTaskDescriptionReadOnly = findViewById(R.id.taskDescriptionReadOnly);

        Intent data = getIntent();
        mTaskTitleReadOnly.setText(data.getStringExtra("title"));
        mTaskDescriptionReadOnly.setText(data.getStringExtra("description"));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}