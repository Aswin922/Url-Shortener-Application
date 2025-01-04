package com.project.urlshortener;

import com.project.urlshortener.controller.UrlShortenerController;
import com.project.urlshortener.dto.UrlDto;
import com.project.urlshortener.exception.UrlNotFoundException;
import com.project.urlshortener.repository.UrlRepository;
import com.project.urlshortener.service.UrlShortenerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the URL Shortener application.
 */
@SpringBootTest
class UrlShortenerApplicationTests {

    private MockMvc mockMvc;

    @Mock
    private UrlShortenerService urlShortenerService;

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private UrlShortenerController urlShortenerController;

    /**
     * Set up MockMvc for testing controllers before each test case.
     */
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(urlShortenerController).build();
    }

    @Test
    void contextLoads() {
        // Verifies if the application context loads without issues.
    }

    /**
     * Test case for successfully adding a URL.
     */
    @Test
    void testAddUrl_Success() throws Exception {
        UrlDto urlDto = createUrlDto("http://example.com", null);
        UrlDto responseDto = createUrlDto("http://example.com", "urls-12345");

        when(urlShortenerService.addUrl(any(UrlDto.class))).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/addurl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"longUrl\":\"http://example.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.shortKey").value("urls-12345"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.longUrl").value("http://example.com"));

        verify(urlShortenerService, times(1)).addUrl(any(UrlDto.class));
    }

    /**
     * Test case for successfully updating a URL's click count.
     */
    @Test
    void testUpdateUrl_Success() throws Exception {
        String shortKey = "urls-12345";
        String longUrl = "http://example.com";

        when(urlShortenerService.updateClick(anyString())).thenReturn(longUrl);

        mockMvc.perform(MockMvcRequestBuilders.put("/updateurl/{shortKey}", shortKey))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(longUrl));

        verify(urlShortenerService, times(1)).updateClick(shortKey);
    }

    /**
     * Test case for attempting to update a non-existent URL.
     */
    @Test
    void testUpdateUrl_NotFound() throws Exception {
        String shortKey = "urls-12345";
        String errorMessage = "Short key not found: " + shortKey;

        when(urlShortenerService.updateClick(anyString())).thenThrow(new UrlNotFoundException(errorMessage));

        mockMvc.perform(MockMvcRequestBuilders.put("/updateurl/{shortKey}", shortKey))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(errorMessage));

        verify(urlShortenerService, times(1)).updateClick(shortKey);
    }

    /**
     * Test case for successfully deleting a URL.
     */
    @Test
    void testDeleteUrl_Success() throws Exception {
        String shortKey = "urls-12345";

        doNothing().when(urlShortenerService).deleteUrl(anyString());

        mockMvc.perform(MockMvcRequestBuilders.delete("/deleteurl/{shortKey}", shortKey))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("URL deleted successfully"));

        verify(urlShortenerService, times(1)).deleteUrl(shortKey);
    }

    /**
     * Test case for attempting to delete a non-existent URL.
     */
    @Test
    void testDeleteUrl_NotFound() throws Exception {
        String shortKey = "urls-12345";
        String errorMessage = "Short key not found: " + shortKey;

        doThrow(new UrlNotFoundException(errorMessage)).when(urlShortenerService).deleteUrl(anyString());

        mockMvc.perform(MockMvcRequestBuilders.delete("/deleteurl/{shortKey}", shortKey))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(errorMessage));

        verify(urlShortenerService, times(1)).deleteUrl(shortKey);
    }

    /**
     * Test case for retrieving all URLs successfully.
     */
    @Test
    void testViewAllUrls_Success() throws Exception {
        UrlDto urlDto = createUrlDto("http://example.com", "urls-12345");
        List<UrlDto> urlList = Collections.singletonList(urlDto);

        when(urlShortenerService.viewAllUrls()).thenReturn(urlList);

        mockMvc.perform(MockMvcRequestBuilders.get("/viewallurl"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].shortKey").value("urls-12345"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].longUrl").value("http://example.com"));

        verify(urlShortenerService, times(1)).viewAllUrls();
    }

    /**
     * Utility method to create a UrlDto object.
     *
     * @param longUrl  The original long URL.
     * @param shortKey The generated short key (if any).
     * @return A new UrlDto object.
     */
    private UrlDto createUrlDto(String longUrl, String shortKey) {
        UrlDto urlDto = new UrlDto();
        urlDto.setLongUrl(longUrl);
        urlDto.setShortKey(shortKey);
        return urlDto;
    }
}
