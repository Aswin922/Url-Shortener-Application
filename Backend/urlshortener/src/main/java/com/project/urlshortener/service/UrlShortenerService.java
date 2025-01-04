package com.project.urlshortener.service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.project.urlshortener.dto.UrlDto;
import com.project.urlshortener.exception.UrlNotFoundException;
import com.project.urlshortener.model.Url;
import com.project.urlshortener.repository.UrlRepository;

/**
 * Implementation of the UrlShortenerServiceInterface.
 * Provides logic for shortening URLs, tracking clicks, and managing URL records.
 */
@Service
public class UrlShortenerService implements UrlShortenerServiceInterface {

    @Autowired
    private UrlRepository repository;

    private static final HashFunction HASH_FUNCTION = Hashing.goodFastHash(32);

    /**
     * Adds a new URL to the system and generates a short key.
     *
     * @param urlDto The DTO containing the original URL
     * @return The updated DTO with the generated short key
     */
    @Override
    public UrlDto addUrl(UrlDto urlDto) {
        Url url = new Url();
        url.setLongUrl(urlDto.getLongUrl());
        url.setShortKey(generateShortKey(urlDto.getLongUrl()));
        url.setClickCount(0);

        repository.save(url);

        urlDto.setShortKey(url.getShortKey());
        return urlDto;
    }

    /**
     * Updates the click count for a given short key.
     *
     * @param shortKey The short key to update
     * @return The original long URL associated with the short key
     * @throws UrlNotFoundException If the short key does not exist
     */
    @Override
    public String updateClick(String shortKey) throws UrlNotFoundException {
        Url url = repository.findByShortKey(shortKey);
        if (url == null) {
            throw new UrlNotFoundException("Short key not found: " + shortKey);
        }

        url.setClickCount(url.getClickCount() + 1);
        repository.updateClickCount(url.getShortKey(), url.getClickCount());
        return url.getLongUrl();
    }

    /**
     * Deletes a URL record by its short key.
     *
     * @param shortKey The short key to delete
     * @throws UrlNotFoundException If the short key does not exist
     */
    @Override
    public void deleteUrl(String shortKey) throws UrlNotFoundException {
        Url url = repository.findByShortKey(shortKey);
        if (url == null) {
            throw new UrlNotFoundException("Short key not found: " + shortKey);
        }
        repository.deleteByShortKey(shortKey);
    }

    /**
     * Retrieves all URL records in the system.
     *
     * @return A list of URL DTOs representing all stored URLs
     */
    @Override
    public List<UrlDto> viewAllUrls() {
        List<Url> urlEntities = repository.findAll();
        List<UrlDto> urlDtos = new ArrayList<>();

        urlEntities.forEach(url -> {
            UrlDto urlDto = new UrlDto();
            urlDto.setLongUrl(url.getLongUrl());
            urlDto.setShortKey(url.getShortKey());
            urlDto.setClickCount(url.getClickCount());
            urlDtos.add(urlDto);
        });

        return urlDtos;
    }

    /**
     * Generates a short key for the given URL.
     *
     * @param url The original long URL
     * @return A unique short key
     */
    private String generateShortKey(String url) {
        return "urls-" + HASH_FUNCTION.hashString(url, StandardCharsets.UTF_8).toString();
    }
}
