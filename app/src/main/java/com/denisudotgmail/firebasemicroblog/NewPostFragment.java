package com.denisudotgmail.firebasemicroblog;


import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewPostFragment extends Fragment {
    private static final String TAG = "NewPostFragment";
    private Button addPostButton;
    private EditText postTextEditText;
    private String post;
    private Date date;
    private FirebaseAuth mAuth;


    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    public NewPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View layout = inflater.inflate(R.layout.fragment_new_post, container, false);
        addPostButton = (Button) layout.findViewById(R.id.new_post_button);
        addPostButton.setOnClickListener(new PostButtonClickListener());
        postTextEditText = (EditText) layout.findViewById(R.id.new_post_text);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        return layout;
    }


    private void submitPost() {
        if (!validateForm()) {
            return;
        }
        final String postMessage = postTextEditText.getText().toString();

//        if (TextUtils.isEmpty(postMessage)) {
//            postText.setError(getResources().getString(R.string.required));
//            return;
//        }
//
//        // Disable button so there are no multi-posts
//        setEditingEnabled(false);
//        Toast.makeText(getActivity().getApplicationContext(), "Posting...", Toast.LENGTH_SHORT).show();
//
//        /////////////////////////////////////
//        // [START single_value_read]
//
//        mAuth.getCurrentUser().getUid();
//        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        // Get user value
//                        UserData user = dataSnapshot.getValue(UserData.class);
//
//                        // [START_EXCLUDE]
//                        if (user == null) {
//                            // User is null, error out
//                            Log.e(TAG, "User " + userId + " is unexpectedly null");
//                            Toast.makeText(getActivity().getApplicationContext(),
//                                    "Error: could not fetch user.",
//                                    Toast.LENGTH_SHORT).show();
//                        } else {
//                            // Write new post
//                            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
//                            String date = df.format(Calendar.getInstance().getTime());
//                            writeNewPost(userId, date, postMessage);
//                        }
//
//                        // Finish this Activity, back to the stream
//                        setEditingEnabled(true);
//                        finish();
//                        // [END_EXCLUDE]
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
//                        // [START_EXCLUDE]
//                        setEditingEnabled(true);
//                        // [END_EXCLUDE]
//                    }
//                });
        // [END single_value_read]
    }
//
//    private void setEditingEnabled(boolean enabled) {
//        mTitleField.setEnabled(enabled);
//        mBodyField.setEnabled(enabled);
//        if (enabled) {
//            mSubmitButton.show();
//        } else {
//            mSubmitButton.hide();
//        }
//    }
//
//    // [START write_fan_out]
//    private void writeNewPost(String userId, String date, String title) {
//        // Create new post at /user-posts/$userid/$postid and at
//        // /posts/$postid simultaneously
//        String key = mDatabase.child("posts").push().getKey();
//        Post post = new Post(userId, date, title);
//        Map<String, Object> postValues = post.toMap();
//
//        Map<String, Object> childUpdates = new HashMap<>();
//        childUpdates.put("/posts/" + key, postValues);
//        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);
//
//        mDatabase.updateChildren(childUpdates);
//    }


    private boolean validateForm() {
        boolean valid = true;

        String email = postTextEditText.getText().toString();
        if (TextUtils.isEmpty(email)) {
            postTextEditText.setError(getResources().getString(R.string.required));
            valid = false;
        } else {
            postTextEditText.setError(null);
        }
        return valid;
    }

    class PostButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            submitPost();
            Toast.makeText(getActivity().getBaseContext(), post+"\n"+date.toString(), Toast.LENGTH_SHORT).show();

        }
    }

}
