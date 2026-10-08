package com.example.learningdashboard_cgi

import androidx.room.Room
import com.example.learningdashboard_cgi.data.AuthRepository
import com.example.learningdashboard_cgi.data.CourseRepository
import com.example.learningdashboard_cgi.data.local.AppDatabase
import com.example.learningdashboard_cgi.data.local.LessonDao
import com.example.learningdashboard_cgi.data.remote.CourseApi
import com.example.learningdashboard_cgi.presentation.dashboard.DashboardViewModel
import com.example.learningdashboard_cgi.presentation.details.CourseDetailsViewModel
import com.example.learningdashboard_cgi.presentation.login.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { CourseApi() }

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "learning_dashboard.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    single {
        get<AppDatabase>().courseDao()
    }

    single { AuthRepository() }

    single<LessonDao> {
        get<AppDatabase>().lessonDao()
    }

    single {
        CourseRepository(
            courseApi = get(),
            courseDao = get(),
            lessonDao = get()
        )
    }

    viewModel { LoginViewModel(get()) }
    viewModel { DashboardViewModel(get()) }
    viewModel { CourseDetailsViewModel(get()) }
}