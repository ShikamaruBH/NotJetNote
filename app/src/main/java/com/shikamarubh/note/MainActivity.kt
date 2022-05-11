package com.shikamarubh.note

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shikamarubh.note.model.Note
import com.shikamarubh.note.ui.theme.NoteTheme
import com.shikamarubh.note.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val noteViewModel = viewModel<NoteViewModel>()
//                    noteViewModel.removeAllNotes()
//                    noteViewModel.addNotes(Note(title = "Test1", description = "Test1 desc", entryDate = Date()))
                    mainView(noteViewModel = noteViewModel)
                }
            }
        }
    }
}

@Composable
fun mainView(noteViewModel: NoteViewModel) {
    Column(modifier = Modifier
        .padding(6.dp)
        .fillMaxSize()) {
        topBar()
        addNote(noteViewModel)
        Divider(modifier = Modifier.padding(10.dp))
        listNote(noteViewModel)
    }
}

@Composable
fun topBar() {
    Surface(modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth()
        .height(60.dp),
        color = Color(0xFFDFE6EB),
        elevation = 6.dp) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(start = 10.dp)) {
            Text(text = "Not JetNote",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp)
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp),
                horizontalArrangement = Arrangement.End) {
                Icon(imageVector = Icons.Filled.Notifications, contentDescription = "notification")
            }
        }
    }
}

@Composable
fun addNote(noteViewModel: NoteViewModel) {
    val titleText = remember {
        mutableStateOf("")
    }
    val contentText = remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 40.dp, bottom = 0.dp, top = 10.dp, end = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {
        TextField(
            value = titleText.value,
            modifier = Modifier.padding(4.dp),
            onValueChange = {
                text -> titleText.value = text
            },
            label = { Text(text = "Title")},
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFFFFFFF)
            ))
        TextField(
            value = contentText.value,
            modifier = Modifier.padding(4.dp),
            onValueChange = {
                    text -> contentText.value = text
            },
            label = { Text(text = "Add a note")},
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFFFFFFF)
            ))
        Button(
            onClick = {
                    noteViewModel.addNotes(
                        Note(title = titleText.value,
                            description = contentText.value,
                            entryDate = Date())
                    )
                    titleText.value = ""
                    contentText.value = ""
                },
            elevation = ButtonDefaults.elevation(4.dp,0.dp),
            modifier = Modifier.padding(4.dp),
            shape = RoundedCornerShape(24.dp)
            ) {
            Text(text = "Save")
        }
    }
}

@Composable
fun listNote(noteViewModel: NoteViewModel) {
    val data by noteViewModel.noteList.collectAsState()
    LazyColumn {
        items(data) {
            item ->
                Surface(modifier = Modifier
                    .padding(4.dp)
                    .clip(RoundedCornerShape(topStart = 33.dp, bottomEnd = 33.dp))
                    .fillMaxWidth(),
                    color = Color(0xFFDFE6EB),
                    elevation = 6.dp
                ) {
                    Row() {
                        Column(modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .clickable { /* Nothing yet */ },
                            horizontalAlignment = Alignment.Start) {
                            Text(modifier = Modifier.padding(2.dp), text = item.title, style = MaterialTheme.typography.subtitle2)
                            Text(modifier = Modifier.padding(2.dp), text = item.description, style = MaterialTheme.typography.subtitle1)
                            Text(modifier = Modifier.padding(2.dp), text = SimpleDateFormat("E,d MMM, y, h:m a").format(item.entryDate), style = MaterialTheme.typography.caption)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Filled.Close,
                                contentDescription = "remove",
                                modifier = Modifier.clickable {
                                    noteViewModel.removeNotes(item)
                                })
                        }
                    }
                }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NoteTheme {
        Greeting("Android")
    }
}