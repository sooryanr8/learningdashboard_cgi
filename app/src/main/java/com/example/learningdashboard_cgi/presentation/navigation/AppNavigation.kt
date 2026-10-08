package com.example.learningdashboard_cgi.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.koin.androidx.compose.koinViewModel
import com.example.learningdashboard_cgi.presentation.login.LoginScreen
import com.example.learningdashboard_cgi.presentation.login.LoginViewModel
import com.example.learningdashboard_cgi.presentation.dashboard.DashboardScreen
import com.example.learningdashboard_cgi.presentation.dashboard.DashboardViewModel
import com.example.learningdashboard_cgi.presentation.details.CourseDetailsScreen
import com.example.learningdashboard_cgi.presentation.details.CourseDetailsViewModel

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // Login
        composable("login") {

            val viewModel: LoginViewModel = koinViewModel()

            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = {
                    navController.navigate("dashboard") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // Dashboard
        composable("dashboard") {

            val viewModel: DashboardViewModel = koinViewModel()

            DashboardScreen(
                viewModel = viewModel,
                onCourseClick = { courseId ->
                    navController.navigate(
                        "course_details/$courseId"
                    )
                }
            )
        }

        // Course Details
        composable(
            route = "course_details/{courseId}",
            arguments = listOf(
                navArgument("courseId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val courseId =
                backStackEntry.arguments?.getInt("courseId")

            val viewModel: CourseDetailsViewModel = koinViewModel()

            if (courseId != null) {

                CourseDetailsScreen(
                    courseId = courseId,
                    viewModel = viewModel
                )

            } else {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Invalid course")
                }
            }
        }
    }
}