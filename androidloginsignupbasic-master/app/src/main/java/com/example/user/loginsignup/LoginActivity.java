package com.example.user.loginsignup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialize();
    }

    private void initialize() {
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        db = new Database(this);
    }

    public void gotoHome(View view) {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if (db.getManager(username)!=null)
        {
            Manager manager = db.getManager(username);
            if(manager.getPassword().equals(password)) {
                //Toast.makeText(this, "You've logged in successfully!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, JobsManagerActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(this, "Incorrect Username or Password!!", Toast.LENGTH_LONG).show();
                return;
            }
        }
        else
        {
            Toast.makeText(this, "Incorrect Username or Password!!", Toast.LENGTH_LONG).show();
        }
    }

    public void gotoSignUp(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}
