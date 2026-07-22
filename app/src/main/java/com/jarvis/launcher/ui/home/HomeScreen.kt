package com.jarvis.launcher.ui.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jarvis.launcher.ui.navigation.JarvisDestination
import com.jarvis.launcher.ui.navigation.JarvisNavigationActions

@Composable fun HomeScreen(navController: NavController, navigationActions: JarvisNavigationActions, vm: HomeViewModel = hiltViewModel()) { val state by vm.state.collectAsState(); Box(Modifier.fillMaxSize().background(Brush.radialGradient(listOf(Color(0xFF063A52), Color(0xFF020712)))).pointerInput(Unit){detectTapGestures(onDoubleTap={navigationActions.navigate(JarvisDestination.Settings)})}) {
    ParticleField(); Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.SpaceBetween) {
        Column { Text(state.now.clock(), fontSize = 64.sp, fontWeight = FontWeight.Thin); Text(state.now.dateLine(), color = MaterialTheme.colorScheme.primary); Spacer(Modifier.height(20.dp)); Text("Weather 24°C • Clear", style = MaterialTheme.typography.titleMedium); Text(state.greeting) }
        StatsGrid(state.stats); OutlinedTextField("", {}, Modifier.fillMaxWidth().clickable { navigationActions.navigate(JarvisDestination.Search) }, placeholder={Text("Ask JARVIS or search apps")}, enabled=false, shape=RoundedCornerShape(28.dp)); Dock(state, vm) { navigationActions.navigate(JarvisDestination.Apps) }
    } } }
@Composable private fun StatsGrid(stats: com.jarvis.launcher.domain.SystemStats) { Column(Modifier.fillMaxWidth().background(Color.White.copy(.08f), RoundedCornerShape(28.dp)).padding(18.dp)) { Text("SYSTEM TELEMETRY", color=MaterialTheme.colorScheme.primary); Text("Battery ${stats.battery}%   RAM ${stats.ram}"); Text("Storage ${stats.storage}   CPU ${stats.cpu}") } }
@Composable private fun Dock(state: HomeUiState, vm: HomeViewModel, allApps: () -> Unit) { Row(Modifier.fillMaxWidth().height(82.dp).background(Color.White.copy(.10f), RoundedCornerShape(32.dp)).padding(12.dp), horizontalArrangement = Arrangement.SpaceEvenly) { state.apps.forEach { AssistChip(onClick={vm.launch(it.packageName)}, label={Text(it.label.take(8))}) }; AssistChip(onClick=allApps, label={Text("Apps")}) } }
@Composable private fun ParticleField() { val t by rememberInfiniteTransition(label="jarvis").animateFloat(0f,1f, infiniteRepeatable(tween(5000), RepeatMode.Reverse), label="pulse"); Canvas(Modifier.fillMaxSize()) { repeat(36){ i -> drawCircle(Color(0xFF00D8FF).copy(alpha=.08f + t*.08f), 2.dp.toPx(), Offset((i*97%size.width.toInt()).toFloat(), (i*173%size.height.toInt()).toFloat())) } } }
