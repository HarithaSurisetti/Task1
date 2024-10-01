package com.example.task1.features.Domain.UseCase

import android.util.Log
import com.example.task1.features.Domain.Model.Note
import com.example.task1.features.Domain.Repository.NoteRepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(private val repository: NoteRepositoryInterface) {
    operator fun invoke(): Flow<List<Note>> {
        return repository.getAllNotes().map { note ->
            Log.d("GetNotesUseCase", "Mapped Notes: ${note.size}")
            note.map { entity ->
                Note(entity.id, entity.title, entity.content, entity.timestamp)
            }
        }
    }
}
