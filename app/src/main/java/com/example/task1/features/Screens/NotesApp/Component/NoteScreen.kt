import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.task1.features.Screens.NotesApp.Component.NoteItem
import com.example.task1.features.Screens.NotesApp.NotesAppViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteScreen(
    viewModel: NotesAppViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val notes by viewModel.noteList.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.loadNotes()
    }

    if (notes.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(notes.size) { index ->
                val note = notes[index]
                NoteItem(note = note)
            }
        }
    } else {
        Text(
            text = "No notes available",
            modifier = Modifier.fillMaxSize().wrapContentSize()
        )
    }
}
