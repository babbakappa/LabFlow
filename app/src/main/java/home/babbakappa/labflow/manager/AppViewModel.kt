package home.babbakappa.labflow.manager

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import home.babbakappa.labflow.database.database.DatabaseDAO
import home.babbakappa.labflow.database.database.GRADES
import home.babbakappa.labflow.database.database.NOT_ACTIVE
import home.babbakappa.labflow.database.database.NOT_DONE
import home.babbakappa.labflow.database.database.NO_GRADE
import home.babbakappa.labflow.database.database.NO_PASS
import home.babbakappa.labflow.database.database.TaskEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppViewModel(private val db: DatabaseDAO) : ViewModel() {


    private val _uiState = MutableStateFlow(UIState.START_SCREEN)
    val uiState = _uiState.asStateFlow()
    private val _currentSubject = MutableStateFlow("")
    val currentSubject = _currentSubject.asStateFlow()

    val allSubjectNames = db.getOnlySubjectNames().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()

    )
    @OptIn(ExperimentalCoroutinesApi::class)
    val currentTasks = _currentSubject.flatMapLatest { subject ->
        db.getSubjectTasks(subject) // Каждый раз при смене subject, Room будет давать новый Flow
    }.stateIn(
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
        activeSteps: Int,
        methodGrade: String
    ) {

        viewModelScope.launch {

            try {
                var statuses = mutableListOf<Int>()
                for (i in 0..4) {
                    if (i >= activeSteps) {
                        statuses.add(NOT_ACTIVE)
                    }
                    else {
                        statuses.add(NOT_DONE)
                    }
                }
                var list = mutableListOf<TaskEntity>()
                for (i in 0 until labCount) {
                    var tempObject = TaskEntity(
                        subjectName = subjectName,
                        grade = if (methodGrade == GRADES) NO_GRADE else NO_PASS,
                        done = false,
                        labNumber = "",
                        labTopic = "",
                        status1 = statuses[0],
                        status2 = statuses[1],
                        status3 = statuses[2],
                        status4 = statuses[3]
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

    fun changeStatusByItsNumber(number: Int, newStatus: Int, id: Long) {

        viewModelScope.launch {

            when (number) {

                1 -> db.setNewStatus1(newStatus, id)
                2 -> db.setNewStatus2(newStatus, id)
                3 -> db.setNewStatus3(newStatus, id)
                4 -> db.setNewStatus4(newStatus, id)

            }

        }

    }

    fun changeNumber(newNumber: String, id: Long) {
        viewModelScope.launch {
            db.changeLabNumber(newNumber, id)
        }
    }

    fun deleteSubject(subjectName: String) {

        viewModelScope.launch {
            db.deleteSubject(subjectName)
        }

    }

}