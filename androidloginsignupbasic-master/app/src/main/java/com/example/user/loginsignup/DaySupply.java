package com.example.user.loginsignup;

/**
 * Created by user on 17/03/2018.
 */

public class DaySupply {

    private int id;
    private int dayId;
    private int supplyId;
    private int count;

    public DaySupply() {
    }

    public DaySupply(int dayId, int supplyId, int count) {
        this.dayId = dayId;
        this.supplyId = supplyId;
        this.count = count;
    }

    public DaySupply(int id, int dayId, int supplyId, int count) {
        this.id = id;
        this.dayId = dayId;
        this.supplyId = supplyId;
        this.count = count;
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

    public int getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(int supplyId) {
        this.supplyId = supplyId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "DaySupply{" +
                "id=" + id +
                ", dayId=" + dayId +
                ", supplyId=" + supplyId +
                ", count=" + count +
                '}';
    }
}
