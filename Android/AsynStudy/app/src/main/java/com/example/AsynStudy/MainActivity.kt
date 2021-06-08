package com.example.AsynStudy

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.os.*
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.AsynStudy.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.io.Closeable
import kotlin.concurrent.thread
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }



}
