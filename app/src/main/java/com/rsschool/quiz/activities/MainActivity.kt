package com.rsschool.quiz.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.rsschool.quiz.R
import com.rsschool.quiz.data.ListOfQuestions
import com.rsschool.quiz.databinding.ActivityMainBinding
import com.rsschool.quiz.fragments.QuizFragment
import com.rsschool.quiz.fragments.contract.Navigator
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var binding : ActivityMainBinding
    private val positionQuestion = 0
    private val answerArray = IntArray(ListOfQuestions.onListOfQueue.size)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

       if(savedInstanceState == null){
           val fragment : QuizFragment = QuizFragment.newInstance(
               positionQuestion,
               answerArray
           )
           supportFragmentManager
               .beginTransaction()
               .replace(R.id.fragmentContainer,fragment)
               .commit()
       }
    }

    private fun launchFragment(fragment: Fragment) {
        val fragment : QuizFragment = QuizFragment.newInstance(
            positionQuestion,
            answerArray
        )
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun goBack() {

        onBackPressed()
    }

    override fun goToStart() {

        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        launchFragment(QuizFragment.newInstance(positionQuestion,answerArray))
    }

    override fun goExit() {
        exitProcess(0)
    }

    fun getScreensCount(): Int {
        return supportFragmentManager.backStackEntryCount + 1
    }

}