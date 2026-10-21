package home.babbakappa.labflow.appui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import home.babbakappa.labflow.database.database.GRADES
import home.babbakappa.labflow.database.database.PASSES

@Composable
fun CreateTableScreen(
    onTakeData: (String, Int, Int, String) -> Unit,
    onClickBack: () -> Unit
) {

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier.fillMaxSize().systemBarsPadding()
        ) {

            DefaultScreenHead(
                "Создание раздела",
                onClickBack
            )
            CreationForm(
                onTakeData = {
                    subjectName, labCount, stepsCount, gradeMethod ->
                    onTakeData(subjectName, labCount, stepsCount, gradeMethod)
                }
            )

        }

    }

}

@Composable
fun HeadOfCreateTableScreen(
    onClickBack: () -> Unit
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.1f),
        contentAlignment = Alignment.Center
    ) {

        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            IconButton(
                onClickBack
            ) {

                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )

            }

            Text(
                "Создание раздела по предмету",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

        }

    }
}

@Composable
fun CreationForm(
    onTakeData: (String, Int, Int, String) -> Unit
) {

    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.9f),
        contentAlignment = Alignment.Center
    ) {

        TextFields(
            onTakeData = {
                subjectName, labCount, stepsCount, gradeMethod ->
                onTakeData(subjectName, labCount, stepsCount, gradeMethod)
            }
        )

    }

}

@Composable
fun TextFields(
    onTakeData: (String, Int, Int, String) -> Unit
) {

    var currentSubjectName by remember { mutableStateOf("") }
    var labCount by remember { mutableStateOf("") }
    var statusCount by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf("") }
    var expandedMenu by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        OutlinedTextField(
            value = currentSubjectName,
            onValueChange = { currentSubjectName = it },
            label = { Text("Название предмета") }
        )

        OutlinedTextField(
            value = labCount,
            onValueChange = {
                if (it.isEmpty() || it.isDigitsOnly()) {
                    labCount = it
                }
            },
            label = { Text("Количество лабораторных") }
        )

        OutlinedTextField(
            value = statusCount,
            onValueChange = {
                if (it.isEmpty() || it.isDigitsOnly() || it.toInt() < 5) {
                    statusCount = it
                }
            },
            label = { Text("Количество этапов") }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {

                OutlinedTextField(
                    value = selectedOption,
                    onValueChange = {
                        selectedOption = it
                    },
                    label = { Text("Оценка/Зачёт") },
                    readOnly = true
                )

                DropdownMenu(
                    expanded = expandedMenu,
                    onDismissRequest = {
                        expandedMenu = false
                    }
                ) {
                    DropdownMenuItem(
                        onClick = {
                            selectedOption = GRADES
                            expandedMenu = false
                        },
                        text = { Text(GRADES) }
                    )
                    DropdownMenuItem(
                        onClick = {
                            selectedOption = PASSES
                            expandedMenu = false
                        },
                        text = { Text(PASSES) }
                    )
                }

            }

            IconButton(
                onClick = {
                    expandedMenu = true
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null
                )
            }

        }

        Button(
            onClick = {
                onTakeData(
                    currentSubjectName,
                    labCount.toInt(),
                    statusCount.toInt(),
                    selectedOption
                )
            }
        ) {
            Text("Создать")
        }

    }

}