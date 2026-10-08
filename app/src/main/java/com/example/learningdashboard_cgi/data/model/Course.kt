package com.example.learningdashboard_cgi.data.model

data class Course(
    val id: Int,
    val name: String,
    val instructor: String,
    val lessons: List<Lesson>
) {
    val progress: Int
        get() {
            if (lessons.isEmpty()) return 0

            return (
                    lessons.count { it.completed }.toFloat() /
                            lessons.size * 100
                    ).toInt()
        }
}