package com.nicholas.application_test_hive.api

import com.nicholas.application_test_hive.data.Habit
import retrofit2.Response
import retrofit2.http.*

interface HabitService {

    @GET("habits")
    suspend fun getHabits(): Response<List<Habit>>

    @POST("habits")
    suspend fun addHabit(@Body newHabit: Habit): Response<Habit>

    @PUT("habits/{id}")
    suspend fun markHabitAsDone(
        @Path("id") id: String,
        @Body updatedHabit: Habit
    ): Response<Habit>
}