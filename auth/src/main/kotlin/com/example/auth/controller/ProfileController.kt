package com.example.auth.controller

import com.example.auth.service.ProfileService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class ProfileController(
    var profileService: ProfileService
) {

    @GetMapping("/profile")
    fun getProfile(
        @RequestHeader(value = "Authorization") token: String
    ):ResponseEntity<Any>{
        val extractToken: String = token.substring("Bearer ".length)
        return ResponseEntity.ok(profileService.getProfile(extractToken))
    }

}