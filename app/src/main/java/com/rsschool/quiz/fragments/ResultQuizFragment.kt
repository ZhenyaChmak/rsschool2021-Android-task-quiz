package com.rsschool.quiz.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.rsschool.quiz.R
import com.rsschool.quiz.data.ListOfQuestions
import com.rsschool.quiz.databinding.FragmentResultQuizBinding
import com.rsschool.quiz.fragments.contract.navigator

class ResultQuizFragment : Fragment() {

    private var _binding : FragmentResultQuizBinding? = null
    private val binding get() = requireNotNull(_binding)


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            _binding = FragmentResultQuizBinding.inflate(inflater, container, false)
        return binding.root
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val answerArray = arguments?.getIntArray(ARRAY_ANSWER_KEY) ?: IntArray(0)
        val correctAnswerArray = ListOfQuestions.answers.toIntArray()
        val result = countingResult(answerArray, correctAnswerArray)
        val yourResult = setYourResult(result,correctAnswerArray.size)

        setPositionClose()

        setPositionRestart()

        setPositionShare(answerArray, yourResult)

    }


    private fun setPositionShare(answerArray: IntArray,yourResult : String) {
        binding.resultTextView.text = getResult(yourResult)

        binding.share.setOnClickListener {
            startActivity(Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.result))
                putExtra(Intent.EXTRA_TEXT, generatingMessage(answerArray, yourResult))
                type = "plain/text"
            })
        }
    }

    private fun getResult(yourResult: String): CharSequence? {
        return  "Your result: ${yourResult}%\n"
    }

    private fun generatingMessage(answerArray: IntArray, yourResult: String ) : String {
        val messageResult = "Your result: ${yourResult}%"
        var text = "$messageResult\n"
        val questionsArray = ListOfQuestions.onListOfQueue
        for (i in questionsArray.indices)
            text += "\n${i+1}) ${resources.getString(questionsArray[i].first)}\n" +
                    "Your answer: ${resources.getString(answerArray[i])}\n"
        return text
    }

    private fun countingResult (answerArray: IntArray, correctAnswerArray: IntArray) : Int{
        var result = 0
        for(i in answerArray.indices)
            if(resources.getString(answerArray[i])==resources.getString(correctAnswerArray[i]))
                result++
        return result
    }

    private fun setYourResult(result : Int, correctAnswerArray : Int ) : String {
        return (((result.toDouble()/correctAnswerArray.toDouble())*100).toInt()).toString()
    }

    private fun setPositionClose(){
        binding.close.setOnClickListener{ navigator().goExit()}
    }

    private fun setPositionRestart() {
        binding.restart.setOnClickListener{ navigator().goToStart() }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object{

        @JvmStatic
        private val ARRAY_ANSWER_KEY = "ARRAY_ANSWER_KEY"

        @JvmStatic
        fun newInstance(answerArray: IntArray): ResultQuizFragment {
            /*val args:Bundle = Bundle().apply{
                putIntArray(ARRAY_ANSWER_KEY,answerArray)
            }
            val fragment = ResultQuizFragment()
            fragment.arguments = args
            return fragment*/

            return ResultQuizFragment().apply {
                arguments = bundleOf(
                   ARRAY_ANSWER_KEY to answerArray
                )
            }
        }
    }

}