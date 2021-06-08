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
import kotlin.concurrent.thread

class Coroutine2 : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        main()
    }

    //'runBlocking'-> 현재쓰레드(메인쓰레드)를 자식 쓰레드들이 완료될때까지 Block 한다.
    private fun main() = runBlocking<Unit> {  //코루틴 1(==runBlocking<Unit> 은 최상위 메인 코루틴)
        val job =GlobalScope.launch { //코루틴 2
            delay(1000L)
            Log.e(ContentValues.TAG, "World!!")
        }
        Log.e(ContentValues.TAG, "Hello")
        job.join() //작업완료를 위해 delay로 대기하는 건 사실 야매이므로 별로 -> 'join' 사용.(non-blocking code)
    }

    private fun main2() {    //메인 쓰레드
        val job = GlobalScope.launch {   //코루틴 1
            delay(1000L)
            Log.e(ContentValues.TAG, "World!!")
        }
        Log.e(ContentValues.TAG, "Hello")
        Thread.sleep(2000L)
    }

    private fun main3() {   //메인 쓰레드
        val thread = thread {   //쓰레드 1
            //delay(1000L) X
            Thread.sleep(1000L)
            Log.e(ContentValues.TAG, "World!!")
        }
        Log.e(ContentValues.TAG, "Hello")
        Thread.sleep(2000L)
    }

}