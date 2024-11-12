package com.example.auth.security

import com.example.auth.exception.UnauthorizedException
import com.example.auth.service.JwtService
import com.example.auth.service.impl.UserDetailsImpl
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException


@Component
@Slf4j
class AuthTokenFilter : OncePerRequestFilter() {

    @Autowired
    lateinit var jwtUtils: JwtService

    @Autowired
    lateinit var userServiceImpl: UserDetailsImpl


    @Autowired
    fun AuthTokenFilter(userDetailsService: UserDetailsImpl, jwtService: JwtService) {
        this.userServiceImpl = userDetailsService
        this.jwtUtils = jwtService
    }

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        var token: String? = null
        var username: String? = null

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7)

            try {
                username = jwtUtils.extractUsernameFromAccessToken(token)

                if (SecurityContextHolder.getContext().authentication == null) {
                    val userDetails: UserDetails = userServiceImpl.loadUserByUsername(username)

                    if (jwtUtils.validateAccessToken(token, userDetails) == true) {
                        val authToken = UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.authorities
                        )

                        authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                        SecurityContextHolder.getContext().authentication = authToken
                    }
                }
            } catch (e: Exception) {
                throw UnauthorizedException("Token is expired or invalid")
            }
        }

        filterChain.doFilter(request, response)
    }

}