package home.babbakappa.labflow.appui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import home.babbakappa.labflow.database.database.TaskEntity

@Composable
fun SubjectScreen(tasks: List<TaskEntity>, name: String, onClickBack: () -> Unit) {

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column() {

            SubjectScreenHead(name, onClickBack)

        }

    }
}

@Composable
fun SubjectScreenHead(name: String, onClickBack: () -> Unit) {

    Box(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.1f)
    ) {
        Row() {
            IconButton(
                onClick = onClickBack
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
            Text(name, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        }
    }

}