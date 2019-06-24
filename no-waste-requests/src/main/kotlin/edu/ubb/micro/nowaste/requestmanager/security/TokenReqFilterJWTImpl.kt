package edu.ubb.micro.nowaste.requestmanager.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.naming.AuthenticationException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class TokenReqFilterJWTImpl(private val authorizationService: AuthorizationService) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {

        val token = authorizationService.resolveToken(request)

        try {
            if (token != null && authorizationService.validateToken(token)) {
                val auth = authorizationService.getAuthentication(token)
                SecurityContextHolder.getContext().authentication = auth
            }
        } catch (exception: AuthenticationException) {
            SecurityContextHolder.clearContext()
            throw exception
        }

        chain.doFilter(request, response)
    }
}