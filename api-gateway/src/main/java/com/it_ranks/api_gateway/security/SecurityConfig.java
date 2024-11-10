package com.it_ranks.api_gateway.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.sql.DataSource;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		http
				.csrf(ServerHttpSecurity.CsrfSpec::disable)
				.authorizeExchange(exchange -> exchange
						.pathMatchers("/swagger").permitAll()
						.pathMatchers("/authenticate").permitAll()
						.anyExchange().authenticated()
				)
				.httpBasic(withDefaults())
				.oauth2ResourceServer(oauth2 -> oauth2
						.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())) // JWT for other endpoints
				);

		return http.build();
	}
	@Bean
	public ReactiveAuthenticationManager authenticationManager(ReactiveUserDetailsService userDetailsService) {
		return new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
	}
	@Bean
	public ReactiveUserDetailsService userDetailsService() {
		return username -> {
			UserDetailsManager userDetailsManager = createUsers();
			UserDetails userDetails = userDetailsManager.loadUserByUsername(username);
			return userDetails != null ? Mono.just(userDetails) : Mono.empty();
		};
	}

	private UserDetailsManager createUsers() {
		return new InMemoryUserDetailsManager(
				User.withUsername("user")
				.password("{noop}password")
				.roles("USER")
				.build(),
		User.withUsername("admin")
				.password("{noop}password")
				.roles("ADMIN")
				.build()
		);
	}
	/*private UserDetailsManager createUsers() {
		var user = User.withUsername("user")
				//.password("{noop}password")
				.password("dummy")
				.roles("USER")
				.build();
		var user2 = User.withUsername("admin")
				.password("dummy")
				.roles("ADMIN")
				.build();
		JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
		manager.createUser(user);
		manager.createUser(user2);
		return manager;
	}*/
	private Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthenticationConverter() {
		JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
		return jwt -> Mono.just(converter.convert(jwt));
	}
	@Bean
	public KeyPair keyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(2048);
		return keyPairGenerator.generateKeyPair();
	}
	@Bean
	public RSAKey rsaKey(KeyPair keyPair) throws NoSuchAlgorithmException {
		return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
				.privateKey(keyPair.getPrivate()) // Optionally include the private key
				.keyID(UUID.randomUUID().toString())
				.build();
	}

	@Bean
	public JWKSource<SecurityContext> jwkSource(RSAKey rsaKey) {
		JWKSet jwkSet = new JWKSet(rsaKey);
		return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
	}

	@Bean
	public ReactiveJwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
		return NimbusReactiveJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
	}
	@Bean
	public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
		return new NimbusJwtEncoder(jwkSource);
	}

	/*@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION).build();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}*/
}
