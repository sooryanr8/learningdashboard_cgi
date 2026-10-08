package com.example.learningdashboard_cgi.data

import com.example.learningdashboard_cgi.data.local.CourseDao
import com.example.learningdashboard_cgi.data.local.CourseEntity
import com.example.learningdashboard_cgi.data.local.LessonDao
import com.example.learningdashboard_cgi.data.local.LessonEntity
import com.example.learningdashboard_cgi.data.remote.CourseApi
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class CourseRepositoryTest {

    @Test
    fun `offline returns cached courses with lessons`() = runTest {

        val courseApi = mockk<CourseApi>()
        val courseDao = mockk<CourseDao>()
        val lessonDao = mockk<LessonDao>()

        val cachedCourses = listOf(
            CourseEntity(
                id = 1,
                name = "Android Development",
                instructor = "John Smith"
            )
        )

        val cachedLessons = listOf(
            LessonEntity(
                id = 1,
                courseId = 1,
                title = "Introduction to Android",
                completed = true
            ),
            LessonEntity(
                id = 2,
                courseId = 1,
                title = "Jetpack Compose",
                completed = false
            ),
            LessonEntity(
                id = 3,
                courseId = 1,
                title = "Room Database",
                completed = false
            )
        )

        coEvery {
            courseApi.getCourses()
        } throws Exception("Network error")

        coEvery {
            courseDao.getAllCourses()
        } returns cachedCourses

        coEvery {
            lessonDao.getLessonsForCourse(1)
        } returns cachedLessons

        val repository = CourseRepository(
            courseApi = courseApi,
            courseDao = courseDao,
            lessonDao = lessonDao
        )

        val result = repository.getCourses()

        assertEquals(1, result.size)

        val course = result.first()

        assertEquals("Android Development", course.name)
        assertEquals("John Smith", course.instructor)

        assertEquals(3, course.lessons.size)
        assertEquals(true, course.lessons[0].completed)
        assertEquals(false, course.lessons[1].completed)

        // 1 completed lesson out of 3 = 33%
        assertEquals(33, course.progress)
    }
}