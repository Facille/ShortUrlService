package org.example;

import org.example.model.Url;
import org.example.repository.UrlRepository;
import org.example.service.UrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ShortUrlServiceApplicationTests {

    @InjectMocks
    private UrlService linkService;

    @Mock
    private UrlRepository linkRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateShortLink() {
        String originalUrl = "http://example.com";
        int clickLimit = 10;
        UUID userId = UUID.randomUUID();

        Url savedLink = new Url();
        savedLink.setId(1L);
        savedLink.setOriginalUrl(originalUrl);
        savedLink.setShortUrl("shortUrl");
        savedLink.setClickLimit(clickLimit);
        savedLink.setClickCount(0);
        savedLink.setExpirationDate(LocalDateTime.now().plusDays(1));
        savedLink.setUserId(userId);

        when(linkRepository.save(any(Url.class))).thenReturn(savedLink);

        Url createdLink = linkService.createShortLink(originalUrl, clickLimit, userId);

        assertNotNull(createdLink);
        assertEquals(originalUrl, createdLink.getOriginalUrl());
        assertEquals(clickLimit, createdLink.getClickLimit());
        assertEquals(userId, createdLink.getUserId());
        verify(linkRepository, times(1)).save(any(Url.class));
    }

    @Test
    public void testGetLink() {
        String shortUrl = "shortUrl";
        Url link = new Url();
        link.setShortUrl(shortUrl);
        link.setOriginalUrl("http://example.com");

        when(linkRepository.findByShortUrl(shortUrl)).thenReturn(link);

        Url foundLink = linkService.getLink(shortUrl);

        assertNotNull(foundLink);
        assertEquals(shortUrl, foundLink.getShortUrl());
        verify(linkRepository, times(1)).findByShortUrl(shortUrl);
    }

    @Test
    public void testIncrementClickCount() {
        Url link = new Url();
        link.setId(1L);
        link.setClickCount(0);

        when(linkRepository.save(any(Url.class))).thenReturn(link);

        linkService.incrementClickCount(link);

        assertEquals(1, link.getClickCount());
        verify(linkRepository, times(1)).save(link);
    }

    @Test
    public void testDeleteLink() {
        Long linkId = 1L;

        doNothing().when(linkRepository).deleteById(linkId);

        linkService.deleteLink(linkId);

        verify(linkRepository, times(1)).deleteById(linkId);
    }

    @Test
    public void testUpdateClickLimit() {
        Long linkId = 1L;
        int newClickLimit = 20;

        Url link = new Url();
        link.setId(linkId);
        link.setClickLimit(10);

        when(linkRepository.findById(linkId)).thenReturn(Optional.of(link));
        when(linkRepository.save(link)).thenReturn(link);

        Url updatedLink = linkService.updateClickLimit(linkId, newClickLimit);

        assertNotNull(updatedLink);
        assertEquals(newClickLimit, updatedLink.getClickLimit());
        verify(linkRepository, times(1)).findById(linkId);
        verify(linkRepository, times(1)).save(link);
    }
}
