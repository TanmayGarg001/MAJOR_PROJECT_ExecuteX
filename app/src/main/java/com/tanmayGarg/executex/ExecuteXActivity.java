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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Random;

public class ExecuteXActivity extends AppCompatActivity {

    //All the required member variables, mapped from layout: activity_execute_xactivity.xml
    //All are private adhering to Encapsulation (data hiding)
    private FloatingActionButton mAddNewTaskButton;
    private RecyclerView mRecyclerView;//Very OP

    //FirebaseAuth, FirebaseUser and FirebaseFirestore object to view data at ExecuteX {Home Page}
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseFirestore mFirebaseFirestore;

    //RecyclerView adapter that listens to a FirestoreArray and displays its data in real time.
    //The FirestoreRecyclerAdapter is a subclass of the normal RecyclerView. Adapter and takes care of most of the complicated stuff
    //like reacting to dataset changes and maintaining the model List.
    FirestoreRecyclerAdapter<FirebaseModel, TaskViewHolder> mFirestoreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_xactivity);

        //Mapping the respective views to their ID's
        mAddNewTaskButton = findViewById(R.id.addNewTaskButton);

        //Getting the instance of FirebaseAuth, current FirebaseUser and instance of FirebaseFirestore
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseFirestore = FirebaseFirestore.getInstance();

        //Query that fetches the task from Cloud FireStore Database
        Query query = mFirebaseFirestore.collection("taskRoot").
                document(mFirebaseUser.getUid()).collection("taskNode").orderBy("title", Query.Direction.ASCENDING);

        //Binds a Query to a RecyclerView.When documents are added, removed, or change these updates are automatically applied to the UI in real time.
        FirestoreRecyclerOptions<FirebaseModel> allTasks = new FirestoreRecyclerOptions.Builder<FirebaseModel>().setQuery(query, FirebaseModel.class).build();

        mFirestoreAdapter = new FirestoreRecyclerAdapter<FirebaseModel, TaskViewHolder>(allTasks) {

            //onBindViewHolder(VH holder, int position) called by RecyclerView to display the data at the specified position.
            //https://stackoverflow.com/questions/37523308/when-onbindviewholder-is-called-and-how-it-works
            @RequiresApi(api = Build.VERSION_CODES.M)//SetGravity option works on API 23+
            @Override
            protected void onBindViewHolder(@NonNull TaskViewHolder holder, int position, @NonNull FirebaseModel model) {

                //Mapping the respective view to its ID
                ImageView taskHolderOptions = holder.itemView.findViewById(R.id.taskOpt);

                //Sets random BackgroundColor to tasks and then set the task and description accordingly
                int colorCode = getRandomizedColor();
                holder.mTask.setBackgroundColor(colorCode);
                holder.mTaskTitle.setText(model.getTitle());
                holder.mTaskDescription.setText(model.getDescription());

                //Gets the snapshot at the specified position in this list
                String documentID = mFirestoreAdapter.getSnapshots().getSnapshot(position).getId();

                holder.itemView.setOnClickListener(v -> {

                    Intent intent = new Intent(v.getContext(), TaskReadOnlyActivity.class);
                    intent.putExtra("title", model.getTitle());
                    intent.putExtra("description", model.getDescription());
                    intent.putExtra("documentID", documentID);
                    v.getContext().startActivity(intent);

                });

                //On click listener that will pop up the Edit and Delete options for every task and do operations according to the coded control flow
                taskHolderOptions.setOnClickListener(v -> {

                    PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                    popupMenu.setGravity(Gravity.START);

                    popupMenu.getMenu().add("Edit").setOnMenuItemClickListener(item -> {
                        Intent intent = new Intent(v.getContext(), EditTaskActivity.class);
                        intent.putExtra("title", model.getTitle());
                        intent.putExtra("description", model.getDescription());
                        intent.putExtra("documentID", documentID);
                        v.getContext().startActivity(intent);

                        return false;
                    });
                    popupMenu.getMenu().add("Delete").setOnMenuItemClickListener(item -> {

                        //A DocumentReference refers to a document location in a Firestore database and can be used to write, read, or listen to the location.
                        //The document at the referenced location may or may not exist. A DocumentReference can also be used to create a CollectionReference to sub-collection.
                        DocumentReference documentReference = mFirebaseFirestore.collection("taskRoot").
                                document(mFirebaseUser.getUid()).collection("taskNode").document(documentID);

                        documentReference.delete().addOnSuccessListener(unused -> Toast.makeText(getApplicationContext(), "Task was deleted", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Failed to delete the task", Toast.LENGTH_SHORT).show());

                        return false;
                    });
                    popupMenu.show();
                });
            }

            @NonNull
            @Override
            public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//Inflates the task_layout.xml
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
                return new TaskViewHolder(view);
            }
        };

        //Setting up the vertical span count and recycler view on homePage
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mRecyclerView.setAdapter(mFirestoreAdapter);
        mRecyclerView.setItemAnimator(null);//https://stackoverflow.com/questions/35653439/recycler-view-inconsistency-detected-invalid-view-holder-adapter-positionviewh

        //On click listener which maneuvers to CreateTaskActivity
        mAddNewTaskButton.setOnClickListener(v -> {
            Intent intent = new Intent(ExecuteXActivity.this, CreateTaskActivity.class);
            startActivity(intent);
        });

    }

    //Returns a random color code for visual aspects, made a colorsList in colors.xml {RANDOM COLORS}
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

        //Member variables referring to task_layout.xml
        LinearLayout mTask;
        TextView mTaskTitle;
        TextView mTaskDescription;

        public TaskViewHolder(@NonNull View itemView) {//constructor
            super(itemView);
            mTaskTitle = itemView.findViewById(R.id.taskTitle);
            mTaskDescription = itemView.findViewById(R.id.taskDescription);
            mTask = itemView.findViewById(R.id.task);
        }

    }

    //Setup for MenuItem for logout purpose at home page, top-right corner
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

    //Inflates the top-right menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_list, menu);
        return true;
    }

    @Override
    protected void onStart() {//Called after onCreate — or after onRestart when the activity had been stopped, but is now again being displayed to the user
        super.onStart();
        mFirestoreAdapter.startListening();//Start listening for database changes and populate the adapter.
    }

    @Override
    protected void onStop() {//Called when you are no longer visible to the user. You will next receive either onRestart,
        super.onStop();      //onDestroy, or nothing, depending on later user activity.
        if (mFirestoreAdapter != null) {
            mFirestoreAdapter.stopListening();//Stop listening for database changes and clear all items in the adapter.
        }
    }
}