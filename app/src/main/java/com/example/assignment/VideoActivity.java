package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import Retrofit.GetDataService;
import Retrofit.RetrofitInstance;
import model.VideoDetails;
import model.VideoStats.VideoStats;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    YouTubePlayerView youTubePlayerView;
    String id,vtitle;
    TextView Vtitle,likes,views;
    ImageView back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        id= getIntent().getStringExtra("Vid");
        vtitle = getIntent().getStringExtra("Vtitle");

        youTubePlayerView = findViewById(R.id.Video);
        Vtitle = findViewById(R.id.text);
        back = findViewById(R.id.backBtn);
        likes =findViewById(R.id.VideoLikes);
        views = findViewById(R.id.VideoViews);

        Vtitle.setText(vtitle);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VideoActivity.this,DashboardActivity.class));
            }
        });

        youTubePlayerView.initialize("AIzaSyAcgrTuXkz8vqQ3wEqFu8NtWcjL7YQiZms",this);

        getStats();
    }

    private void getStats() {
        GetDataService dataService = RetrofitInstance.getRetrofit().create(GetDataService.class);
        Call<VideoStats> videoStatsCall = dataService.getVideoStats("statistics","AIzaSyAcgrTuXkz8vqQ3wEqFu8NtWcjL7YQiZms",id);
        videoStatsCall.enqueue(new Callback<VideoStats>() {
            @Override
            public void onResponse(Call<VideoStats> call, Response<VideoStats> response) {
                if(response.isSuccessful())
                {
                    if(response.body()!=null)
                    {
                        views.setText(response.body().getItems().get(0).getStatistics().getViewCount());
                        likes.setText(response.body().getItems().get(0).getStatistics().getLikeCount());
                    }
                    else
                    {
                        Log.e("NULL EXCEPTION","response body is null");
                    }
                }
                else
                {
                    Log.e("RESPONSE NOT SUCCESSFULL", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<VideoStats> call, Throwable t) {
                Toast.makeText(VideoActivity.this,"Error has occurred, please retry",Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.cueVideo(id);//videoID
        youTubePlayer.play();
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}