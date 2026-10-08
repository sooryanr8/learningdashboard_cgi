package com.example.learningdashboard_cgi.presentation.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningdashboard_cgi.data.CourseRepository
import com.example.learningdashboard_cgi.data.model.Course
import kotlinx.coroutines.launch

sealed class DashboardUiState{
    object Idle : DashboardUiState()
    object Loading : DashboardUiState()
    data class Success(val courses : List<Course>) : DashboardUiState()
    data class Error(val message : String) : DashboardUiState()
}

class DashboardViewModel(private val repository: CourseRepository) : ViewModel()
{
    var uiState by mutableStateOf<DashboardUiState>(DashboardUiState.Idle)
        private set

    fun getCourses()
    {
        viewModelScope.launch {
            uiState = DashboardUiState.Loading

            try {
                val courses = repository.getCourses()
                uiState = DashboardUiState.Success(courses)
            } catch (e:  Exception)
            {
                uiState = DashboardUiState.Error(e.message ?: "Something went wrong")
            }
        }
    }
}