package home.babbakappa.labflow.appui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import home.babbakappa.labflow.database.database.TaskEntity

@Composable
fun SelectScreen(
    names: List<String>,
    onClickItem: (String) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LazyColumn() {
            items(names) { name ->
                SubjectItem(
                    name,
                    onClickItem = {
                        taken -> onClickItem(taken)
                    }
                )
            }
        }
    }
}

@Composable
fun SubjectItem(
    name: String,
    onClickItem: (String) -> Unit
) {

    Card(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.2f)
        .clickable(
            onClick = {
                onClickItem(name)
            }
        )
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        ) {

            Text(name, fontSize = 20.sp, fontWeight = FontWeight.Bold)

        }

    }

}