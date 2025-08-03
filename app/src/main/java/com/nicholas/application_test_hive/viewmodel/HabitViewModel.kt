package com.nicholas.application_test_hive.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicholas.application_test_hive.data.Habit
import com.nicholas.application_test_hive.repository.HabitRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HabitViewModel(private val habitRepository: HabitRepository) : ViewModel() {

    private val _habits = MutableStateFlow<List<Habit>>(emptyList())
    val habits: StateFlow<List<Habit>> = _habits

    // Helper function to get and format the current date (backwards-compatible)
    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    fun fetchHabits() {
        viewModelScope.launch {
            _habits.value = habitRepository.getHabits()
        }
    }

    fun addHabit(newHabit: Habit) {
        viewModelScope.launch {
            val addedHabit = habitRepository.addHabit(newHabit)
            if (addedHabit != null) {
                fetchHabits()
            }
        }
    }

    fun markHabitAsDone(habit: Habit) {
        viewModelScope.launch {
            val currentDate = getCurrentDate()
            val updatedHabit = habitRepository.markHabitAsDone(
                habit.id,
                habit.copy(lastCompletedDate = currentDate)
            )
            if (updatedHabit != null) {
                fetchHabits()
            }
        }
    }
}