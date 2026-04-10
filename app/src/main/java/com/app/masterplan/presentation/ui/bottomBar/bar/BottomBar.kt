package com.app.masterplan.presentation.ui.bottomBar.bar

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.MailOutline
import androidx.compose.material.icons.rounded.ModeEditOutline
import androidx.compose.material.icons.rounded.PersonOutline
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.app.masterplan.R
import com.app.masterplan.presentation.ui.bottomBar.components.BottomNavigationItem
import com.app.masterplan.presentation.ui.bottomBar.viewModel.BottomBarViewModel
import com.app.masterplan.presentation.ui.common.MasterPlanState


@Composable
fun BottomBar(
    navController: NavHostController,
    viewModel: BottomBarViewModel = hiltViewModel()
) {

    val directorItems = listOf(
        BottomNavigationItem(
            route = "emp_list",
            icon = Icons.Rounded.Groups,
            title = stringResource(id = R.string.employees)
        ),

        BottomNavigationItem(
            route = "reports",
            icon = Icons.Rounded.MailOutline,
            title = stringResource(id = R.string.reports)
        ),

        BottomNavigationItem(
            route = "plans",
            icon = Icons.AutoMirrored.Rounded.List,
            title = stringResource(id = R.string.plans)
        ),

        BottomNavigationItem(
            route = "tasks",
            icon = Icons.Rounded.ModeEditOutline,
            title = stringResource(id = R.string.tasks)
        ),

        BottomNavigationItem(
            route = "profile",
            icon = Icons.Rounded.PersonOutline,
            title = stringResource(id = R.string.profile)
        ),

        )


    val employeeItems = listOf(

        BottomNavigationItem(
            route = "reports",
            icon = Icons.Rounded.MailOutline,
            title = stringResource(id = R.string.reports)
        ),


        BottomNavigationItem(
            route = "tasks",
            icon = Icons.Rounded.ModeEditOutline,
            title = stringResource(id = R.string.tasks)
        ),

        BottomNavigationItem(
            route = "profile",
            icon = Icons.Rounded.PersonOutline,
            title = stringResource(id = R.string.profile)
        ),
    )


    val adminItems = listOf(
        BottomNavigationItem(
            route = "requests",
            icon = Icons.Rounded.MailOutline,
            title = stringResource(id = R.string.requests)
        ),

        BottomNavigationItem(
            route = "employees",
            icon = Icons.Rounded.AccountCircle,
            title = stringResource(id = R.string.employees)
        ),

        BottomNavigationItem(
            route = "options",
            icon = Icons.Rounded.Settings,
            title = stringResource(id = R.string.options)
        ),
    )

    viewModel.setLists(directorItems, employeeItems, adminItems)

    val itemsListState = viewModel.itemsFlow.collectAsState()

    viewModel.loadItems()

    when (itemsListState.value){
        MasterPlanState.Loading -> CircularProgressIndicator()
        is MasterPlanState.Success -> {
            val items  = (itemsListState.value as MasterPlanState.Success).result
            BottomNavigationBar(navController, items)
        }
        MasterPlanState.Waiting -> null
        is MasterPlanState.Failure -> {
            Log.e("BottomBar",
                (itemsListState.value as MasterPlanState.Failure).exception.message.toString()
            )
        }
    }


}

@Composable
fun BottomNavigationBar(navController: NavHostController,items: List<BottomNavigationItem>) {
    NavigationBar(containerColor = (MaterialTheme.colorScheme.secondaryContainer)){

        //Отслеживание текушего маршрута
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        Row()
        {
            items.forEach { item->
                NavigationBarItem(selected = currentRoute == item.route, modifier = Modifier.semantics
                { contentDescription = item.route },
                    onClick = {
                        navController.popBackStack()
                        navController.navigate(item.route)
                    },
                    icon = {
                        Icon(imageVector = item.icon,
                            contentDescription =item.route,
                            tint = MaterialTheme.colorScheme.onSecondaryContainer)
                    },
                    label = {
                        Text(text = item.title, color = MaterialTheme.colorScheme.onSecondaryContainer)
                    })
            }
        }
    }
}

