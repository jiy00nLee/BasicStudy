package com.example.basestructure.base

import android.app.Activity
import android.view.View
import androidx.annotation.StringRes

interface BaseView {
    //fun snackbarObserving() //(RXjava) api콜 관련 실패 메세지 -> 뷰모델에 라이브형태로 관찰 필요.

    fun showSnackbar(message : String)

    fun showToast(message: String)

    fun showToast(@StringRes stringRes: Int)    //Resource Type Anotations == 전달되는 id가 이 type이 아닐경우 error를 표시한다.

    //fun setToolbarTitle(title : String?)      //MainActivity에서 네비게이션 컴포넌트로 처리한다.

    fun setupKeyboardHide(view: View, activity: Activity?)

    //fun loadingIndicatorObserving()
}