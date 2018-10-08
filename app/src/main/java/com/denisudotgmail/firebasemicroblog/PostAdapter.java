package com.denisudotgmail.firebasemicroblog;

import android.content.Context;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

public class PostAdapter extends FirebaseRecyclerAdapter<Post, PostViewHolder> {

    private Context context;
    public PostAdapter(Class<Post> modelClass, int postLayout, Class<PostViewHolder> viewHolderClass, DatabaseReference ref, Context context){
        super(modelClass, postLayout, viewHolderClass, ref);
        this.context = context;
    }
    @Override
    protected void populateViewHolder(PostViewHolder viewHolder, Post model, int position) {
        viewHolder.postTextView.setText(model.text);
    }
}
