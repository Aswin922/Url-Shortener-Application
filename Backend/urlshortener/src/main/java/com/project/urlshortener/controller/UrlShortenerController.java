package com.project.urlshortener.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.urlshortener.dto.UrlDto;
import com.project.urlshortener.exception.UrlNotFoundException;
import com.project.urlshortener.model.Url;
import com.project.urlshortener.repository.UrlRepository;
import com.project.urlshortener.service.UrlShortenerService;

import jakarta.validation.Valid;

/**
 * Controller for managing URL shortener operations.
 * Exposes endpoints to create, update, delete, and retrieve shortened URLs.
 */
@RestController
@RequestMapping(value = "/")
@CrossOrigin(origins = "http://localhost:3000")
@Validated
public class UrlShortenerController {

	@Autowired
    private UrlRepository repository;
	
    @Autowired
    private UrlShortenerService urlService;

    /**
     * Endpoint to add a new URL.
     *
     * @param url The DTO containing the URL to be shortened
     * @return ResponseEntity with status and message
     */
    @PostMapping(value = "/addurl")
    public ResponseEntity<Object> addUrl(@RequestBody UrlDto url) {
    	Url existingUrl = repository.findByLongUrl(url.getLongUrl());
        if (existingUrl != null) {
            return new ResponseEntity<>("Long URL already exists with short key: " + existingUrl.getShortKey(), HttpStatus.CONFLICT);
        }
        UrlDto createdUrl = urlService.addUrl(url);
        return new ResponseEntity<>(createdUrl, HttpStatus.CREATED);
    }

    /**
     * Endpoint to update click count for a shortened URL.
     *
     * @param shortKey The shortened URL key
     * @return ResponseEntity with status and message
     */
    @PutMapping("/updateurl/{shortKey}")
    public ResponseEntity<Object> updateUrl(@Valid @PathVariable String shortKey) {
        try {
            String longUrl = urlService.updateClick(shortKey);
            return new ResponseEntity<>(longUrl, HttpStatus.OK);
        } catch (UrlNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint to delete a shortened URL.
     *
     * @param shortKey The shortened URL key to be deleted
     * @return ResponseEntity with status and success message
     * @throws UrlNotFoundException If URL is not found
     */
    @DeleteMapping(value = "/deleteurl/{shortKey}")
    public ResponseEntity<String> deleteUrl(@Valid @PathVariable String shortKey) throws UrlNotFoundException {
    	try {
            urlService.deleteUrl(shortKey);
            return new ResponseEntity<>("URL deleted successfully", HttpStatus.OK);
        } catch (UrlNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint to view all shortened URLs.
     *
     * @return ResponseEntity with list of all shortened URLs
     */
    @GetMapping(value = "/viewallurl")
    public ResponseEntity<List<UrlDto>> viewAllUrl() {
        List<UrlDto> urlList = urlService.viewAllUrls();
        return new ResponseEntity<>(urlList, HttpStatus.OK);
    }
}
