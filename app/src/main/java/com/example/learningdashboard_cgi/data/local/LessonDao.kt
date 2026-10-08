package com.example.learningdashboard_cgi.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LessonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(lessons: List<LessonEntity>)

    @Query("SELECT * FROM lessons WHERE courseId = :courseId")
    suspend fun getLessonsForCourse(courseId: Int): List<LessonEntity>

    @Query("""
        UPDATE lessons
        SET completed = :completed
        WHERE id = :lessonId
    """)
    suspend fun updateCompletion(
        lessonId: Int,
        completed: Boolean
    )

    @Query("DELETE FROM lessons")
    suspend fun clearLessons()

    @Query("DELETE FROM lessons WHERE courseId = :courseId")
    suspend fun clearLessonsForCourse(courseId: Int)
}