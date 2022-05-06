package com.tanmayGarg.executex;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Random;

public class ExecuteXActivity extends AppCompatActivity {

    //Member variables
    private FloatingActionButton mAddNewTaskBtn;
    private RecyclerView mRecyclerView;

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

        Query query = mFirebaseFirestore.collection("taskRoot").//Fetches the task from Cloud FireStore
                document(mFirebaseUser.getUid()).collection("taskNode").orderBy("title", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<FirebaseModel> allTasks = new FirestoreRecyclerOptions.Builder<FirebaseModel>().setQuery(query, FirebaseModel.class).build();

        mFirestoreAdapter = new FirestoreRecyclerAdapter<FirebaseModel, TaskViewHolder>(allTasks) {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            protected void onBindViewHolder(@NonNull TaskViewHolder holder, int position, @NonNull FirebaseModel model) {
                ImageView taskHolderOptions = holder.itemView.findViewById(R.id.taskOpt);
                int colorCode = getRandomizedColor();
                holder.mTask.setBackgroundColor(colorCode);

                holder.mTaskTitle.setText(model.getTitle());
                holder.mTaskDescription.setText(model.getDescription());

                String documentID = mFirestoreAdapter.getSnapshots().getSnapshot(position).getId();

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), TaskReadOnlyActivity.class);
                        intent.putExtra("title", model.getTitle());
                        intent.putExtra("description", model.getDescription());
                        intent.putExtra("documentID", documentID);
                        v.getContext().startActivity(intent);
                    }
                });
                taskHolderOptions.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                        popupMenu.setGravity(Gravity.START);
                        popupMenu.getMenu().add("Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                Intent intent = new Intent(v.getContext(), EditTaskActivity.class);
                                intent.putExtra("title", model.getTitle());
                                intent.putExtra("description", model.getDescription());
                                intent.putExtra("documentID", documentID);
                                v.getContext().startActivity(intent);
                                return false;
                            }
                        });
                        popupMenu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                DocumentReference documentReference = mFirebaseFirestore.collection("taskRoot").
                                        document(mFirebaseUser.getUid()).collection("taskNode").document(documentID);
                                documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(getApplicationContext(), "Task was deleted", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "Failed to delete the task", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return false;
                            }
                        });
                        popupMenu.show();
                    }
                });
            }

            @NonNull
            @Override
            public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
                return new TaskViewHolder(view);
            }
        };

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mRecyclerView.setAdapter(mFirestoreAdapter);
        mRecyclerView.setItemAnimator(null);

    }

    private int getRandomizedColor() {
        String[] colorsList = getApplicationContext().getResources().getStringArray(R.array.colorsList);
        ArrayList<Integer> colorCodeList = new ArrayList<>();
        for (String s : colorsList) {
            int colorCode = Color.parseColor(s);
            colorCodeList.add(colorCode);
        }
        int rand = new Random().nextInt(colorCodeList.size());
        return colorCodeList.get(rand);
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
            mFirestoreAdapter.stopListening();
        }
    }
}