package com.jarvis.launcher.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jarvis.launcher.ui.navigation.JarvisNavigationActions

@Composable fun SearchScreen(navController: NavController, navigationActions: JarvisNavigationActions) { var q by remember { mutableStateOf("") }; Scaffold(topBar={TopAppBar(title={Text("Universal Search")}, navigationIcon={TextButton({navigationActions.back()}){Text("Back")}})}) { pad -> Column(Modifier.padding(pad).padding(16.dp), verticalArrangement=Arrangement.spacedBy(12.dp)) { OutlinedTextField(q,{q=it}, Modifier.fillMaxWidth(), placeholder={Text("Apps, contacts, files, settings, web")}); listOf("Installed apps","Contacts","Files","Settings","Web results","AI suggestions").forEach{ AssistChip(onClick={}, label={Text(it)}) } } } }
