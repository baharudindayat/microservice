package com.example.store.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/store")
class StoreController {

    @GetMapping("/get")
    fun getStore(): String {
        return "Store"
    }

}