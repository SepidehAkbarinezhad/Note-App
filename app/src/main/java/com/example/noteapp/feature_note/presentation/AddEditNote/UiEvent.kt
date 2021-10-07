package com.example.noteapp.feature_note.presentation.AddEditNote

sealed class UiEvent {
    data class ShowSnackbar(val message: String): UiEvent()
    object SaveNote: UiEvent()
}
