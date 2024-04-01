package com.mwn.quizapp

import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mwn.quizapp.databinding.ActivityQuizBinding
import com.mwn.quizapp.databinding.ScoreDialogBinding
import com.mwn.quizapp.models.QuestionModel
import com.mwn.quizapp.models.QuizTypesModel

class QuizActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        var questionModelList: List<QuestionModel> = listOf()
        var time: String = ""
    }

    lateinit var binding: ActivityQuizBinding

    var currentQuestionIndex = 0
    var selectedAnswer = ""
    var score = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btn0.setOnClickListener(this@QuizActivity)
            btn1.setOnClickListener(this@QuizActivity)
            btn2.setOnClickListener(this@QuizActivity)
            btn3.setOnClickListener(this@QuizActivity)
            btnNext.setOnClickListener(this@QuizActivity)
        }

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        loadQuestions()
        startTimer()

    }


    private fun startTimer() {
        val totalTimeInMilis = time.toInt() * 60 * 1000L
        object : CountDownTimer(totalTimeInMilis, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                val minutes = seconds / 60
                val remainingSeconds = seconds % 60
                binding.timerTxtView.text = String.format("%02d:%02d", minutes, remainingSeconds)
            }

            override fun onFinish() {
                //Finish the Quiz
            }

        }.start()
    }

    private fun loadQuestions() {
        selectedAnswer = ""
        if (currentQuestionIndex == questionModelList.size) {
            finishQuiz()
            return
        }

        binding.apply {
            // To display Questions Numbers
            questionIndicatorTxtView.text =
                "Question ${currentQuestionIndex + 1} / ${questionModelList.size}"
            // To calculate progress
            quizProgressBar.progress =
                (currentQuestionIndex.toFloat() / questionModelList.size * 100).toInt()

            // To Display Question
            quizQuestion.text = questionModelList[currentQuestionIndex].question
            // To Display Options
            btn0.text = questionModelList[currentQuestionIndex].options[0]
            btn1.text = questionModelList[currentQuestionIndex].options[1]
            btn2.text = questionModelList[currentQuestionIndex].options[2]
            btn3.text = questionModelList[currentQuestionIndex].options[3]
        }
    }

    private fun finishQuiz() {
        val totalQuestions = questionModelList.size
        val percentage = (score.toFloat() / totalQuestions.toFloat() * 100).toInt()

        val dialogBinding = ScoreDialogBinding.inflate(layoutInflater)
        dialogBinding.apply {
            scoreProgressIndicator.progress = percentage
            scoreProgressTxt.text = "$percentage %"

            if (percentage > 60) {
                scoreTitle.text = "Congrats! You have passed"
                scoreTitle.setTextColor(Color.BLUE)
            } else {
                scoreTitle.text = "Sorry! You have Faild"
                scoreTitle.setTextColor(Color.RED)
            }
            scoreSubtitle.text = "$score out of $totalQuestions are correct"
            finishBtn.setOnClickListener { finish() }
        }

        AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setCancelable(false)
            .show()

    }

    override fun onClick(view: View?) {
        val clickedBtn = view as Button
        binding.apply {
            btn0.setBackgroundColor(getColor(R.color.gray))
            btn1.setBackgroundColor(getColor(R.color.gray))
            btn2.setBackgroundColor(getColor(R.color.gray))
            btn3.setBackgroundColor(getColor(R.color.gray))
        }

        if (clickedBtn.id == R.id.btn_next) {
            // Next button is clicked

            if (selectedAnswer.isEmpty()){
                Toast.makeText(this, "Please Select Your Answer", Toast.LENGTH_SHORT).show()
            }

            if (selectedAnswer == questionModelList[currentQuestionIndex].answer) {
                score++
                Log.i(TAG, "onClick: Score is incremented $score")
            }

            currentQuestionIndex++
            loadQuestions()
        } else {
            // Option button is clicked
            selectedAnswer = clickedBtn.text.toString()
            clickedBtn.setBackgroundColor(getColor(R.color.blue))
        }
    }
}