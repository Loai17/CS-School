package com.example.user.loginsignup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class WorkerProfileActivity extends AppCompatActivity {

    private Database db;
    private Worker worker;

    Button btnBack;
    TextView tvUsername;
    TextView tvFullName;
    TextView tvDetails; //Include experience, age
    TextView tvDescription;

    //Include gridview variable for jobs

    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_profile);

        initialize();
    }

    private void initialize() {
        db = new Database(this);

        btnBack = (Button) findViewById(R.id.btnBack);
        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvFullName = (TextView) findViewById(R.id.tvFullName);
        tvDetails = (TextView) findViewById(R.id.tvDetails);
        tvDescription = (TextView) findViewById(R.id.tvDescription);

        btnDelete = (Button) findViewById(R.id.btnDelete);

        String wUsername = getIntent().getStringExtra("WORKER_USERNAME");

        List<Worker> allWorkers = db.getAllWorkers();
        boolean found = false;
        for (Worker w: allWorkers) {
            if(w.getUsername().equals(wUsername) && !found) {
                worker = w;
                found=true;
            }
        }

        tvUsername.setText(worker.getUsername());
        tvFullName.setText(worker.getName());
        tvDetails.setText(worker.getExperience()+", "+worker.getDob());
        tvDescription.setText("Paid " + worker.getPayment() +" "+worker.getPaymentMethod());
    }


    public void backToWorkers(View view) {
        Intent intent = new Intent(this, WorkersActivity.class);
        startActivity(intent);
    }

    public void deleteWorker(View view) {
        db.deleteWorker(worker);

        Intent intent = new Intent(this, WorkersActivity.class);
        startActivity(intent);
    }
}
