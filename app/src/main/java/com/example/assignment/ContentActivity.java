package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeBaseActivity;

import java.util.ArrayList;
import java.util.List;

import Retrofit.GetDataService;
import Retrofit.RetrofitInstance;
import adapter.VideoDetailsAdapter;
import de.hdodenhof.circleimageview.CircleImageView;
import model.Item;
import model.VideoDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContentActivity extends AppCompatActivity{

    TextView channelName,header;
    String id,name,logo;
    private RecyclerView recyclerView;
    private VideoDetailsAdapter videoDetailsAdapter;
    private final  String TAG = ContentActivity.class.getSimpleName();
    CircleImageView circleImageView;
    ImageView back;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        id= getIntent().getStringExtra("channelid");
        name = getIntent().getStringExtra("channelname");
        logo = getIntent().getStringExtra("channellogo");

        recyclerView = findViewById(R.id.ChannelContent);
        circleImageView = findViewById(R.id.channelContentLogo);
        channelName = findViewById(R.id.channelContentName);
        header = findViewById(R.id.text);
        back = findViewById(R.id.backBtn);

        Glide.with(circleImageView.getContext()).load(logo).into(circleImageView);
        channelName.setText(name);
        header.setText(name);

        GetDataService dataService = RetrofitInstance.getRetrofit().create(GetDataService.class);
        Call<VideoDetails> videoDetailsCall = dataService.getVideoData("snippet",id,"20","viewCount","AIzaSyBa6Rbke2I7TSie9u-hVGlka_gHoeMbD28");
        videoDetailsCall.enqueue(new Callback<VideoDetails>() {
            @Override
            public void onResponse(Call<VideoDetails> call, Response<VideoDetails> response) {
                if(response.isSuccessful())
                {
                    if(response.body()!=null)
                    {
                        Log.e(TAG,"Response Successfull");
                        Toast.makeText(ContentActivity.this,"LOADING, Please Wait",Toast.LENGTH_SHORT).show();
                        setUpRecyclerView(response.body().getItems());

                    }
                }

            }


            @Override
            public void onFailure(Call<VideoDetails> call, Throwable t) {
                Toast.makeText(ContentActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.e(TAG.concat("API REQUEST FAILED"), t.getMessage());

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ContentActivity.this,DashboardActivity.class));
            }
        });




    }

    private void setUpRecyclerView(List<Item> items) {
        videoDetailsAdapter = new VideoDetailsAdapter(ContentActivity.this,items,getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ContentActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(videoDetailsAdapter);

    }
}