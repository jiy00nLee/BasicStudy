package com.example.AsynStudy

import android.os.*
import androidx.appcompat.app.AppCompatActivity
import com.example.AsynStudy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var thread : Thread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setProgressBar()

        binding.button1.setOnClickListener {
            if (thread == null){
                thread = Thread(RunnableTimer(1))
                thread!!.start()
            }
        }
        binding.button2.setOnClickListener {
            stopThread()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopThread()
    }


    inner class RunnableTimer(var seconds: Int = 0) : Runnable {
        @Synchronized
        override fun run() {
            try {
                while(!Thread.currentThread().isInterrupted){
                    if (seconds >= 100 ) stopThread()
                    runOnUiThread {
                        binding.textView.text = seconds.toString()
                        binding.progressBar.progress = seconds%100
                    }
                    Thread.sleep(1000)
                    seconds++
                }
            }catch (e : InterruptedException){  }
            finally { System.out.println("Thread is dead!!") }
        }
    }


    private fun stopThread(){
        thread?.let { it -> it.interrupt()
            thread = null
        }
    }

    private fun setProgressBar(){
        binding.progressBar.max = 100
        binding.progressBar.progress = 0
    }

}