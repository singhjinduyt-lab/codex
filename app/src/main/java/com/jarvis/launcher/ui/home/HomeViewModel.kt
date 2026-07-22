package com.jarvis.launcher.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarvis.launcher.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

data class HomeUiState(val now: LocalDateTime = LocalDateTime.now(), val stats: SystemStats = SystemStats(0,"—","—","—"), val apps: List<LauncherApp> = emptyList(), val greeting: String = "Systems online. Good day, sir.")
@HiltViewModel class HomeViewModel @Inject constructor(private val apps: AppRepository, private val system: SystemRepository): ViewModel() {
    private val _state = MutableStateFlow(HomeUiState()); val state: StateFlow<HomeUiState> = _state
    init { refresh() }
    fun refresh() = viewModelScope.launch { _state.value = _state.value.copy(now = LocalDateTime.now(), stats = system.stats(), apps = apps.installedApps().take(5)) }
    fun launch(packageName: String) = apps.launch(packageName)
}
fun LocalDateTime.clock() = format(DateTimeFormatter.ofPattern("HH:mm")); fun LocalDateTime.dateLine() = format(DateTimeFormatter.ofPattern("EEEE, MMM d"))
