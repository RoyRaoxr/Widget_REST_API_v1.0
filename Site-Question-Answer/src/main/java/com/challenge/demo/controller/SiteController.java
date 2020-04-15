package com.challenge.demo.controller;

import com.challenge.demo.bean.User;
import com.challenge.demo.repo.SiteRepository;
import com.challenge.demo.bean.Site;
import com.challenge.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/sites")
public class SiteController {

    @Autowired
    SiteRepository siteRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Site createSite(@RequestBody Site createSite) {
        createSite.setSiteUUID(UUID.randomUUID());

        return siteRepository.save(createSite);
    }

    @GetMapping()
    public ResponseEntity<List<Site>> getSites() {
        return Optional
                .ofNullable(siteRepository.findAll())
                .map(sites -> ResponseEntity.ok(sites))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Site> updateSite(@RequestBody Site updatedSite, @PathVariable(value = "id") Long siteId) {
        return siteRepository
                .findById(siteId)
                .map(site -> {
                    site.setUrl(updatedSite.getUrl());
                    return new ResponseEntity<>(siteRepository.save(site), HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Site> deleteSite(@PathVariable(value = "id") Long siteId) {
        return siteRepository
                .findById(siteId)
                .map(site -> {
                    siteRepository.delete(site);
                    return ResponseEntity.ok(site);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Site> getSiteById(@PathVariable(value = "id") Long siteId) {
        return siteRepository
                .findById(siteId)
                .map(site -> ResponseEntity.ok(site))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
