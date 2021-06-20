package com.rsschool.quiz.fragments.contract

import androidx.fragment.app.Fragment


fun Fragment.navigator():Navigator{
    return requireActivity() as Navigator
}

interface Navigator {

    fun goBack()

    fun goToStart()

    fun goExit()

}