package com.app.masterplan.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.masterplan.presentation.ui.AppNavigation
import com.app.masterplan.presentation.ui.bottomBar.bar.BottomBar
import com.app.masterplan.presentation.ui.options.viewmodel.OptionsViewModel
import com.app.masterplan.presentation.ui.theme.MasterPlanTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.enableEdgeToEdge(window)

        setContent {
            val viewModel: OptionsViewModel by viewModels()
            val isDarkTheme = viewModel.isDarkMode.collectAsState()

            MasterPlanTheme(darkTheme = isDarkTheme.value) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    //Получение текущего состояния экрана
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val excludedRoutes = setOf("login")

    Scaffold(
        bottomBar = {
            if (currentRoute !in excludedRoutes) {
                BottomBar(navController)
            }
        }
    ) {
            innerPadding ->
        AppNavigation(innerPadding = innerPadding, navController = navController)

    }
}
