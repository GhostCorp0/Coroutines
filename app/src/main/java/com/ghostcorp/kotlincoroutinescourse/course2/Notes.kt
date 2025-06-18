package com.ghostcorp.kotlincoroutinescourse.course2

class Notes {
    /**
     *  What are coroutines?
     *
     *  App has a main thread to perform small operations,UI interaction,button click,mathematical operations or small logic operations.
     *
     *  but what about long operations,network operation,file download,image loading,database queries if we run these operations also on main thread then
     *  the main thread will get block and application will get freezed and eventually will face a crash and its a really bad idea.
     *
     *  We can use a background thread or worker thread to run these long operations.
     *
     *  Creating threads is very very expensive and device can become out of memory if we create too many threads.
     *
     *  So here is the solution for this Coroutines.
     *
     *   One background thread -
     *                      -- Coroutine 1- File Upload
     *                      -- Coroutine 2 - Network Operation
     *                      -- Coroutine 3 - File Download
     *                      -- Coroutine 4 - Image Loading
     *                      -- Coroutine 5 - Database Queries
     *
     *
     * Coroutines are lightweight threads.
     *
     *        Like threads, coroutines can run in parallel,wait for each other and communicate with each other also.
     *
     *        Coroutine != Thread
     *
     *        Coroutines are very very cheap almost free.Create thousands of them without any memory issue.
     *
     *        Best for modern application.
     *
     *
     *        -- How to use coroutines
     *
     *
     *        -- Unlike threads, for coroutines the application by default does not wait for it to finish
     *        its execution.
     *
     *        --What are suspend modifier? What are suspending functions ?
     *
     *       A function with a suspend modifier is known as suspending function
     *
     *       The suspending functions are only allowed to be called from a coroutine or from another suspending function.
     *
     *       They cannot be called from outside of a coroutine.
     *
     *
     *       -- Coroutine Builders
     *
     *       These are the functions using which we create coroutines.
     *
     *       Three coroutine builders are :
     *
     *       -- launch  --(We have GlobalScope.launch{})
     *
     *       -- async  -- (We have GlobalScope.async{})
     *
     *       -- ruBlocking  -- (We don't have such in runBlocking)
     *
     *
     *       Global Coroutines are the top-level coroutines and can survive the entire life of the application.
     *
     *       // Create coroutines at global (app) level.
     *       GlobalScope.launch {
     *           // File download
     *
     *           // Play music
     *       }
     *
     *       // Create coroutines at local level.
     *       launch {
     *          // Some data computation
     *
     *          // Some login operation
     *
     *       }
     *
     *       Using GlobalScope.launch {} is highly discouraged. Use only when needed.
     *
     *       -- launch coroutine builder (Fire and Forget)
     *
     *        -- Launches a new coroutine without blocking the current thread
     *
     *        -- Inherits the thread & coroutine scope of the immediate parent coroutine.
     *
     *
     *        -- Returns a reference to the Job object.
     *
     *        -- Using Job object you can cancel the coroutine or wait for coroutine to finish.
     *
     *
     *
     *        -- async coroutine builder
     *
     *        -- Launches a coroutine without blocking the current thread.
     *
     *        -- Inherits the thread & coroutine scope of the immediate parent coroutine
     *
     *        -- Returns a reference to the Deferred<T> object.
     *
     *        -- Using Deferred object you can cancel the coroutine, wait for coroutine to finish, or retrieve
     *        -- the returned result.
     *
     *
     *        -- runBlocking coroutine builder
     *
     *         -- remember async and launch never blocks the thread in which they operates.
     *
     *         -- but the runBlocking blocks the thread in which it operates until the coroutine is fully executed.
     *
     *
     *         -- Coroutine Cancellation
     *
     *         -- Cancellation, Timeouts and Exception Handling
     *
     *         -- Why would you cancel a Coroutine ?
     *
     *             -- Result no longer needed ?
     *             -- Coroutine is taking too long to respond ?
     *
     *         -- To cancel a coroutine, it should be cooperative
     *
     *         -- val job = launch {
     *               // code has to be cooperative in order to get cancelled.
     *         }
     *
     *         -- job.cancel() // if the coroutine is cooperative then cancel it
     *
     *         -- job.join() // Waits for the coroutine to finish
     *
     *         // If the coroutine is cooperative then cancel it else, if it is not cooperative
     *         // then wait for the coroutine to finish
     *
     *         job.cancelAndJoin()
     *
     *         -- What makes a Coroutine Cooperative?
     *
     *         --Two ways to make coroutine cooperative
     *
     *         --Periodically invoke a suspending function that checks for cancellation.
     *
     *         -- Only those suspending functions that belongs to kotlinx.coroutines package will make coroutine cooperative.
     *
     *         -- delay(), yield(), withContext(),withTimeout() etc. are the suspending functions that belongs to
     *         -- kotlinx.coroutines package
     *
     *
     *        -- Second way to make coroutine cooperative
     *
     *        Explicitly check for the cancellation status within the coroutine
     *
     *          - CoroutineScope.isActive boolean flag
     *
     *        -- Threads do not have such in-built mechanism to cancel itself internally another advantage of using
     *        -- coroutine in place of thread.
     *
     *
     *
     *        -- Handling exceptions while cancelling the coroutine
     *
     *       -- Cancellable suspending functions such as yield(),delay() etc.
     *
     *       --throw CancellationException on the coroutine cancellation.
     *
     *       -- you cannot execute a suspending function from the finally block because the
     *
     *       -- coroutine running this code is already cancelled.
     *
     *       -- but in extreme cases if you want to execute a suspending function from a finally block then wrap the
     *       -- code within withContext(NonCancellable) function.
     *
     *       -- You can print your own cancellation message using job.cancel(CancellationException("My Crash Message"))
     *
     *
     *        -- Timeouts
     *
     *        -- Similar to launch and async functions, with Timeout and withTimeoutOrNull functions are coroutine builders.
     *
     *        -- Timeout Cancellation Exception is a subclass of CancellationException
     *
     *
     *         -- Composing Suspending Functions
     *
     *         --Sequential,Concurrent and Lazy execution
     *
     *        -- Sequential Execution -- Functions execution are sequential by default
     *
     *        -- Concurrent Execution -- Achieve concurrent execution by async{}
     *
     *        -- Lazy Coroutine Execution -- Lazily execute code in coroutine
     *
     *  -- Coroutines by default execute sequentially.
     *
     *  -- Concurrent means parallel.
     *
     *  -- Concurrent execution is achieved by async{} or launch{} as child coroutine builders.
     *
     *  -- Lazy coroutine execution Lazily execute code in coroutine.
     *
     *
     *  // CoroutineScope, CoroutineContext and Dispatchers.
     *
     *  What role dispatchers play while assigning thread to a coroutine ?
     *
     *  CoroutineScope?
     *
     *  Each coroutine has its own CoroutineScope instance attached to it.
     *
     *  launch { // this : CoroutineScope
     *
     *       // Some operation...
     *
     *  }
     *
     *  async { // this : CoroutineScope
     *      *
     *      *       // Some operation...
     *      *
     *      *  }
     *
     *
     *  runBlocking { // this : CoroutineScope
     *      *
     *      *       // Some operation...
     *      *
     *      *  }
     *
     * // So basically coroutine scope represents what kind of coroutine you have created.
     *
     * // Each coroutine being irrespective of parent or a child has its own CoroutineScope
     *
     *
     * -- CoroutineContext
     *
     * -- Child coroutine can inherit coroutine context from parent coroutine.
     *
     * -- Coroutine context has two major components.
     *
     * -- Dispatcher and Job object.
     *
     * -- Dispatcher determines the thread of a coroutine.
     *
     * -- We can also assign name to a coroutine CoroutineName
     *
     *  -- We have four types of dispatchers:
     *
     *
     *  Dispatchers are those which decides on which thread your coroutine will execute.
     *
     *  - Dispatchers:
     *
     *  - Default
     *
     *  - Unconfined
     *
     *  - Main
     *
     *  - IO
     *
     * -- Coroutine name
     *
     *
     *  A CoroutineScope manages the lifecycle of coroutines. it keeps track of all coroutines it launches and can cancel them together.
     *
     *  Think of it like a container or environment in which coroutines run.
     *
     *  Coroutine context is a set of elements that define the behaviour of coroutine
     *
     *  like dispatcher, Job, Coroutine name ,
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     */
}