package org.example.repository;

import org.example.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LinklRepository extends JpaRepository<Link, Long> {
    List<Link> findByUserId(UUID userId);
    Link findByShortUrl(String shortUrl);
}