package com.example.noteapp.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noteapp.feature_note.domain.util.NoteOrder
import com.example.noteapp.feature_note.domain.util.OrderType
import com.example.noteapp.feature_note.presentation.notes.NotesState

@Composable
fun OrderSelection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChanged: (NoteOrder) -> Unit
) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(text = "Title",
                selected = noteOrder is NoteOrder.Title,
                onSelect = { onOrderChanged(NoteOrder.Title(noteOrder.orderType)) })
            DefaultRadioButton(text = "Date",
                selected = noteOrder is NoteOrder.Date,
                onSelect = { onOrderChanged(NoteOrder.Date(noteOrder.orderType)) })
            DefaultRadioButton(text = "Color",
                selected = noteOrder is NoteOrder.Color,
                onSelect = { onOrderChanged(NoteOrder.Color(noteOrder.orderType)) })
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(text = "Ascending",
                selected = noteOrder.orderType is OrderType.Ascending,
                onSelect = { onOrderChanged(noteOrder.updateOrderType(OrderType.Ascending)) })
            DefaultRadioButton(text = "Descending",
                selected = noteOrder.orderType is OrderType.Descending,
                onSelect = { onOrderChanged(noteOrder.updateOrderType(OrderType.Descending)) })
        }

    }
}

@Preview
@Composable
fun ComposablePreview() {
    OrderSelection(){
        print("")
    }
}