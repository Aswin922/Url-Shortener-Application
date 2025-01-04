package com.project.urlshortener.service;

import java.util.List;
import com.project.urlshortener.dto.UrlDto;
import com.project.urlshortener.exception.UrlNotFoundException;

/**
 * Interface for URL Shortener Service.
 * 
 * Provides methods for adding, updating, deleting, and viewing URLs.
 */
public interface UrlShortenerServiceInterface {

    /**
     * Adds a new URL to the system.
     *
     * @param urlDto The URL DTO containing the long URL and optional metadata
     * @return The created URL DTO, including the generated short key
     */
    UrlDto addUrl(UrlDto urlDto);

    /**
     * Updates the click count for a short key.
     *
     * @param shortKey The short key of the URL
     * @return The long URL associated with the short key
     * @throws UrlNotFoundException If the short key does not exist
     */
    String updateClick(String shortKey) throws UrlNotFoundException;

    /**
     * Deletes a URL by its short key.
     *
     * @param shortKey The short key of the URL to be deleted
     * @throws UrlNotFoundException If the short key does not exist
     */
    void deleteUrl(String shortKey) throws UrlNotFoundException;

    /**
     * Retrieves all URLs stored in the system.
     *
     * @return A list of URL DTOs representing all stored URLs
     */
    List<UrlDto> viewAllUrls();
}
