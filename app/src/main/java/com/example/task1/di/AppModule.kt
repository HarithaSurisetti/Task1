package com.example.task1.di


import android.app.Application
import com.example.task1.features.Data.NoteDao
import com.example.task1.features.Data.NoteDatabase
import com.example.task1.features.Data.NoteRepository
import com.example.task1.features.Domain.UseCase.AddNoteUseCase
import com.example.task1.features.Domain.UseCase.GetNoteByIdUseCase
import com.example.task1.features.Domain.UseCase.GetNotesUseCase
import com.example.task1.features.Domain.UseCase.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return NoteDatabase.getDatabase(app)
    }

    @Provides
    @Singleton
    fun provideNoteRepository(dao: NoteDao): NoteRepository {
        return NoteRepository(dao)
    }

    @Provides
    fun provideNoteDao(database: NoteDatabase): NoteDao {
        return database.noteDao()
    }


    @Provides
    @Singleton
    fun provideNotesUseCase(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            addNoteUseCase = AddNoteUseCase(repository),
            getNotesUseCase = GetNotesUseCase(repository),
            getNoteByIdUseCase = GetNoteByIdUseCase(repository)
        )
    }
}