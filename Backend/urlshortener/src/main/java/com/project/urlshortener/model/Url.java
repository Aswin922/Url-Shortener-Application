package com.project.urlshortener.model;

import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

/**
 * Represents a URL entity stored in the database.
 * 
 * This class maps to the "urls" collection in MongoDB and holds information 
 * about the long URL, its short key, and the number of clicks.
 */
@Document(collection = "urls")
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // For future extensibility if needed
    private String id; // Unique identifier for the document

    private String longUrl;  // The original long URL
    private String shortKey; // The short key representing the shortened URL
    private int clickCount;  // The number of times the short URL has been clicked

    @Transient // Excluded from persistence
    private String analyticsData; // Example: Additional data not persisted

    /**
     * Parameterized constructor for creating a Url object with specific values.
     *
     * @param longUrl   The original long URL
     * @param shortKey  The short key representing the shortened URL
     * @param clickCount The number of times the short URL has been clicked
     */
    public Url(String longUrl, String shortKey, int clickCount) {
        this.longUrl = longUrl;
        this.shortKey = shortKey;
        this.clickCount = clickCount;
    }

    /**
     * Default constructor for creating a Url object with default values.
     */
    public Url() {
        // Default constructor
    }

    // Getters and Setters

    /**
     * Gets the unique identifier for the URL entity.
     *
     * @return The unique identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the URL entity.
     *
     * @param id The unique identifier
     */
    public void setId(String id) {
        this.id = id;
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
     * Gets the short key representing the shortened URL.
     *
     * @return The short key
     */
    public String getShortKey() {
        return shortKey;
    }

    /**
     * Sets the short key representing the shortened URL.
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
     * Gets additional analytics data (if any).
     *
     * @return The analytics data
     */
    public String getAnalyticsData() {
        return analyticsData;
    }

    /**
     * Sets additional analytics data.
     *
     * @param analyticsData The analytics data
     */
    public void setAnalyticsData(String analyticsData) {
        this.analyticsData = analyticsData;
    }

    /**
     * Provides a string representation of the Url object.
     *
     * @return A string representation of the Url
     */
    @Override
    public String toString() {
        return "Url {" +
                "id='" + id + '\'' +
                ", longUrl='" + longUrl + '\'' +
                ", shortKey='" + shortKey + '\'' +
                ", clickCount=" + clickCount +
                '}';
    }
}
