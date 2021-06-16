package com.example.AsynStudy.coroutineBasics

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.AsynStudy.databinding.FragmentStudyBinding
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class Coroutine4  : AppCompatActivity() {
    private lateinit var binding: FragmentStudyBinding

    // Job을 등록할 수 있도록 초기화
    // CoroutineScope의 동작을 제어할 객체
    // 안드로이드 상에서는 Lifecycle을 활용할 수 있도록 도와준다.
    lateinit var job : Job

    // coroutine의 스레드를 어떠한 형태로 사용할지 지정할 수 있다.
    private val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    // 작업 중이던 모든 job, children을 종료 처리
    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        easyMain()
        hardMain()
    }

    fun easyMain() {
        job = Job()

        runBlocking {
            delay(1000L)
            Log.e("coroutineScope","STEP 1")
        }

        Log.e("coroutineScope","STEP 2")

        CoroutineScope(coroutineContext).launch{
            delay(1000L)
            Log.e("coroutineScope","STEP 4")
        }

        Log.e("coroutineScope","STEP 3")
    }

    fun hardMain() = runBlocking { // this: CoroutineScope
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
