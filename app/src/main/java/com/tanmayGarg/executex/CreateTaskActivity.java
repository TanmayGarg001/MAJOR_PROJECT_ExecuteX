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

    //All the required member variables, mapped from layout: activity_create_task.xml
    //All are private adhering to Encapsulation (data hiding)
    private EditText mTitleOfTask;
    private EditText mDescriptionOfTask;
    private FloatingActionButton mAddNewTaskBtn;

    //FirebaseUser and FirebaseFirestore object to create task in FirestoreDatabase
    private FirebaseUser mFirebaseUser;
    private FirebaseFirestore mFirebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        //Change the title of Action bar
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add a task");

        //Mapping the respective views to their ID's
        mTitleOfTask = findViewById(R.id.taskTitleCreateTask);
        mDescriptionOfTask = findViewById(R.id.taskDescriptionCreateTask);
        mAddNewTaskBtn = findViewById(R.id.createNewTaskButton);

        //Getting the instance of current FireBaseUser and instance of FirebaseFirestore
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseFirestore = FirebaseFirestore.getInstance();

        //On click listener which will create a new task to the FirebaseFirestore
        mAddNewTaskBtn.setOnClickListener(v -> {

            //Get the title and description entered by the user and perform required operations
            String title = mTitleOfTask.getText().toString().trim();
            String description = mDescriptionOfTask.getText().toString().trim();

            //Control flow for the CreateNewTask FAB Button, if any field is empty a toast is displayed else the task is added
            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please fill the required fields", Toast.LENGTH_SHORT).show();
            } else {
                //A DocumentReference refers to a document location in a Firestore database and can be used to write, read, or listen to the location.
                //The document at the referenced location may or may not exist. A DocumentReference can also be used to create a CollectionReference to a sub-collection.
                DocumentReference documentReference = mFirebaseFirestore.collection("taskRoot").
                        document(mFirebaseUser.getUid()).collection("taskNode").document();

                //HashMap is used because Firestore is a "document store database" where a document is a HashMap.
                //The elements within that hash are name/value pairs, where the name is a string and the value can be:
                //String, Boolean, Number, Timestamp, Array, (Hash)Map, Geolocation.
                //https://stackoverflow.com/questions/67406280/why-do-we-set-the-parameter-in-hashmap-to-object-when-sending-data-to-firestore
                HashMap<String, Object> task = new HashMap<>();
                task.put("title", title);
                task.put("description", description);
                documentReference.set(task).addOnSuccessListener(unused -> {
                    Toast.makeText(getApplicationContext(), "Task was added", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreateTaskActivity.this, MainActivity.class));
                }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Failed to add task", Toast.LENGTH_SHORT).show());
            }
        });
    }

}