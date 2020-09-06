package com.mh2020.android.pillparser;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private RecyclerView perscriptionList;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        perscriptionList = findViewById(R.id.perscription_list);
        firebaseFirestore = FirebaseFirestore.getInstance();

        //query
        Query query = firebaseFirestore.collection("perscriptions");

        //recycler options
        FirestoreRecyclerOptions<Perscription> options =  new FirestoreRecyclerOptions.Builder<Perscription>().setQuery(query, Perscription.class).build();

        adapter = new FirestoreRecyclerAdapter<Perscription, PerscriptionViewHolder>(options) {

            @NonNull
            @Override
            public PerscriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.perscription_item, parent,false);
                return new PerscriptionViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull PerscriptionViewHolder holder, int position, @NonNull Perscription model) {
                holder.perscription_name.setText(model.getMedicationName());
                holder.dosage.setText(model.getDosage() + "mg");
                holder.instructions.setText(model.getInstructions());
                holder.image.setImageDrawable(getDrawable(R.drawable.ic_wb_sunny_black_24dp));

            }
        };

        perscriptionList.setHasFixedSize(true);
        perscriptionList.setLayoutManager(new LinearLayoutManager(this));
        perscriptionList.setAdapter(adapter);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent cameraIntent = new Intent(android.media.action.IMAGE_CAPTURE);

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        adapter.startListening();
    }

    @Override
    public void onStop(){
        super.onStop();
        adapter.stopListening();
    }

    private void updateUI(FirebaseUser currentUser) {
        //add things that will show up when a user logs in : )
        //reeeee
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private class PerscriptionViewHolder extends RecyclerView.ViewHolder{
        private ImageView image;
        private TextView perscription_name;
        private TextView dosage;
        private TextView instructions;

        public PerscriptionViewHolder (@NonNull View itemView){
            super(itemView);

            perscription_name = itemView.findViewById(R.id.medication_name_textview);
            dosage = itemView.findViewById(R.id.dosage_textview);
            instructions = itemView.findViewById(R.id.description_textview);
            image = itemView.findViewById(R.id.word_image_view);

        }
    }
}
