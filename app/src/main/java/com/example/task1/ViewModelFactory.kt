package com.example.task1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.task1.PresentationLayer.NotesViewModel
import com.example.task1.features.Data.NoteRepository
import com.example.task1.features.Domain.UseCase.NoteUseCases

class NotesViewModelFactory(private val noteUseCases: NoteUseCases) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesViewModel(noteUseCases) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
