package com.example.learningdashboard_cgi.data.model

data class Lesson(
    val id: Int,
    val title: String,
    val completed: Boolean = false
)