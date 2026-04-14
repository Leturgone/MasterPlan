package com.app.masterplan.presentation.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.masterplan.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchSec(
    placeholder: String,
    onSearch: (String) -> Unit,
    onSearchEmpty: () -> Unit,
    searchHistoryState: State<MasterPlanState<List<String>>>,
    onGetSearchHistory: () -> Unit,
    onSetSearchText: (String) -> Unit,
    onClearSearchHistory: () -> Unit,

    ) {
    var query by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        onGetSearchHistory()
    }

    SearchBar(
        inputField = { SearchBarDefaults.InputField(
            query = query,
            onQueryChange = { newQuery ->
                query = newQuery
            },
            onSearch = { searchQuery ->
                if (searchQuery.isNotEmpty()) {
                    onSearch(searchQuery)
                }else {
                    onSearchEmpty()
                }
                expanded = false
            },
            expanded = expanded,
            onExpandedChange = { expanded = it },
            placeholder = { Text(placeholder) },
            leadingIcon = { Icon(
                Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.clickable {
                    if (query.isNotEmpty()) {
                        expanded = false
                        onSetSearchText(query)
                        onSearch(query)
                    }else{
                        onSearchEmpty()
                    }
                }
            ) },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    Text(
                        text = stringResource(id = R.string.clear),
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier
                            .clickable {
                                query = ""
                                expanded = false
                            }
                            .padding(horizontal = 8.dp)
                    )
                } else {
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.clickable {
                            expanded = false
                        }
                    )
                }
            }
        )},
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        if (expanded) {
            when (searchHistoryState.value) {
                is MasterPlanState.Loading -> {
                    Text(stringResource(id = R.string.loading_search_history))
                }

                is MasterPlanState.Success -> {
                    val history = (searchHistoryState.value as MasterPlanState.Success<List<String>>).result
                    if (history.isNotEmpty()) {
                        Text(
                            text = stringResource(id = R.string.clear_search_history),
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier
                                .clickable {
                                    onClearSearchHistory()
                                    expanded = false
                                }
                                .padding(horizontal = 8.dp)
                        )
                        LazyColumn {
                            items(history.size) { index ->
                                val historyElement = history[index]
                                Text(
                                    text = historyElement,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .clickable {
                                            query = historyElement
                                            onSetSearchText(historyElement)
                                            expanded = false
                                        }
                                )
                            }
                        }
                    } else {
                        Text(stringResource(id = R.string.search_history_empty))
                    }
                }

                is MasterPlanState.Failure -> {
                    Text(stringResource(id = R.string.cant_load_search_history))
                }

                else -> {
                    Text(stringResource(id = R.string.loading_search_history))
                }
            }
        }
    }
}