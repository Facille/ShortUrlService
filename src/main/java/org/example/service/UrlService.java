package org.example.service;

import org.example.model.Url;
import org.example.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UrlService {

    @Autowired
    private UrlRepository linkRepository;

    public Url createShortLink(String originalUrl, int clickLimit, UUID userId) {
        Url link = new Url();
        link.setOriginalUrl(originalUrl);
        link.setShortUrl(generateShortUrl(originalUrl, userId));
        link.setClickLimit(clickLimit);
        link.setClickCount(0);
        link.setExpirationDate(LocalDateTime.now().plusDays(1));
        link.setUserId(userId);
        return linkRepository.save(link);
    }

    private String generateShortUrl(String originalUrl, UUID userId) {

        return UUID.randomUUID().toString().substring(0, 8);
    }

    public Url getLink(String shortUrl) {
        return linkRepository.findByShortUrl(shortUrl);
    }

    public void incrementClickCount(Url link) {
        link.setClickCount(link.getClickCount() + 1);
        linkRepository.save(link);
    }

    public void deleteLink(Long id) {
        linkRepository.deleteById(id);
    }

    @Scheduled(fixedRate = 60000)
    public void checkExpiredLinks() {
        List<Url> links = linkRepository.findAll();
        for (Url link : links) {
            if (link.getClickCount() >= link.getClickLimit() || LocalDateTime.now().isAfter(link.getExpirationDate())) {
                System.out.println("Link expired or limit reached: " + link.getShortUrl());
                deleteLink(link.getId());
            }
        }
    }

    public List<Url> getLinksByUserId(UUID userId) {
        return linkRepository.findByUserId(userId);
    }
    public Url updateClickLimit(Long id, int newClickLimit) {
        Url link = linkRepository.findById(id).orElseThrow(() -> new RuntimeException("Link not found"));
        link.setClickLimit(newClickLimit);
        return linkRepository.save(link);
    }
}