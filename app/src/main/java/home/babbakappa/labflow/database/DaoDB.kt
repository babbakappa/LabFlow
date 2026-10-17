package home.babbakappa.labflow.database.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DatabaseDAO {

    @Query("SELECT * FROM tasks WHERE subject_name = :subjectName")
    fun getSubjectTasks(subjectName: String): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("DELETE FROM tasks")
    fun deleteAllTasks()

    @Query("DELETE FROM tasks WHERE subject_name = :subjectName")
    fun deleteSubjectTasks(subjectName: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTask(task: TaskEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTasks(tasks: List<TaskEntity>)

}