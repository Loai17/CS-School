package com.example.user.loginsignup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class ShowDayDetailsActivity extends AppCompatActivity {

    Button btnBack;
    TextView tvDayName;
    TextView tvDayNumber;
    TextView tvDescription;
    Button btnDone;

    private static final String MY_PREFS_NAME = "UserPrefs";
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;

    Database db;
    Day day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_day_details);

        initialize();
    }

    private void initialize() {
        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        btnBack = (Button) findViewById(R.id.btnBack);
        tvDayName = (TextView) findViewById(R.id.tvDayName);
        tvDayNumber = (TextView) findViewById(R.id.tvDayNumber);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        btnDone = (Button) findViewById(R.id.btnDelete);

        db = new Database(this);

        String dayNameTemp = (getIntent().getStringExtra("DayName"));
        int jobIdTemp = Integer.parseInt(getIntent().getStringExtra("JobId"));

        List<Day> tempDays = db.getAllDays();

        for (Day d : tempDays) {
            if (d.getJobId() == jobIdTemp && d.getName().equals(dayNameTemp)) {
                day = d;
            }
        }

        tvDayName.setText(day.getName());
        tvDescription.setText(day.getDescription());
        tvDayNumber.setText("Day " + day.getDate());

        if (prefs.getBoolean("manager", false) == false) {
            btnDone.setVisibility(View.GONE);
        }
    }

    public void backToJobDetails(View view) {
        Intent intent = new Intent(getBaseContext(), JobDetailsActivity.class);
        intent.putExtra("JOB_NAME", getIntent().getStringExtra("JOB_NAME"));
        startActivity(intent);
    }

    public void markJobDone(View view) {
        db.deleteDay(day);

        Intent intent = new Intent(getBaseContext(), JobDetailsActivity.class);
        intent.putExtra("JOB_NAME", getIntent().getStringExtra("JOB_NAME"));
        startActivity(intent);
    }
}
