package com.example.AsynStudy.coroutineBasics

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.AsynStudy.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class Coroutine3 : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //main()
        //main2()
        main3()
    }

    /*
        coroutineScope vs CoroutinScope
       == structured vs unstructured (concurrency)
     */

    //-------------------------------------------------------------------------------

    private fun main() {
        runBlocking<Unit> { //메인 쓰레드 블로킹
            CoroutineScope(Dispatchers.IO).launch {
                delay(1000L)
                Log.e(ContentValues.TAG, "World!!")
            }
            Log.e(ContentValues.TAG, "Hello!!")
            delay(2000L)
        }
        Log.e(ContentValues.TAG, "End Function") //메인 쓰레드 -> 가장 마지막에 출력.
    }

    private fun main2() {
        runBlocking<Unit> { //메인 쓰레드 블로킹
            coroutineScope {
                launch {
                    delay(1000L)
                    Log.e(ContentValues.TAG, "World2!!")
                }
            }
            Log.e(ContentValues.TAG, "Hello2!!")
            delay(2000L)
        }
        Log.e(ContentValues.TAG, "End Function") //메인 쓰레드 -> 가장 마지막에 출력.
    }

    //-------------------------------------------------------------------------------

    fun main3() = runBlocking { // this: CoroutineScope
        //자식 스레드가 완료될 때 까지 현재 스레드를 block 한다.
        launch {
            delay(200L)
            Log.e("main","Task from runBlocking")       //2
        }
        coroutineScope { // Creates a coroutine scope
            //자식 스레드
            launch {
                delay(500L)
                Log.e("main","Task from nested launch") //3
            }
            //현재 스레드
            delay(100L)
            Log.e("main","Task from coroutine scope")   //1
        }
/*        CoroutineScope(Dispatchers.Main).launch{
            delay(500L)
            Log.e("main","Task from nested launch") //3
        }*/
        Log.e("main","Coroutine scope is over")         //4
    }
}