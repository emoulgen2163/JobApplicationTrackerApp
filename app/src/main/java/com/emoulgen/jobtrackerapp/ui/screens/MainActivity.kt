package com.emoulgen.jobtrackerapp.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.emoulgen.jobtrackerapp.ui.screens.jobDetail.JobEntryScreen
import com.emoulgen.jobtrackerapp.ui.screens.main.MainScreen
import com.emoulgen.jobtrackerapp.ui.theme.JobTrackerAppTheme
import com.emoulgen.jobtrackerapp.ui.viewModel.JobViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JobTrackerAppTheme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController){
    val jobViewModel: JobViewModel = hiltViewModel()

    NavHost(navController, startDestination = "Home") {

        composable("Home"){
            MainScreen(navController = navController, jobViewModel = jobViewModel)
        }

        composable("AddOrEdit"){
            JobEntryScreen(navController = navController, viewModel = jobViewModel)
        }
    }

}
