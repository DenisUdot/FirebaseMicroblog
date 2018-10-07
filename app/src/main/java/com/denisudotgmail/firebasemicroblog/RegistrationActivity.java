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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    public static final String USER_DATA = "user_data";
    UserData userData;
    private EditText emailEditText, nameEditText, surnameEditText, ageEditText, passwordEditText1, passwordEditText2;
    private RadioGroup genderRadioGroup;
    private RadioButton maleButton, femaleButton;
    private Button registrateButton;
    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        emailEditText = (EditText) findViewById(R.id.email_field);
        nameEditText = (EditText) findViewById(R.id.name_field);
        surnameEditText = (EditText) findViewById(R.id.surname_field);
        passwordEditText1 = (EditText) findViewById(R.id.reg_password_field1);
        passwordEditText2 = (EditText) findViewById(R.id.reg_password_field2);
        ageEditText = (EditText) findViewById(R.id.age_field);

        registrateButton = (Button) findViewById(R.id.sing_up_button);
        registrateButton.setOnClickListener(new RegistrateButtonClickListener());

        genderRadioGroup = (RadioGroup) findViewById(R.id.gender_radio_group);
        maleButton = (RadioButton) findViewById(R.id.male_button);
        femaleButton = (RadioButton) findViewById(R.id.female_button);
        maleButton.setChecked(true);

        mAuth = FirebaseAuth.getInstance();
        userData = new UserData();
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "creating Account:" + email);
        if (!validateForm()) {
            return;
        }
        progressDialog.show();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference ref = database.getReference();
                            DatabaseReference myRef = ref.child(mAuth.getUid());
                            myRef.setValue(userData);

                            Intent intent = new Intent(RegistrationActivity.this, AuthorizationActivity.class);
                            startActivity(intent);
                            // [END create_user_with_email]

                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistrationActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                        }
                        progressDialog.hide();
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = emailEditText.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError(getResources().getString(R.string.required));
            valid = false;
        } else {
            emailEditText.setError(null);
        }

        String name = nameEditText.getText().toString();
        if (TextUtils.isEmpty(name)) {
            nameEditText.setError(getResources().getString(R.string.required));
            valid = false;
        } else {
            userData.setName(name);
            nameEditText.setError(null);
        }

        String surname = surnameEditText.getText().toString();
        if (TextUtils.isEmpty(surname)){
            surnameEditText.setError(getResources().getString(R.string.required));
            valid = false;
        } else {
            userData.setSurname(surname);
            surnameEditText.setError(null);
        }

        switch (genderRadioGroup.getCheckedRadioButtonId()) {
            case R.id.male_button:
                userData.setGender("male");
                break;
            case R.id.female_button:
                userData.setGender("female");
            break;
        }

        String textAge = ageEditText.getText().toString();
        if (TextUtils.isEmpty(textAge)){
            ageEditText.setError(getResources().getString(R.string.required));
            valid = false;
        }else {
            ageEditText.setError(null);
            int age = Integer.parseInt(textAge);
            if (0 >= age || age >= 120) {
                ageEditText.setError(getResources().getString(R.string.required));
                valid = false;
            } else {
                userData.setAge(age);
                ageEditText.setError(null);
            }
        }

        String password1 = passwordEditText1.getText().toString();
        String password2 = passwordEditText2.getText().toString();
        if (password1.length() < 6) {
            passwordEditText1.setError(getResources().getString(R.string.min_required_length));
            valid = false;
        }if(password2.length() < 6) {
            passwordEditText2.setError(getResources().getString(R.string.min_required_length));
            valid = false;
        } else {
            if(!TextUtils.equals(password1,password2)) {
                passwordEditText2.setError(getResources().getString(R.string.different_passwords));
            }else {
                passwordEditText1.setError(null);
                passwordEditText2.setError(null);
            }
        }

        return valid;
    }

    class RegistrateButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            createAccount(emailEditText.getText().toString(), passwordEditText2.getText().toString());
        }
    }


//    // may be never used
//    class RadioGroupClickListener implements RadioGroup.OnCheckedChangeListener{
//        @Override
//        public void onCheckedChanged(RadioGroup group, int checkedId) {
//            switch (checkedId){
//                case R.id.male_button:
//                    Toast.makeText(getApplicationContext(), "male", Toast.LENGTH_SHORT).show();
//                    break;
//                case R.id.female_button:
//                    Toast.makeText(getApplicationContext(), "female", Toast.LENGTH_SHORT).show();
//                    break;
//                case (-1):
//                    //any button was't pressed
//                    break;
//            }
//        }
//    }
}
