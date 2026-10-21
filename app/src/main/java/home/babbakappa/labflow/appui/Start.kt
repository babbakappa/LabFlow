package home.babbakappa.labflow.appui

import androidx.compose.foundation.background
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
import home.babbakappa.labflow.tools.BODY_COLOR
import home.babbakappa.labflow.tools.BUTTON_COLOR
import home.babbakappa.labflow.tools.DISABLED_BUTTON_COLOR
import home.babbakappa.labflow.tools.TEXT_COLOR_BLACK
import home.babbakappa.labflow.tools.TEXT_COLOR_WHITE

@Composable
fun StartScreen(
    onOpenSubjects: () -> Unit,
    onCreateSubject: () -> Unit,
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BODY_COLOR
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
        .fillMaxHeight(0.4f).background(color = BODY_COLOR),
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            "Лабораторные работы",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = TEXT_COLOR_BLACK
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
        .fillMaxHeight(0.2f).background(color = BODY_COLOR),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Button(
                onClick = onOpenSubjects,
                colors = ButtonColors(
                    containerColor = BUTTON_COLOR,
                    contentColor = TEXT_COLOR_WHITE,
                    disabledContainerColor = DISABLED_BUTTON_COLOR,
                    disabledContentColor = TEXT_COLOR_WHITE
                )
            ) {
                Text(
                    "Открыть"
                )
            }

            Button(
                onClick = onCreateSubject,
                colors = ButtonColors(
                    containerColor = BUTTON_COLOR,
                    contentColor = TEXT_COLOR_WHITE,
                    disabledContainerColor = DISABLED_BUTTON_COLOR,
                    disabledContentColor = TEXT_COLOR_WHITE
                )
            ) {
                Text(
                    "Создать"
                )
            }

        }



    }
}