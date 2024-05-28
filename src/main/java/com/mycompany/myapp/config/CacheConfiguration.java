package com.mycompany.myapp.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(
                Object.class,
                Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries())
            )
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build()
        );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.mycompany.myapp.domain.User.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Authority.class.getName());
            createCache(cm, com.mycompany.myapp.domain.User.class.getName() + ".authorities");
            createCache(cm, com.mycompany.myapp.domain.Prod.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Prod.class.getName() + ".prodCountries");
            createCache(cm, com.mycompany.myapp.domain.Prod.class.getName() + ".prodStds");
            createCache(cm, com.mycompany.myapp.domain.Prod.class.getName() + ".cerfProds");
            createCache(cm, com.mycompany.myapp.domain.Prod.class.getName() + ".feeProdCerfCompanies");
            createCache(cm, com.mycompany.myapp.domain.Prod.class.getName() + ".prodStickers");
            createCache(cm, com.mycompany.myapp.domain.Country.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Country.class.getName() + ".prodCountries");
            createCache(cm, com.mycompany.myapp.domain.Country.class.getName() + ".countryStds");
            createCache(cm, com.mycompany.myapp.domain.Country.class.getName() + ".countryCerts");
            createCache(cm, com.mycompany.myapp.domain.Country.class.getName() + ".countryMarks");
            createCache(cm, com.mycompany.myapp.domain.Cerf.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Cerf.class.getName() + ".cerfProds");
            createCache(cm, com.mycompany.myapp.domain.Cerf.class.getName() + ".cerfStds");
            createCache(cm, com.mycompany.myapp.domain.Cerf.class.getName() + ".cerfMarks");
            createCache(cm, com.mycompany.myapp.domain.Cerf.class.getName() + ".cerfCompanies");
            createCache(cm, com.mycompany.myapp.domain.Cerf.class.getName() + ".feeProdCerfCompanies");
            createCache(cm, com.mycompany.myapp.domain.Cerf.class.getName() + ".countryCerts");
            createCache(cm, com.mycompany.myapp.domain.Std.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Std.class.getName() + ".prodStds");
            createCache(cm, com.mycompany.myapp.domain.Std.class.getName() + ".cerfStds");
            createCache(cm, com.mycompany.myapp.domain.Std.class.getName() + ".countryStds");
            createCache(cm, com.mycompany.myapp.domain.Mark.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Mark.class.getName() + ".cerfMarks");
            createCache(cm, com.mycompany.myapp.domain.Mark.class.getName() + ".stickerMarks");
            createCache(cm, com.mycompany.myapp.domain.Mark.class.getName() + ".countryMarks");
            createCache(cm, com.mycompany.myapp.domain.Company.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Company.class.getName() + ".cerfCompanies");
            createCache(cm, com.mycompany.myapp.domain.Company.class.getName() + ".feeProdCerfCompanies");
            createCache(cm, com.mycompany.myapp.domain.FeeProdCerfCompany.class.getName());
            createCache(cm, com.mycompany.myapp.domain.CerfCompany.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Sticker.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Sticker.class.getName() + ".stickerMarks");
            createCache(cm, com.mycompany.myapp.domain.Sticker.class.getName() + ".prodStickers");
            createCache(cm, com.mycompany.myapp.domain.ProdCountry.class.getName());
            createCache(cm, com.mycompany.myapp.domain.ProdStd.class.getName());
            createCache(cm, com.mycompany.myapp.domain.CerfProd.class.getName());
            createCache(cm, com.mycompany.myapp.domain.CerfStd.class.getName());
            createCache(cm, com.mycompany.myapp.domain.CountryStd.class.getName());
            createCache(cm, com.mycompany.myapp.domain.CerfMark.class.getName());
            createCache(cm, com.mycompany.myapp.domain.StickerMark.class.getName());
            createCache(cm, com.mycompany.myapp.domain.CountryCert.class.getName());
            createCache(cm, com.mycompany.myapp.domain.ProdSticker.class.getName());
            createCache(cm, com.mycompany.myapp.domain.CountryMark.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Wcc412View.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Wcc421View.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
