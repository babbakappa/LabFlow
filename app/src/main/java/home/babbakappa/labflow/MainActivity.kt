package home.babbakappa.labflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import home.babbakappa.labflow.appui.MainHub
import home.babbakappa.labflow.database.database.AppDatabase
import home.babbakappa.labflow.manager.AppViewModel
import home.babbakappa.labflow.ui.theme.LabFlowTheme

class MainActivity : ComponentActivity() {


    private val vm: AppViewModel by viewModels {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                // Здесь applicationContext уже будет доступен
                val database = AppDatabase.getDatabase(applicationContext)
                val dao = database.databaseDao()
                return AppViewModel(dao) as T
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            LabFlowTheme()
            {
                MainHub(vm)

            }

        }
    }
}