package com.example.user.loginsignup;

/**
 * Created by Loai on 3/16/2018.
 */

public class DayWorker {

    private int id;
    private int dayId;
    private int workerId;

    public DayWorker() {
    }

    public DayWorker(int dayId, int workerId) {
        this.dayId = dayId;
        this.workerId = workerId;
    }

    public DayWorker(int id, int dayId, int workerId) {
        this.id = id;
        this.dayId = dayId;
        this.workerId = workerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDayId() {
        return dayId;
    }

    public void setDayId(int dayId) {
        this.dayId = dayId;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    @Override
    public String toString() {
        return "DayWorker{" +
                "id=" + id +
                ", dayId=" + dayId +
                ", workerId=" + workerId +
                '}';
    }
}
