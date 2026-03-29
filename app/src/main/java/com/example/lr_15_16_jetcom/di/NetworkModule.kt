package com.example.lr_15_16_jetcom.di

import com.example.lr_15_16_jetcom.data.remote.api.TaskApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// [ЛР 17, Задание 3] Модуль для создания сетевых зависимостей
@Module
@InstallIn(SingletonComponent::class)  // Живёт всё время приложения
object NetworkModule {

    // [ЛР 18, Задание 3] Выносим baseUrl в DI
    @Provides
    @Singleton
    @BaseUrl
    fun provideBaseUrl(): String {
        return "https://jsonplaceholder.typicode.com/"
    }

    // Создаем Gson с кастомными настройками (если нужно)
    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        val gson = GsonBuilder()
            .setLenient()  // Разрешаем нестрогий JSON
            .create()
        return GsonConverterFactory.create(gson)
    }

    // [ЛР 17] Создаем Retrofit
    @Provides
    @Singleton
    fun provideRetrofit(
        @BaseUrl baseUrl: String,  // [ЛР 18] Внедряем baseUrl
        converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .build()
    }

    // Создаем TaskApi
    @Provides
    @Singleton
    fun provideTaskApi(retrofit: Retrofit): TaskApi {
        return retrofit.create(TaskApi::class.java)
    }
}