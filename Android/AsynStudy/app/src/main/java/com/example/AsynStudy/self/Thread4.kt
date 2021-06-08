package com.example.AsynStudy.self

import android.os.*
import androidx.appcompat.app.AppCompatActivity
import com.example.AsynStudy.databinding.ActivityMainBinding

// 4) 쓰레드 예제-프로그레스 바.
class Thread4 : AppCompatActivity() {
    private var isRunning = true
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setProgressBar()
        val thread :  ThreadClass? = null
        binding.button1.setOnClickListener {
            ThreadClass(1).start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }

    inner class ThreadClass(var num : Int) : Thread() {
        override fun run() {
            super.run()
            while (isRunning) {
                runOnUiThread(Timer(num))
                SystemClock.sleep(1000)
                num++
            }
        }
    }

    inner class Timer(val num : Int) : Runnable {
        override fun run() {
            binding.textView.text = num.toString()
            binding.progressBar.progress = num%100
        }
    }

    private fun setProgressBar(){
        binding.progressBar.max = 100
        binding.progressBar.progress = 0
    }

}


