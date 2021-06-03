package com.example.thread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import com.example.thread.databinding.ActivityMainBinding
import java.util.concurrent.CopyOnWriteArrayList

class MainActivity: AppCompatActivity() {
    private var isRunning = true
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ThreadClass().start()
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }

    inner class ThreadClass() : Thread(){
        override fun run() {
            super.run()
            while (isRunning){
                SystemClock.sleep(100)

                //UI 쓰레드 접근방법 1. View.post()
                binding.textView.post(UIClass())

                //UI 쓰레드 접근방법 2. activity.runOnUiThread()
               // runOnUiThread(UIClass())    //Runnable 클래스 상속받은 경우에만 해당 UI 쓰레드 실행가능.*/
            }
        }
    }

    //Runnable 클래스 상속 필요.
    inner class UIClass() : Runnable {
        override fun run() {
            binding.textView.text = System.currentTimeMillis().toString()
        }
    }

}