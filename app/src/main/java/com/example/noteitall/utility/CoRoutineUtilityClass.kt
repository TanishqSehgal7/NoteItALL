package com.example.noteitall.utility

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class CoRoutineUtilityClass : AppCompatActivity(),CoroutineScope {

    private lateinit var job:Job
//job is a background task in coroutines
    override val coroutineContext: CoroutineContext
        get() = job+Dispatchers.Main
    // Dispatchers define the threads where we want to execute our jobs


    override fun onCreate(savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        job=Job()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

}