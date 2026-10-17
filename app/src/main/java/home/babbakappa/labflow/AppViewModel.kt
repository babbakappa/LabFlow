package home.babbakappa.labflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import home.babbakappa.labflow.core.repos.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn

class AppViewModel(private val rep: Repository) : ViewModel() {

    val tasks = rep.tasks.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _uiState = MutableStateFlow(UIState.START_SCREEN)
    val uiState = _uiState.asStateFlow()

    fun changeUIState(newUIState: UIState) {
        _uiState.value = newUIState
    }

}