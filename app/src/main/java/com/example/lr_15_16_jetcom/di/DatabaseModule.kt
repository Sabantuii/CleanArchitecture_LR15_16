package com.example.lr_15_16_jetcom.di

import android.content.Context

@Module // 1. Объявляем, что это модуль с зависимостями
@InstallIn(SingletonComponent::class) // 2. Указываем, где эти зависимости живут (во всём приложении)
object DatabaseModule {

    @Provides // 3. Говорим Hilt: "Эта функция умеет создавать объект"
    @Singleton // 4. Область видимости: один экземпляр на всё приложение
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "notes_db"
        ).build()
    }

    @Provides
    fun provideNoteDao(database: AppDatabase): NoteDao {
        return database.noteDao() // Hilt сам подставит базу данных в этот параметр
    }
}