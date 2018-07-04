package com.example.user.loginsignup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WorkersActivity extends AppCompatActivity {

    Button btnAddWorker;
    ListView lvWorkers;

    ArrayList<ImageView> images= new ArrayList<ImageView>();
    ArrayList<String> names= new ArrayList<String>();
    ArrayList<String> usernames=new ArrayList<String>();

    Database db;

    private static final String MY_PREFS_NAME = "UserPrefs";
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workers);

        initialize();
    }

    private void initialize(){
        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        btnAddWorker = (Button) findViewById(R.id.btnAddWorker);
        lvWorkers = (ListView) findViewById(R.id.listView);

        db = new Database(this);

        String username = prefs.getString("username",null);


        List<Worker> allWorkers = db.getAllWorkers();
        for (Worker worker:allWorkers) { //Filling the Names and Experience Vars.
            if(worker.getCompanyId()==db.getManager(username).getCompanyId()){
                names.add(worker.getName().toString());
                usernames.add(worker.getUsername().toString());
            }
        }

        CustomAdapter customAdapter = new CustomAdapter();
        lvWorkers.setAdapter(customAdapter);

        lvWorkers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getBaseContext(), WorkerProfileActivity.class);
                intent.putExtra("WORKER_USERNAME", usernames.get(i));
                startActivity(intent);
            }
        });

    }

    public void gotoAddWorker(View view){
        Intent intent = new Intent(this, AddWorkerActivity.class);
        startActivity(intent);
    }

    public void backToJobManager(View view) {
        Intent intent = new Intent(this, JobsManagerActivity.class);
        startActivity(intent);
    }


    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return names.size();
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
            view = getLayoutInflater().inflate(R.layout.workers_custom_layout,null);

            ImageView imageView = (ImageView) view.findViewById(R.id.ivProfile);
            TextView tvName = (TextView) view.findViewById(R.id.tvName);
            TextView tvSecondary = (TextView) view.findViewById(R.id.tvSecondary);

//            imageView.setImageResource(images.get(i));
            tvName.setText(names.get(i));
            tvSecondary.setText(usernames.get(i));

            return view;
        }
    }
}
