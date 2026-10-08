package com.example.learningdashboard_cgi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.learningdashboard_cgi.presentation.navigation.AppNavigation
import com.example.learningdashboard_cgi.ui.theme.LearningDashboard_CGITheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LearningDashboard_CGITheme {
                AppNavigation()
            }
        }
    }
}