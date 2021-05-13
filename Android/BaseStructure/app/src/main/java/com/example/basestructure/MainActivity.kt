package com.example.basestructure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.basestructure.base.BaseActivity
import com.example.basestructure.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>() {
    override lateinit var  viewbinding: ActivityMainBinding
    override val viewmodel: MainActivityViewModel by viewModel()
    override val layoutResourceId: Int
        get() = R.layout.activity_main

    override fun initViewbinding() {
        viewbinding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)
    }

    override fun initViewStart(savedInstanceState: Bundle?) {

    }

    override fun initDataBinding(savedInstanceState: Bundle?) {

    }

    override fun initViewFinal(savedInstanceState: Bundle?) {

    }

}