package edu.ubb.micro.nowaste.requestmanager.security

import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
class SecurityConfig(private val tokenFilter: TokenReqFilterJWTImpl): WebSecurityConfigurerAdapter() {

	@Throws(Exception::class)
	override fun configure(http: HttpSecurity) {

		http.csrf().disable()

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

		http.headers().cacheControl()

		http.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers(HttpMethod.GET, "/requests/health").permitAll()
			.anyRequest().authenticated()

		http.exceptionHandling().accessDeniedHandler { _, response, _ ->
			response.sendError(HttpStatus.UNAUTHORIZED.value(), "Provide a valid token")
		}

		http.exceptionHandling().authenticationEntryPoint { _, response, _ ->
			response.sendError(HttpStatus.UNAUTHORIZED.value(), "Provide a valid token")
		}

		http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter::class.java)
	}
}