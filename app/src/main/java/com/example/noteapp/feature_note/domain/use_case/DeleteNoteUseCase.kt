package com.example.noteapp.feature_note.domain.use_case

import com.example.noteapp.feature_note.domain.entity.Note
import com.example.noteapp.feature_note.domain.repository.NoteRepository

class DeleteNoteUseCase (
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}