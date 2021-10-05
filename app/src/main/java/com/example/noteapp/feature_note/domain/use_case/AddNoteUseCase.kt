package com.example.noteapp.feature_note.domain.use_case

import com.example.noteapp.feature_note.domain.entity.Note
import com.example.noteapp.feature_note.domain.repository.NoteRepository

class AddNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw Exception("The title of the note can't be empty.")
        }
        if (note.content.isBlank()) {
            throw Exception("The content of the note can't be empty.")
        }
        repository.insertNote(note)
    }
}