package com.rsschool.quiz.fragments.contract

import androidx.annotation.DrawableRes

interface NavigationToolBar {
    fun getNavigationToolBar () : NavigationQuestionBack
}

class NavigationQuestionBack( @DrawableRes val navigationIcon: Int, val onNavigationQuestionBack : Runnable)

