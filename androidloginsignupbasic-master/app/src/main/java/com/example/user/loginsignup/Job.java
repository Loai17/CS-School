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

    public Job() {
    }

    public Job(int companyId, String name, String location, String description) {
        this.companyId = companyId;
        this.name = name;
        this.location = location;
        this.description = description;
    }

    public Job(int id, int companyId, String name, String location, String description) {
        this.id = id;
        this.companyId = companyId;
        this.name = name;
        this.location = location;
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

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
