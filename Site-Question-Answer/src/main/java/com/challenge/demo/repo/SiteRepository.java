package com.challenge.demo.repo;

import com.challenge.demo.bean.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface SiteRepository extends JpaRepository<Site, Long> {

	Site findBySiteUUID(UUID siteUUID);
}