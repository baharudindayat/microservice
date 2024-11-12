package com.example.auth.constant


interface Constant {
    object Response {
        const val SUCCESS_CODE = 200
        const val SUCCESS_MESSAGE = "Success"
        const val SUCCESS_VALID_TOKEN_MESSAGE = "Access token valid"
    }

    object Message {
        const val EXIST_DATA_MESSAGE = "data already exist"
        const val NOT_FOUND_DATA_MESSAGE = "data not found"
        const val FORBIDDEN_REQUEST_MESSAGE = "Different {value} with exist data is forbidden"
        const val INVALID_LOGIN_MESSAGE = "Username / Password wrong"
        const val INVALID_TOKEN_MESSAGE = "Invalid access token"
    }

    object Regex {
        const val NUMERIC = "\\d+"
        const val ALPHANUMERIC = "^[a-zA-Z0-9]+$"
        const val ALPHABET = "^[a-zA-Z]+$"
        const val ALPHANUMERIC_WITH_DOT_AND_SPACE = "^[a-zA-Z0-9.' ]+$"
    }

    companion object {
        val AUTH_WHITELIST = arrayOf(
            "/swagger-ui/**",
            "/api-docs/**",
            "/swagger-ui.html",
            "/auth/register",
            "/auth/login",
            "/auth/validateAccessToken",
        )
    }
}
