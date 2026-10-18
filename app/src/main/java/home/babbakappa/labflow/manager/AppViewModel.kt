package home.babbakappa.labflow.manager

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import home.babbakappa.labflow.database.database.DatabaseDAO
import home.babbakappa.labflow.database.database.GRADES
import home.babbakappa.labflow.database.database.NO_GRADE
import home.babbakappa.labflow.database.database.NO_PASS
import home.babbakappa.labflow.database.database.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

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

    fun createNewSubject(
        subjectName: String,
        labCount: Int,
        stepsCount: Int,
        methodGrade: String
    ) {

        viewModelScope.launch {

            try {
                var list = mutableListOf<TaskEntity>()
                for (i in 0 until labCount) {
                    var tempObject = TaskEntity(
                        subjectName = subjectName,
                        howMuchStatuses = stepsCount,
                        grade = if (methodGrade == GRADES) NO_GRADE else NO_PASS,
                        done = false,
                        labNumber = "",
                        labTopic = "",
                        statusesDone = 0
                    )
                    list.add(tempObject)
                }
                db.addTasks(list)
            }
            catch (error: Exception) {
                Log.e("Ошибка!", "$error")
            }

        }


    }

}