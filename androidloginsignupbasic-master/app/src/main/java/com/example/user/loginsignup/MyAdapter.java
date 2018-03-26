package com.example.user.loginsignup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by looby on 3/26/2018.
 */

public class MyAdapter extends BaseAdapter {
    Context context;
    ArrayList<Job> jobsList;
    private static LayoutInflater inflater = null;

    public MyAdapter(Context context, ArrayList<Job> empList) {
        this.context = context;
        this.jobsList = empList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return jobsList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null)
            convertView = inflater.inflate(R.layout.layout_grid_item, null);

        TextView codeTextView = (TextView) convertView.findViewById(R.id.tv_emp_id);
        TextView nameTextView = (TextView) convertView.findViewById(R.id.tv_emp_name);
        TextView emailTextView = (TextView) convertView.findViewById(R.id.tv_emp_email);
        TextView addressTextView = (TextView) convertView.findViewById(R.id.tv_emp_address);

        Job j = new Job();
        j = jobsList.get(position);
        codeTextView.setText("ID: " + String.valueOf(j.getId()));
        nameTextView.setText("Name: " + j.getName());
        emailTextView.setText("Location: " + j.getLocation());
        addressTextView.setText("Description: " + j.getDescription());
        return convertView;
    }

}