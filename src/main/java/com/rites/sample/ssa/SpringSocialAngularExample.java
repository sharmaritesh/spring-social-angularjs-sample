package com.rites.sample.ssa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.config.annotation.EnableSocial;

@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties
@EnableZuulProxy
@EnableSocial
@ComponentScan(basePackages = {"com.rites.sample.ssa"})
public class SpringSocialAngularExample {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringSocialAngularExample.class, args);
	}
}
