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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import home.babbakappa.labflow.tools.BODY_COLOR
import home.babbakappa.labflow.tools.BOX_COLOR
import home.babbakappa.labflow.tools.HEAD_COLOR
import home.babbakappa.labflow.tools.verifyToPlaceStepInUI
import home.babbakappa.labflow.tools.whatToShowInStep

val DEFAULT_BOX_HEIGHT = 64.dp
val DEFAULT_BOX_WIDTH = 64.dp
val DESCRIPTION_BOX_HEIGHT = 64.dp
val DESCRIPTION_BOX_WIDTH = 64.dp * 5f
val CORNER_DEGREE = 6.dp

@Composable
fun SubjectScreen(
    tasks: List<TaskEntity>,
    subjectName: String,
    onClickBack: () -> Unit,
    onStatusChange: (Int, Int, Long) -> Unit,
    onChangedNumber: (String, Long) -> Unit
) {

    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BODY_COLOR)
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

                modifier = Modifier.horizontalScroll(scrollState)

            ) {

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(tasks) {
                            task ->
                        OneLine(
                            task,
                            onStatusChange = { num, st, id -> onStatusChange(num, st, id) },
                            onChangedNumber = { newValue, id -> onChangedNumber(newValue, id) }
                        )
                    }
                }

            }

        }

    }
}

@Composable
fun SubjectScreenHead(name: String, onClickBack: () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = HEAD_COLOR,
                shape = RoundedCornerShape(CORNER_DEGREE)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onClickBack
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
            Text(
                name,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            )
        }
    }

}

@Composable
fun OneLine(
    obj: TaskEntity,
    onStatusChange: (Int, Int, Long) -> Unit,
    onChangedNumber: (String, Long) -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        BoxLabNumber(
            obj.labNumber,
            {newValue -> onChangedNumber(newValue, obj.id) }
        )
        BoxLabDescription(obj.labTopic)
        StepsStatuses(
            obj.status1,
            obj.status2,
            obj.status3,
            obj.status4,
            onStatusChange = { num, st -> onStatusChange(num, st, obj.id) }
        )

    }

}

@Composable
fun BoxLabNumber(
    number: String,
    onChangedNumber: (String) -> Unit
) {

    var launchDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .height(DEFAULT_BOX_HEIGHT)
            .width(DEFAULT_BOX_WIDTH)
            .background(
                color = BOX_COLOR,
                shape = RoundedCornerShape(CORNER_DEGREE)
            )
            .clickable(
                onClick = {
                    launchDialog = !launchDialog
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(number)
    }

    if (launchDialog) {
        DefaultDialogWindow(
            "Номер лабораторной",
            "Номер",
            number,
            onCloseDialog = { launchDialog = false },
            onChangedData = { newValue -> onChangedNumber(newValue)
                launchDialog = false
            }
        )
    }
}

@Composable
fun BoxLabDescription(description: String) {

    Box(
        modifier = Modifier
            .height(DESCRIPTION_BOX_HEIGHT)
            .width(DESCRIPTION_BOX_WIDTH)
            .background(
                color = BOX_COLOR,
                shape = RoundedCornerShape(CORNER_DEGREE)
            ),
        contentAlignment = Alignment.Center
    ) {
        Row {
            Text(description)
        }
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
                    st -> onStatusChange(1, st)
                }
            )
        }
        if (verifyToPlaceStepInUI(status2)) {
            OneStepBox(
                status2,
                changeStatus = {
                    st -> onStatusChange(2, st)
                }
            )
        }
        if (verifyToPlaceStepInUI(status3)) {
            OneStepBox(
                status3,
                changeStatus = {
                    st -> onStatusChange(3, st)
                }
            )
        }
        if (verifyToPlaceStepInUI(status4)) {
            OneStepBox(
                status4,
                changeStatus = {
                    st -> onStatusChange(4, st)
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
                color = BOX_COLOR,
                shape = RoundedCornerShape(CORNER_DEGREE)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            whatToShowInStep(status),
            modifier = Modifier.clickable(
                onClick = {
                    changeStatus(if (status == NOT_DONE) DONE else NOT_DONE)
                }
            )
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

        Card(
            modifier = Modifier.fillMaxWidth(0.8f).fillMaxHeight(0.2f)
        ) {

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
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