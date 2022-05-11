package com.shikamarubh.note.data

import androidx.room.*
import com.shikamarubh.note.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("select * from note")
    fun getNotes(): Flow<List<Note>>

    @Query("select * from note where id=:id")
    suspend fun getNoteById(id: String): Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(note: Note)

    @Query("delete from note")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(note: Note)
}