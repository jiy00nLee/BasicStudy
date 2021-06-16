package com.example.AsynStudy.thread_self

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.AsynStudy.databinding.FragmentStudyBinding

class Thread5 : AppCompatActivity() {
    private var isRunning = true
    private lateinit var binding: FragmentStudyBinding
    private val handler : Handler = Handler(Looper.getMainLooper())

    private var thread : Thread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setProgressBar()
        binding.button1.setOnClickListener {
//            isRunning = true
            if (thread == null){
                thread = Thread(RunnableTimer(1))
                thread!!.start()
            }
        }
        binding.button2.setOnClickListener {
//            isRunning = false
            stopThread()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopThread()
    }

    /*    inner class ThreadTimer(var seconds : Int = 0) : Thread() {
        override fun run() {
            super.run()
            while (isRunning) {
                try {
                    sleep(1000)
                    seconds++
                }catch (e : InterruptedException){

                }
            }
        }
    }*/


    //  [방법 1. 'interrupt'를 통한 쓰레드 중지 방법]
    inner class RunnableTimer(var seconds: Int = 0) : Runnable {
        @Synchronized
        override fun run() {
            try {
                while(!Thread.currentThread().isInterrupted){
                    if (seconds >= 100 ) stopThread()
                    handler.post {
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


//  [방법 2. 'flag'를 통한 쓰레드 중지 방법]
//    inner class RunnableTimer(var seconds : Int = 0) : Runnable {
//        @Synchronized
//        override fun run() {
//            while (isRunning) {
//                try {
//                    /*runOnUiThread {
//                        binding.textView.text = seconds.toString()
//                        binding.progressBar.progress = seconds%100
//                    }*/
//                    if (seconds >= 100 ) isRunning = false
//                    handler.post {
//                        binding.textView.text = seconds.toString()
//                        binding.progressBar.progress = seconds%100
//                    }
//                    Thread.sleep(1000)
//                    seconds++
//                }catch (e : InterruptedException){  }
//            }
//        }
//    }

    private fun setProgressBar(){
        binding.progressBar.max = 100
        binding.progressBar.progress = 0
    }

}