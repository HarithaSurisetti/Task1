package com.example.task1.features.Domain.UseCase

data class NoteUseCases(
    val getNotesUseCase: GetNotesUseCase,
    val addNoteUseCase: AddNoteUseCase,
    val getNoteByIdUseCase: GetNoteByIdUseCase
)