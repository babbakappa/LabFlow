package home.babbakappa.labflow.tools

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Dock
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Error
import androidx.compose.ui.graphics.vector.ImageVector
import home.babbakappa.labflow.database.database.DONE
import home.babbakappa.labflow.database.database.NOT_ACTIVE
import home.babbakappa.labflow.database.database.NOT_DONE
import home.babbakappa.labflow.database.database.NO_PASS
import home.babbakappa.labflow.database.database.PASS
import home.babbakappa.labflow.database.database.PASSES

fun verifyToPlaceStepInUI(status: Int): Boolean {
    return when (status) {
        DONE -> true
        NOT_DONE -> true
        NOT_ACTIVE -> false
        else -> false
    }
}

fun whatToShowInStep(status: Int): String {
    return when (status) {
        DONE -> "+"
        NOT_DONE -> "-"
        NOT_ACTIVE -> "Неактивно"
        else -> "Неактивно"
    }
}

fun whatToShowInResult(status: Boolean): ImageVector {

    return when (status) {

        false -> Icons.Default.Close
        true -> Icons.Default.Done

    }

}

fun whatToShowInGrade(status: Int): String {

    return when(status) {

        PASS -> "ЗЧ"
        NO_PASS -> "НЗ"
        2 -> "2"
        3 -> "3"
        4 -> "4"
        5 -> "5"
        else -> "Error"

    }

}