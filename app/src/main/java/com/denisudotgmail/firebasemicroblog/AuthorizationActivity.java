package com.denisudotgmail.firebasemicroblog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AuthorizationActivity extends AppCompatActivity {
    TextView emailTextView, passwordTextView;
    Button regButton, autButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        emailTextView = (TextView)findViewById(R.id.aut_email_field);
        passwordTextView = (TextView) findViewById(R.id.aut_password_field);
        autButton = (Button) findViewById(R.id.aut_button);
        regButton = (Button) findViewById(R.id.reg_button);
        ButtonClickListener buttonClickListener = new ButtonClickListener();
        autButton.setOnClickListener(buttonClickListener);
        regButton.setOnClickListener(buttonClickListener);

    }
    class ButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()){
                case R.id.aut_button:
                    intent = new Intent(AuthorizationActivity.this, MenuActivity.class);
                    break;
                case R.id.reg_button:
                    intent = new Intent(AuthorizationActivity.this, RegistrationActivity.class);
                    break;
            }
            startActivity(intent);

        }
    }
}
