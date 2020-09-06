package com.mh2020.android.pillparser;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
//import androidx.multidex.MultiDex;
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
import android.widget.Toast;

import com.google.firebase.firestore.Query;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;

import java.io.IOException;
import java.net.URI;

public class MainActivity extends AppCompatActivity {

    //private FirebaseAuth mAuth;
    private RecyclerView perscriptionList;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;
    //recognize when the user comes back from the image gallery Activity
    public static final int IMAGE_PICK_CODE = 1000;
    public static final int PERMISSION_CODE = 1001;
    public int morningNight = 1;
    InputImage image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initialize FirebaseAuth mAuth = FirebaseAuth.getInstance();
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
                if (morningNight%2 == 0){
                    holder.image.setImageDrawable(getDrawable(R.drawable.ic_brightness_3_black_48dp));
                } else {
                    holder.image.setImageDrawable(getDrawable(R.drawable.ic_wb_sunny_black_24dp));
                }
                    morningNight++;
            }
        };

        perscriptionList.setHasFixedSize(true);
        perscriptionList.setLayoutManager(new LinearLayoutManager(this));
        perscriptionList.setAdapter(adapter);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get and image from the gallery

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED){
                        ///permission not granted, request it
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show popup for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else {
                        //permission already granted
                        pickImageFromGallery();
                    }
                }


                /*
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE);
                */

                //Intent cameraIntent = new Intent(android.media.action.IMAGE_CAPTURE);
                /*
                InputImage image;
                try {
                    image = InputImage.fromFilePath(context, uri);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                 */

            }
        });
    }

    private void pickImageFromGallery() {
        //intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    //handle result of runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE: {
                if (grantResults.length>0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission was granted
                    pickImageFromGallery();
                }
                else{
                    //permission was denied
                    Toast.makeText(this, "Permission denied",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //handle result of picked image


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE && null != data) {
            //send image to google?????
            Uri selectedImage = data.getData();

            try {
                image = InputImage.fromFilePath(this, selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //2. Get an instance of TextRecognizer
        TextRecognizer recognizer = TextRecognition.getClient();

        //3. Process the image
        Task<Text> result =
                recognizer.process(image)
                        .addOnSuccessListener(new OnSuccessListener<Text>() {
                            @Override
                            public void onSuccess(Text visionText) {
                                // Task completed successfully
                                // ...
                            }
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Task failed with an exception
                                        // ...
                                    }
                                });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
        adapter.startListening();
    }

    @Override
    public void onStop(){
        super.onStop();
        adapter.stopListening();
    }
/*
    private void updateUI(FirebaseUser currentUser) {
        //add things that will show up when a user logs in : )
        //reeeee
    }
*/
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
    /*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            //TODO: action
        }
    }
    */

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
/*
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
*/
}

