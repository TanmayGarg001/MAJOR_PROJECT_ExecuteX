package com.tanmayGarg.executex;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ExecuteXActivity extends AppCompatActivity {

    //Member variables
    private FloatingActionButton mAddNewTaskBtn;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;

    //Firebase authentication object, FireBase User and Firestore object
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseFirestore mFirebaseFirestore;

    //RecyclerAdapter for FireStore
    FirestoreRecyclerAdapter<FirebaseModel, TaskViewHolder> mFirestoreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_xactivity);

        mAddNewTaskBtn = findViewById(R.id.addNewTaskBtn);

        //creating a new instance of FirebaseAuth class
        mFirebaseAuth = FirebaseAuth.getInstance();

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseFirestore = FirebaseFirestore.getInstance();

        //On click listener to add a new task which will maneuver to CreateTaskActivity
        mAddNewTaskBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ExecuteXActivity.this, CreateTaskActivity.class);
            startActivity(intent);
        });

        Query query = mFirebaseFirestore.collection("TaskRoot").//Fetches the task from Cloud FireStore
                document(mFirebaseUser.getUid()).collection("TaskNode").orderBy("Title", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<FirebaseModel> allTasks = new FirestoreRecyclerOptions.Builder<FirebaseModel>().setQuery(query, FirebaseModel.class).build();
        mFirestoreAdapter = new FirestoreRecyclerAdapter<FirebaseModel, TaskViewHolder>(allTasks) {
            @Override
            protected void onBindViewHolder(@NonNull TaskViewHolder holder, int position, @NonNull FirebaseModel model) {
                holder.mTaskTitle.setText(model.getTitle());
                holder.mTaskDescription.setText(holder.mTaskDescription.getText().toString());
            }

            @NonNull
            @Override
            public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
                return new TaskViewHolder(view);
            }
        };
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mFirestoreAdapter);
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        //member variables referring to task_layout
        LinearLayout mTask;
        TextView mTaskTitle;
        TextView mTaskDescription;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            mTaskTitle = itemView.findViewById(R.id.taskTitle);
            mTaskDescription = itemView.findViewById(R.id.taskDescription);
            mTask = itemView.findViewById(R.id.task);
        }

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
            mFirebaseAuth.signOut();
            finish();
            Intent intent = new Intent(ExecuteXActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {//Called after onCreate — or after onRestart when the activity had been stopped, but is now again being displayed to the user
        super.onStart();
        mFirestoreAdapter.startListening();//Start listening for database changes and populate the adapter.
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mFirestoreAdapter != null) {
            mFirestoreAdapter.startListening();
        }
    }
}