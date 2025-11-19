package com.example.myapplication;

public class AvatarDto {
    private String name;
    private String description;
    private String imageUrl;

    // Constructor
    public AvatarDto(String name, String description, String imageUrl) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
}
