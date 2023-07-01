package com.example.travelque.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.travelque.Database.UserHelper;
import com.example.travelque.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText etRgUsername, etRgEmail, etRgPassword;
    TextView tvRgLogin;
    Button btnRgRegister;
    UserHelper userHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etRgUsername = findViewById(R.id.etRgUsername);
        etRgEmail = findViewById(R.id.etRgEmail);
        etRgPassword = findViewById(R.id.etRgPassword);
        tvRgLogin = findViewById(R.id.tvRgLogin);
        btnRgRegister = findViewById(R.id.btnRgRegister);
        userHelper = new UserHelper(this);

        tvRgLogin.setOnClickListener(v -> {
            Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        });

        btnRgRegister.setOnClickListener(v -> {
            if (validate()) {
                userHelper.open();
                userHelper.addUser(etRgUsername.getText().toString(), etRgEmail.getText().toString(), etRgPassword.getText().toString());
                userHelper.close();
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

    }

    private boolean validate() {
        if (etRgUsername.getText().toString().isEmpty() || etRgPassword.getText().toString().isEmpty() || etRgEmail.getText().toString().isEmpty()) {
            etRgUsername.setError("Username must be filled!");
            etRgPassword.setError("Password must be filled!");
            etRgEmail.setError("Email must be filled!");
            return false;
        }
        else if(!etRgUsername.getText().toString().matches("^[a-zA-Z0-9]*$")){
            etRgUsername.setError("Username must be alphanumeric!");
            return false;
        }

        userHelper.open();
        Boolean checkUsername = userHelper.checkUsername(etRgUsername.getText().toString());
        userHelper.close();
        if (checkUsername){
            etRgUsername.setError("Username already exists!");
            return false;
        }

        else if(!etRgEmail.getText().toString().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
            etRgEmail.setError("Email must be valid!");
            return false;
        }
        else if(etRgPassword.getText().toString().matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$") && etRgPassword.getText().toString().length() < 8){
            etRgPassword.setError("Password must be at least 8 characters and unique!");
            return false;
        }
        else{
            return true;
        }
    }
}