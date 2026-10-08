package com.example.learningdashboard_cgi.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lessons")
data class LessonEntity(
    @PrimaryKey
    val id: Int,
    val courseId: Int,
    val title: String,
    val completed: Boolean
)