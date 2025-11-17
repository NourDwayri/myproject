package com.example.myapplication;


public class Avatar {
    private String name;
    private String description;
    private int imageResourceId;

    //drinks is an array of Drinks
    public static final Avatar[] drinks = {
            new Avatar("Michael", "Beat me if you can",R.drawable.michael),
            new Avatar("Lina", "I'm a girl, therefore I'm smart",R.drawable.lina),
            new Avatar("Fadi", "Being a nerd is one of my coolest traits !",R.drawable.fadi)
    };

    //Each Drink has a name, description, and an image resource
    private Avatar(String name, String description, int imageResourceId) {
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }
    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String toString() {
        return this.name;
    }
}
