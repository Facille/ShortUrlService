package org.example.service;

import org.example.model.Link;
import org.example.repository.LinklRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class LinkService {

    @Autowired
    private LinklRepository linkRepository;

    public Link createShortLink(String originalUrl, int clickLimit, UUID userId) {
        Link link = new Link();
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

    public Link getLink(String shortUrl) {
        return linkRepository.findByShortUrl(shortUrl);
    }

    public void incrementClickCount(Link link) {
        link.setClickCount(link.getClickCount() + 1);
        linkRepository.save(link);
    }

    public void deleteLink(Long id) {
        linkRepository.deleteById(id);
    }

    @Scheduled(fixedRate = 60000)
    public void checkExpiredLinks() {
        List<Link> links = linkRepository.findAll();
        for (Link link : links) {
            if (link.getClickCount() >= link.getClickLimit() || LocalDateTime.now().isAfter(link.getExpirationDate())) {
                System.out.println("Link expired or limit reached: " + link.getShortUrl());
                deleteLink(link.getId());
            }
        }
    }

    public List<Link> getLinksByUserId(UUID userId) {
        return linkRepository.findByUserId(userId);
    }
    public Link updateClickLimit(Long id, int newClickLimit) {
        Link link = linkRepository.findById(id).orElseThrow(() -> new RuntimeException("Link not found"));
        link.setClickLimit(newClickLimit);
        return linkRepository.save(link);
    }
}