package home.babbakappa.labflow.appui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import home.babbakappa.labflow.manager.AppViewModel
import home.babbakappa.labflow.manager.UIState
import home.babbakappa.labflow.tools.EMPTY_COLOR
import home.babbakappa.labflow.tools.HEAD_COLOR
import home.babbakappa.labflow.tools.TEXT_COLOR_WHITE

@Composable
fun MainHub(vm: AppViewModel) {

    val tasks by vm.currentTasks.collectAsStateWithLifecycle()
    val uiState by vm.uiState.collectAsState()
    val currentName by vm.currentSubject.collectAsState()
    val names by vm.allSubjectNames.collectAsState()

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
            SelectScreen(
                names,
                onClickItem = { newName ->
                    vm.changeCurrentSubject(newName)
                    vm.changeUIState(UIState.SUBJECT_SCREEN)
                },
                onClickBack = {
                    vm.changeUIState(UIState.START_SCREEN)
                }
            )
        }

        UIState.SUBJECT_SCREEN -> {
            SubjectScreen(tasks,
                currentName,
                onClickBack = {
                    vm.changeUIState(UIState.START_SCREEN)
                },
                onStatusChange = {num, st, id -> vm.changeStatusByItsNumber(num, st, id)},
                onChangedNumber = { newNumber, id -> vm.changeNumber(newNumber, id) }
            )
        }

        UIState.CREATE_SCREEN -> {
            CreateTableScreen(
                onTakeData = {
                    subjectName, labCount, stepsCount, gradeMethod ->
                    vm.createNewSubject(subjectName, labCount, stepsCount, gradeMethod)
                    vm.changeUIState(UIState.SELECT_SCREEN)
                },
                onClickBack = { vm.changeUIState(UIState.START_SCREEN) }
            )
        }
    }

}

@Composable
fun DefaultScreenHead(stringInHead: String, arrowBackAction: () -> Unit) {

    Box(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.1f).background(color = HEAD_COLOR)
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = arrowBackAction,
                colors = IconButtonColors(
                    containerColor = EMPTY_COLOR,
                    contentColor = TEXT_COLOR_WHITE,
                    disabledContainerColor = EMPTY_COLOR,
                    disabledContentColor = TEXT_COLOR_WHITE
                )
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }

            Text(
                stringInHead,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                color = TEXT_COLOR_WHITE
            )

        }

    }

}