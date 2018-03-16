package com.example.user.loginsignup;

import java.util.Date;

/**
 * Created by user on 16/03/2018.
 */

public class Day {

    private int id;
    private int companyId;
    private int jobId;
    private Date date;
    private String name;
    private String description;

    public Day() {
    }

    public Day(int companyId, int jobId, Date date, String name, String description) {
        this.companyId = companyId;
        this.jobId = jobId;
        this.date = date;
        this.name = name;
        this.description = description;
    }

    public Day(int id, int companyId, int jobId, Date date, String name, String description) {
        this.id = id;
        this.companyId = companyId;
        this.jobId = jobId;
        this.date = date;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Day{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", jobId=" + jobId +
                ", date=" + date +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
