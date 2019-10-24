//  Waheguru Ji!

package com.khalsa_ji.ems.configuration;

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
    /**
     * Method to generate a Bean for {@code LocaleResolver} and set its default locale to US.
     *
     * @return An instance of {@code LocaleResolver}
     */

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
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
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("language");
        return lci;
    }

    /**
     * Method for overriding the default addInterceptors and registers the instance of the {@code LocaleChangeInterceptor}.
     *
     * @param registry An instance of {@code InterceptorRegistry}
     */

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
