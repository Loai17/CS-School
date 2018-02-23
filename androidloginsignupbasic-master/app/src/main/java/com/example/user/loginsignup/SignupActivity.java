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
    private UserDB db;


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
        db = new UserDB(this);
    }

    public void signUp(View view) {

        String name = etName.getText().toString();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        // if username exists: db.getUser(username)

        if (name.equals("")) {
            Toast.makeText(this, "Please enter a Name!", Toast.LENGTH_LONG).show();
            return;
        }
        else if (username.equals("")) {
            Toast.makeText(this, "Please enter a Username!", Toast.LENGTH_LONG).show();
            return;
        }
        else {
            if (!name.equals("") && !username.equals("")) {
//                if(db.getUser(username)){
//                    Toast.makeText(this, "Username already exists!", Toast.LENGTH_LONG).show();
//                    return;
//                }
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(this, "Password does not match!", Toast.LENGTH_LONG).show();
                    return;
                }
                //Add User
                User user = new User(name, username, password);
                db.addUser(user);
                Toast.makeText(this, "You've signed up successfully!", Toast.LENGTH_LONG).show();
                //Go to Login
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        }
    }
}
