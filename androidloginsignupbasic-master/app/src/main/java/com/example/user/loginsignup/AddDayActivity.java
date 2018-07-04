package com.example.user.loginsignup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class AddDayActivity extends AppCompatActivity {

    TextView tvDayX;
    EditText etName;
    EditText etDescription;

    Database db;

    private static final String MY_PREFS_NAME = "UserPrefs";
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;

    private Manager mngr;
    private int jobId;
    private String dayNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_day);

        initialize();
    }

    private void initialize() {
        tvDayX = (TextView) findViewById(R.id.tvDayX);
        etName = (EditText) findViewById(R.id.etName);
        etDescription = (EditText) findViewById(R.id.etDescription);

        db = new Database(this);

        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        String manager_username = prefs.getString("username", null);
        mngr = db.getManager(manager_username);

        jobId = Integer.parseInt(getIntent().getStringExtra("JobId"));

        dayNumber = getIntent().getStringExtra("DayNumber");

        tvDayX.setText("Day " + dayNumber);
    }

    public void gotoNextDay(View view) {
        Day day = new Day(db.getAllDays().size(),mngr.getCompanyId(),jobId,dayNumber,etName.getText().toString(),etDescription.getText().toString());
        db.addDay(day);

        Intent intent = new Intent(this, AddDayActivity.class);
        intent.putExtra("JobId", Integer.toString(jobId));

        int dayNumberTemp = Integer.parseInt(dayNumber) + 1;
        intent.putExtra("DayNumber", Integer.toString(dayNumberTemp));
        startActivity(intent);
    }

    public void finishJobAdding(View view) {
        Day day = new Day(db.getAllDays().size(),mngr.getCompanyId(),jobId,dayNumber,etName.getText().toString(),etDescription.getText().toString());
        db.addDay(day);

        Intent intent = new Intent(this, JobFinalDetailsActivity.class);
        intent.putExtra("JobId", Integer.toString(jobId));
        int dayNumberTemp = Integer.parseInt(dayNumber) + 1;
        intent.putExtra("DayNumber", Integer.toString(dayNumberTemp));

        startActivity(intent);
    }
}
