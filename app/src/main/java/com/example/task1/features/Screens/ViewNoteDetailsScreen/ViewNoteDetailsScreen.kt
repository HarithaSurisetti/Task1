import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.task1.features.Screens.ViewNoteDetailsScreen.NoteDetailsViewModel

@Composable
fun ViewNoteDetailsScreen(navController: NavHostController) {
    val viewModel: NoteDetailsViewModel = hiltViewModel()
    val notes by viewModel.noteList.collectAsState()



    LaunchedEffect(Unit) {
        viewModel.loadNotes()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(notes.size) { index ->
            val note = notes[index]
            NoteDetailsItem(note = note)
        }
    }
}
