package com.example.lr_15_16_jetcom.data.remote.mapper

import com.example.lr_15_16_jetcom.data.remote.dto.TaskDto
import com.example.lr_15_16_jetcom.domain.model.Task

fun TaskDto.toDomain(): Task = Task(
    id = id,
    title = title,
    isCompleted = isCompleted,
    createdAt = createdAt
)

fun Task.toDto(): TaskDto = TaskDto(
    id = id,
    title = title,
    isCompleted = isCompleted,
    createdAt = createdAt
)