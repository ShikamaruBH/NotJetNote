package com.shikamarubh.note.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shikamarubh.note.model.Note
import com.shikamarubh.note.viewmodel.Converters

@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)

abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}