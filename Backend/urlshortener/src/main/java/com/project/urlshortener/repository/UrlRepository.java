package com.project.urlshortener.repository;

import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;
import com.project.urlshortener.model.Url;

/**
 * Repository interface for managing URL entities in the MongoDB database.
 * 
 * Extends MongoRepository to leverage common CRUD operations.
 */
@Repository
public interface UrlRepository extends MongoRepository<Url, String> { // Changed ID type to String for MongoDB compatibility.

    /**
     * Finds a URL entity by its short key.
     *
     * @param shortKey The short key representing the shortened URL
     * @return The URL entity associated with the given short key
     */
    Url findByShortKey(String shortKey);

    /**
     * Finds a URL entity by its long URL.
     *
     * @param longUrl The original long URL
     * @return The URL entity associated with the given long URL
     */
    Url findByLongUrl(String longUrl);

    /**
     * Updates the click count for a URL entity by its short key.
     *
     * @param shortKey  The short key representing the shortened URL
     * @param clickCount The updated click count
     * @return The number of affected documents
     */
    @Query("{'shortKey': ?0}")
    @Update("{'$set': {'clickCount': ?1}}") // Spring Data MongoDB requires this for update operations.
    Integer updateClickCount(String shortKey, int clickCount);

    /**
     * Deletes a URL entity by its short key.
     *
     * @param shortKey The short key representing the shortened URL
     */
    @DeleteQuery("{'shortKey': ?0}")
    void deleteByShortKey(String shortKey);
}
