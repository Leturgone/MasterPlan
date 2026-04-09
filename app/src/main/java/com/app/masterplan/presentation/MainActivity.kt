package com.app.masterplan.presentation

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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

        enableEdgeToEdge()

        //Панель прозрачная
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        //ЧТобы приложение могло рисовать фон системных панелей
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        //Делаем цвет иконок нав пнели на светлые
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR


        //Чтобы контент рисовался за пределами основного экрана
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)


        WindowCompat.setDecorFitsSystemWindows(window, false)
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
