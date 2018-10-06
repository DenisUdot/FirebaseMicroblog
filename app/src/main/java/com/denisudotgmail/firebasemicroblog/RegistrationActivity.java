package com.denisudotgmail.firebasemicroblog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {
    UserData userData;
    EditText email, name, surname, password1, password2;
    RadioGroup genderRadioGroup;
    RadioButton maleButton, femaleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        genderRadioGroup = (RadioGroup) findViewById(R.id.gender_radio_group);
        genderRadioGroup.setOnCheckedChangeListener(new RadioGroupClickListener());
    }

    class RegistrateButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {

        }
    }

    class RadioGroupClickListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.male_button:
                    Toast.makeText(getApplicationContext(), "male", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.female_button:
                    Toast.makeText(getApplicationContext(), "female", Toast.LENGTH_SHORT).show();
                    break;
                case (-1):
                    //any button was't pressed
                    break;
            }
        }
    }
}
