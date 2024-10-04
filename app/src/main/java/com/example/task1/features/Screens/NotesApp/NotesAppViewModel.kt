package com.example.task1.features.Screens.NotesApp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task1.features.Domain.Model.Note
import com.example.task1.features.Domain.UseCase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesAppViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _notes = MutableStateFlow("")
    val notes: StateFlow<String> = _notes

    private val _noteList = MutableStateFlow<List<Note>>(emptyList())
    val noteList: StateFlow<List<Note>> = _noteList

        fun loadNotes() {
            viewModelScope.launch {
                noteUseCases.getNotesUseCase().collect { notes ->
                    _noteList.value = notes
                }
            }
        }
    }


