package com.example.assignment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChannelsAdapter extends FirebaseRecyclerAdapter<ChannelsModel, ChannelsAdapter.MyViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ChannelsAdapter(@NonNull FirebaseRecyclerOptions<ChannelsModel> options) {
        super(options);
    }
    Context context;
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull ChannelsModel model) {
        holder.name.setText(model.getName());
        Glide.with(holder.logo.getContext()).load(model.getLogo()).into(holder.logo);
        holder.logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ContentActivity.class);
                intent.putExtra("channelid",model.getChannelID());
                intent.putExtra("channelname",model.getName());
                intent.putExtra("channellogo",model.getLogo());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.channels_design,parent,false);
        return new ChannelsAdapter.MyViewHolder(view);

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        CircleImageView logo;
        TextView name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            logo = itemView.findViewById(R.id.channelLogo);
            name = itemView.findViewById(R.id.channelName);

        }
    }
}
