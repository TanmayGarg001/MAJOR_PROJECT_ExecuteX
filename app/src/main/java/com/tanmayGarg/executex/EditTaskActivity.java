package com.tanmayGarg.executex;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;

public class EditTaskActivity extends AppCompatActivity {
    //member variables
    Intent data;
    EditText mTaskTitleEditTask;
    EditText mTaskDescriptionEditTask;
    FloatingActionButton mEditAndSaveTaskBtn;

    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;
    FirebaseFirestore mFirebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        Toolbar toolbar = findViewById(R.id.editTaskToolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mTaskTitleEditTask = findViewById(R.id.taskTitleEditTask);
        mTaskDescriptionEditTask = findViewById(R.id.taskDescriptionEditTask);
        mEditAndSaveTaskBtn = findViewById(R.id.editAndSaveTaskBtn);
        data = getIntent();

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        mEditAndSaveTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedTitle = mTaskTitleEditTask.getText().toString();
                String updatedDescription = mTaskDescriptionEditTask.getText().toString();
                if (updatedTitle.isEmpty() || updatedDescription.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill the required fields", Toast.LENGTH_SHORT).show();
                } else {
                    DocumentReference documentReference = mFirebaseFirestore.collection("taskRoot").
                            document(mFirebaseUser.getUid()).collection("taskNode").document(data.getStringExtra("documentID"));

                    HashMap<String, Object> task = new HashMap<>();
                    task.put("title", updatedTitle);
                    task.put("description", updatedDescription);
                    documentReference.set(task).addOnSuccessListener(unused -> {
                        Toast.makeText(getApplicationContext(), "Task was updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditTaskActivity.this, ExecuteXActivity.class));
                    }).addOnFailureListener(e -> {
                        Toast.makeText(getApplicationContext(), "Failed to update the task", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditTaskActivity.this, ExecuteXActivity.class));
                    });
                }
            }
        });

        String taskTitle = data.getStringExtra("title");
        String taskDescription = data.getStringExtra("description");
        mTaskTitleEditTask.setText(taskTitle);
        mTaskDescriptionEditTask.setText(taskDescription);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}