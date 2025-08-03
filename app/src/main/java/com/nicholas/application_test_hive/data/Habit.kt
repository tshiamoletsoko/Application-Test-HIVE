package com.nicholas.application_test_hive.data

import com.google.gson.annotations.SerializedName

data class Habit(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("lastCompletedDate") val lastCompletedDate: String
)