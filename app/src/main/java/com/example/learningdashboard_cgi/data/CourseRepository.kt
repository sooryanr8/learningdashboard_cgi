package com.example.learningdashboard_cgi.data

import com.example.learningdashboard_cgi.data.local.CourseDao
import com.example.learningdashboard_cgi.data.local.CourseEntity
import com.example.learningdashboard_cgi.data.local.LessonDao
import com.example.learningdashboard_cgi.data.local.LessonEntity
import com.example.learningdashboard_cgi.data.model.Course
import com.example.learningdashboard_cgi.data.model.Lesson
import com.example.learningdashboard_cgi.data.remote.CourseApi

class CourseRepository(
    private val courseApi: CourseApi,
    private val courseDao: CourseDao,
    private val lessonDao: LessonDao
) {

    private var simulateNetworkError = false

    suspend fun getCourses(): List<Course> {
        return try {

            if (simulateNetworkError) {
                throw Exception("Network error")
            }

            val courses = courseApi.getCourses()

            /*
             * Preserve lesson completion status from the local database.
             * This prevents a fresh API call from resetting the user's
             * locally completed lessons.
             */
            val mergedCourses = courses.map { course ->

                val cachedLessons =
                    lessonDao.getLessonsForCourse(course.id)

                val cachedById = cachedLessons.associateBy { it.id }

                course.copy(
                    lessons = course.lessons.map { lesson ->
                        lesson.copy(
                            completed =
                                cachedById[lesson.id]?.completed
                                    ?: lesson.completed
                        )
                    }
                )
            }

            /*
             * Store courses locally.
             */
            courseDao.insertAll(
                mergedCourses.map { course ->
                    CourseEntity(
                        id = course.id,
                        name = course.name,
                        instructor = course.instructor
                    )
                }
            )

            /*
             * Store individual lessons locally.
             */
            mergedCourses.forEach { course ->

                lessonDao.clearLessonsForCourse(course.id)

                lessonDao.insertAll(
                    course.lessons.map { lesson ->
                        LessonEntity(
                            id = lesson.id,
                            courseId = course.id,
                            title = lesson.title,
                            completed = lesson.completed
                        )
                    }
                )
            }

            mergedCourses

        } catch (e: Exception) {

            /*
             * API failed → load cached data.
             */
            val cachedCourses = courseDao.getAllCourses()

            if (cachedCourses.isEmpty()) {
                throw Exception("Unable to load courses")
            }

            cachedCourses.map { entity ->

                val lessons =
                    lessonDao.getLessonsForCourse(entity.id)

                Course(
                    id = entity.id,
                    name = entity.name,
                    instructor = entity.instructor,
                    lessons = lessons.map { lesson ->
                        Lesson(
                            id = lesson.id,
                            title = lesson.title,
                            completed = lesson.completed
                        )
                    }
                )
            }
        }
    }

    suspend fun getCourseById(id: Int): Course? {

        val courseEntity = courseDao.getCourseById(id)
            ?: return null

        val lessons =
            lessonDao.getLessonsForCourse(id)

        return Course(
            id = courseEntity.id,
            name = courseEntity.name,
            instructor = courseEntity.instructor,
            lessons = lessons.map { lesson ->
                Lesson(
                    id = lesson.id,
                    title = lesson.title,
                    completed = lesson.completed
                )
            }
        )
    }

    suspend fun updateLessonCompletion(
        lessonId: Int,
        completed: Boolean
    ) {
        lessonDao.updateCompletion(
            lessonId = lessonId,
            completed = completed
        )
    }
}