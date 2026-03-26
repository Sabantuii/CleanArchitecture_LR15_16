package com.example.lr_15_16_jetcom.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lr_15_16_jetcom.data.Note
import com.example.lr_15_16_jetcom.data.NotesRepository
import com.example.lr_15_16_jetcom.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

// [ЛР 17] @HiltViewModel - Hilt управляет жизненным циклом ViewModel
@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: NotesRepository,

    // [ЛР 18] Внедряем Dispatcher с квалификатором
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    val notes: Flow<List<Note>> = repository.getAllNotes()

    fun addNote(title: String, content: String) {
        viewModelScope.launch(ioDispatcher) {
            repository.insertNote(Note(title = title, content = content))
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch(ioDispatcher) {
            repository.updateNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(ioDispatcher) {
            repository.deleteNote(note)
        }
    }
}