package com.example.user.loginsignup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Console;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class AddJob extends AppCompatActivity {

    Database db;
    EditText name;
    EditText location;
    EditText description;
    Button btnAdd;

    private static final String MY_PREFS_NAME = "UserPrefs";
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;

    private Manager mngr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        initialize();
    }

    private void initialize() {
        name = (EditText) findViewById(R.id.etName);
        location = (EditText) findViewById(R.id.etLocation);
        description = (EditText) findViewById(R.id.etDescription);
        btnAdd = (Button) findViewById(R.id.btnAdd);

        db = new Database(this);

        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        String manager_username = prefs.getString("username", null);
        mngr = db.getManager(manager_username);
    }


    public void addJobFunc(View view) {
        Job job = new Job(db.getAllJobs().size(),mngr.getCompanyId(),name.getText().toString(),location.getText().toString(),description.getText().toString());
        db.addJob(job);

//        if(db.getJob(job.getName())==null) { // If this is true then it means that there is already Jobs in DB.
//
//            List<Job> allJobs = db.getAllJobs(); // USE THIS WHENEVER CREATING A NEW DATABASE ITEM
//
//            boolean flag = false;
//            while (!flag) {
//                for (Job j : allJobs) {
//                    if (job.getId() == j.getId()) {
//                        job.setId(job.getId() + 1);
//                        flag = false;
//                    } else {
//                        flag = true;
//                    }
//                }
//            }
//            db.addJob(job);
//        }

        Intent intent = new Intent(this, AddDayActivity.class);
        intent.putExtra("JobId", Integer.toString(db.getJob(job.getName()).getId()));
        intent.putExtra("DayNumber", "1");
        startActivity(intent);
    }
}
