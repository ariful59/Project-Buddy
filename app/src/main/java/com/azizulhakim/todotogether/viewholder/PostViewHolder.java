package com.azizulhakim.todotogether.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.azizulhakim.todotogether.R;
import com.azizulhakim.todotogether.models.Task;

public class PostViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView authorView;
    public ImageView starView;
    public TextView numStarsView;
    public TextView bodyView;

    public PostViewHolder(View itemView) {
        super(itemView);

        titleView = (TextView) itemView.findViewById(R.id.post_title);
        authorView = (TextView) itemView.findViewById(R.id.post_author);
        starView = (ImageView) itemView.findViewById(R.id.star);
        numStarsView = (TextView) itemView.findViewById(R.id.post_num_stars);
        bodyView = (TextView) itemView.findViewById(R.id.post_body);
    }

    public void bindToPost(Task task, View.OnClickListener starClickListener) {
        titleView.setText(task.title);
        authorView.setText(task.author);
        numStarsView.setText(String.valueOf(task.starCount));
        bodyView.setText(task.body);

        starView.setOnClickListener(starClickListener);
    }
}
