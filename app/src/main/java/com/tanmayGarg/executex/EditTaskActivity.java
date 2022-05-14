package com.tanmayGarg.executex;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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

    //All the required member variables, mapped from layout: activity_edit_task.xml
    //All are private adhering to Encapsulation (data hiding)
    EditText mTaskTitleEditTask;
    EditText mTaskDescriptionEditTask;
    FloatingActionButton mEditAndSaveTaskBtn;

    //FirebaseUser and FirebaseFirestore object to edit and save task in FirestoreDatabase
    FirebaseUser mFirebaseUser;
    FirebaseFirestore mFirebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        //Adds a back arrow at Action bar
        Toolbar toolbar = findViewById(R.id.editTaskToolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //Mapping the respective views to their ID's
        mTaskTitleEditTask = findViewById(R.id.taskTitleEditTask);
        mTaskDescriptionEditTask = findViewById(R.id.taskDescriptionEditTask);
        mEditAndSaveTaskBtn = findViewById(R.id.editAndSaveTaskBtn);

        Intent data = getIntent();//Getting the intent that started this activity

        //Getting the instance of current FirebaseUser and instance of FirebaseFirestore
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //Filling the task title and description whilst editing the task
        String taskTitle = data.getStringExtra("title");
        String taskDescription = data.getStringExtra("description");
        mTaskTitleEditTask.setText(taskTitle);
        mTaskDescriptionEditTask.setText(taskDescription);

        //On click listener which will edit and save the task
        mEditAndSaveTaskBtn.setOnClickListener(v -> {

            //Get the title and description entered by the user and perform required operations
            String updatedTitle = mTaskTitleEditTask.getText().toString();
            String updatedDescription = mTaskDescriptionEditTask.getText().toString();

            //Control flow for the EditAndSaveTask FAB Button, if any field is empty a toast is displayed else the task is updated
            if (updatedTitle.isEmpty() || updatedDescription.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please fill the required fields", Toast.LENGTH_SHORT).show();
            } else {
                //A DocumentReference refers to a document location in a Firestore database and can be used to write, read, or listen to the location.
                //The document at the referenced location may or may not exist. A DocumentReference can also be used to create a CollectionReference to a sub-collection.
                DocumentReference documentReference = mFirebaseFirestore.collection("taskRoot").
                        document(mFirebaseUser.getUid()).collection("taskNode").document(data.getStringExtra("documentID"));

                //HashMap is used because Firestore is a "document store database" where a document is a HashMap.
                //The elements within that hash are name/value pairs, where the name is a string and the value can be:
                //String, Boolean, Number, Timestamp, Array, (Hash)Map, Geolocation.
                //https://stackoverflow.com/questions/67406280/why-do-we-set-the-parameter-in-hashmap-to-object-when-sending-data-to-firestore
                HashMap<String, Object> task = new HashMap<>();
                task.put("title", updatedTitle);
                task.put("description", updatedDescription);

                //Updates and saves the title and description of the updated task & overwrites the document referred to by this DocumentReference
                documentReference.set(task).addOnSuccessListener(unused -> {
                    Toast.makeText(getApplicationContext(), "Task was updated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditTaskActivity.this, ExecuteXActivity.class));
                }).addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Failed to update the task", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditTaskActivity.this, ExecuteXActivity.class));
                });
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();//Called when the activity has detected the user's press of the back key
        }
        return super.onOptionsItemSelected(item);
    }

}