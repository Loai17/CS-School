package com.example.user.loginsignup;

/**
 * Created by User on 2/16/2018.
 */

public class Manager {

    private int id;
    private String fullName;
    private String username;
    private int companyId;
    private String email;
    private String password;

    public Manager() {

    }

    public Manager(String fullName, String username, int companyId,String email, String password) {
        this.fullName = fullName;
        this.username = username;
        this.companyId = companyId;
        this.email = email;
        this.password = password;
    }

    public Manager(int id, String fullName, String username, int companyId,String email, String password) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.companyId = companyId;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCompanyId() {return companyId;}

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", username='" + username + '\'' +
                ", companyId='" + companyId + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
