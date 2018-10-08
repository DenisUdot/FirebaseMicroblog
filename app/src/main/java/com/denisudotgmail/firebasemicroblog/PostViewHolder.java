package com.denisudotgmail.firebasemicroblog;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class PostViewHolder extends RecyclerView.ViewHolder {

        TextView postTextView;

        public PostViewHolder(View itemView) {
            super(itemView);
            postTextView = (TextView) itemView.findViewById(R.id.post_view);

    }
}
