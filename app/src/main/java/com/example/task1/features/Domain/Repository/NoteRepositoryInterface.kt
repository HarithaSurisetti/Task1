package com.example.task1.features.Domain.Repository

import com.example.task1.features.Domain.Model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepositoryInterface {
    suspend fun insert(note: Note)
    fun getAllNotes(): Flow<List<Note>>
    suspend fun getNoteById(id: Int): Note?

}
