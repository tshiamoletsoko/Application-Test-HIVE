package com.nicholas.application_test_hive.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nicholas.application_test_hive.data.Habit
import com.nicholas.application_test_hive.databinding.ItemHabitBinding

class HabitAdapter(
    private val onMarkAsDoneClicked: (Habit) -> Unit
) : ListAdapter<Habit, HabitAdapter.HabitViewHolder>(HabitDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = ItemHabitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = getItem(position)
        holder.bind(habit)
    }

    inner class HabitViewHolder(private val binding: ItemHabitBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(habit: Habit) {
            Log.d("HabitAdapter", "Binding habit: ${habit.name} with date: ${habit.lastCompletedDate}")
            binding.habitName.text = habit.name

            binding.lastCompletedDate.text = habit.lastCompletedDate

            binding.markAsDoneButton.setOnClickListener {
                onMarkAsDoneClicked(habit)
            }
        }
    }
}

class HabitDiffCallback : DiffUtil.ItemCallback<Habit>() {
    override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean {
        return oldItem == newItem
    }
}