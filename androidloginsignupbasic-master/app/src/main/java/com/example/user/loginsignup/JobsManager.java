package com.example.user.loginsignup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Adapter;

public class JobsManager extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_manager_main);

//        initialize();
    }

//    private void addItem(String item) {
//        mItems.add(item);
//        mAdapter.notifyDataSetChanged();
////    }
//
//    private void initialize() {
//        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        db = new Database(this);
//
//        recyclerView.set
//    }
}
