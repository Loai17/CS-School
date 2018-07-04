package com.example.user.loginsignup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;

public class AddWorkerActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etDob;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private RadioGroup rgExperience;
    private RadioGroup rgPayment;
    private EditText etPrice;

    RadioButton rbBeginner;
    RadioButton rbIntermediate;
    RadioButton rbProfessional;

    RadioButton rbDaily;
    RadioButton rbHourly;


    private Button btnAddWorker;

    private Database db;
    private static final String MY_PREFS_NAME = "UserPrefs";
    private SharedPreferences prefs;


    private Worker foundWorker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_worker);

        initialize();
    }

    private void initialize(){
        etName = (EditText) findViewById(R.id.etName);
        etDob = (EditText) findViewById(R.id.etDob);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        rgExperience = (RadioGroup) findViewById(R.id.rgExperience);
        rgPayment = (RadioGroup) findViewById(R.id.rgPayment);
        etPrice = (EditText) findViewById(R.id.etPrice);

        rbBeginner = (RadioButton) findViewById(R.id.rbBeginner);
        rbIntermediate = (RadioButton) findViewById(R.id.rbIntermediate);
        rbProfessional = (RadioButton) findViewById(R.id.rbProfessional);
        rbDaily = (RadioButton) findViewById(R.id.rbDaily);
        rbHourly = (RadioButton) findViewById(R.id.rbHourly);


        btnAddWorker = (Button) findViewById(R.id.btnAddWorker);

        db = new Database(this);
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        String wUsername = getIntent().getStringExtra("WORKER_USERNAME");

        Toast.makeText(this, "Worker username : " + wUsername, Toast.LENGTH_LONG).show();


    }

    public void AddWorkerFunc(View view){
        String name = etName.getText().toString();
        String dob = etDob.getText().toString();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        String Experience;
        String Payment;
        double price = Double.parseDouble(etPrice.getText().toString());

        RadioButton selectedExperience = (RadioButton) findViewById(rgExperience.getCheckedRadioButtonId());
        Experience = selectedExperience.getText().toString();

        RadioButton selectedPayment = (RadioButton) findViewById(rgPayment.getCheckedRadioButtonId());
        Payment = selectedPayment.getText().toString();

        String managerUsername = prefs.getString("username", null);
        Manager managerInCharge = db.getManager(managerUsername);

        if(db.getWorker(username)==null){
            if(password.equals(confirmPassword)){
                if(Experience.equals("") || Payment.equals("")){
                    Toast.makeText(this, "Please fill all of the radio buttons!", Toast.LENGTH_LONG).show();

                    return;
                }
                Worker worker = new Worker(managerInCharge.getCompanyId(),name,dob,username,password,Experience,price,Payment,"None");
                db.addWorker(worker);

                List<Worker> allWorkers = db.getAllWorkers();
                boolean found = false;
                for (Worker w: allWorkers) {
                    if(w.getUsername().equals(username) && !found) {
                        foundWorker = w;
                        found=true;
                    }
                }

                if(foundWorker==null) {

                    boolean flag = false;// USE THIS WHENEVER CREATING A NEW DATABASE ITEM
                    while (!flag) {
                        for (Worker w : allWorkers) {
                            if (worker.getId() == w.getId()) {
                                worker.setId(worker.getId() + 1);
                                flag = false;
                            } else {
                                flag = true;
                            }
                        }
                    }
                    db.addWorker(worker);
                }

                Toast.makeText(this, "New worker created and added successfully!", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(this, WorkersActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(this, "The passwords are not matching. Make sure you write them correctly.", Toast.LENGTH_LONG).show();
                return;
            }
        }
        else{
            Toast.makeText(this, "There's an account for this username already. Please change that.", Toast.LENGTH_LONG).show();
            return;
        }
    }


}
