package com.shikamarubh.note.data

import com.shikamarubh.note.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NoteRepository
    @Inject constructor(private val noteDao: NoteDao) {
    fun getAllNotes(): Flow<List<Note>> = noteDao.getNotes().flowOn(Dispatchers.IO)
    suspend fun addNote(note: Note) = noteDao.insert(note)
    suspend fun updateNote(note: Note) = noteDao.update(note)
    suspend fun deleteNote(note: Note) = noteDao.delete(note)
    suspend fun deleteAllNote() = noteDao.deleteAll()
}