package com.openclassrooms.mddapi.config;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.source.ImmutableSecret;

/**
 * Classe Configuration  de Spring Security.
 */

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	
	
	 @Value("${jwtKey}")
	  private String jwtKey;
	 
	 @Autowired
	  private CustomUserDetailService customUserDetailService;

	 /**
	 * Configure la chaîne de filtrage de sécurité de l'application.
	 *
	 * @param http L'objet HttpSecurity à configurer.
	 * @return La chaîne de filtrage de sécurité configurée.
	 * @throws Exception si une erreur survient lors de la configuration.
	 */
	  @Bean
	  public SecurityFilterChain securityFilterChain(HttpSecurity http)
	    throws Exception {
	    return http
	      .csrf(csrf -> csrf.disable())
	      .cors(cors -> cors.disable())
	      .httpBasic(httpBasic -> httpBasic.disable())
	      .sessionManagement(session ->
	        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	      )
	      .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
	      .authorizeHttpRequests(auth ->
	        auth
	          .requestMatchers("/api/auth/**")
	          .permitAll()
	          .anyRequest()
	          .authenticated()
	      )
	      .build();
	  }

	  /**
	  * Configure le bean JwtDecoder pour le décodage des jetons JWT.
	  *
	  * @return Le JwtDecoder configuré.
	  */
	  @Bean
	  public JwtDecoder jwtDecoder() {
	    SecretKeySpec secretKey = new SecretKeySpec(
	      this.jwtKey.getBytes(),
	      0,
	      this.jwtKey.getBytes().length,
	      "RSA"
	    );
	    return NimbusJwtDecoder
	      .withSecretKey(secretKey)
	      .macAlgorithm(MacAlgorithm.HS256)
	      .build();
	  }

	  /**
	  * Configure le bean JwtEncoder pour l'encodage des jetons JWT.
	  *
	  * @return Le JwtEncoder configuré.
	  */
	  @Bean
	  public JwtEncoder jwtEncoder() {
	    return new NimbusJwtEncoder(new ImmutableSecret<>(this.jwtKey.getBytes()));
	  }

	  /**
	  * Configure le bean BCryptPasswordEncoder pour l'encodage des mots de passe.
	  *
	  * @return Le BCryptPasswordEncoder configuré.
	  */
	  @Bean
	  public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	  }

	  /**
	  * Configure le bean AuthenticationManager.
	  *
	  * @param http L'objet HttpSecurity à configurer.
	  * @param bCryptPasswordEncoder Le BCryptPasswordEncoder utilisé pour l'encodage des mots de passe.
	  * @return L'AuthenticationManager configuré.
	  * @throws Exception si une erreur survient lors de la configuration.
	  */
	  @Bean
	  public AuthenticationManager authenticationManager(
	    HttpSecurity http,
	    BCryptPasswordEncoder bCryptPasswordEncoder
	  ) throws Exception {
	    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(
	      AuthenticationManagerBuilder.class
	    );
	    authenticationManagerBuilder
	      .userDetailsService(customUserDetailService)
	      .passwordEncoder(bCryptPasswordEncoder);
	    return authenticationManagerBuilder.build();
	  }

}
