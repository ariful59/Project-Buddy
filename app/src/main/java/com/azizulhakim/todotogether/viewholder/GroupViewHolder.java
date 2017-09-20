package com.azizulhakim.todotogether.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.azizulhakim.todotogether.R;
import com.azizulhakim.todotogether.models.Group;

public class GroupViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    //public TextView authorView;
    public ImageView starView;
    public TextView numStarsView;
    public TextView bodyView;

    public GroupViewHolder(View itemView) {
        super(itemView);

        titleView = (TextView) itemView.findViewById(R.id.group_name_fragment);
        //authorView = (TextView) itemView.findViewById(R.id.post_author);
        //starView = (ImageView) itemView.findViewById(R.id.star);
        //numStarsView = (TextView) itemView.findViewById(R.id.post_num_stars);
        bodyView = (TextView) itemView.findViewById(R.id.group_body);
    }

    public void bindToPost(Group group, View.OnClickListener starClickListener) {
        titleView.setText(group.groupname);
        //authorView.setText(group.groupname);
        //numStarsView.setText(String.valueOf(group.starCount));
        bodyView.setText(group.about);
        //starView.setOnClickListener(starClickListener);
    }
}
