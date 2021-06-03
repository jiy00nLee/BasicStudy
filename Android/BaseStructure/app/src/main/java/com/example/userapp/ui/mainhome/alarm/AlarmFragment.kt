package com.example.userapp.ui.mainhome.alarm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.userapp.base.BaseFragment
import com.example.userapp.databinding.FragmentMainhomeAlarmBinding

class AlarmFragment: BaseFragment<FragmentMainhomeAlarmBinding, AlarmViewModel> (){
    override lateinit var viewbinding: FragmentMainhomeAlarmBinding

    override val viewmodel: AlarmViewModel by viewModels()

    override fun initViewbinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewbinding = FragmentMainhomeAlarmBinding.inflate(inflater, container, false)
        return viewbinding.root
    }

    override fun initViewStart(savedInstanceState: Bundle?) {

    }

    override fun initDataBinding(savedInstanceState: Bundle?) {

    }

    override fun initViewFinal(savedInstanceState: Bundle?) {

    }
}