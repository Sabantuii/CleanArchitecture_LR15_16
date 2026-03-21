package com.example.lr_15_16_jetcom.data.remote.api

import com.example.lr_15_16_jetcom.data.remote.dto.TaskDto
import retrofit2.http.*

interface TaskApi {
    @GET("todos")
    suspend fun getTasks(): List<TaskDto>

    @POST("todos")
    suspend fun addTask(@Body task: TaskDto): TaskDto
}