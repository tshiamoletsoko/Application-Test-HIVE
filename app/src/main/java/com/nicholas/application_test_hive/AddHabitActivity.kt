package com.nicholas.application_test_hive

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.nicholas.application_test_hive.api.RetrofitClient
import com.nicholas.application_test_hive.data.Habit
import com.nicholas.application_test_hive.databinding.ActivityAddHabitBinding
import com.nicholas.application_test_hive.repository.HabitRepository
import com.nicholas.application_test_hive.viewmodel.HabitViewModel
import com.nicholas.application_test_hive.viewmodel.HabitViewModelFactory

class AddHabitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddHabitBinding
    private lateinit var viewModel: HabitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddHabitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the ViewModel
        val habitService = RetrofitClient.habitService
        val habitRepository = HabitRepository(habitService)
        val factory = HabitViewModelFactory(habitRepository)
        viewModel = ViewModelProvider(this, factory)[HabitViewModel::class.java]

        binding.saveButton.setOnClickListener {
            val habitName = binding.habitNameEditText.text.toString()
            // Get text from the new date field
            val lastCompletedDate = binding.lastCompletedDateEditText.text.toString()

            // Check that both fields are not blank
            if (habitName.isNotBlank() && lastCompletedDate.isNotBlank()) {
                val newHabit = Habit(
                    id = "placeholder", // mockapi.io will replace this ID
                    name = habitName,
                    lastCompletedDate = lastCompletedDate
                )
                viewModel.addHabit(newHabit)
                finish() // Close the activity and return to the main screen
            }
        }
    }
}