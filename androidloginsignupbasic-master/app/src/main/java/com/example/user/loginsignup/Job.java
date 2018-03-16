package com.example.user.loginsignup;

import java.util.Date;

/**
 * Created by user on 16/03/2018.
 */

public class Job {

    private int id;
    private int companyId; //secondary
    private String name;
    private String location;
    private String description;
    private Date startDate;
    private Date endDate;

    public Job() {
    }

    public Job(int companyId, String name, String location, String description, Date startDate, Date endDate) {
        this.companyId = companyId;
        this.name = name;
        this.location = location;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Job(int id, int companyId, String name, String location, String description, Date startDate, Date endDate) {
        this.id = id;
        this.companyId = companyId;
        this.name = name;
        this.location = location;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
