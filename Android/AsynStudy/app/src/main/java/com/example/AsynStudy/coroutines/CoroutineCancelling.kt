package com.example.AsynStudy.coroutines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.AsynStudy.databinding.FragmentStudyBinding
import kotlinx.coroutines.*
import java.io.Closeable

class CoroutineCancelling : AppCompatActivity() {
    private lateinit var binding: FragmentStudyBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //cancel1()
        //cancel2()
        //cancel3()
        //cancel4()
        //cancel5()
        //cancel6()
        //cancel7()
        //cancel8()
        //cancel9()
        //cancel10()
        cancel11()
    }

    //[방법1] 코루틴 job 취소
    private fun cancel1() = CoroutineScope(Dispatchers.IO).launch {
        val job = launch {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
        }
        delay(1300L) // delay a bit
        println("main: I'm tired of waiting!")
        job.cancel() // cancels the job
        job.join() // waits for job's completion
        println("main: Now I can quit.")
    }

    //[방법2] 코루틴 job 여러개 취소 (문제! 복잡한 상황의 경우, 취소가 안됨.)
    private fun cancel2() = CoroutineScope(Dispatchers.IO).launch {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 5) { // computation loop, just wastes CPU
                // print a message twice a second
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
        }
        delay(1300L) // delay a bit
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // cancels the job and waits for its completion
        println("main: Now I can quit.")
    }

    //------------------------------------------------------------------------------------------------------
    //방법 2 해결 방법
    //[방법3] 계산 코드 취소하는 방법 1 - 주기로 취소 체킹(suspending function 활용 - yield())

    private fun cancel3() = CoroutineScope(Dispatchers.IO).launch {
        val job = launch(Dispatchers.Default) {
            for (i in 1..10) {
                yield()
                println("I'm sleeping $i ...")
                Thread.sleep(500L)
            }
        }
        delay(1300L)
        println("main : I'm tired of waiting!")
        job.cancelAndJoin()
        println("main : Now I can quit.")
    }


    //[방법4] 계산 코드 취소하는 방법 2 - 주기로 취소 체킹(suspending function 활용 - 'isActive' 속성 활용)
    private fun cancel4() = CoroutineScope(Dispatchers.IO).launch {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (isActive) { // (상태체킹!)
                // print a message twice a second
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
        }
        delay(1300L) // delay a bit
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // cancels the job and waits for its completion
        println("main: Now I can quit.")

    }


    //[방법5] 계산 코드 취소하는 방법 3 - 'try-finally'
    private fun cancel5() = CoroutineScope(Dispatchers.IO).launch {
        val job = launch {
            try {
                repeat(1000) { i ->
                    println("I'm sleeping $i ...")
                    delay(500L)
                }
            } finally {
                println("I'm running finally")
            }
        }

        delay(1300L) // delay a bit
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // cancels the job and waits for its completion
        println("main: Now I can quit.")
    }

    //[방법6] 방법5's 에러 처리 방법 - try-finally
    private fun cancel6() = CoroutineScope(Dispatchers.IO).launch {
        val job = launch {
            try {
                repeat(1000) { i ->
                    println("I'm sleeping $i ...")
                    delay(500L)
                }
            } finally {
                println("I'm running finally")
            }
        }
        delay(1300L) // delay a bit
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // cancels the job and waits for its completion
        println("main: Now I can quit.")
    }

    //[방법7] 방법5's 에러 처리 방법 - try-finally 응용 (use)
    private fun cancel7() = CoroutineScope(Dispatchers.IO).launch {
        val job = launch {
            SleepingBed().use { it.sleep(1000) }
        }
        delay(1300L)
        println("main : I'm tired of waiting!")
        job.cancelAndJoin()
        println("main : Now I can quit.")
    }

    class SleepingBed : Closeable {
        suspend fun sleep(times: Int) {
            repeat(times) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
        }

        override fun close() {
            println("main : I'm running close() in SleepingBed!")
        }
    }


    // 공부 필요! - 보통 closing 연산들(ex.파일 닫기, job 취소)은 일반적으로 non-blocking이고 suspending을 포함하지 않기에 문제가 되지 않으나 간혹 아래와 같은 해결 필요할 경우 O.
    //[방법8] try-finally 보완 방법 -> 취소된 코루틴에서 'delay' 혹은 '중단'해야 하는 경우 충돌하는 코드를 해결해야함.
    //(withContext, withContext(NonCancellable)로 해결 가능.)
    private fun cancel8() = CoroutineScope(Dispatchers.IO).launch {
        val job = launch {
            try {
                repeat(1000) { i ->
                    println("I'm sleeping $i ...")
                    delay(500L)
                }
            } finally {
                println("I'm running finally")
            }
        }
        delay(1300L) // delay a bit
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // cancels the job and waits for its completion
        println("main: Now I can quit.")
    }

//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //Time-out // 실행시간이 초과한 경우, 실행중인 코루틴을 취소해야할 떄 사용.
    //-> 해당 코루틴에 대한 참조를 수동적으로 추적해 delay 혹은 일시 중단 후에 참조를 통해 추적해 취소할 수 있거나, 'whileTimeout' 기능으로 취소 가능.

    //[방법9] Time-out  - 코루틴에 대한 참조를 수동적으로 추적해 취소.
    // (1.제한 시간을 설정할 대상인 코루틴 생성/ 2.일정기간 지연후 job이 끝나지 않았을 경우 취소동작하는 코루틴 생성 후 1에서 만든 후 실행한 코루틴의 job 객체 전달/ 3.1번 코루틴 > 2번 코루틴 하게 만듬.)
    private fun cancel9() = runBlocking<Unit> {
        val job = launch {
            try {
                repeat(1000) { i ->
                    println("I'm sleeping $i ...")
                    delay(500L)
                }
            } finally { println("main : I'm running finally!") }
        }

        launch {
            delay(1300L)
            println("main : I'm tired of waiting. Cancel the job!")
            if (job.isActive) { job.cancelAndJoin() }
        }
    }


    //[방법10] Time-out  - (에러버전!) 'whileTimeout' 기능으로 추적해 취소.
    // 'TimeoutCancellationException' 발생! -> 예제가 메인 함수에서 바로 실행되었기 때문.
    private fun cancel10() = runBlocking<Unit> {
        withTimeout(1300L) {
            launch {
                try {
                    repeat(1000) { i ->
                        println("I'm sleeping $i ...")
                        delay(500L)
                    }
                } finally {
                    println("main : I'm running finally!")
                }
            }
        }
/*        withTimeout(1300L) {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
        }*/
    }


    //[방법11] Time-out  - 'whileTimeout' 기능으로 추적해 취소.
    // 'TimeoutCancellationException' 에러 발생 해결
    private fun cancel11() = runBlocking<Unit> {
        val result = withTimeoutOrNull(1300L) {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
            "Done" // will get cancelled before it produces this result
        }
        println("Result is $result")
    }


}
