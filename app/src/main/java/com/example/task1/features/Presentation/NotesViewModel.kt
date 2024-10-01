package com.example.task1.PresentationLayer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task1.features.Data.NoteDao
import com.example.task1.features.Data.NoteDatabase
import com.example.task1.features.Data.NoteRepository
import com.example.task1.features.Domain.Model.Note
import com.example.task1.features.Domain.UseCase.NoteUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class NotesViewModel(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _noteList = MutableStateFlow<List<Note>>(emptyList())
    val noteList: StateFlow<List<Note>> = _noteList

    private val _noteDetail = MutableStateFlow<Note?>(null)
    val noteDetail: StateFlow<Note?> = _noteDetail

    init{
        loadNotes()
    }

    fun loadNotes() {
        viewModelScope.launch {
            noteUseCases.getNotesUseCase().collect() { notes ->
                Log.d("NotesViewModel", "Fetched notes: $notes")
                _noteList.value = notes
            }
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteUseCases.addNoteUseCase(note)
            loadNotes()
        }
    }

    fun getNoteById(id: Int): Note? {
        var note: Note? = null
        viewModelScope.launch(Dispatchers.IO) {
            note = noteUseCases.getNoteByIdUseCase(id)
        }
        return note
    }

}
