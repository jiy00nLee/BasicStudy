package com.example.thread

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.thread.databinding.ActivityMainBinding

//멀티쓰레드 예제
class Thread2  : AppCompatActivity() {
    private var isRunning = true
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedObject = sharedObject()
        val thread1 = ThreadClass("쓰레드1", sharedObject)
        val thread2 = ThreadClass("쓰레드2", sharedObject)
        thread1.start()
        thread2.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }

    inner class ThreadClass(val threadname : String, val sharedObject: sharedObject) : Thread(){
        override fun run() {
            super.run()
            for (i in 1..10){ //코틀린은 둘다 포함.
                SystemClock.sleep(100)
                sharedObject.add()
                Log.e(threadname + "i!!", i.toString())
                Log.e(threadname, sharedObject.num.toString())
            }
        }
    }

    inner class sharedObject(var num : Int = 0) { //arrayList X. CopyOnWriteArrayList O
        @Synchronized fun add() { num++}
    }

}
