package com.example.user.loginsignup;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class JobsManagerActivity extends AppCompatActivity {

    GridView gridView;
    List<Job> jobsList;
//    MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_manager);

        initialize();
    }

    private void initialize() {
        gridView = (GridView) findViewById(R.id.gv_jobs);

        Database databaseHelper = new Database(JobsManagerActivity.this);

        Job job = new Job(1,"LES","Nazareth","Bla bla","Today","Tomorrow");
        databaseHelper.addJob(job);

        jobsList = new ArrayList<Job>();

        jobsList = databaseHelper.getAllJobs();
//        adapter = new MyAdapter(JobsManagerActivity.this, (ArrayList<Job>) jobsList);
//        gridView.setAdapter(adapter);
    }
}
