package com.shikamarubh.note.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shikamarubh.note.data.NoteRepository
import com.shikamarubh.note.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel
    @Inject constructor(private val noteRepository: NoteRepository) : ViewModel() {
    private val _noteList = MutableStateFlow<List<Note>>(emptyList())
    val noteList = _noteList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.getAllNotes().distinctUntilChanged()
                .collect {
                    listOfNotes ->
                        if (listOfNotes.isNullOrEmpty())
                            Log.d("DEBUG", "No notes")
                        else
                            _noteList.value = listOfNotes
                }
        }
    }
    fun addNotes(note: Note) = viewModelScope.launch { noteRepository.addNote(note) }
    fun updateNotes(note: Note) = viewModelScope.launch { noteRepository.updateNote(note) }
    fun removeNotes(note: Note) = viewModelScope.launch { noteRepository.deleteNote(note) }
    fun removeAllNotes() = viewModelScope.launch { noteRepository.deleteAllNote() }
}