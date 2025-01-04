package com.project.urlshortener.dto;

/**
 * Data Transfer Object (DTO) representing a URL entity.
 * 
 * This class is used to transfer data between layers of the application.
 */
public class UrlDto {

    private String longUrl;  // The original long URL
    private String shortKey; // The short key corresponding to the URL
    private int clickCount;  // The number of times the short URL has been clicked

    /**
     * Parameterized constructor for creating a UrlDto object with specific values.
     *
     * @param longUrl   The original long URL
     * @param shortKey  The short key corresponding to the URL
     * @param clickCount The number of times the short URL has been clicked
     */
    public UrlDto(String longUrl, String shortKey, int clickCount) {
        this.longUrl = longUrl;
        this.shortKey = shortKey;
        this.clickCount = clickCount;
    }

    /**
     * Default constructor for creating a UrlDto object with default values.
     */
    public UrlDto() {
        // Default constructor
    }

    /**
     * Gets the original long URL.
     *
     * @return The original long URL
     */
    public String getLongUrl() {
        return longUrl;
    }

    /**
     * Sets the original long URL.
     *
     * @param longUrl The original long URL
     */
    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    /**
     * Gets the short key corresponding to the URL.
     *
     * @return The short key
     */
    public String getShortKey() {
        return shortKey;
    }

    /**
     * Sets the short key corresponding to the URL.
     *
     * @param shortKey The short key
     */
    public void setShortKey(String shortKey) {
        this.shortKey = shortKey;
    }

    /**
     * Gets the number of times the short URL has been clicked.
     *
     * @return The click count
     */
    public int getClickCount() {
        return clickCount;
    }

    /**
     * Sets the number of times the short URL has been clicked.
     *
     * @param clickCount The click count
     */
    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    /**
     * Provides a string representation of the UrlDto object.
     *
     * @return A string representation of the UrlDto
     */
    @Override
    public String toString() {
        return "UrlDto {" +
                "longUrl='" + longUrl + '\'' +
                ", shortKey='" + shortKey + '\'' +
                ", clickCount=" + clickCount +
                '}';
    }
}
