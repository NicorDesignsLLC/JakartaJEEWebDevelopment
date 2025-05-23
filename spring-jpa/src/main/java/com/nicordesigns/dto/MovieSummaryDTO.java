package com.nicordesigns.dto;

public class MovieSummaryDTO {
    private String title;
    private String studioName;

    public MovieSummaryDTO(String title, String studioName) {
        this.title = title;
        this.studioName = studioName;
    }

    // Getters (required for projection or printing)
    public String getTitle() {
        return title;
    }

    public String getStudioName() {
        return studioName;
    }

    // Optional: toString() for logging/debugging
    @Override
    public String toString() {
        return "MovieSummaryDTO{" +
                "title='" + title + '\'' +
                ", studioName='" + studioName + '\'' +
                '}';
    }
}
