package edu.ubb.micro.nowaste.proxy.config

import edu.ubb.micro.nowaste.proxy.security.TokenRequestFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.http.HttpServletResponse

@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

	@Autowired
	lateinit var tokenRequestFilter: TokenRequestFilter

	@Throws(Exception::class)
	override fun configure(http: HttpSecurity) {

		http.csrf().disable()

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

		http.headers().cacheControl()

		http.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
			.antMatchers(HttpMethod.GET, "/api/auth/health").permitAll()
			.anyRequest().authenticated()

		http.exceptionHandling().accessDeniedHandler { _, response, _ ->
			response.sendError(HttpStatus.UNAUTHORIZED.value(), "Provide a valid token")
		}

		http.exceptionHandling().authenticationEntryPoint { _, response, _ ->
			response.sendError(HttpStatus.UNAUTHORIZED.value(), "Provide a valid token")
		}

		http.addFilterBefore(tokenRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
	}
}