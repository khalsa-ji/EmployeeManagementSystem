//  Waheguru Ji!

package com.khalsa_ji.ems.configuration;

import org.apache.catalina.filters.RemoteIpFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The class {@code WebConfiguration} is a <em>configuration</em> class that provides a bean for Tomcat's RemoteIpFilter
 *
 * @author Ravikiran Singh
 * @version 1.0
 * @since 1.0
 * @see RemoteIpFilter
 */

@Configuration
public class WebConfiguration {
    private final Logger logger = LoggerFactory.getLogger(WebConfiguration.class);

    /**
     * Method to generate a bean and fetch an instance of the {@code RemoteIpFilter}. It is helpful for retrieving
     * the real IP address of the client making any requests to the server. It works even in the case when application
     * runs behind a load balancer.
     *
     * <p>{@code RemoteIpFilter} - Servlet filter to integrate "X-Forwarded-For" and "X-Forwarded-Proto" HTTP headers.</p>
     *
     * @return An instance of the {@code RemoteIpFilter}
     * @see RemoteIpFilter
     */

    @Bean
    public RemoteIpFilter getRemoteIpFilter() {
        logger.info("Registering a bean for RemoteIpFilter.");
        return new RemoteIpFilter();
    }
}
