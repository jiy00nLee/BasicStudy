package com.example.basestructure.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.example.basestructure.utils.TextUtils
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : Fragment() , BaseView{

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

/*    override fun setToolbarTitle(title: String?) {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(findViewById(R.id.toolbar))
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            when (!TextUtils.isEmpty(title)) {
                true -> supportActionBar?.title = title
                false -> supportActionBar?.setDisplayShowTitleEnabled(false)
            }
        }
    }*/

    @Throws(IllegalArgumentException::class)
    override fun showSnackbar(message: String) {
        if (isDetached) return
        activity?.let { activity ->
            Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show() } }
    override fun showToast(message: String) { Toast.makeText(activity, message, Toast.LENGTH_SHORT).show() }
    override fun showToast(stringRes: Int) { Toast.makeText(activity, stringRes, Toast.LENGTH_SHORT).show() }
    override fun setupKeyboardHide(view: View, activity: Activity?) {
        if (view !is EditText || view !is Button) {
            view.setOnTouchListener { _, _ ->
                activity?.let {
                    val imm = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(it.currentFocus?.windowToken, 0)
                }
                return@setOnTouchListener false
            }
        }
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                setupKeyboardHide(view.getChildAt(i), activity)
            }
        }
    }

    // lifecycle aware dialog
    fun showDialog(dialog: Dialog, lifecycleOwner: LifecycleOwner?, cancelable: Boolean = true, dismissHandler: (() -> Unit)? = null) {
        val targetEvent = if (cancelable) Lifecycle.Event.ON_STOP else Lifecycle.Event.ON_DESTROY
        val observer = LifecycleEventObserver { _: LifecycleOwner, event: Lifecycle.Event ->
            if (event == targetEvent && dialog.isShowing) {
                dialog.dismiss()
                dismissHandler?.invoke()
            }
        }
        dialog.show()
        lifecycleOwner?.lifecycle?.addObserver(observer)
        dialog.setOnDismissListener { lifecycleOwner?.lifecycle?.removeObserver(observer) }

    }
}