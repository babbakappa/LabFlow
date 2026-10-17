package home.babbakappa.labflow.core.repos

import home.babbakappa.labflow.core.database.DatabaseDAO
import home.babbakappa.labflow.core.toDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Repository(private val db: DatabaseDAO) {

    val scope = CoroutineScope(Dispatchers.IO)

    var tasks = db.getAllTasks().map { list ->
        list.map { task -> task.toDomain() }
    }

    fun getSubjectTasks(subjectName: String): Flow<List<TaskDomain>> {
        return db.getSubjectTasks(subjectName).map { list ->
            list.map { task -> task.toDomain() }
        }
    }

}