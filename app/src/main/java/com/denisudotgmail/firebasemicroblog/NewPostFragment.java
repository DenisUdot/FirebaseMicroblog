package com.denisudotgmail.firebasemicroblog;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewPostFragment extends Fragment {
    private EditText postEditText;
    private FirebaseAuth mAuth;


    private DatabaseReference mDatabase;

    public NewPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_new_post, container, false);
        Button addPostButton = layout.findViewById(R.id.new_post_button);
        addPostButton.setOnClickListener(new PostButtonClickListener());
        postEditText = layout.findViewById(R.id.new_post_text);
        postEditText.requestFocus();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        return layout;
    }


    private void submitPost() {
        if (!validateForm()) {
            return;
        }
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm", Locale.getDefault());
        String date = df.format(Calendar.getInstance().getTime());
        String postMessage = postEditText.getText().toString();

        writeNewPost(mAuth.getCurrentUser().getUid(),date,postMessage);
    }

    private void writeNewPost(String userId, String date, String title) {

        String key = mDatabase.child("posts").push().getKey();
        Post post = new Post(userId, date, title);
        Map<String, Object> postValues = post.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }


    private boolean validateForm() {
        boolean valid = true;

        String email = postEditText.getText().toString();
        if (TextUtils.isEmpty(email)) {
            postEditText.setError(getResources().getString(R.string.required));
            valid = false;
        } else {
            postEditText.setError(null);
        }
        return valid;
    }

    class PostButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            submitPost();
            postEditText.setText("");
            returnToCurrentFragment();
        }
    }

    private void returnToCurrentFragment(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, new PostListFragment(), "visible_fragment");
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

}
