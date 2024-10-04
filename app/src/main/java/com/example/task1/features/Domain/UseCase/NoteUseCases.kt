package com.example.task1.features.Domain.UseCase

import com.example.task1.features.Data.NoteRepository
import com.example.task1.features.Domain.Repository.NoteRepositoryInterface
import javax.inject.Inject

data class NoteUseCases @Inject constructor(
    val getNotesUseCase: GetNotesUseCase,
    val addNoteUseCase: AddNoteUseCase,
    val getNoteByIdUseCase: GetNoteByIdUseCase
)