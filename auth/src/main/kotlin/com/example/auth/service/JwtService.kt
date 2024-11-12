package com.example.auth.service

import org.springframework.security.core.userdetails.UserDetails

interface JwtService {

    fun generateAccessToken(username: String?): String?
    fun generateRefreshToken(username: String?): String?
    fun extractUsernameFromAccessToken(accessToken: String?): String?
    fun extractUsernameFromRefreshToken(refreshToken: String?): String?
    fun validateAccessToken(accessToken: String?, userDetails: UserDetails?): Boolean?
    fun validateRefreshToken(refreshToken: String?): Boolean?
    fun isTokenExpired(token: String?, secret: String?): Boolean?
}