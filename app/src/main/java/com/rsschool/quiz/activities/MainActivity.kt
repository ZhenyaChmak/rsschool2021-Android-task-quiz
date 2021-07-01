package com.rsschool.quiz.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.rsschool.quiz.data.ListOfQuestions
import com.rsschool.quiz.databinding.ActivityMainBinding
import com.rsschool.quiz.fragments.QuizFragment
import com.rsschool.quiz.fragments.contract.Navigator
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var binding : ActivityMainBinding
    private var positionQuestion = 0
    private val answerArray = IntArray(ListOfQuestions.onListOfQueue.size)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       if(savedInstanceState == null){
           val fragment : QuizFragment = QuizFragment.newInstance(
               positionQuestion,
               answerArray
           )
           supportFragmentManager
               .beginTransaction()
               .replace(binding.fragmentContainer.id,fragment)
               .commit()
       }
    }

    private fun launchFragment() {
        val fragment : QuizFragment = QuizFragment.newInstance(
            positionQuestion,
            answerArray
        )
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }

    override fun onBackPressed() {
        val count = getSupportFragmentManager().getBackStackEntryCount()

        if (count==0) {
            AlertDialog.Builder(this).apply {
                setTitle("Подтверждение")
                setMessage("Вы уверены, что хотите выйти из программы?")

                setPositiveButton("Да") { _, _ ->
                    super.onBackPressed()
                }

                setNegativeButton("Нет") { _, _ ->
                    Toast.makeText(
                        this@MainActivity, "Продолжим",
                        Toast.LENGTH_LONG
                    ).show()
                }
                setCancelable(true)
            }.create().show()
        }
       else if (count==5){
            Toast.makeText(
                this@MainActivity, "Error",
                Toast.LENGTH_LONG
            ).show()
        }
        else {
            super.onBackPressed()
        }
    }

    override fun goToStart() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        launchFragment()
    }

    override fun goExit() {
        AlertDialog.Builder(this).apply {
            setTitle("Подтверждение")
            setMessage("Вы уверены, что хотите выйти из программы?")

            setPositiveButton("Да") { _, _ ->
                exitProcess(0)
            }

            setNegativeButton("Нет") { _, _ ->
                Toast.makeText(
                    this@MainActivity, "Продолжим",
                    Toast.LENGTH_LONG
                ).show()
            }
            setCancelable(true)
        }.create().show()
    }
}