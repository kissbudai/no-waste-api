package edu.ubb.micro.nowaste.proxy.security.jwt

import edu.ubb.micro.nowaste.proxy.security.AuthenticationException
import edu.ubb.micro.nowaste.proxy.security.TokenRequestFilter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class TokenReqFilterJWTImpl(private val tokenProvider: AuthTokenProviderJWTImpl) : TokenRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {

        val token = tokenProvider.resolveToken(request)

        try {
            if (token != null && tokenProvider.validateToken(token)) {
                val auth = tokenProvider.getAuthentication(token)
                SecurityContextHolder.getContext().authentication = auth
            }
        } catch (exception: AuthenticationException) {
            SecurityContextHolder.clearContext()
            throw exception
        }

        chain.doFilter(request, response)
    }
}