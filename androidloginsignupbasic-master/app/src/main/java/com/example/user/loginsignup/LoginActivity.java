package com.example.user.loginsignup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "--" ;
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Database db;


    private static final String MY_PREFS_NAME = "UserPrefs";
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;



//    private FirebaseAuth mAuth;


    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

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


        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        String username = prefs.getString("username", null);
        String password = prefs.getString("password", null);

        Toast.makeText(this, "username :" + username, Toast.LENGTH_LONG).show();
        Worker workerLoggedIn= new Worker();
        if(username!=null) {
            if (username.equals("") != true) {
//            List<Worker> workers = db.getAllWorkers();
//            for (Worker worker:workers) {
//                if(worker.getUsername().equals(username)){
//                    workerLoggedIn=worker;
//                }
//            }
////            if(workerLoggedIn!=null){
                if (db.getWorker(username) != null) {
                    if (db.getWorker(username).getPassword().equals(password)) {
                        editor.putBoolean("manager", false);
                        editor.apply();
                        Intent intent = new Intent(this, JobsManagerActivity.class); // ------CHANGE THIS TO WHERE THE WORKER IS SUPPOUSED TO GO-------
                        startActivity(intent);
                    }
                } else if (db.getManager(username) != null) {
                    if (db.getManager(username).getPassword().equals(password)) {
                        editor.putBoolean("manager", true);
                        editor.apply();
                        Intent intent = new Intent(this, JobsManagerActivity.class);
                        startActivity(intent);
                    }
                }
            }
        }


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }

//    private void updateUI(FirebaseUser currentUser) {

//    }

    public void gotoHome(View view) {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();


//        mAuth.signInWithEmailAndPassword(username, password)
//
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.getException());
//                            Toast.makeText(LoginActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//
//                        // ...
//                    }
//                });




        if (db.getManager(username)!=null)
        {
            Manager manager = db.getManager(username);
            if(manager.getPassword().equals(password)) {
                Toast.makeText(this, "You've logged in successfully!", Toast.LENGTH_LONG).show();
                editor.putBoolean("manager",true);

                editor.putString("username",username);
                editor.putString("password",password);
                editor.apply();

                Intent intent = new Intent(this, JobsManagerActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(this, "Incorrect Username or Password!!", Toast.LENGTH_LONG).show();
                return;
            }
        }
        else if(db.getWorker(username)!=null){
            Worker worker = db.getWorker(username);

            if(worker.getPassword().equals(password)) {
                Toast.makeText(this, "You've logged in successfully!", Toast.LENGTH_LONG).show();
                editor.putBoolean("manager",false);

                editor.putString("username",username);
                editor.putString("password",password);
                editor.apply();

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
            return;
        }
    }

    public void gotoSignUp(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}
