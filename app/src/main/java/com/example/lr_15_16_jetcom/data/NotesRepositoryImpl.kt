package com.example.lr_15_16_jetcom.data

import kotlinx.coroutines.flow.Flow

class NotesRepositoryImpl(
    private val noteDao: NoteDao
    // [ЛР 18] Можно добавить: private val remoteDataSource: RemoteDataSource
) : NotesRepository {

    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return noteDao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    override suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }
}