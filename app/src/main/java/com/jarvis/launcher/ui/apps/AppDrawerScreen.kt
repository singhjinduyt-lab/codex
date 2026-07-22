package com.jarvis.launcher.ui.apps

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.jarvis.launcher.ui.navigation.JarvisNavigationActions
import com.jarvis.launcher.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel class AppsViewModel @Inject constructor(private val repo: AppRepository): ViewModel() { val apps = MutableStateFlow<List<LauncherApp>>(emptyList()); init { viewModelScope.launch { apps.value = repo.installedApps() } }; fun launch(p: String)=repo.launch(p) }
@Composable fun AppDrawerScreen(navController: NavController, navigationActions: JarvisNavigationActions, vm: AppsViewModel = hiltViewModel()) { val apps by vm.apps.collectAsState(); var query by remember { mutableStateOf("") }; Scaffold(topBar={TopAppBar(title={Text("App Drawer")}, navigationIcon={TextButton({navigationActions.back()}){Text("Home")}})}) { pad -> Column(Modifier.padding(pad).padding(16.dp)) { OutlinedTextField(query,{query=it}, Modifier.fillMaxWidth(), placeholder={Text("Search apps, folders, favorites")}); LazyColumn { items(apps.filter{it.label.contains(query,true)}) { ListItem(headlineContent={Text(it.label)}, supportingContent={Text(it.packageName)}, modifier=Modifier.fillMaxWidth(), trailingContent={Text("↗")}); HorizontalDivider() } } } } }
