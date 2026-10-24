package home.babbakappa.labflow.appui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import home.babbakappa.labflow.database.database.DONE
import home.babbakappa.labflow.database.database.NOT_DONE
import home.babbakappa.labflow.database.database.TaskEntity
import home.babbakappa.labflow.tools.verifyToPlaceStepInUI
import home.babbakappa.labflow.tools.whatToShowInGrade
import home.babbakappa.labflow.tools.whatToShowInResult
import home.babbakappa.labflow.tools.whatToShowInStep

val DEFAULT_BOX_HEIGHT = 64.dp
val DEFAULT_BOX_WIDTH = 64.dp
val DESCRIPTION_BOX_HEIGHT = 64.dp
val DESCRIPTION_BOX_WIDTH = 64.dp * 5f
val CORNER_DEGREE = 6.dp

@Composable
fun SubjectScreen(
    currentTasks: List<TaskEntity>,
    subjectName: String,
    onClickBack: () -> Unit,
    onChangedStatus: (Int, Int, Long) -> Unit,
    onChangedNumber: (String, Long) -> Unit,
    onChangedDescription: (String, Long) -> Unit,
    onChangedGrade: (Int, Long) -> Unit,
    doneCount: Int,
    onChangedResult: (Boolean, Long) -> Unit
) {

    val scrollStateHorizontal = rememberScrollState()
    val percentage by remember(doneCount, currentTasks.size) {
        derivedStateOf {
            if (currentTasks.isEmpty()) {
                0 // Если задач нет — 0%
            } else {
                // Умножаем на 100 и округляем до целого числа
                ((doneCount.toFloat() / currentTasks.size) * 100).toInt()
            }
        }
    }


    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.systemBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            DefaultScreenHead(
                subjectName,
                onClickBack
            )

            Row(

                modifier = Modifier.horizontalScroll(scrollStateHorizontal)

            ) {

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(currentTasks) {
                            task ->
                        OneLine(
                            task,
                            onChangedStatus = { statusNumber, newState, id -> onChangedStatus(statusNumber, newState, id) },
                            onChangedNumber = { newNumber, id -> onChangedNumber(newNumber, id) },
                            onChangedDescription = {newValue, id -> onChangedDescription(newValue, id) },
                            onChangedGrade = {newGrade, id -> onChangedGrade(newGrade, id)},
                            onChangedResult = {newValue, id -> onChangedResult(newValue, id)}
                        )
                    }
                }

            }

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Процент выполнения: $percentage%",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

        }

    }
}

@Composable
fun OneLine(
    task: TaskEntity,
    onChangedStatus: (Int, Int, Long) -> Unit,
    onChangedNumber: (String, Long) -> Unit,
    onChangedDescription: (String, Long) -> Unit,
    onChangedGrade: (Int, Long) -> Unit,
    onChangedResult: (Boolean, Long) -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        BoxLabNumber(
            task.labNumber,
            onChangedNumber = {
                newValue -> onChangedNumber(newValue, task.id)
            }
        )
        BoxLabDescription(
            task.labTopic,
            onChangedDescription = { newValue -> onChangedDescription(newValue, task.id) }
        )
        StepsStatuses(
            task.status1,
            task.status2,
            task.status3,
            task.status4,
            onStatusChange = { num, st -> onChangedStatus(num, st, task.id) }
        )
        GradeBox(
            task.grade,
            onChangedGrade = {
                newGrade -> onChangedGrade(newGrade, task.id)
            }
        )
        ResultBox(
            task.done,
            onValueChange = {newValue -> onChangedResult(newValue, task.id)}
        )

    }

}

@Composable
fun BoxLabNumber(
    number: String,
    onChangedNumber: (String) -> Unit
) {

    var dialogIsActive by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .height(DEFAULT_BOX_HEIGHT)
            .width(DEFAULT_BOX_WIDTH)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(CORNER_DEGREE)
            )
            .clickable(
                onClick = {
                    dialogIsActive = !dialogIsActive
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            number,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 26.sp
        )
    }

    if (dialogIsActive) {
        DefaultDialogWindow(
            "Номер лабораторной",
            "Номер",
            number,
            onCloseDialog = { dialogIsActive = false },
            onChangedData = { newValue -> onChangedNumber(newValue)
                dialogIsActive = false
            }
        )
    }
}

@Composable
fun BoxLabDescription(
    description: String,
    onChangedDescription: (String) -> Unit
) {

    var dialogIsActive by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .height(DESCRIPTION_BOX_HEIGHT)
            .width(DESCRIPTION_BOX_WIDTH)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(CORNER_DEGREE)
            )
            .clickable(
                onClick = { dialogIsActive = !dialogIsActive }
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .verticalScroll(scrollState),

        ) {
            Text(
                description,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

    if (dialogIsActive) {
        DefaultDialogWindow(
            "Тема лабораторной",
            "Тема",
            description,
            onCloseDialog = { dialogIsActive = false },
            onChangedData = { newValue -> onChangedDescription(newValue)
                dialogIsActive = false
            }
        )
    }

}

@Composable
fun StepsStatuses(
    status1: Int,
    status2: Int,
    status3: Int,
    status4: Int,
    onStatusChange: (Int, Int) -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        if (verifyToPlaceStepInUI(status1)) {
            OneStepBox(
                status1,
                changeStatus = {
                    newStatus -> onStatusChange(1, newStatus)
                }
            )
        }
        if (verifyToPlaceStepInUI(status2)) {
            OneStepBox(
                status2,
                changeStatus = {
                    newStatus -> onStatusChange(2, newStatus)
                }
            )
        }
        if (verifyToPlaceStepInUI(status3)) {
            OneStepBox(
                status3,
                changeStatus = {
                    newStatus -> onStatusChange(3, newStatus)
                }
            )
        }
        if (verifyToPlaceStepInUI(status4)) {
            OneStepBox(
                status4,
                changeStatus = {
                    newStatus -> onStatusChange(4, newStatus)
                }
            )
        }

    }


}

@Composable
fun OneStepBox(status: Int, changeStatus: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .height(DEFAULT_BOX_HEIGHT)
            .width(DEFAULT_BOX_WIDTH)
            .background(
                shape = RoundedCornerShape(CORNER_DEGREE),
                color = MaterialTheme.colorScheme.primary
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            whatToShowInStep(status),
            modifier = Modifier.clickable(
                onClick = {
                    changeStatus(if (status == NOT_DONE) DONE else NOT_DONE)
                }
            ),
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 34.sp
        )
    }
}

@Composable
fun DefaultDialogWindow(
    stringInHead: String,
    stringInField: String,
    stringFromBox: String,
    onCloseDialog: () -> Unit,
    onChangedData: (String) -> Unit
) {

    var rememberedValue by remember { mutableStateOf(stringFromBox) }

    Dialog(
        onCloseDialog
    ) {

        Card {

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text(stringInHead)
                OutlinedTextField(
                    value = rememberedValue,
                    onValueChange = { newValue ->
                        rememberedValue = newValue
                    },
                    label = { Text(stringInField) }
                )
                Button(
                    onClick = { onChangedData(rememberedValue) }
                ) {
                    Text("Изменить")
                }

            }

        }

    }

}


@Composable
fun DialogWindowForGrade(
    stringInHead: String,
    stringInField: String,
    stringFromBox: String,
    onCloseDialog: () -> Unit,
    onChangedData: (Int) -> Unit
) {

    var rememberedValue by remember { mutableStateOf(stringFromBox) }
    var menuIsOpen by remember { mutableStateOf(false) }

    Dialog(
        onCloseDialog
    ) {

        Card {

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text(stringInHead)

                Column() {

                    OutlinedTextField(
                        value = rememberedValue,
                        onValueChange = { newValue ->
                            rememberedValue = newValue
                        },
                        label = { Text(stringInField) },
                        trailingIcon = {
                            IconButton(
                                onClick = { menuIsOpen = true }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = null
                                )
                            }
                        }
                    )

                    DropdownMenu(
                        onDismissRequest = { menuIsOpen = false },
                        expanded = menuIsOpen
                    ) {

                        DropdownMenuItem(
                            onClick = {
                                rememberedValue = "0"
                                menuIsOpen = false
                            },
                            text = {Text("Незачет")}
                        )

                        DropdownMenuItem(
                            onClick = {
                                rememberedValue = "1"
                                menuIsOpen = false
                            },
                            text = {Text("Зачет")}
                        )

                        DropdownMenuItem(
                            onClick = {
                                rememberedValue = "2"
                                menuIsOpen = false
                            },
                            text = {Text("2")}
                        )

                        DropdownMenuItem(
                            onClick = {
                                rememberedValue = "3"
                                menuIsOpen = false
                            },
                            text = {Text("3")}
                        )

                        DropdownMenuItem(
                            onClick = {
                                rememberedValue = "4"
                                menuIsOpen = false
                            },
                            text = {Text("4")}
                        )

                        DropdownMenuItem(
                            onClick = {
                                rememberedValue = "5"
                                menuIsOpen = false
                            },
                            text = {Text("5")}
                        )

                    }

                }

                Button(
                    onClick = { onChangedData(rememberedValue.toInt()) }
                ) {
                    Text("Изменить")
                }

            }

        }

    }

}



@Composable
fun GradeBox(
    status: Int,
    onChangedGrade: (Int) -> Unit
) {
    var dialogIsActive by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .height(DEFAULT_BOX_HEIGHT)
            .width(DEFAULT_BOX_WIDTH)
            .background(
                shape = RoundedCornerShape(CORNER_DEGREE),
                color = MaterialTheme.colorScheme.primary
            )
            .clickable(onClick = { dialogIsActive = true }),
        contentAlignment = Alignment.Center
    ) {
        Text(
            whatToShowInGrade(status),
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 26.sp
        )
    }

    if (dialogIsActive) {
        DialogWindowForGrade(
            "Оценка лабораторной",
            "Оценка",
            status.toString(),
            onCloseDialog = { dialogIsActive = false },
            onChangedData = { newValue -> onChangedGrade(newValue)
                dialogIsActive = false
            }
        )
    }
}

@Composable
fun ResultBox(
    status: Boolean,
    onValueChange: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .height(DEFAULT_BOX_HEIGHT)
            .width(DEFAULT_BOX_WIDTH)
            .background(
                shape = RoundedCornerShape(CORNER_DEGREE),
                color = MaterialTheme.colorScheme.primary
            )
            .clickable(
                onClick = { onValueChange(!status) }
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = whatToShowInResult(status),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}