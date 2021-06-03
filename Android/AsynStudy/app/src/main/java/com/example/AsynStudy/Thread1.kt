package com.example.AsynStudy

import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import com.example.AsynStudy.databinding.ActivityMainBinding

// 1) 기본 쓰레드 예제. (post/runOnUiThread)

class Thread1 : AppCompatActivity() {
    private var isRunning = true
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

  /*      Thread{
            while (isRunning){
                SystemClock.sleep(100)
                runOnUiThread {
                    binding.textView.text = System.currentTimeMillis().toString()
                }
            }
        }.start()*/
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
                /*runOnUiThread {
                    binding.textView.text = System.currentTimeMillis().toString()
                } */       //람다함수는 Runnable 필요 X.

                //UI 쓰레드 접근방법 1. View.post()
                binding.textView.post(UIClass())

                //UI 쓰레드 접근방법 2. activity.runOnUiThread()
                runOnUiThread(UIClass())    //Runnable 클래스 상속받은 경우에만 해당 UI 쓰레드 실행가능.*/
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