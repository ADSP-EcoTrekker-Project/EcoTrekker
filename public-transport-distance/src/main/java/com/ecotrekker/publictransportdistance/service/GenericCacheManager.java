package com.ecotrekker.publictransportdistance.service;

import java.time.Duration;

import org.springframework.stereotype.Component;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Scheduler;

import reactor.core.publisher.Mono;

@Component
public class GenericCacheManager<K,V> {

    private final Cache<K, V> cache = Caffeine.newBuilder()
            .expireAfterAccess(Duration.ofMinutes(10))
            .maximumSize(1000L)
            .scheduler(Scheduler.systemScheduler())
            .build();

    public Mono<V> get(K key, Mono<V> handler) {
        return Mono.justOrEmpty(this.cache.getIfPresent(key))
            .switchIfEmpty(Mono.defer(() -> handler.flatMap(it -> this.put(key, it))));
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