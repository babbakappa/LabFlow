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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import home.babbakappa.labflow.database.database.TaskEntity
import home.babbakappa.labflow.tools.BODY_COLOR
import home.babbakappa.labflow.tools.BOX_COLOR
import home.babbakappa.labflow.tools.HEAD_COLOR
import home.babbakappa.labflow.tools.TEXT_COLOR_WHITE

@Composable
fun SelectScreen(
    names: List<String>,
    onClickItem: (String) -> Unit,
    onClickBack: () -> Unit
) {

    Surface(
        color = BODY_COLOR
    ) {

        Column(
            modifier = Modifier.fillMaxSize().systemBarsPadding(),
        ) {
            DefaultScreenHead(
                stringInHead = "Выбор предмета",
                arrowBackAction = onClickBack
            )
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

}


@Composable
fun HeadOfSelectScreen(
    onClickBack: () -> Unit
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.1f)
        .background(color = HEAD_COLOR)
    ) {

        Row(
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
                "Выбор предмета",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

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
        .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = BOX_COLOR, contentColor = TEXT_COLOR_WHITE)
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        ) {

            Text(name, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(16.dp))

        }

    }

}