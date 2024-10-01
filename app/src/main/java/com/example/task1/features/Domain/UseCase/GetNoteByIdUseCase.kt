package com.example.task1.features.Domain.UseCase

import com.example.task1.features.Data.NoteRepository
import com.example.task1.features.Domain.Model.Note
import com.example.task1.features.Domain.Repository.NoteRepositoryInterface
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(private val repository: NoteRepositoryInterface)  {
    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}