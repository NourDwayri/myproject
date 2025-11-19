package com.example.myapplication;

public class AvatarDto {
    private String name;
    private String description;
    private String imageUrl; // URL instead of drawable

    // ✅ Constructor to create an AvatarDto with all fields
    public AvatarDto(String name, String description, String imageUrl) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    // ✅ Default no-argument constructor (needed by Retrofit/Gson)
    public AvatarDto() {}

    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }

    // Optional: Setters if you want to modify after creation
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
