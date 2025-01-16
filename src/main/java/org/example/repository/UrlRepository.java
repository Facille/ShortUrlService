package org.example.repository;

import org.example.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    List<Url> findByUserId(UUID userId);
    Url findByShortUrl(String shortUrl);
}