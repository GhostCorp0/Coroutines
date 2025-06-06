package com.ghostcorp.kotlincoroutinescourse.course1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ghostcorp.kotlincoroutinescourse.ui.theme.KotlinCoroutinesCourseTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinCoroutinesCourseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    var clickCount by remember { mutableStateOf(0) }

    Column {
        Box(
            modifier = Modifier.fillMaxWidth().height(200.dp).padding(16.dp)
        ) {
            Text(
                text = "${clickCount}",
                modifier = modifier.align(Alignment.Center)
            )
        }


        Button(
            onClick = {
                clickCount++
            },modifier = modifier.fillMaxWidth().height(50.dp)
        ) { Text(text = "Click Me") }

        Button(
            onClick = {
                doActionWithCoroutines()
            },modifier = modifier.fillMaxWidth().height(50.dp)
        ) { Text(text = "Do Action") }
    }
}

// How long running tasks were handled before coroutines with the help of thread.
private fun doActionWithThread() {
    thread(start = true) {
        executeLongRunningTask()
    }
}

// Long running task handled with coroutines
private fun doActionWithCoroutines(){
    // Using CoroutineScope
    CoroutineScope(Dispatchers.IO).launch {
        Log.d("coroutine>>>","1>>${Thread.currentThread().name}")
    }

    GlobalScope.launch(Dispatchers.Main) {
        Log.d("coroutine>>>","2>>${Thread.currentThread().name}")
    }

    MainScope().launch(Dispatchers.Default) {
        Log.d("coroutine>>>","3>>${Thread.currentThread().name}")
    }


}

// Long running task
private fun executeLongRunningTask(){
    for(i in 1..100000000000000L){
    }
}



