package home.babbakappa.labflow.manager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import home.babbakappa.labflow.database.database.DatabaseDAO
import home.babbakappa.labflow.database.database.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn

class AppViewModel(private val db: DatabaseDAO) : ViewModel() {

    private val _uiState = MutableStateFlow(UIState.START_SCREEN)
    val uiState = _uiState.asStateFlow()
    private val _currentSubject = MutableStateFlow("")
    val currentSubject = _currentSubject.asStateFlow()

    val tasks = db.getSubjectTasks(currentSubject.value).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun changeUIState(newUIState: UIState) {
        _uiState.value = newUIState
    }

    fun changeCurrentSubject(newSubject: String) {
        _currentSubject.value = newSubject
    }

}