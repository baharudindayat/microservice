package com.example.auth.dto

data class ResponseLoginDto(

     var userId: String,

     val username: String,

     val accessToken: String,

     val refreshToken: String,

     val tokenType: String,

     val expiresIn: Int,

     val role: String?

)