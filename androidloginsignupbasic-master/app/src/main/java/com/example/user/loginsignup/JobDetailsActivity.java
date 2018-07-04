package com.example.user.loginsignup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class JobDetailsActivity extends AppCompatActivity {

    TextView jobName;
    TextView location;
    TextView description;
    Button btnBack;
    Button btnDelete;
    ListView lvDays;


    Database db;
    Job job;
    ArrayList<Day> days= new ArrayList<>();

    private static final String MY_PREFS_NAME = "UserPrefs";
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        initalize();
    }

    private void initalize() {
        jobName = (TextView) findViewById(R.id.tvJobName);
        location = (TextView) findViewById(R.id.tvLocation);
        description = (TextView) findViewById(R.id.tvDescription);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        lvDays = (ListView) findViewById(R.id.lvDays);

        db = new Database(this);

        job = db.getJob(getIntent().getStringExtra("JOB_NAME"));

        jobName.setText(job.getName());
        location.setText(job.getLocation());
        description.setText(job.getDescription());

        List<Day> Tempdays=db.getAllDays();
        for (Day day:Tempdays) {
            if(day.getJobId()==job.getId()) {
                days.add(day);
            }
        }

        JobDetailsActivity.CustomAdapter customAdapter = new JobDetailsActivity.CustomAdapter();
        lvDays.setAdapter(customAdapter);

        lvDays.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getBaseContext(), ShowDayDetailsActivity.class);
                intent.putExtra("DayName", days.get(i).getName());
                intent.putExtra("JobId",  String.valueOf(job.getId()));
                intent.putExtra("JOB_NAME",jobName.getText().toString());
                startActivity(intent);
            }
        });

        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        if(prefs.getBoolean("manager",false)==false) {
            btnDelete.setVisibility(View.GONE);
        }
    }

    public void backToJobManager(View view) {
        Intent intent = new Intent(getBaseContext(), JobsManagerActivity.class);
        startActivity(intent);
    }

    public void deleteJobFunc(View view) {
        List<Day> tempDays = db.getAllDays();
        for (Day d:tempDays) {
            if(d.getJobId()==job.getId()){
                db.deleteDay(d);
            }
        }

        db.deleteJob(db.getJob(jobName.getText().toString()));
        Intent intent = new Intent(getBaseContext(), JobsManagerActivity.class);
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
