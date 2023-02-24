package com.example.demo;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.example.demo.SecurityInterceptor;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
@EnableCaching
public class DemoApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean // org.springframework.web.servlet.LocaleResolver
	public org.springframework.web.servlet.LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(new Locale("en"));
		return slr;
	}

	@Bean // hello?lang=en
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang"); // cấu hình params tham số gửi lên
		return lci;
	}
	
	@Bean
	JsonMessageConverter converter() {
		return new JsonMessageConverter();
	}
	
//	@Autowired
//	SecurityInterceptor securityInterceptor;
//	
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(securityInterceptor);
//	}
}
