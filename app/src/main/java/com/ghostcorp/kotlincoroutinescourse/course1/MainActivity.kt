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
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import kotlin.concurrent.thread

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

      CoroutineScope(Dispatchers.IO).launch {
          main()
      }
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

///////////////////////   First Example //////////////////////

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

/////////////  Second Example /////////////////

suspend fun task1 () {
    Log.d("tag>>>","STARTING TASK 1")
    Log.d("tag>>>","ENDING TASK 1")
}

suspend fun task2() {
    Log.d("tag>>>","STARTING TASK 2")
    yield()
    Log.d("tag>>>","ENDING TASK 2")
}

//////////// Third Example /////////////

private suspend fun getFbFollowers() : Int {
    delay(1000)
    return 54
}

private suspend fun printFollowers() {
    var fbFollowers = 0
    val job = CoroutineScope(Dispatchers.IO).launch {
        fbFollowers = getFbFollowers()
    }
    job.join()
    Log.d("tag>>>>",fbFollowers.toString())
}

/////// Fourth Example ////////

private suspend fun printFollowers2() {
    val job = CoroutineScope(Dispatchers.IO).async {
        getFbFollowers2()
    }

    val result = job.await()

    Log.d("tag>>>>",result.toString())
}

private suspend fun getFbFollowers2() : Int {
    delay(1000)
    return 54
}

//////// Fifth Example ////////

private suspend fun getInstaFollowers() : Int {
    delay(1000)
    return 113
}

private suspend fun printFollowers3() {
    var fbFollowers = 0
    var instaFollowers = 0
    val job = CoroutineScope(Dispatchers.IO).launch {
        fbFollowers = getFbFollowers()
    }

    val job2 = CoroutineScope(Dispatchers.IO).launch {
        instaFollowers = getInstaFollowers()
    }
    job.join()
    job2.join()
    Log.d("tag>>>>","FB $fbFollowers, Insta $instaFollowers")
}


//Sixth Example //////

private suspend fun printFollowers4() {
    val fb = CoroutineScope(Dispatchers.IO).async {
        getFbFollowers2()
    }

    val insta = CoroutineScope(Dispatchers.IO).async {
        getInstaFollowers()
    }

    val fbCount = fb.await()

    val instaCount = insta.await()

    Log.d("tag>>>>","FB $fbCount, Insta $instaCount")
}


// Seventh Example //////

private suspend fun printFollowers5() {
    CoroutineScope(Dispatchers.IO).launch {
        var fb = async { getFbFollowers2() }
        var insta = async { getInstaFollowers() }

        val fbCount = fb.await()

        val instaCount = insta.await()

        Log.d("tag>>>>","FB $fbCount, Insta $instaCount")
    }
}

//Eighth Example

private suspend fun execute() {
    val parentJob = GlobalScope.launch(Dispatchers.Main) {
        Log.d("tag>>>>","Parent - $coroutineContext")
        Log.d("tag>>>>","Parent Started")


        val childJob = launch {
            Log.d("tag>>>>","Child Job Started")
            delay(5000)
            Log.d("tag>>>>","Child Job Ended")
        }

        delay(3000)

        Log.d("tag>>>>","Parent Ended")
    }

    delay(1000)
    parentJob.cancel()
    parentJob.join()
    Log.d("tag>>>>","Parent Completed")
}


// Ninth Example////
private suspend fun execute2() {
    val parentJob = CoroutineScope(Dispatchers.IO).launch{
        for(i in 1..1000) {
            Log.d("tag>>>>",i.toString())
            executeLongRunningTask2()
        }
    }

    delay(100)
    Log.d("tag>>>>","Cancelling Job ")
    parentJob.cancel()
    parentJob.join()
    Log.d("tag>>>>","parent job completed")
}

private fun executeLongRunningTask2(){
    for(i in 1..100000000000){

    }
}

// Tenth Example

private suspend fun executeTask() {
    Log.d("tag>>>>","Before")
    withContext(Dispatchers.IO) {
        delay(1000)
        Log.d("tag>>>>","Inside")
    }
    Log.d("tag>>>>","After")
}


// Eleventh Example

private fun main() {
    runBlocking {
        launch {
            delay(1000)
            Log.d("tag>>>>","World")
        }

        Log.d("tag>>>>","Hello")
    }
}


















