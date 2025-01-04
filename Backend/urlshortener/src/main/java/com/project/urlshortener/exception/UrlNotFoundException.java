package com.project.urlshortener.exception;

/**
 * Custom exception for scenarios where a URL is not found in the application.
 */
public class UrlNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor for UrlNotFoundException.
     */
    public UrlNotFoundException() {
        super("URL not found."); // Providing a default message
    }

    /**
     * Constructor for UrlNotFoundException with a custom message.
     *
     * @param message The custom error message
     */
    public UrlNotFoundException(String message) {
        super(message);
    }
}
