package com.example.learningdashboard_cgi.data.remote

import com.example.learningdashboard_cgi.data.model.Course
import com.example.learningdashboard_cgi.data.model.Lesson
import kotlinx.coroutines.delay

class CourseApi {

    suspend fun getCourses(): List<Course> {
        delay(1000)

        return listOf(
            Course(
                id = 1,
                name = "Android Development",
                instructor = "John Smith",
                lessons = listOf(
                    Lesson(1, "Introduction to Android", true),
                    Lesson(2, "Activities & Fragments", true),
                    Lesson(3, "Jetpack Compose", false),
                    Lesson(4, "Room Database", false),
                    Lesson(5, "Coroutines", false)
                )
            ),
            Course(
                id = 2,
                name = "Kotlin Fundamentals",
                instructor = "Sarah Johnson",
                lessons = listOf(
                    Lesson(6, "Variables & Data Types", true),
                    Lesson(7, "Functions", true),
                    Lesson(8, "Classes & Objects", false),
                    Lesson(9, "Collections", false),
                    Lesson(10, "Coroutines", false)
                )
            ),
            Course(
                id = 3,
                name = "Jetpack Compose",
                instructor = "David Wilson",
                lessons = listOf(
                    Lesson(11, "Composable Functions", true),
                    Lesson(12, "Layouts", true),
                    Lesson(13, "State Management", true),
                    Lesson(14, "Lists", true),
                    Lesson(15, "Navigation", false)
                )
            )
        )
    }
}