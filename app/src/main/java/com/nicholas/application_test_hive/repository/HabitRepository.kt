package com.nicholas.application_test_hive.repository

import com.nicholas.application_test_hive.api.HabitService
import com.nicholas.application_test_hive.data.Habit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HabitRepository(private val habitService: HabitService) {

    suspend fun getHabits(): List<Habit> {
        return withContext(Dispatchers.IO) {
            val response = habitService.getHabits()
            if (response.isSuccessful && response.body() != null) {
                response.body()!!
            } else {
                emptyList()
            }
        }
    }

    suspend fun addHabit(newHabit: Habit): Habit? {
        return withContext(Dispatchers.IO) {
            val response = habitService.addHabit(newHabit)
            if (response.isSuccessful && response.body() != null) {
                response.body()
            } else {
                null
            }
        }
    }

    suspend fun markHabitAsDone(id: String, updatedHabit: Habit): Habit? {
        return withContext(Dispatchers.IO) {
            val response = habitService.markHabitAsDone(id, updatedHabit)
            if (response.isSuccessful && response.body() != null) {
                response.body()
            } else {
                null
            }
        }
    }
}