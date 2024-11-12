package com.example.auth.service

import com.example.auth.dto.GlobalResponse
import com.example.auth.dto.RequestRegisterDto
import com.example.auth.dto.ResponseLoginDto

interface AuthService {

    fun login(email: String, password: String): GlobalResponse<Any>

    fun register(requestRegisterDto: RequestRegisterDto): GlobalResponse<Any>

    fun logout(refreshToken: String): GlobalResponse<Any>

    fun refreshToken(refreshToken: String): GlobalResponse<Any>

}