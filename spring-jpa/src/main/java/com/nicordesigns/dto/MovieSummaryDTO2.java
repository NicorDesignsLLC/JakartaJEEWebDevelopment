package com.nicordesigns.dto;

import java.time.LocalDate; // Assuming releaseDate is a date type in your Movie entity

public class MovieSummaryDTO2 {
    private String title;
    private String studioName;
    private LocalDate releaseDate; // Added field

    // Updated constructor to include releaseDate
    public MovieSummaryDTO2(String title, String studioName, LocalDate releaseDate) {
        this.title = title;
        this.studioName = studioName;
        this.releaseDate = releaseDate;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getStudioName() {
        return studioName;
    }

    public LocalDate getReleaseDate() { // New getter
        return releaseDate;
    }

    // Updated toString() to include releaseDate
    @Override
    public String toString() {
        return "MovieSummaryDTO2{" +
                "title='" + title + '\'' +
                ", studioName='" + studioName + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }
}