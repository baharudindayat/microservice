package com.example.gateway.constant

interface Constant {

    object Message {
        const val FORBIDDEN_MESSAGE: String = "You don't have access"
        const val INVALID_TOKEN_MESSAGE: String = "Invalid access token"
    }

    companion object {
        val AUTH_WHITELIST = arrayOf(
            "/swagger-ui/**",
            "/api-docs/**",
            "/swagger-ui.html",
            "/auth/register",
            "/auth/login",
            "/eureka",
        )
    }



}