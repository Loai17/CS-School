package com.example.user.loginsignup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    private EditText etFullName;
    private EditText etUsername;
    private EditText etCompany;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Database db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initialize();
    }

    private void initialize() {
        etFullName = (EditText) findViewById(R.id.etFullName);
        etCompany = (EditText) findViewById(R.id.etCompany);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        db = new Database(this);
    }

    public void signUp(View view) {

        String fullName = etFullName.getText().toString();
        String username = etUsername.getText().toString();
        String company = etCompany.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        // if username exists: db.getUser(username)

        if (fullName.equals("") || username.equals("") || company.equals("") || email.equals("") || password.equals("") || confirmPassword.equals("")) {
            Toast.makeText(this, "Please fill all blanks!", Toast.LENGTH_LONG).show();
            return;
        }
        else {
            if (!fullName.equals("") && !username.equals("")) {
                if(db.getManager(username) != null){
                    Toast.makeText(this, "Username already exists!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(this, "Password does not match!", Toast.LENGTH_LONG).show();
                    return;
                }
                //Add User
                Manager manager = new Manager(fullName,username,1,email,password);
                db.addManager(manager);
                Toast.makeText(this, "You've signed up successfully!", Toast.LENGTH_LONG).show();
                //Go to Login
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        }
    }
}
