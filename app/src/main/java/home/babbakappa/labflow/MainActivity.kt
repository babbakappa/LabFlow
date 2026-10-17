package home.babbakappa.labflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import home.babbakappa.labflow.appui.MainHub
import home.babbakappa.labflow.core.database.AppDatabase
import home.babbakappa.labflow.core.repos.Repository
import home.babbakappa.labflow.ui.theme.LabFlowTheme

class MainActivity : ComponentActivity() {


    private val vm: AppViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val database = AppDatabase.getDatabase(applicationContext)
                val dao = database.databaseDao()
                val rep = Repository(dao)
                return AppViewModel(rep) as T
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            MainHub(vm)

        }
    }
}