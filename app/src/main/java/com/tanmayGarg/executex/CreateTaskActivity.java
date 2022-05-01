package com.tanmayGarg.executex;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;

public class CreateTaskActivity extends AppCompatActivity {

    //Member variables
    private EditText mTitleOfTask;
    private EditText mDescriptionOfTask;
    private FloatingActionButton mAddNewTaskBtn;

    //Firebase authentication object, FireBase User and Firestore object to store task in database.
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseFirestore mFirebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Add a task");//change title of Action bar.

        mTitleOfTask = findViewById(R.id.titleOfTask);
        mDescriptionOfTask = findViewById(R.id.descriptionOfTask);
        mAddNewTaskBtn = findViewById(R.id.addNewTaskBtn);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseFirestore = FirebaseFirestore.getInstance();

        mAddNewTaskBtn.setOnClickListener(v -> {
            String title = mTitleOfTask.getText().toString();
            String description = mDescriptionOfTask.getText().toString();
            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please fill the required fields", Toast.LENGTH_SHORT).show();
            } else {
                DocumentReference documentReference = mFirebaseFirestore.collection("TaskRoot").//Tree hierarchy
                        document(mFirebaseUser.getUid()).collection("TaskNode").document();

                HashMap<String, Object> task = new HashMap<>();
                task.put("Title", title);
                task.put("Description", description);

                documentReference.set(task).addOnSuccessListener(unused -> {
                    Toast.makeText(getApplicationContext(), "Task was added", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreateTaskActivity.this, MainActivity.class));
                }).addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Failed to add task", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(CreateTaskActivity.this, MainActivity.class));
                });
            }
        });
    }

}