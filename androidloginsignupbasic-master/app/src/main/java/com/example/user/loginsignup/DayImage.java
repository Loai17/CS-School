package com.example.user.loginsignup;

/**
 * Created by user on 17/03/2018.
 */

public class DayImage {

    private int id;
    private int dayId;
    private String image;
    private String description;

    public DayImage() {
    }

    public DayImage(int dayId, String image, String description) {
        this.dayId = dayId;
        this.image = image;
        this.description = description;
    }

    public DayImage(int id, int dayId, String image, String description) {
        this.id = id;
        this.dayId = dayId;
        this.image = image;
        this.description = description;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "DayImage{" +
                "id=" + id +
                ", dayId=" + dayId +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
