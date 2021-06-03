package com.example.userapp.utils


import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

open class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val mPending = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.")
        }

        // Observe the internal MutableLiveData
        super.observe(owner, { t ->     //t는 LiveData.java의 observe함수의 두번째 매개변수 Observer<? super T>의 T이다.
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)       //onChanged된 observer을 리턴함. (super의 함수를 override하여 해당과 같이 기능을 응용한 것.)
            }
        })
    }

    @MainThread
    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        //value = null
        postValue(null)
    }

    companion object {
        private val TAG = "SingleLiveEvent"
    }
}