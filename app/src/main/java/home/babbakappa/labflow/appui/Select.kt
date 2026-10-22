package home.babbakappa.labflow.appui

import android.view.Surface
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import home.babbakappa.labflow.database.database.TaskEntity

@Composable
fun SelectScreen(
    names: List<String>,
    onClickItem: (String) -> Unit,
    onClickBack: () -> Unit,
    onDeleteSubject: (String) -> Unit
) {

    Surface(
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
        ) {
            DefaultScreenHead(
                stringInHead = "Выбор предмета",
                arrowBackAction = onClickBack
            )

            if (names.isEmpty()) {
                EmptyScreen()
            }
            else {

                LazyColumn() {
                    items(names) { name ->
                        SubjectItem(
                            name,
                            onClickItem = {
                                    taken -> onClickItem(taken)
                            },
                            onDeleteSubject = {name -> onDeleteSubject(name)}
                        )
                    }
                }

            }

        }

    }

}

@Composable
fun EmptyScreen() {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Нет предметов", fontSize = 18.sp)

    }

}

@Composable
fun SubjectItem(
    name: String,
    onClickItem: (String) -> Unit,
    onDeleteSubject: (String) -> Unit
) {

    var menuIsOpened by remember { mutableStateOf(false) }

    Card(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.2f)
        .clickable(
            onClick = {
                onClickItem(name)
            }
        )
        .padding(8.dp)
    ) {

        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {

            Text(name, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))

            Column()
            {
                IconButton(
                    onClick = { menuIsOpened = !menuIsOpened }
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = null
                    )
                }
                ThreeDotsMenu(
                    menuIsOpened,
                    onDeleteSubject = {
                        onDeleteSubject(name)
                    },
                    onClose = {
                        menuIsOpened = !menuIsOpened
                    }
                )
            }

        }

    }

}

@Composable
fun ThreeDotsMenu(
    openedMenu: Boolean,
    onClose: () -> Unit,
    onDeleteSubject: () -> Unit
) {



    DropdownMenu(
        expanded = openedMenu,
        onDismissRequest = onClose
    ) {

        DropdownMenuItem(
            text = {Text("Удалить")},
            onClick = onDeleteSubject
        )

    }

}