package com.custom.aopimpl;

import com.custom.aopimpl.cache.LoggingCacheManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class AopCrudImplApplication {

	public static void main(String[] args) {
		SpringApplication.run(AopCrudImplApplication.class, args);
	}
}
