package com.app.masterplan.presentation

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.masterplan.presentation.ui.AppNavigation
import com.app.masterplan.presentation.ui.theme.MasterPlanTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.contains

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.enableEdgeToEdge(window)

        setContent {
//            val themeViewModel: AppThemeViewModel = hiltViewModel()
//            val isDarkTheme = themeViewModel.isDarkMode.collectAsState()

            MasterPlanTheme(darkTheme = true) {
                //SetBarColor(color = MaterialTheme.colorScheme.background)
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
//themeViewModel: AppThemeViewModel
fun MainScreen() {
    val navController = rememberNavController()
    //Получение текущего состояния экрана
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    //val profileViewModel:ProfileViewModel = hiltViewModel(LocalContext.current as ViewModelStoreOwner)
    val excludedRoutes = setOf("reg","log")

    Scaffold(
//        bottomBar = {
//            if (currentRoute !in excludedRoutes) {
//                BottomNavigationBar(navController,profileViewModel)
//            }
//        }
    ) {
            innerPadding ->
        //themeViewModel,profileViewModel
        AppNavigation(innerPadding = innerPadding, navController = navController)

    }
}
