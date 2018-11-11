package com.denisudotgmail.firebasemicroblog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthorizationActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";

    private TextView emailTextView, passwordTextView, statusTextView;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_authorization);
        emailTextView = findViewById(R.id.aut_email_field);
        passwordTextView = findViewById(R.id.aut_password_field);
        statusTextView = findViewById(R.id.status_text_field);
        Button autButton = findViewById(R.id.aut_button);
        Button regButton = findViewById(R.id.reg_button);
        ButtonClickListener buttonClickListener = new ButtonClickListener();
        autButton.setOnClickListener(buttonClickListener);
        regButton.setOnClickListener(buttonClickListener);

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        getSupportActionBar().setTitle(R.string.login);

    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            signOut();
        }
    }

    private void signOut() {
        mAuth.signOut();
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            Intent intent = new Intent(AuthorizationActivity.this, MenuActivity.class);
                            startActivity(intent);
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(AuthorizationActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                            statusTextView.setText(R.string.auth_failed);
                        }
                        progressDialog.hide();
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = emailTextView.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailTextView.setError(getResources().getString(R.string.required));
            valid = false;
        } else {
            emailTextView.setError(null);
        }

        String password = passwordTextView.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordTextView.setError(getResources().getString(R.string.required));
            valid = false;
        } else {
            passwordTextView.setError(null);
        }
        return valid;
    }

    class ButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.aut_button:
                    statusTextView.setText("");
                    signIn(emailTextView.getText().toString(), passwordTextView.getText().toString());
                    break;
                case R.id.reg_button:
                    Intent intent = new Intent(AuthorizationActivity.this, RegistrationActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}
