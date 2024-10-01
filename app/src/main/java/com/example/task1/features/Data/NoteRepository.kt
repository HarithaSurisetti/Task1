package com.example.task1.features.Data

import android.util.Log
import com.example.task1.features.Domain.Model.Note
import com.example.task1.features.Domain.Repository.NoteRepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDao: NoteDao) : NoteRepositoryInterface {
    override suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
            .onEach { notes ->
                Log.d("NoteRepository", "Fetched Notes: ${notes.size}")
            }
    }

    override suspend fun getNoteById(id: Int): Note? {
        return noteDao.getNoteById(id)
    }
}

