package com.denisudotgmail.firebasemicroblog;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewPostFragment extends Fragment {
    Button addPostButton;
    EditText postText;
    String post, data;
    Date date;

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
        postText = (EditText) layout.findViewById(R.id.new_post_text);
        return layout;
    }

    class PostButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if (postText != null){
                date = Calendar.getInstance().getTime();
                post = postText.getText().toString();
                Toast.makeText(getActivity().getBaseContext(), post+"\n"+date.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

}
