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
    private Button regButton, autButton;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        emailTextView = (TextView)findViewById(R.id.aut_email_field);
        passwordTextView = (TextView) findViewById(R.id.aut_password_field);
        statusTextView = (TextView) findViewById(R.id.status_text_field);
        autButton = (Button) findViewById(R.id.aut_button);
        regButton = (Button) findViewById(R.id.reg_button);
        ButtonClickListener buttonClickListener = new ButtonClickListener();
        autButton.setOnClickListener(buttonClickListener);
        regButton.setOnClickListener(buttonClickListener);

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
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
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            statusTextView.setText("");
                            Intent intent = new Intent(AuthorizationActivity.this, MenuActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(AuthorizationActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                            statusTextView.setText(R.string.auth_failed);
                        }
                        progressDialog.hide();
                    }
                });
        // [END sign_in_with_email]
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
