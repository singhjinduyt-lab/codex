package com.jarvis.launcher.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.jarvis.launcher.ui.navigation.JarvisDestination
import com.jarvis.launcher.ui.navigation.JarvisNavigationActions
import com.jarvis.launcher.data.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel class SettingsViewModel @Inject constructor(val repo: SettingsRepository): ViewModel() { val settings = repo.settings; fun provider(p:String)=viewModelScope.launch{repo.setProvider(p)} }
@Composable fun SettingsScreen(navController: NavController, navigationActions: JarvisNavigationActions, vm: SettingsViewModel = hiltViewModel()) { val s by vm.settings.collectAsState(initial = com.jarvis.launcher.data.LauncherSettings()); Scaffold(topBar={TopAppBar(title={Text("JARVIS Settings")}, navigationIcon={TextButton({navigationActions.back()}){Text("Back")}})}) { pad -> Column(Modifier.padding(pad).padding(16.dp), verticalArrangement=Arrangement.spacedBy(14.dp)) { Text("Themes • Accent colors • Animations • Fonts • Icon packs • Wallpaper • Grid • Dock", style=MaterialTheme.typography.titleMedium); Text("Active AI provider: ${s.provider}"); listOf("Google AI Studio","OpenAI","OpenRouter","Gemini","Claude","DeepSeek").forEach { Button({vm.provider(it)}, Modifier.fillMaxWidth()) { Text(it) } }; Button({navigationActions.navigate(JarvisDestination.Chat)}, Modifier.fillMaxWidth()) { Text("Open AI Chat") } } } }
