package adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assignment.ContentActivity;
import com.example.assignment.R;
import com.example.assignment.VideoActivity;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import model.Item;
import model.VideoDetails;

public class VideoDetailsAdapter extends RecyclerView.Adapter<VideoDetailsAdapter.VideoDetailsViewHolder> {
    public VideoDetailsAdapter(Context context, List<Item> list, Context context2) {
        this.context = context;
        this.list = list;
        this.context2 = context2;
    }

    private Context context,context2;
    private List<Item> list;
    private String videoId;
    @NonNull
    @Override
    public VideoDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_item1,parent,false);
        return new VideoDetailsAdapter.VideoDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoDetailsViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.title.setText(list.get(position).getSnippet().getTitle());
        holder.desc.setText(list.get(position).getSnippet().getDescription());
        String date = list.get(position).getSnippet().getPublishedAt();
        holder.date.setText(list.get(position).getSnippet().getPublishedAt());
        Glide.with(holder.poster.getContext()).load(list.get(position).getSnippet().getThumbnails().getMedium().getUrl()).into(holder.poster);
        holder.poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VideoActivity.class);
                intent.putExtra("Vid",list.get(position).getId().getVideoId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        if(!list.isEmpty())
        {
            return list.size();
        }
        else
        {
            return 0;
        }
    }

    public class VideoDetailsViewHolder extends RecyclerView.ViewHolder{

        private TextView title,desc,date;
        private ImageView poster;

        public VideoDetailsViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.VideoTitle);
            desc = itemView.findViewById(R.id.VideoDesc);
            date = itemView.findViewById(R.id.VideoDate);
            poster = itemView.findViewById(R.id.VideoPoster);
        }

    }


}
