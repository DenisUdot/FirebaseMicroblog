package com.denisudotgmail.firebasemicroblog;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

class PostViewHolder extends RecyclerView.ViewHolder {

        private TextView postTextView;
        private TextView dateTextView;

        public PostViewHolder(View itemView) {
            super(itemView);
            postTextView = itemView.findViewById(R.id.post_view);
            dateTextView = itemView.findViewById(R.id.date_view);
    }

    public void bindToPost(Post post) {
        postTextView.setText(post.text);
        dateTextView.setText(post.date);
    }
}
