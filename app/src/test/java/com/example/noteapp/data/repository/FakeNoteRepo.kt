package com.example.noteapp.data.repository

import com.example.noteapp.feature_note.domain.entity.Note
import com.example.noteapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeNoteRepo : NoteRepository {

    var notes = mutableListOf<Note>()


    override fun getNotes(): Flow<List<Note>> {
        return flow {
            emit(notes)
        }
    }

    override suspend fun getNoteById(id: Int): Note? {
        return notes.find { it.id==id }
    }

    override suspend fun insertNote(note: Note) {
        notes.add(note)
    }

    override suspend fun deleteNote(note: Note) {
        notes.remove(note)
    }
}