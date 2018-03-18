package com.example.user.loginsignup;

import android.media.Image;

import java.util.Date;

/**
 * Created by user on 16/03/2018.
 */

public class Worker {

    private int id;
    private int companyId;
    private String name;
    private String dob;
    private String username;
    private String password;
    private String experience;
    private double payment;
    private String paymentMethod;
    private String photo;

    public Worker() {
    }

    public Worker(int companyId, String name, String dob, String username, String password, String experience, double payment, String paymentMethod, String photo) {
        this.companyId = companyId;
        this.name = name;
        this.dob = dob;
        this.username = username;
        this.password = password;
        this.experience = experience;
        this.payment = payment;
        this.paymentMethod = paymentMethod;
        this.photo = photo;
    }

    public Worker(int id, int companyId, String name, String dob, String username, String password, String experience, double payment, String paymentMethod, String photo) {
        this.id = id;
        this.companyId = companyId;
        this.name = name;
        this.dob = dob;
        this.username = username;
        this.password = password;
        this.experience = experience;
        this.payment = payment;
        this.paymentMethod = paymentMethod;
        this.photo = photo;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", experience='" + experience + '\'' +
                ", payment=" + payment +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
