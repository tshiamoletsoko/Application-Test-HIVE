package com.nicholas.application_test_hive

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nicholas.application_test_hive.adapter.HabitAdapter
import com.nicholas.application_test_hive.api.RetrofitClient
import com.nicholas.application_test_hive.databinding.ActivityMainBinding
import com.nicholas.application_test_hive.repository.HabitRepository
import com.nicholas.application_test_hive.viewmodel.HabitViewModel
import com.nicholas.application_test_hive.viewmodel.HabitViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: HabitViewModel
    private lateinit var habitAdapter: HabitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Initialize the ViewModel
        val habitService = RetrofitClient.habitService
        val habitRepository = HabitRepository(habitService)
        val factory = HabitViewModelFactory(habitRepository)
        viewModel = ViewModelProvider(this, factory)[HabitViewModel::class.java]

        // 2. Initialize the Adapter and RecyclerView
        habitAdapter = HabitAdapter { habit ->
            // This is the lambda that handles the "Mark as Done" click
            viewModel.markHabitAsDone(habit)
        }
        binding.habitListRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = habitAdapter
        }

        // 3. Observe the habits from the ViewModel and update the adapter
        lifecycleScope.launch {
            viewModel.habits.collectLatest { habitsList ->
                habitAdapter.submitList(habitsList)
            }
        }

        // 4. Set up the click listeners for your buttons
        binding.btnSync.setOnClickListener {
            viewModel.fetchHabits()
        }

        binding.btnAddHabit.setOnClickListener {
            val intent = Intent(this, AddHabitActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Fetch habits every time the MainActivity comes into the foreground
        viewModel.fetchHabits()
    }
}