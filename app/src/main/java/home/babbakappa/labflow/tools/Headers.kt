package home.babbakappa.labflow.tools

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

suspend fun writeHeadersForSubject(context: Context, subjectName: String, arr: List<String>) = withContext(Dispatchers.IO) {
    val file = File(context.filesDir, "$subjectName.txt")
    file.writeText(arr.joinToString(separator = System.lineSeparator()))
}

suspend fun readHeadersForSubject(context: Context, subjectName: String): List<String> = withContext(Dispatchers.IO) {
    try {
        val file = File(context.filesDir, "$subjectName.txt")

        // Список дефолтных заголовков на случай, если файла нет или он пустой
        val defaultHeaders = listOf("Шаг 1", "Шаг 2", "Шаг 3", "Шаг 4")

        if (file.exists()) {
            val lines = file.readLines()
            // Если в файле почему-то записано меньше 4 строк, дополняем дефолтными
            if (lines.size < 4) {
                lines + defaultHeaders.takeLast(4 - lines.size)
            } else {
                lines
            }
        } else {
            defaultHeaders
        }
    } catch (error: Exception) {
        error.printStackTrace()
        // При любой непредвиденной ошибке тоже отдаем дефолт, чтобы UI не падал
        listOf("Шаг 1", "Шаг 2", "Шаг 3", "Шаг 4")
    }
}

