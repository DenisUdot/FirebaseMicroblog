package com.denisudotgmail.firebasemicroblog;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostListFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private RecyclerView postRecycleView;
    private LinearLayoutManager linearLayoutManager;
    private PostAdapter mPostAdapter;
    private DatabaseReference mRef;
    private DatabaseReference childRef;


    public PostListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_post_list, container, false);
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
        //set fragment Title
//        getActivity().setTitle(getString(R.string.age));

        linearLayoutManager = new LinearLayoutManager(getActivity());
        postRecycleView = (RecyclerView) layout.findViewById(R.id.post_list);
        postRecycleView.setHasFixedSize(true);
        myRef = FirebaseDatabase.getInstance().getReference();
        childRef = myRef.child("user-posts");//write correct way
        mPostAdapter = new PostAdapter(Post.class, R.layout.post_view, PostViewHolder.class, childRef, layout.getContext());
        postRecycleView.setLayoutManager(linearLayoutManager);
        postRecycleView.setAdapter(mPostAdapter);

        return layout;
    }



}
