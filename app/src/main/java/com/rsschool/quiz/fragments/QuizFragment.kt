package com.rsschool.quiz.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.rsschool.quiz.R
import com.rsschool.quiz.data.ListOfQuestions
import com.rsschool.quiz.databinding.FragmentQuizBinding
import com.rsschool.quiz.fragments.contract.navigator


class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = requireNotNull(_binding)
    private var answerArray : IntArray = IntArray(1)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setColor(getPositionQuestion())
        answerArray = arguments?.getIntArray(ARRAY_ANSWER_KEY) ?: IntArray(1)
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListOfQuestion()

        setPositionNextButton()

        setPositionToolBar()

        setPositionPreviousButton()

    }

    private fun setListOfQuestion() {
        val radioButtons = arrayOf(
            binding.optionOne,
            binding.optionTwo,
            binding.optionThree,
            binding.optionFour,
            binding.optionFive
        )

        binding.question.text =resources
            .getString(ListOfQuestions
                .onListOfQueue[getPositionQuestion()]
                .first)

        val variants = Array(radioButtons.size){
            radioButtons[it].text = resources.getString(
                ListOfQuestions.onListOfQueue[getPositionQuestion()].second[it])
            radioButtons[it] to ListOfQuestions.onListOfQueue[getPositionQuestion()].second[it]
        }

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            answerSelection(variants, checkedId)
        }
    }

    private fun setColor(nameColor: Int){
        when(nameColor){
            0->{ context?.setTheme(R.style.Theme_Quiz_One)
                activity?.window?.statusBarColor = getResources().getColor(R.color.deep_orange_100)
            }
            1-> { context?.setTheme(R.style.Theme_Quiz_Tho)
                activity?.window?.statusBarColor = getResources().getColor(R.color.deep_purple_100_dark)
            }
            2-> { context?.setTheme(R.style.Theme_Quiz_Three)
                activity?.window?.statusBarColor = getResources().getColor(R.color.cyan_100_dark)
            }
            3->{ context?.setTheme(R.style.Theme_Quiz_Four)
                activity?.window?.statusBarColor = getResources().getColor(R.color.light_green_100_dark)
            }
            4->{ context?.setTheme(R.style.Theme_Quiz_Five)
                activity?.window?.statusBarColor = getResources().getColor(R.color.yellow_100)
            }
        }
    }

    private fun answerSelection(variants: Array<Pair<RadioButton, Int>>, checkedId: Int){
        for (i in variants)
            if (i.first.id == checkedId)
                answerArray[getPositionQuestion()] = i.second

       binding.nextButton.isEnabled = true
    }

    private fun setPositionNextButton() {
        binding.nextButton.setEnabled(false)
        binding.nextButton.setOnClickListener { nextQuestion() }

        if(getPositionQuestion()==4) binding.nextButton.text = "SUBMIT"
    }

    private fun setPositionToolBar() {
        binding.toolbar.title = getString(R.string.question,getPositionQuestion()+1)

        if(getPositionQuestion()>0) {
            binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_chevron_left_24)
            binding.toolbar.setNavigationOnClickListener{ navigator().onBackPressed()}
        }
    }

    private fun setPositionPreviousButton () {
       binding.previousButton.setOnClickListener { navigator().onBackPressed() }

        if(getPositionQuestion()>0) binding.previousButton.setEnabled(true)
        else binding.previousButton.setEnabled(false)
    }

    private fun nextQuestion() {
        if(getPositionQuestion()<answerArray.size-1) {

            val fragment: QuizFragment = QuizFragment.newInstance (
                getPositionQuestion()+1,
                answerArray
            )
            parentFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, fragment)
                .commit()
        }
        else{
            val fragment : ResultQuizFragment = ResultQuizFragment.newInstance(
                answerArray
            )
            parentFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainer,fragment)
                .commit()
        }
    }

    private fun getPositionQuestion() : Int = requireArguments().getInt(POSITION_QUESTION_KEY)

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {

        @JvmStatic
        private val POSITION_QUESTION_KEY = "POSITION_QUESTION_KEY"

        @JvmStatic
        private val ARRAY_ANSWER_KEY = "ARRAY_ANSWER_KEY"

        @JvmStatic
        fun newInstance(positionQuestion: Int, answerArray: IntArray):QuizFragment{

           /* val args:Bundle = Bundle().apply{
                putInt(POSITION_QUESTION_KEY,positionQuestion)
                putIntArray(ARRAY_ANSWER_KEY,answerArray)
            }
            val fragment = QuizFragment()
            fragment.arguments = args
            return fragment
*/
            return QuizFragment().apply {
                arguments = bundleOf(
                    POSITION_QUESTION_KEY to positionQuestion,
                    ARRAY_ANSWER_KEY to answerArray
                )
            }
        }
    }
}



