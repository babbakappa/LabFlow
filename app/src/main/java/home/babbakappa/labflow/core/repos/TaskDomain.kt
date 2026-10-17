package home.babbakappa.labflow.core.repos

data class TaskDomain (
    val id: Long = 0,
    val subjectName: String,
    val labNumber: String,
    val labTopic: String
)
