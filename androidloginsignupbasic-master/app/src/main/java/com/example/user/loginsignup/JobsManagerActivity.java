package com.example.user.loginsignup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class JobsManagerActivity extends AppCompatActivity {

    Database db;
    GridView gridView;
    Button addBtn;
    Button workersBtn;
    TextView title;


    private static final String MY_PREFS_NAME = "UserPrefs";
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;

    private static ArrayList<String> jobsList = new ArrayList<>();


//    private Session session; // Session

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_manager);

        initialize();
//        session = new Session(this);

    }

    private void initialize() {
        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);


        gridView = (GridView) findViewById(R.id.gv_jobs);
        addBtn = (Button) findViewById(R.id.addJob);
        workersBtn = (Button) findViewById(R.id.btnWorkers);
        title = (TextView) findViewById(R.id.tvTitle);
        db = new Database(this);
        jobsList = new ArrayList<>();


        Boolean mngrIsThere = false;
        Boolean workerIsThere = false;

        String username = prefs.getString("username",null);

        for (Manager Tempmanager:db.getAllManagers()) {
            if(Tempmanager.getUsername().equals(username)){
                mngrIsThere = true;
            }
        }

        for (Worker wrker:db.getAllWorkers()) {
            if(wrker.getUsername().equals(username)){
                workerIsThere = true;
            }
        }


        if(mngrIsThere) {
            Manager mngr = db.getManager(username);


            for (Job job : db.getAllJobs()) {
                if (mngr.getCompanyId() == job.getCompanyId()) {
                    jobsList.add(job.getName());
                }
            }
        }
        else if(workerIsThere){
            Worker worker = db.getWorker(username);

            for (Job job:db.getAllJobs()) {
                if(worker.getCompanyId()==job.getCompanyId()) {
                    jobsList.add(job.getName());
                }
            }
        }

        gridView.setAdapter(new ArrayAdapter(JobsManagerActivity.this,android.R.layout.simple_list_item_1,jobsList));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getBaseContext(), JobDetailsActivity.class);
                intent.putExtra("JOB_NAME", jobsList.get(i));
                startActivity(intent);
            }
        });

        if(prefs.getBoolean("manager",false)==false){
            //Hide add job button and workers button.
            addBtn.setVisibility(View.GONE);
            workersBtn.setVisibility(View.GONE);
            title.setText("Jobs Board");
        }

    }


    public void gotoAddJob(View view) {
        Intent intent = new Intent(this, AddJob.class);
        startActivity(intent);
    }

    public void gotoWorkers(View view){
        Intent intent = new Intent(this, WorkersActivity.class);
        startActivity(intent);
    }


    public void signOut(View view) {
        editor.putString("username","");
        editor.putString("password","");
        editor.apply();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
