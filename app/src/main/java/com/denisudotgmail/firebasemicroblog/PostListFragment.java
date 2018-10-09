package com.denisudotgmail.firebasemicroblog;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostListFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private RecyclerView postRecycleView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter<Post, PostViewHolder> mPostAdapter;
    private DatabaseReference mRef;
    private DatabaseReference childRef;
    private String uid;

    Query query;

    public PostListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_post_list, container, false);
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        query = FirebaseDatabase.getInstance()
                .getReference()
                .child("user-posts/"+uid)
                .limitToLast(50);
        //set fragment Title
//        getActivity().setTitle(getString(R.string.age));

        linearLayoutManager = new LinearLayoutManager(getActivity());


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        childRef = myRef.child("user-posts/"+uid);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        postRecycleView = (RecyclerView) layout.findViewById(R.id.post_list);
        postRecycleView.setHasFixedSize(true);
        postRecycleView.setLayoutManager(mLayoutManager);
        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        attachRecyclerViewAdapter();


    }

    @Override
    public void onStop() {
        super.onStop();

    }

    private void attachRecyclerViewAdapter() {

        final RecyclerView.Adapter adapter = newAdapter();

        // Scroll to bottom on new messages
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                postRecycleView.smoothScrollToPosition(adapter.getItemCount());
            }
        });

        postRecycleView.setAdapter(adapter);
    }

    protected RecyclerView.Adapter newAdapter() {
        FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>().setQuery(query, Post.class).build();
        return new FirebaseRecyclerAdapter<Post, PostViewHolder>(options) {
            @Override
            public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new PostViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.post_view, parent, false));
            }

            @Override
            protected void onBindViewHolder(PostViewHolder holder, int position, Post model) {
                holder.postTextView.setText(model.text);
                holder.dateTextView.setText(model.date);
            }

            @Override
            public void onDataChanged() {
            }
        };
    }
}
