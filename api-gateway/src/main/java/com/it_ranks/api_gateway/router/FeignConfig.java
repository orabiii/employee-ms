package com.it_ranks.api_gateway.router;

import feign.Client;
import feign.codec.Decoder;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class FeignConfig {
	@Bean
	public HttpMessageConverters messageConverters() {
		HttpMessageConverter<?> converter = new MappingJackson2HttpMessageConverter();
		return new HttpMessageConverters(converter);
	}

	@Bean
	public WebClient.Builder webClientBuilder() {
		return WebClient.builder();
	}
}