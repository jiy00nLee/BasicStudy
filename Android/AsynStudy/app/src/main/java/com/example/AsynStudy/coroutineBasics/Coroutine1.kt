package com.example.AsynStudy.coroutineBasics

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.AsynStudy.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Coroutine1 : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        main()
        //Log.e(TAG, "DDD!!!")
    }


    private fun main() {
        runBlocking<Unit> {
            //코루틴 1
            val scope = GlobalScope
            scope.launch {
                //코루틴 2
                delay(1000L)
                Log.e(ContentValues.TAG, "World!!")
            }

            Log.e(ContentValues.TAG, "Hello!!")
            delay(2000L)
        }
        //메인 쓰레드
        Log.e(ContentValues.TAG, "End Function")
    }
/*
    private fun main() = runBlocking {
        //코루틴 1
        val scope = GlobalScope
        //val scope2 = CoroutineScope(Dispatchers.Main)

        scope.launch {
            //코루틴 2
            delay(1000L)
            Log.e(TAG, "WWW!!")
        }

        Log.e(TAG, "PPP!!")
        delay(2000L)
    }*/

}