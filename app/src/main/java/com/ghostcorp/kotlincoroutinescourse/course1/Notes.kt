package com.ghostcorp.kotlincoroutinescourse.course1

class Notes {
    /**
     *  Program is set of instructions.
     *
     *  For running a program a process is created.
     *
     *  Process ID
     *
     *  State
     *
     *  Memory
     *
     *  Handles for Networking,File System etc.
     *
     *  Thread(i.e Thread of Execution)
     *
     *  Every process has at least one thread
     *
     *  Sequential Execution
     *
     *  Process - Instruction1 -> Instruction2 -> Instruction 3
     *
     *  What types of instructions are we executing these days? - I/O Operations.
     *
     *  Main Thread:
     *
     *  Looper  - It always look for message queue as soon as it gets a message it takes it and execute on main thread
     *
     *  Message Queue -
     *
     *  Coroutines :
     *
     *  - Executed inside a thread
     *
     *  - One thread can have many coroutines
     *
     *  - Cheap
     *
     *  Coroutine Scope - Defines lifetime of coroutine
     *
     *  Coroutine Context - Defines context of coroutine
     *
     *  Dispatchers :
     *
     *  1. Coroutines run on top of threads.
     *
     *  2. Dispatchers is a way to define threads on which coroutines are executed
     *
     *  3. Dispatchers are used to determine which thread pool the coroutine should use
     *
     *  4. Predefined dispatchers are:
     *
     *      4.1 - Dispatchers.IO
     *
     *      4.2 - Dispatchers.Main
     *
     *      4.3 - Dispatchers.Default
     *
     *      4.4 - Dispatchers.Unconfined
     *
     *
     *  5. Types of Scopes
     *
     *      5.1 - GlobalScope
     *
     *      5.2 - CoroutineScope
     *
     *      5.3 -  MainScope
     *
     *      5.4 - lifecycleScope
     *
     *      5.5 - viewModelScope
     *
     *      5.6 - rememberCoroutineScope
     *
     *
     *  6. Why Coroutines
     *
     *  Coroutines helps to implement functionality that can be suspended & later resumed at specified points without blocking the thread.
     *
     *  7. Suspend modifier & Suspending functions
     *
     *   Functions with suspend modifier
     *
     *   Helps coroutine to suspend the computation at a particular point.
     *
     *   Suspending functions must be called from either coroutines or other suspending function.
     *
     *   Coroutine Builders :
     *
     *   Coroutine Builders - Functions that help in creating coroutines.
     *
     *   We have already seen launch function.
     *
     *   CoroutineScope(Dispatchers.IO).launch {
     *        // code
     *   }
     *
     *   Use launch when you do not care about the result. (Fire & Forget)
     *
     *   Use Async - when you expect result/output from your coroutine.
     *
     *   Although both can be used to achieve the same functionality but it is better to use
     *   things that are meant for it.
     *
     *   8. Coroutine Job & Job Hierarchy

     *   9.Cancellation
     *
     *   10. With Context
     *
     *   Context switching
     *
     *   To run a coroutine on a specific context for easy switching between contexts like between main thread to background thread
     *
     *    withContext(Dispatchers.IO) {
     *         delay(1000)
     *         Log.d("tag>>>>","Inside")
     *     }
     *
     *   11. runBlocking
     *
     *   To block the application so that it run all its coroutines and then finish
     *
     *   12.Viewmodel Scope
     *
     *   Coroutine scope attached with your view models.
     *
     *   Coroutine in this scope will be cancelled automatically when viewmodel is cleared.
     *   We dont need to manually cancel the coroutines.
     *
     *   13.Lifecycle Scope
     *
     *   Coroutine scope attached with your lifecycle of objects like activity or fragments..
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
     *
     *
     * ## */


}