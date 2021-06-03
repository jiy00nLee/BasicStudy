package com.example.userapp.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.example.userapp.utils.SnackbarMessageString
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel  : ViewModel(){

    private val snackbarMessageString = SnackbarMessageString()

    protected val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    private fun addDisposable(disposable: Disposable){
        compositeDisposable.add(disposable)
    }

    fun observeSnackbarMessageString(lifecycleOwner: LifecycleOwner, ob: (String) -> Unit){
        snackbarMessageString.observe(lifecycleOwner, ob)
    }

    fun showSnackbar(str: String){
        snackbarMessageString.postValue(str)
    }

}