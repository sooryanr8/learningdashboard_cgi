package com.example.learningdashboard_cgi

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class LearningDashboardApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@LearningDashboardApplication)
            modules(appModule)
        }
    }
}