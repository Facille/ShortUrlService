package org.example.controller;

import org.example.model.Link;
import org.example.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/links")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @PostMapping
    public ResponseEntity<Link> createLink(@RequestParam String originalUrl, @RequestParam int clickLimit) {
        UUID userId = UUID.randomUUID();
        Link createdLink = linkService.createShortLink(originalUrl, clickLimit, userId);
        return new ResponseEntity<>(createdLink, HttpStatus.CREATED);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortUrl) {
        Link link = linkService.getLink(shortUrl);
        if (link != null) {
            if (link.getClickCount() < link.getClickLimit() && LocalDateTime.now().isBefore(link.getExpirationDate())) {
                linkService.incrementClickCount(link);
                return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(link.getOriginalUrl())).build();
            } else {
                return ResponseEntity.status(HttpStatus.GONE).body("Link is not available");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Link not found");
    }

    @PutMapping("/{id}/limit")
    public ResponseEntity<Link> updateClickLimit(@PathVariable Long id, @RequestParam int newClickLimit) {
        Link updatedLink = linkService.updateClickLimit(id, newClickLimit);
        return new ResponseEntity<>(updatedLink, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLink(@PathVariable Long id) {
        linkService.deleteLink(id);
        return ResponseEntity.noContent().build();
    }
}