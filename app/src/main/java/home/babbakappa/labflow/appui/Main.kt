package home.babbakappa.labflow.appui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import home.babbakappa.labflow.manager.AppViewModel
import home.babbakappa.labflow.manager.UIState

@Composable
fun MainHub(vm: AppViewModel) {

    val tasks by vm.tasks.collectAsStateWithLifecycle()
    val uiState by vm.uiState.collectAsState()

    when (uiState) {
        UIState.START_SCREEN -> StartScreen(
            onOpenSubjects = {
                vm.changeUIState(UIState.SELECT_SCREEN)
            },
            onCreateSubject = {
                vm.changeUIState(UIState.CREATE_SCREEN)
            }
        )

        UIState.SELECT_SCREEN -> {
            SelectScreen()
        }

        UIState.LABS_SCREEN -> {

        }

        UIState.CREATE_SCREEN -> {
            CreateTableScreen(
                onTakeData = {
                    subjectName, labCount, stepsCount, gradeMethod ->
                    vm.createNewSubject(subjectName, labCount, stepsCount, gradeMethod)
                },
                onClickBack = { vm.changeUIState(UIState.START_SCREEN) }
            )
        }
    }

}