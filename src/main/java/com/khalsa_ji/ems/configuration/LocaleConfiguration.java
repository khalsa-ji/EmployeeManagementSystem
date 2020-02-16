//  Waheguru Ji!

package com.khalsa_ji.ems.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * The class {@code LocaleConfiguration} is a <em>configuration</em> class for extending the application's
 * support for internationalisation(i18n) and localisation(l10n).
 *
 * @author Ravikiran Singh
 * @version 1.0
 * @since 1.0
 */

@Configuration
public class LocaleConfiguration implements WebMvcConfigurer {
    private final Logger logger = LoggerFactory.getLogger(LocaleConfiguration.class);

    /**
     * Method to generate a Bean for {@code LocaleResolver} and set its default locale to US.
     *
     * @return An instance of {@code LocaleResolver}
     */

    @Bean
    public LocaleResolver localeResolver() {
        logger.info("Registering a bean for LocaleResolver.");
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        logger.info("Configured defaultLocale for SessionLocaleResolver as Locale.US");
        logger.debug("slr[SessionLocaleResolver] --> {}.", slr);
        return slr;
    }

    /**
     * Method to generate a bean for {@code LocaleChangeInterceptor} which sets an additional query string parameter
     * for changing the default locale.
     *
     * @return An instance of {@code LocaleChangeInterceptor}
     */

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        logger.info("Registering a bean for LocaleChangeInterceptor");
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("language");
        logger.info("Configured LocaleChangeInterceptor's paramName to language.");
        logger.debug("lci[LocaleChangeInterceptor] --> {}.", lci);
        return lci;
    }

    /**
     * Method for overriding the default addInterceptors and registers the instance of the {@code LocaleChangeInterceptor}.
     *
     * @param registry An instance of {@code InterceptorRegistry}
     */

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("addInterceptors() called.");
        logger.debug("PARAMETER registry[InterceptorRegistry] --> {}.", registry);
        registry.addInterceptor(localeChangeInterceptor());
        logger.info("localeChangeInterceptor() added as an interceptor in InterceptorRegistry.");
    }
}
