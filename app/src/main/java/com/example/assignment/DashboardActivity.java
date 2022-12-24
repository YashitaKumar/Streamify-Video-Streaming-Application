package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardActivity extends AppCompatActivity {

    ImageView btn;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    RecyclerView recyclerView;
    ChannelsAdapter channelsAdapter;
    CircleImageView circleImageView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btn = findViewById(R.id.signout);
        recyclerView = findViewById(R.id.Channels);
        circleImageView = findViewById(R.id.profile);


        //Channels
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        FirebaseRecyclerOptions<ChannelsModel> options = new FirebaseRecyclerOptions.Builder<ChannelsModel>().setQuery(FirebaseDatabase.getInstance().getReference().child("/channels"),ChannelsModel.class).build();
        channelsAdapter = new ChannelsAdapter(options);
        channelsAdapter.setContext(getApplicationContext());
        recyclerView.setAdapter(channelsAdapter);

        //User Management
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(this);
        if(acc!=null)
        {
            String pic = String.valueOf(acc.getPhotoUrl());
            Glide.with(circleImageView.getContext()).load(pic).into(circleImageView);

        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();

            }
        });

    }
    void signOut(){
        googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
                startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        channelsAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        channelsAdapter.stopListening();
    }
}