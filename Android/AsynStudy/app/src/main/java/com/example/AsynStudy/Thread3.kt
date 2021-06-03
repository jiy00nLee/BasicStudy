package com.example.AsynStudy

import android.os.*
import androidx.appcompat.app.AppCompatActivity
import com.example.AsynStudy.databinding.ActivityMainBinding

// 3) 기본 쓰레드 예제.(Looper&Handler)
class Thread3: AppCompatActivity() {
    private var isRunning = true
    private lateinit var binding: ActivityMainBinding

    val HandlerThreadClass=  HandlerThread("message which you want to send")
    val handler : CustomHandler = CustomHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val thread = ThreadClass()
        binding.button1.setOnClickListener {
            //thread.start()
            ThreadClass().start()
        }
        //HandlerThreadClass.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }

    inner class ThreadClass() : Thread(){
        var value : Int = 0
        val bundle : Bundle = Bundle()

        override fun run() {
            super.run()
            while (isRunning){

                SystemClock.sleep(1000)
                value +=1
                val message : Message = handler.obtainMessage()
                bundle.putInt("value", value)
                message.data = bundle
                handler.sendMessage(message)

            }
        }
    }

    //메인쓰레드 관리해주는 핸들러(루퍼)
    inner class CustomHandler : Handler(Looper.getMainLooper()){
        var value : Int = 0
        override fun handleMessage(message: Message) {
            super.handleMessage(message)
            value = message.data.getInt("value")
            binding.textView.text = value.toString()
        }
    }

    /*   //Runnable 클래스 상속 필요.
       inner class UIClass() : Runnable {
           override fun run() {
               binding.textView.text = System.currentTimeMillis().toString()
           }
       }*/

}