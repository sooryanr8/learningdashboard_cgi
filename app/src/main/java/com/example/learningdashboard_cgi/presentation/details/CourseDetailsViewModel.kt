package com.example.learningdashboard_cgi.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningdashboard_cgi.data.CourseRepository
import com.example.learningdashboard_cgi.data.model.Course
import kotlinx.coroutines.launch

sealed class CourseDetailsUiState {
    object Loading : CourseDetailsUiState()

    data class Success(
        val course: Course
    ) : CourseDetailsUiState()

    data class Error(
        val message: String
    ) : CourseDetailsUiState()
}

class CourseDetailsViewModel(
    private val repository: CourseRepository
) : ViewModel() {

    var uiState by mutableStateOf<CourseDetailsUiState>(
        CourseDetailsUiState.Loading
    )
        private set

    fun getCourse(courseId: Int) {
        viewModelScope.launch {
            try {
                val course = repository.getCourseById(courseId)

                if (course != null) {
                    uiState = CourseDetailsUiState.Success(course)
                } else {
                    uiState = CourseDetailsUiState.Error(
                        "Course not found"
                    )
                }
            } catch (e: Exception) {
                uiState = CourseDetailsUiState.Error(
                    e.message ?: "Something went wrong"
                )
            }
        }
    }

    fun markLessonCompleted(lessonId: Int) {

        val currentState = uiState

        if (currentState !is CourseDetailsUiState.Success) {
            return
        }

        val course = currentState.course

        val lesson = course.lessons
            .firstOrNull { it.id == lessonId }
            ?: return

        if (lesson.completed) {
            return
        }

        viewModelScope.launch {

            try {

                repository.updateLessonCompletion(
                    lessonId = lessonId,
                    completed = true
                )

                val updatedLessons = course.lessons.map {
                    if (it.id == lessonId) {
                        it.copy(completed = true)
                    } else {
                        it
                    }
                }

                val updatedCourse = course.copy(
                    lessons = updatedLessons
                )

                uiState = CourseDetailsUiState.Success(
                    updatedCourse
                )

            } catch (e: Exception) {

                uiState = CourseDetailsUiState.Error(
                    e.message ?: "Unable to update lesson"
                )
            }
        }
    }
}