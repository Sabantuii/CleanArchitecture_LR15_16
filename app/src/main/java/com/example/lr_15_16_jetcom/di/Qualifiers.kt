package com.example.lr_15_16_jetcom.di

import javax.inject.Qualifier

// [ЛР 18, Задание 2] Квалификаторы для различия одинаковых типов
// Без них Hilt не поймёт, какой именно Dispatcher или API использовать

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher  // Для IO операций (сеть, БД)

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher  // Для вычислений

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl  // Для базового URL API