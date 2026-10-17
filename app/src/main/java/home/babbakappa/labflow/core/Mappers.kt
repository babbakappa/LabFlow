package home.babbakappa.labflow.core

import home.babbakappa.labflow.core.database.TaskEntity
import home.babbakappa.labflow.core.repos.TaskDomain

fun TaskDomain.toEntity(): TaskEntity {
    return TaskEntity(
        id = this.id,
        subjectName = this.subjectName,
        labNumber = this.labNumber,
        labTopic = this.labTopic
    )
}

fun TaskEntity.toDomain(): TaskDomain {
    return TaskDomain(
        id = this.id,
        subjectName = this.subjectName,
        labNumber = this.labNumber,
        labTopic = this.labTopic
    )
}