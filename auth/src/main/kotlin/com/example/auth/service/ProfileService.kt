package com.example.auth.service

import com.example.auth.dto.GlobalResponse

interface ProfileService {

    fun getProfile(username: String): GlobalResponse<Any>
}