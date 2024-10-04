package com.example.task1.features.Data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.task1.features.Domain.Model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Query("SELECT * FROM note ORDER BY timestamp DESC") // Ensure table name matches entity
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id = :noteId LIMIT 1")
    suspend fun getNoteById(noteId: Int): Note
}

