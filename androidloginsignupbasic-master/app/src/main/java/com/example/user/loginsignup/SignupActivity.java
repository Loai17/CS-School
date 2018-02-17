package com.example.user.loginsignup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initialize();
    }

    private void initialize() {
        etName = (EditText) findViewById(R.id.etName);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
    }

    public void signUp(View view) {
        if (etName.getText().toString().equals("")) {
            Toast.makeText(this, "Please enter a Name!", Toast.LENGTH_LONG).show();
        }
        if (etName.getText().toString().equals("")) {
            Toast.makeText(this, "Please enter a Username!", Toast.LENGTH_LONG).show();
        }
        if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
            Toast.makeText(this, "Password does not match!", Toast.LENGTH_LONG).show();
        } else {
            if (!etName.getText().toString().equals("") && !etName.getText().toString().equals("")) {
                //Add User
                User user = new User(etName.getText().toString(), etUsername.getText().toString(), etPassword.getText().toString());
                Toast.makeText(this, "You've signed up successfully!", Toast.LENGTH_LONG).show();
                //Go to Login
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        }
    }
}
