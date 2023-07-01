package com.example.travelque.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.travelque.Database.UserHelper;
import com.example.travelque.R;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText etLgUsername, etLgPassword;
    TextView tvLgRegister;
    Button btnLgLogin;
    UserHelper userHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etLgUsername = findViewById(R.id.etLgUsername);
        etLgPassword = findViewById(R.id.etLgPassword);
        tvLgRegister = findViewById(R.id.tvLgRegister);
        btnLgLogin = findViewById(R.id.btnLgLogin);
        userHelper = new UserHelper(this);

        tvLgRegister.setOnClickListener(v -> {
            Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
        });

        btnLgLogin.setOnClickListener(v -> {
            if (validate()) {
                userHelper.open();
                if (userHelper.checkAccount(etLgUsername.getText().toString(), etLgPassword.getText().toString())) {
                    Intent mainIntent = new Intent(LoginActivity.this, NavigationActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
                else {
                    etLgUsername.setError("Username or password is wrong!");
                    etLgPassword.setError("Username or password is wrong!");
                }
                userHelper.close();
            }
        });


    }

    private boolean validate() {
        if (etLgUsername.getText().toString().isEmpty() || etLgPassword.getText().toString().isEmpty()) {
            etLgUsername.setError("Username must be filled!");
            etLgPassword.setError("Password must be filled!");
            return false;
        }
        userHelper.open();
        Boolean checkUser = userHelper.checkAccount(etLgUsername.getText().toString(), etLgPassword.getText().toString());
        Boolean checkUsername = userHelper.checkUsername(etLgUsername.getText().toString());
        userHelper.close();

        if (checkUsername == true && checkUser == false){
            etLgPassword.setError("Wrong Password!");
            return false;
        }

        if (checkUser == false){
            etLgUsername.setError("Username and Password are not found, Please Register First!");
            etLgPassword.setError("Username and Password are not found, Please Register First!");
            return false;
        }
        return true;
    }
}