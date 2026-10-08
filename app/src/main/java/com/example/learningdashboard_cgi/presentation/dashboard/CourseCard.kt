package com.example.learningdashboard_cgi.presentation.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.learningdashboard_cgi.data.model.Course

@Composable
fun CourseCard(
    course: Course,
    onContinueClick: () -> Unit
) {
    Card(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(course.name)

            Text("Instructor: ${course.instructor}")

            Spacer(modifier = Modifier.height(8.dp))

            Text("Progress: ${course.progress}%")

            Text("Lessons: ${course.lessons.size}")

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = onContinueClick) {
                Text("Continue")
            }
        }
    }
}