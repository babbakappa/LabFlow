package home.babbakappa.labflow.database.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "subject_name")
    var subjectName: String,

    @ColumnInfo(name = "lab_number")
    var labNumber: String,

    @ColumnInfo(name = "lab_topic")
    var labTopic: String,

    @ColumnInfo(name = "status_1")
    var status1: Int = NOT_DONE,

    @ColumnInfo(name = "status_2")
    var status2: Int = NOT_ACTIVE,

    @ColumnInfo(name = "status_3")
    var status3: Int = NOT_ACTIVE,

    @ColumnInfo(name = "status_4")
    var status4: Int = NOT_ACTIVE,

    @ColumnInfo(name = "grade")
    var grade: Int,

    @ColumnInfo(name = "done")
    var done: Boolean

)

// для зачета/незачета
val PASS = 1
val NO_PASS = 0
val PASSES = "Зачёт/Незачёт"

// для оценок
val NO_GRADE = 2
val GRADES = "Оценка"

// для шагов
val NOT_ACTIVE = -1
val NOT_DONE = 0
val DONE = 1