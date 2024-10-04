package com.example.task1.features.Screens.AddNoteScreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task1.features.Domain.Model.Note
import com.example.task1.features.Domain.UseCase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    private val _content = MutableStateFlow("")
    val content: StateFlow<String> = _content

    fun setTitle(newTitle: String) {
        viewModelScope.launch {
            _title.emit(newTitle)
        }
    }


    fun setContent(newContent: String) {
        viewModelScope.launch {
            _content.emit(newContent)
        }
    }


    fun onSaveButtonClicked(title: String, content: String) {
        if (validateInput(title, content)) {
            val newNote = Note(
                id = 0,
                title = title,
                content = content,
                timestamp = System.currentTimeMillis().toString()
            )
            viewModelScope.launch {
                noteUseCases.addNoteUseCase(newNote)
            }
        }
    }

    private fun validateInput(title: String, content: String): Boolean {
        return title.isNotBlank() && content.isNotBlank()
    }
}
