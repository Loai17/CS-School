package com.example.user.loginsignup;

import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JobFinalDetailsActivity extends AppCompatActivity {

    TextView tvJobName;
    ListView lvDays;
    Button btnAddDay;
    Button btnCancel;
    Button btnCreate;

    ArrayList<Day> days= new ArrayList<Day>();

    Database db;
    int jobId;
    Job job;

    private String dayNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_final_details);

        initialize();
    }

    private void initialize() {
        tvJobName = (TextView) findViewById(R.id.tvJobName);
        lvDays = (ListView) findViewById(R.id.lvDays);
        btnAddDay = (Button) findViewById(R.id.btnAddDay);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCreate = (Button) findViewById(R.id.btnCreate);

        db = new Database(this);

        jobId = Integer.parseInt(getIntent().getStringExtra("JobId"));

        dayNumber = getIntent().getStringExtra("DayNumber");


        List<Day> Tempdays=db.getAllDays();
        for (Day day:Tempdays) {
            if(day.getJobId()==jobId) {
                days.add(day);
            }
        }
        Log.i("Job id : "+jobId, days.toString());

        List<Job> tempJobs = db.getAllJobs();
        for (Job j:tempJobs) {
            if (j.getId()==jobId){
                job = j;
            }
        }

        tvJobName.setText(job.getName());


        JobFinalDetailsActivity.CustomAdapter customAdapter = new JobFinalDetailsActivity.CustomAdapter();
        lvDays.setAdapter(customAdapter);


    }

    public void createJob(View view) {
        Intent intent = new Intent(this, JobsManagerActivity.class);
        startActivity(intent);
    }

    public void cancelCreation(View view) {
        for (Day d:days) {
            db.deleteDay(d);
        }
        db.deleteJob(job);

        Intent intent = new Intent(this, JobsManagerActivity.class);
        startActivity(intent);
    }

    public void addDayFunction(View view) {
        Intent intent = new Intent(this, AddDayActivity.class);
        intent.putExtra("JobId", Integer.toString(jobId));
        intent.putExtra("DayNumber", dayNumber);

        startActivity(intent);
    }


    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return days.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.job_details_custom_layout,null);

            TextView tvDayText = (TextView) view.findViewById(R.id.tvDayText);

            tvDayText.setText(days.get(i).getDate()+ "- " +days.get(i).getName() );
            return view;
        }
    }
}
