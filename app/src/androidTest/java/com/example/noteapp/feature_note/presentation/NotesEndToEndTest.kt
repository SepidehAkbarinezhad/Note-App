package com.example.noteapp.feature_note.presentation

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.test.core.app.ApplicationProvider
import com.example.noteapp.R
import com.example.noteapp.core.util.TestTag
import com.example.noteapp.di.AppModule
import com.example.noteapp.feature_note.presentation.AddEditNote.AddEditNoteScreen
import com.example.noteapp.feature_note.presentation.notes.NotesScreen
import com.example.noteapp.feature_note.presentation.util.Screen
import com.example.noteapp.ui.theme.NoteAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalAnimationApi
@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesEndToEndTest {

    val context = ApplicationProvider.getApplicationContext<Context>()

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)


    @get:Rule(order = 0)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        composeRule.setContent {
            NoteAppTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NotesScreen.route
                    ) {
                        composable(route = Screen.NotesScreen.route) {
                            NotesScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditNoteScreen.route +
                                    "?noteId={noteId}&noteColor={noteColor}",
                            arguments = listOf(
                                navArgument(
                                    name = "noteId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "noteColor"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                            )
                        ) {
                            val color = it.arguments?.getInt("noteColor") ?: -1
                            AddEditNoteScreen(
                                navController = navController,
                                noteColor = color
                            )
                        }
                    }
                }
            }
        }
    }

    @Test
    fun addNewNote_editAfterward() {

        //click on fab to get to AddNoteScreen
        composeRule.onNodeWithContentDescription(context.getString(R.string.add_note))
            .performClick()

        //enter texts in title and content textFields
        composeRule
            .onNodeWithTag(TestTag.TITLE_TEXT_FIELD)
            .performTextInput("test title")
        composeRule
            .onNodeWithTag(TestTag.Content_TEXT_FIELD)
            .performTextInput("test content")
        //save the new note
        composeRule
            .onNodeWithContentDescription(context.getString(R.string.save_note))
            .performClick()

        //make sure the note is added to list
        composeRule.onNodeWithText("test title").assertIsDisplayed()
        //click on note to edit it
        composeRule.onNodeWithText("test title").performClick()

        //make sure selected note is shown in title and content
        composeRule
            .onNodeWithTag(TestTag.TITLE_TEXT_FIELD)
            .assertTextEquals("test title")
        composeRule
            .onNodeWithTag(TestTag.Content_TEXT_FIELD)
            .assertTextEquals("test content")
        //add "_edited" to the content textField
        composeRule
            .onNodeWithTag(TestTag.Content_TEXT_FIELD)
            .performTextInput("_edited")
        //update the note
        composeRule
            .onNodeWithContentDescription(context.getString(R.string.save_note))
            .performClick()

        //make sure the update was applied to list
        composeRule.onNodeWithText("test content_edited").assertIsDisplayed()

    }

    @Test
    fun saveNewNotes_orderByTitleDescending() {
        for (i in 1..3) {

            //click on fab to get to AddNoteScreen
            composeRule.onNodeWithContentDescription(context.getString(R.string.add_note))
                .performClick()

            //enter texts in title and content textFields
            composeRule
                .onNodeWithTag(TestTag.TITLE_TEXT_FIELD)
                .performTextInput(i.toString())
            composeRule
                .onNodeWithTag(TestTag.Content_TEXT_FIELD)
                .performTextInput(i.toString())
            //save the new note
            composeRule
                .onNodeWithContentDescription(context.getString(R.string.save_note))
                .performClick()

        }
        composeRule.onNodeWithText("1").assertIsDisplayed()
        composeRule.onNodeWithText("2").assertIsDisplayed()
        composeRule.onNodeWithText("3").assertIsDisplayed()

        composeRule.onNodeWithContentDescription(context.getString(R.string.sort_notes))
        composeRule.onNodeWithContentDescription("Title")
        composeRule.onNodeWithContentDescription("Descending")

        composeRule.onAllNodesWithTag(TestTag.NOTE_ITEM)[0]
            .assertTextContains("3")
        composeRule.onAllNodesWithTag(TestTag.NOTE_ITEM)[1]
            .assertTextContains("2")
        composeRule.onAllNodesWithTag(TestTag.NOTE_ITEM)[2]
            .assertTextContains("1")

    }


}