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
    suspend fun deleteAllTasks()

    @Query("DELETE FROM tasks WHERE subject_name = :subjectName")
    suspend fun deleteSubjectTasks(subjectName: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(task: TaskEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTasks(tasks: List<TaskEntity>)

    @Query("SELECT DISTINCT subject_name FROM tasks")
    fun getOnlySubjectNames(): Flow<List<String>>

    @Query("UPDATE tasks SET status_1 = :newStatus WHERE id == :givenID")
    suspend fun setNewStatus1(newStatus: Int, givenID: Long)

    @Query("UPDATE tasks SET status_2 = :newStatus WHERE id == :givenID")
    suspend fun setNewStatus2(newStatus: Int, givenID: Long)

    @Query("UPDATE tasks SET status_3 = :newStatus WHERE id == :givenID")
    suspend fun setNewStatus3(newStatus: Int, givenID: Long)

    @Query("UPDATE tasks SET status_4 = :newStatus WHERE id == :givenID")
    suspend fun setNewStatus4(newStatus: Int, givenID: Long)

    @Query("UPDATE tasks SET lab_number = :newNumber WHERE id == :id")
    suspend fun changeLabNumber(newNumber: String, id: Long)

}