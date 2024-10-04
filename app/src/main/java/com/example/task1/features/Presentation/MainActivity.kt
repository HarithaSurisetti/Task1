package com.example.task1.features.Presentation

import ViewNoteDetailsScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.task1.features.Screens.AddNoteScreen.AddNoteScreen
import com.example.task1.features.Screens.NotesApp.NotesApp
import com.example.task1.ui.Task1Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Task1Theme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "note_list") {
                    composable("note_list") {
                        NotesApp(navController = navController)
                    }
                    composable("add_note") {
                        AddNoteScreen(navController = navController)
                    }
                    composable("view_note") {
                        ViewNoteDetailsScreen(navController = navController)
                    }
                }
            }
        }
    }
}
