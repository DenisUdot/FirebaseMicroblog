package com.denisudotgmail.firebasemicroblog;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";
    private UserData userData = null;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    private TextView emailTextView, nameTextView, surnameTextView, genderTextView, ageTextView;
    private DatabaseReference myRef;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_profile, container, false);
        emailTextView = (TextView) layout.findViewById(R.id.profile_email_field);
        nameTextView = (TextView) layout.findViewById(R.id.profile_name_field);
        surnameTextView = (TextView) layout.findViewById(R.id.profile_surname_field);
        genderTextView = (TextView) layout.findViewById(R.id.profile_gender_field);
        ageTextView = (TextView) layout.findViewById(R.id.profile_age_field);

        myRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        return layout;
    }

    @Override
    public void onStart(){
        super.onStart();
        progressDialog.show();
        writeUserDataFields();
    }

    private void getUserData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        if(mAuth.getCurrentUser() != null) {
            DatabaseReference myRef = database.getReference(mAuth.getUid());
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    userData = (UserData) dataSnapshot.getValue(UserData.class);
                    if (userData != null) {
                        writeUserDataFields();
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }
        progressDialog.hide();
    }

    private void writeUserDataFields(){
        getUserData();
        if(userData != null) {
            if(mAuth.getCurrentUser() != null){
                emailTextView.setText(mAuth.getCurrentUser().getEmail());
            }
            nameTextView.setText(userData.getName());
            surnameTextView.setText(userData.getSurname());
            genderTextView.setText(userData.getGender());
            ageTextView.setText(Integer.toString(userData.getAge()));
        }
    }
}
