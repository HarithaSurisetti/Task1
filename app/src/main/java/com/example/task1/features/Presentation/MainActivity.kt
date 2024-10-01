package com.example.task1.features.Presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.task1.NotesViewModelFactory
import com.example.task1.PresentationLayer.NotesViewModel
import com.example.task1.features.Data.NoteDatabase
import com.example.task1.features.Data.NoteRepository
import com.example.task1.features.Domain.Model.Note
import com.example.task1.features.Domain.UseCase.AddNoteUseCase
import com.example.task1.features.Domain.UseCase.GetNoteByIdUseCase
import com.example.task1.features.Domain.UseCase.GetNotesUseCase
import com.example.task1.features.Domain.UseCase.NoteUseCases
import com.example.task1.ui.Task1Theme
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : ComponentActivity() {

    // Lazy initialization for the DAO and repository
    private val noteDao by lazy {
        NoteDatabase.getDatabase(application).noteDao()
    }

    private val repository by lazy {
        NoteRepository(noteDao)
    }


    private val noteUseCases by lazy {
        NoteUseCases(
            getNotesUseCase = GetNotesUseCase(repository),
            addNoteUseCase = AddNoteUseCase(repository),
            getNoteByIdUseCase = GetNoteByIdUseCase(repository)
        )
    }
    private val notesViewModel: NotesViewModel by viewModels {
        NotesViewModelFactory(noteUseCases)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Task1Theme {
                val navController = rememberNavController()

                // Navigation graph
                NotesNavHost(navController = navController, viewModel = notesViewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesApp(viewModel: NotesViewModel, navController: NavHostController) {
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
                // Floating button for displaying note details
                FloatingActionButton(onClick = {
                    // Navigate to a new screen or display the note details
                    navController.navigate("view_note") // Navigate to a screen for note details
                }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "View Notes")
                }
                // Floating Action Button for Adding Note
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

@Composable
fun NotesNavHost(navController: NavHostController, viewModel: NotesViewModel) {
    NavHost(navController = navController, startDestination = "note_list") {
        composable("note_list") {
            NotesApp(viewModel = viewModel, navController = navController)
        }
        composable("add_note") {
            AddNoteScreen(navController = navController, viewModel = viewModel)
        }
        // New composable for viewing note details
        composable("view_note") {
            ViewNoteDetailsScreen(navController = navController, viewModel = viewModel)
        }
    }
}

@Composable
fun ViewNoteDetailsScreen(navController: NavHostController, viewModel: NotesViewModel) {
    val notes by viewModel.noteList.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(notes) { note ->
            // Display the note details
            NoteDetailsItem(note = note)
        }
    }
}

// A composable to display individual note details
@Composable
fun NoteDetailsItem(note: Note) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Title: ${note.title}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Content: ${note.content}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Added on: ${formatDate(note.timestamp)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteScreen(viewModel: NotesViewModel, modifier: Modifier = Modifier) {
    val notes by viewModel.noteList.collectAsState() // Check if you're getting the correct notes

    if (notes.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Two columns of fixed size
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(notes) { note ->
                NoteItem(note = note)
            }
        }
    } else {
        Text("No notes available", modifier = Modifier.fillMaxSize().wrapContentSize())
    }
}



@Composable
fun NoteItem(note: Note) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Title of the Note
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Content of the Note
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))

        }
    }
}



@Composable
fun AddNoteScreen(navController: NavHostController, viewModel: NotesViewModel) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Content") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val newNote = Note(
                    id = 0,
                    title = title,
                    content = content,
                    timestamp = System.currentTimeMillis().toString()
                )
                viewModel.addNote(newNote)
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Save Note")
        }
    }
}

@Composable
fun formatDate(timestamp: String): String {
    val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
    val date = sdf.format(timestamp.toLong())
    return date
}
