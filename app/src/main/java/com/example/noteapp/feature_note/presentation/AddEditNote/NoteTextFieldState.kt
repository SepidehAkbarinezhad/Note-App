package com.example.noteapp.feature_note.presentation.AddEditNote

data class NoteTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)
