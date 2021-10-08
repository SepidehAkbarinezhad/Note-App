package com.example.noteapp.feature_note.domain.use_case

import com.example.noteapp.data.repository.FakeNoteRepo
import com.example.noteapp.feature_note.domain.entity.Note
import com.example.noteapp.feature_note.domain.repository.NoteRepository
import com.example.noteapp.feature_note.domain.util.NoteOrder
import com.example.noteapp.feature_note.domain.util.OrderType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetNotesUseCaseTest {

    private lateinit var getNotesUseCase: GetNotesUseCase
    private lateinit var fakeNoteRepo: NoteRepository

    @Before
    fun setup() {
        fakeNoteRepo = FakeNoteRepo()
        getNotesUseCase = GetNotesUseCase(fakeNoteRepo)

        val notesToInsert = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, c ->
            notesToInsert.add(
                Note(
                    title = c.toString(),
                    content = c.toString(),
                    timestamp = index.toLong(),
                    color = index
                )
            )
        }

        notesToInsert.shuffle()
        runBlocking {
            notesToInsert.forEach {
                fakeNoteRepo.insertNote(it)
            }
        }

    }

    @Test
    fun `order notes by title ascending , correct order`() {
        runBlocking {
            val notes = getNotesUseCase(NoteOrder.Title(OrderType.Ascending)).first()
            for (i in 0..notes.size - 2) {
                assertThat(notes[i].title).isLessThan(notes[i + 1].title)

            }
        }
    }

    @Test
    fun `order notes by title descending , correct order`() {
        runBlocking {
            val notes = getNotesUseCase(NoteOrder.Title(OrderType.Descending)).first()
            for (i in 0..notes.size - 2) {
                assertThat(notes[i].title).isGreaterThan(notes[i + 1].title)
            }
        }
    }

    @Test
    fun `order notes by date ascending , correct order`() {
        runBlocking {
            val notes = getNotesUseCase(NoteOrder.Date(OrderType.Ascending)).first()
            for (i in 0..notes.size - 2) {
                assertThat(notes[i].timestamp).isLessThan(notes[i + 1].timestamp)

            }
        }
    }

    @Test
    fun `order notes by date descending , correct order`() {
        runBlocking {
            val notes = getNotesUseCase(NoteOrder.Date(OrderType.Descending)).first()
            for (i in 0..notes.size - 2) {
                assertThat(notes[i].timestamp).isGreaterThan(notes[i + 1].timestamp)
            }
        }
    }

    @Test
    fun `order notes by color ascending , correct order`() {
        runBlocking {
            val notes = getNotesUseCase(NoteOrder.Color(OrderType.Ascending)).first()
            for (i in 0..notes.size - 2) {
                assertThat(notes[i].color).isLessThan(notes[i + 1].color)

            }
        }
    }

    @Test
    fun `order notes by color descending , correct order`() {
        runBlocking {
            val notes = getNotesUseCase(NoteOrder.Color(OrderType.Descending)).first()
            for (i in 0..notes.size - 2) {
                assertThat(notes[i].color).isGreaterThan(notes[i + 1].color)
            }
        }
    }
}