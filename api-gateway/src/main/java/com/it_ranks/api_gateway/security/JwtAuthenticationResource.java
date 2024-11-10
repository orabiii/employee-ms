package com.it_ranks.api_gateway.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Instant;

@RestController
public class JwtAuthenticationResource {
	private final JwtEncoder jwtEncoder;

	public JwtAuthenticationResource(JwtEncoder jwtEncoder) {
		this.jwtEncoder = jwtEncoder;
	}
	@PostMapping("/authenticate")
	public Mono<String> authenticate(Authentication authentication) {
		if (authentication == null || !authentication.isAuthenticated()) {
			throw new IllegalStateException("Authentication is not available : Check your credentials");
		}
		Instant now = Instant.now();
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("self")
				.issuedAt(now)
				.expiresAt(Instant.now().plusSeconds(60*60))
				.subject(authentication.getName())
				.build();

		return Mono.just(jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue());

	}
	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication error: " + ex.getMessage());
	}
}
