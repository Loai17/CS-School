package com.example.user.loginsignup;

/**
 * Created by user on 17/03/2018.
 */

public class Supply {

    private int id;
    private String name;
    private String image;
    private String description;

    public Supply() {
    }

    public Supply(String name, String image, String description) {
        this.name = name;
        this.image = image;
        this.description = description;
    }

    public Supply(int id, String name, String image, String description) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "Supply{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
