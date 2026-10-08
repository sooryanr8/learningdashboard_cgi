package com.example.learningdashboard_cgi.data

import kotlinx.coroutines.delay

class AuthRepository{
    suspend fun login(email: String, password: String): Boolean{
        delay(1500)
        return email == "test@gmail.com" &&
                password == "123456"
    }
}