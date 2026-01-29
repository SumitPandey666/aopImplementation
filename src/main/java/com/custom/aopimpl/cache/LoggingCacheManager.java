package com.custom.aopimpl.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.concurrent.Callable;

@Slf4j
public class LoggingCacheManager implements CacheManager {

    private final CacheManager delegate;

    public LoggingCacheManager(CacheManager delegate) {
        this.delegate = delegate;
    }

    @Override
    public Cache getCache(String name) {
        Cache cache = delegate.getCache(name);
        return (cache == null) ? null : new LoggingCache(cache, name);
    }

    @Override
    public Collection<String> getCacheNames() {
        return delegate.getCacheNames();
    }

    static class LoggingCache implements Cache {
        private final Cache delegate;
        private final String cacheName;

        LoggingCache(Cache delegate, String cacheName) {
            this.delegate = delegate;
            this.cacheName = cacheName;
        }

        @Override
        public ValueWrapper get(Object key) {
            ValueWrapper value = delegate.get(key);
            if (value != null) {
                log.info("Cache HIT [{}] key={}", cacheName, key);
            } else {
                log.info("Cache MISS [{}] key={}", cacheName, key);
            }
            return value;
        }

        @Nullable
        @Override
        public <T> T get(Object key, @Nullable Class<T> type) {
            return null;
        }

        @Nullable
        @Override
        public <T> T get(Object key, Callable<T> valueLoader) {
            return null;
        }

        @Override
        public void put(Object key, Object value) {
            log.info("Cache PUT [{}] key={} value={}", cacheName, key, value);
            delegate.put(key, value);
        }

        // delegate other methods...
        @Override public void evict(Object key) { delegate.evict(key); }
        @Override public void clear() { delegate.clear(); }
        @Override public String getName() { return delegate.getName(); }
        @Override public Object getNativeCache() { return delegate.getNativeCache(); }
    }
}