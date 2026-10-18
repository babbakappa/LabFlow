package home.babbakappa.labflow.data

import android.content.Context
import home.babbakappa.labflow.database.database.TaskEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

suspend fun writeHeadersOfSubject(
    context: Context,
    task: TaskEntity,
    listHeaders: List<String>
) = withContext(Dispatchers.IO)
{
    val generatedName = task.subjectName + ".mycfg"
    val fileStream = File(context.filesDir, generatedName)
    for (element in listHeaders) {
        fileStream.appendText(element + "\n")
    }
}

suspend fun readHeadersOfSubject(
    context: Context,
    task: TaskEntity
): List<String> = withContext(Dispatchers.IO)
{
    val generatedName = task.subjectName + ".mycfg"
    val fileStream = File(context.filesDir, generatedName)
    val lines = fileStream.readLines()
    lines.map {line -> line.trim()}
}