package home.babbakappa.labflow.core.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "subject_name")
    val subjectName: String,

    @ColumnInfo(name = "lab_number")
    val labNumber: String,

    @ColumnInfo(name = "lab_topic")
    val labTopic: String,

)