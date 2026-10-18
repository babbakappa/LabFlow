package home.babbakappa.labflow.appui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StartScreen(
    onOpenSubjects: () -> Unit,
    onCreateSubject: () -> Unit,
) {

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {

            HeadOfStartScreen()
            FirstButtons(
                onOpenSubjects,
                onCreateSubject
            )

        }

    }

}

@Composable
fun HeadOfStartScreen() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.1f),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "Лабораторные работы",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun FirstButtons(
    onOpenSubjects: () -> Unit,
    onCreateSubject: () -> Unit
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.1f),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Button(
                onClick = onOpenSubjects,
            ) {
                Text(
                    "Открыть"
                )
            }

            Button(
                onClick = onCreateSubject
            ) {
                Text(
                    "Создать"
                )
            }

        }



    }
}