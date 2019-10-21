//  Waheguru Ji!

package com.khalsa_ji.ems.Web_Controller;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RequestTemplateController {
    @Bean
public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
