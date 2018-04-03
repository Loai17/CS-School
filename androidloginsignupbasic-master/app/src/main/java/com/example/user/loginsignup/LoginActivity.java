package com.example.user.loginsignup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

//    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialize();

//        mAuth = FirebaseAuth.getInstance();
    }

    private void initialize() {
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        db = new Database(this);
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
//        Intent intent = new Intent(this, SignupActivity.class);
//        startActivity(intent);

        Company company = new Company("LES");
        Job job = new Job(company.getId(),"JobNumOne","Nazareth","descccc","1","2");
        Day day = new Day(company.getId(),job.getId(),"1","Day1","descc for job");
        DayImage dayImage = new DayImage(day.getId(),"imageLink","desssscription");
        Supply supply = new Supply("supply name","imageLinkkk","supply description");
        DaySupply daySupply = new DaySupply(day.getId(),supply.getId(),3);
        Worker worker = new Worker(company.getId(),"Loai Worker","11/07","llo2ay","123123","professional",200,"Daily","photoLink again");
        DayWorker dayWorker = new DayWorker(day.getId(),worker.getId());
        Manager manager1 = new Manager("Nazeeh Qubti","nzh",company.getId(),"nazeeh@gmail.com","123123");

        db.addCompany(company);
//        db.addDay(day);
//        db.addJob(job);
//        db.addDayImage(dayImage);
//        db.addDaySupply(daySupply);
//        db.addDayWorker(dayWorker);
//        db.addWorker(worker);
//        db.addManager(manager1);
//        db.addSupply(supply);

        Company company1 = db.getCompany("LES");
        Toast.makeText(this, company1.getId()+company1.getName(), Toast.LENGTH_LONG).show();

    }
}
