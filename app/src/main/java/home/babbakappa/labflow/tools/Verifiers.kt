package home.babbakappa.labflow.tools

import home.babbakappa.labflow.database.database.DONE
import home.babbakappa.labflow.database.database.NOT_ACTIVE
import home.babbakappa.labflow.database.database.NOT_DONE

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