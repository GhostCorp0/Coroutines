package com.ghostcorp.kotlincoroutinescourse.course2

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import kotlinx.coroutines.yield
import org.junit.Assert
import org.junit.Test
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

// Example of how threads work in parallel

/*
fun main() {

    println("Main Program Starts : ${Thread.currentThread().name}")

    thread {
        println("Fake Work Starts : ${Thread.currentThread().name}")
        Thread.sleep(1000) //////// Pretending to do some work  may be file upload
        println("Fake Work Finished : ${Thread.currentThread().name}")
    }


    println("Main Program Ends : ${Thread.currentThread().name}")

}*/


// A minimal example of coroutine
/*
fun main() {

    println("Main Program Starts : ${Thread.currentThread().name}")

    GlobalScope.launch {
        println("Fake Work Starts : ${Thread.currentThread().name}")
        Thread.sleep(1000) //////// Pretending to do some work  may be file upload
        println("Fake Work Finished : ${Thread.currentThread().name}")
    }
    // Blocks the current main thread & wait for coroutine to finish (practically not a right way to wait)
    Thread.sleep(2000)
    println("Main Program Ends : ${Thread.currentThread().name}")
}*/

// Example of using delay to suspend the coroutine

/*
fun main() {

    println("Main Program Starts : ${Thread.currentThread().name}")

    GlobalScope.launch {
        println("Fake Work Starts : ${Thread.currentThread().name}")
        delay(1000) // Coroutine is suspended but Thread is not blocked
        println("Fake Work Finished : ${Thread.currentThread().name}")
    }
    // Blocks the current main thread & wait for coroutine to finish (practically not a right way to wait)
    Thread.sleep(2000)
    println("Main Program Ends : ${Thread.currentThread().name}")
}*/

// Global scope launch example code
/*
fun main() {
    runBlocking {
        println("Main Program Starts : ${Thread.currentThread().name}")

        GlobalScope.launch {
            println("Fake Work Starts : ${Thread.currentThread().name}")
            delay(1000) // Coroutine is suspended but Thread is not blocked
            println("Fake Work Finished : ${Thread.currentThread().name}")
        }
        // Blocks the current main thread & wait for coroutine to finish (practically not a right way to wait)

        delay(2000) //Create a coroutine that blocks the main thread.

        println("Main Program Ends : ${Thread.currentThread().name}")
    }
}*/


// using launch example instead of Global scope
// This will return the name of thread as main because its parent is runBlocking and it is running on main
// that's why the child will return the thread name as main

// Example of job.join() and job.cancel()
/*fun main() {
    runBlocking {
        println("Main Program Starts : ${Thread.currentThread().name}")

        val job: Job = launch {
            println("Fake Work Starts : ${Thread.currentThread().name}")
            delay(1000) // Coroutine is suspended but Thread is not blocked
            println("Fake Work Finished : ${Thread.currentThread().name}")
        }
        // Blocks the current main thread & wait for coroutine to finish (practically not a right way to wait)

     //   delay(2000) //Create a coroutine that blocks the main thread.

        // delay was not the appropriate way here to wait for the coroutine to complete
        // so instead of that here we have to use job.join() cause CoroutineScope.launch() returns
        // a job object and it has a .join() method which will wait till the coroutine is completely executed
        // and only after the complete execution of coroutine next statement will be executed.

        job.join()

        // Also we can cancel the coroutine using job.cancel() method

        //job.cancel()

        println("Main Program Ends : ${Thread.currentThread().name}")
    }
}*/


// Async coroutine builder example

// Async returns deferredJob object instead of job object and this deferred is of generic type

/*
fun main() {
    runBlocking {
        println("Main Program Starts : ${Thread.currentThread().name}")

        val jobDeferred: Deferred<Int> = async {
            println("Fake Work Starts : ${Thread.currentThread().name}")
            delay(1000) // Coroutine is suspended but Thread is not blocked
            println("Fake Work Finished : ${Thread.currentThread().name}")
            15 // returning a int value
        }

        val result = jobDeferred.await()

        // use this result to get the return result from async


        println("Main Program Ends : ${Thread.currentThread().name}")
    }
}
*/


//runBlocking example

// To test the testcases with coroutines so that it doesn't mark as complete till the coroutine is fully executed.

/*
@Test
fun myFirstTest() = runBlocking{
    myOwnSuspendingFunc()
    Assert.assertEquals(10,5+5)
}


suspend fun myOwnSuspendingFunc() {
    delay(1000)
}
*/


// Cooperative coroutine example

// With Delay

/*
fun main () = runBlocking {  // Creates a blocking coroutine that executes in current thread (main)

    println("Main program starts : ${Thread.currentThread().name}") // main thread

    val job:Job = launch {  // Thread T1: Crates a non-blocking coroutine
        for(i in 0..500) {
            print("$i.")
           // Thread.sleep(50)
            delay(50)
        }
    }

    delay(200) // Let's print a few values before we cancel
    job.cancel()
    job.join() // Waits for coroutine to finish

    // We can also use
    // job.cancelAndJoin()  it will work same as job.cancel() and job.join()

    println("\nMain program ends: ${Thread.currentThread().name}") //main thread
}
*/


// Using yield

/*
fun main () = runBlocking {  // Creates a blocking coroutine that executes in current thread (main)

    println("Main program starts : ${Thread.currentThread().name}") // main thread

    val job:Job = launch {  // Thread T1: Crates a non-blocking coroutine
        for(i in 0..500) {
            print("$i.")
            // Thread.sleep(50)
            yield() // it does not delay
        }
    }

    delay(200) // Let's print a few values before we cancel
    // We can also use
     job.cancelAndJoin() // it will work same as job.cancel() and job.join()

    println("\nMain program ends: ${Thread.currentThread().name}") //main thread
}
*/

// Second way to make coroutine cooperative
// by using isActive boolean flag

/*fun main () = runBlocking {  // Creates a blocking coroutine that executes in current thread (main)

    println("Main program starts : ${Thread.currentThread().name}") // main thread

    val job:Job = launch(Dispatchers.Default) {  // Thread T1: Crates a non-blocking coroutine
        for(i in 0..500) {
            if(!isActive) {
                break
            }
            print("$i.")
            Thread.sleep(1)
        }
    }

    delay(200) // Let's print a few values before we cancel
    // We can also use
    job.cancelAndJoin() // it will work same as job.cancel() and job.join()

    println("\nMain program ends: ${Thread.currentThread().name}") //main thread
}*/


// Example of Cancellation Exception

/*
fun main () = runBlocking {  // Creates a blocking coroutine that executes in current thread (main)

    println("Main program starts : ${Thread.currentThread().name}") // main thread

    val job:Job = launch(Dispatchers.Default) {  // Thread T1: Crates a non-blocking coroutine
        try {
            for(i in 0..500) {
                print("$i.")
                delay(5)
            }
        }catch (ex:CancellationException) {
            print("\nException caught safely")
        }finally {
            print("\nClose resources in finally")
        }
    }

    delay(200) // Let's print a few values before we cancel
    // We can also use
    job.cancelAndJoin() // it will work same as job.cancel() and job.join()

    println("\nMain program ends: ${Thread.currentThread().name}") //main thread
}
*/

// Example of withContext(NonCancellable)

/*
fun main () = runBlocking {  // Creates a blocking coroutine that executes in current thread (main)

    println("Main program starts : ${Thread.currentThread().name}") // main thread

    val job:Job = launch(Dispatchers.Default) {  // Thread T1: Crates a non-blocking coroutine
        try {
            for(i in 0..500) {
                print("$i.")
                delay(5)
            }
        }catch (ex:CancellationException) {
            print("\nException caught safely")
        }finally {
            withContext(NonCancellable){
                delay(1000)
                print("\nClose resources in finally")
            }
        }
    }

    delay(200) // Let's print a few values before we cancel
    // We can also use
    job.cancelAndJoin() // it will work same as job.cancel() and job.join()

    println("\nMain program ends: ${Thread.currentThread().name}") //main thread
}
*/


// Example of passing custom message for cancellation exception

/*
fun main () = runBlocking {  // Creates a blocking coroutine that executes in current thread (main)

    println("Main program starts : ${Thread.currentThread().name}") // main thread

    val job:Job = launch(Dispatchers.Default) {  // Thread T1: Crates a non-blocking coroutine
        try {
            for(i in 0..500) {
                print("$i.")
                delay(5)
            }
        }catch (ex:CancellationException) {
            print("\nException caught safely : ${ex.message}")
        }finally {
            withContext(NonCancellable){
                delay(1000)
                print("\nClose resources in finally")
            }
        }
    }

    delay(200) // Let's print a few values before we cancel
    job.cancel(CancellationException("My own crash message"))
    job.join()

    println("\nMain program ends: ${Thread.currentThread().name}") //main thread
}
*/

// Example of timeouts

// withTimeout
/*fun main () = runBlocking {  // Creates a blocking coroutine that executes in current thread (main)

    println("Main program starts : ${Thread.currentThread().name}") // main thread

    withTimeout(2000) {
        try {
            for(i in 0..500) {
                print("$i")
                delay(500)
            }
        }catch (ex:TimeoutCancellationException) {
            print("")
        }finally {

        }
    }
    println("\nMain program ends: ${Thread.currentThread().name}") //main thread
}*/

// withTimeoutOrNull

/*
fun main () = runBlocking {  // Creates a blocking coroutine that executes in current thread (main)

    println("Main program starts : ${Thread.currentThread().name}") // main thread

    val result:String? = withTimeoutOrNull(2000) {
        for(i in 0..500) {
            print("$i")
            delay(500)
        }

        "I am done"
    }
    println("\nResult : $result") //main thread


    println("\nMain program ends: ${Thread.currentThread().name}") //main thread
}
*/

// Examples of composing suspending functions

/*
fun main() = runBlocking {
    println("Main program starts : ${Thread.currentThread().name}") // main thread

    val time = measureTimeMillis {
        val msgOne:Deferred<String> = async {getMessageOne()}

        val msgTwo:Deferred<String> = async{getMessageTwo()}

        println("The entire message is : ${msgOne.await() + msgTwo.await()}")
    }

    println("Completed in $time ms ")

    println("\nMain program ends: ${Thread.currentThread().name}") //main thread
}

suspend fun getMessageOne() : String {
    delay(1000L)
    return "Hello"
}

suspend fun getMessageTwo() : String {
    delay(1000L)
    return "World!"
}
*/

// Lazy coroutine execution with async

/*
fun main() = runBlocking {
    println("Main program starts : ${Thread.currentThread().name}") // main thread

    val msgOne:Deferred<String> = async(start = CoroutineStart.LAZY) {getMessageOne()}

    val msgTwo:Deferred<String> = async(start = CoroutineStart.LAZY){getMessageTwo()}

    //println("The entire message is : ${msgOne.await() + msgTwo.await()}")  if not printing the result the coroutines will not execute.

    println("\nMain program ends: ${Thread.currentThread().name}") //main thread
}

suspend fun getMessageOne() : String {
    delay(1000L)
    println("After working in getMessageOne()")
    return "Hello"
}

suspend fun getMessageTwo() : String {
    delay(1000L)
    println("After working in getMessageTwo()")
    return "World!"
}
*/


// CoroutineScope Example

/*
fun main()= runBlocking {

    println("runBlocking : $this")

    launch {
        print("launch: $this")

        launch {
            print("child launch: $this")
        }
    }

    async {
        print("launch: $this")
    }

    println("...some other code ... ")

}
*/


// CoroutineContext Example

/*
fun main() = runBlocking { //Thread : main

    // this:CoroutineScope instance

    //  coroutineContext : CoroutineContext instance

    // Without parameter: CONFINED [CONFINED DISPATCHER].../

    launch {
        println("C1 : ${Thread.currentThread().name}") //Thread: main
        delay(1000)
        println("C1 after delay : ${Thread.currentThread().name}") //Thread: main

        //It takes the coroutine context from the immediate parent coroutine
    }

    // With parameter : Dispatchers.Default [similar to GlobalScope.launch{}] .. /
    // Gets its own context at Global level. Executes in a separate background thread.
    // After delay() or suspending function execution, it continues to run either in the same thread or other thread.

    launch(Dispatchers.Default) {
        println("C2 : ${Thread.currentThread().name}") //Thread: T1
        delay(1000)
        println("C2 after delay : ${Thread.currentThread().name}") //Thread: T1 or some other thread
    }

    // With parameter: Dispatchers.Unconfined [UNCONFINED DISPATCHER] .../
    // Inherits CoroutineContext from the immediate parent coroutine.
    // After delay() or suspending function execution, it continues to run in some other thread

    launch(Dispatchers.Unconfined) {

        println("C3 : ${Thread.currentThread().name}") //Thread: main

        delay(100)

        println("C3 after delay: ${Thread.currentThread().name}") //Thread: some other thread

    }


   */
/* launch(Dispatchers.Main) {
        println("C4 : ${Thread.currentThread().name}") //Thread: T1
        delay(1000)
        println("C4 after delay : ${Thread.currentThread().name}") //Thread: T1 or some other thread
    }*//*


    launch(Dispatchers.IO) {
        println("C5 : ${Thread.currentThread().name}") //Thread: T1
        delay(1000)
        println("C5 after delay : ${Thread.currentThread().name}") //Thread: T1 or some other thread
    }

    //Using coroutineContext property to flow context from parent to child.
    launch(coroutineContext) {
        println("C6 : ${Thread.currentThread().name}") //Thread: T1
        delay(1000)
        println("C6 after delay : ${Thread.currentThread().name}") //Thread: T1 or some other thread
    }

    print("...Main Program...")
}

*/
// Coroutine name

fun main()= runBlocking {

    println("runBlocking : $this")

 //   val scope = CoroutineScope(coroutineContext + CoroutineName("MyCoroutine")) // returns main coz parent coroutine context is main

    val scope = CoroutineScope(CoroutineName("MyCoroutine"))  // returns default dispatcher because coroutine scope without any parameter

    scope.launch {
        println("Running in: ${Thread.currentThread().name}")
        println("Coroutine Context: ${coroutineContext}")
    }

    println("...some other code ... ")

}

