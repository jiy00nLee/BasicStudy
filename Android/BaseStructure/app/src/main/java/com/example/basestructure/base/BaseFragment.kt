package com.example.basestructure.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : Fragment() {

    abstract val viewbinding : VB
    abstract val viewmodel : VM
    // abstract val layoutResourceId: Int -> 프래그먼트의 경우 패키지 구조 - 없는게 나은듯.

    private val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    private fun addDisposable(disposable: Disposable){
        compositeDisposable.add(disposable)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return initViewbinding(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewStart(savedInstanceState)
        initDataBinding(savedInstanceState)
        initViewFinal(savedInstanceState)
        super.onViewCreated(view, savedInstanceState)
    }

    abstract fun initViewbinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View?
    abstract fun initViewStart(savedInstanceState: Bundle?)     //첫번째, 레이아웃 초기 설정- 뷰&액티비티 (ex.리사이클러뷰, 툴바, 드로어뷰)
    abstract fun initDataBinding(savedInstanceState: Bundle?)   //두번째, 데이터바인딩& RxJava 설정 (ex.RxJava observe, Databinding observe)
    abstract fun initViewFinal(savedInstanceState: Bundle?)     //세번째, 마무리 커스텀 (ex. 클릭리스너 이벤트)
}