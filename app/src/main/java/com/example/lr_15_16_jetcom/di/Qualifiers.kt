package com.example.lr_15_16_jetcom.di

import javax.inject.Qualifier

@Qualifier // [ЛР 18] Говорит Hilt, что это аннотация для различия зависимостей
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher // Например, для IO потоков

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher // Для Default потоков

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalDataSource // Для локальной базы

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteDataSource // Для сети (если будет)