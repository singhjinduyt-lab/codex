package com.jarvis.launcher.ui.chat

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
import com.jarvis.launcher.data.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel class ChatViewModel @Inject constructor(private val dao: ConversationDao): ViewModel() { val messages = dao.observe(); fun send(text:String)=viewModelScope.launch{dao.insert(ConversationEntity(role="user", content=text)); dao.insert(ConversationEntity(role="assistant", content="Streaming-ready JARVIS response for: **$text**"))} }
@Composable fun ChatScreen(navController: NavController, navigationActions: JarvisNavigationActions, vm: ChatViewModel = hiltViewModel()) { val messages by vm.messages.collectAsState(initial= emptyList()); var input by remember{ mutableStateOf("") }; Scaffold(topBar={TopAppBar(title={Text("AI Chat")}, navigationIcon={TextButton({navigationActions.back()}){Text("Back")}})}) { pad -> Column(Modifier.padding(pad).padding(16.dp)) { LazyColumn(Modifier.weight(1f)){ items(messages){ ElevatedCard(Modifier.fillMaxWidth().padding(vertical=4.dp)){ Column(Modifier.padding(12.dp)){ Text(it.role.uppercase(), color=MaterialTheme.colorScheme.primary); Text(it.content) } } } }; Row { OutlinedTextField(input,{input=it}, Modifier.weight(1f), placeholder={Text("Message JARVIS")}); Button({ if(input.isNotBlank()){vm.send(input); input=""} }){Text("Send")} } } } }
