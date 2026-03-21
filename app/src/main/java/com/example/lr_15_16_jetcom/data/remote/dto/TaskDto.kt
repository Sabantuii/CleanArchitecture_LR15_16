package com.example.lr_15_16_jetcom.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TaskDto(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("is_completed") val isCompleted: Boolean,
    @SerializedName("created_at") val createdAt: Long
)