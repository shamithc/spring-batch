package com.shamith.springbatch;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/outbox")
@RequiredArgsConstructor
public class CacheController {

    @Autowired
    private CacheService cacheService;

    @GetMapping
    public String test() {
        cacheService.saveToCache();
        return "Test";
    }
}
