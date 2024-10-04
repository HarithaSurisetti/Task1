package com.example.task1.features.Screens.NotesApp

import NoteScreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesApp(navController: NavHostController) {
    val viewModel: NotesAppViewModel = hiltViewModel()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Notes App") },
                modifier = Modifier.fillMaxWidth()
            )
        },
        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp), // Space between FABs
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FloatingActionButton(onClick = {
                    navController.navigate("view_note")
                }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "View Notes")
                }
                FloatingActionButton(onClick = {
                    navController.navigate("add_note")
                }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")
                }
            }
        },
        content = { paddingValues ->
            NoteScreen(
                viewModel = viewModel,
                modifier = Modifier.padding(paddingValues)
            )
        }
    )
}
