package com.ecotrekker.routemanager.service;

import java.time.Duration;

import org.springframework.stereotype.Component;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GenericCacheManager<K, V> {

    private final Cache<K, V> cache = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(15))
            .maximumSize(1000L)
            .scheduler(Scheduler.systemScheduler())
            .build();

    public Mono<V> get(K key, Mono<V> handler) {
        return Mono.justOrEmpty(this.cache.getIfPresent(key))
                .doOnNext(value -> log.info("[CACHE] hit for key" + key))
                .switchIfEmpty(Mono.defer(() -> handler
                        .doOnNext(value -> log
                                .info("[CACHE] miss... written new value for key" + key + " value: " + value))
                        .flatMap(it -> this.put(key, it))));
    }

    public V get(K key) {
        return this.cache.getIfPresent(key);
    }

    public Mono<V> put(K key, V object) {
        return Mono.just(object)
                .doOnNext(element -> cache.put(key, element));
    }

    public void remove(K key) {
        this.cache.invalidate(key);
    }

    public void reset() {
        this.cache.invalidateAll();
    }
}