package com.example.arithmeticbraingames

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    var correctAnswerPosition:Int?=null
    var newGame:Boolean=true
    var score=0
    var clicked:Boolean=true
    var timer:CountDownTimer?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        start.setOnClickListener {
            start.visibility=View.GONE
            startGame()
        }
    }
/**
 * the numbers in the problem is selected randomly and the options are also assigned randomly
 * */
    private fun startGame() {
        if (newGame) {
            gameTimer()
            next.visibility=View.GONE
            resulttvw.text=""
            newGame=false
            val a = (0..100).random()
            val b = (0..100).random()
            val correctAnswer = a + b
            correctAnswerPosition = (0..3).random()
            val optionsList = arrayListOf<Int>()
            val opt = (0..3).toList()
            problem.text = a.toString() + " + " + b.toString()
            opt.forEachIndexed { index, i ->
                if (correctAnswerPosition == index) {
                    optionsList.add(index, correctAnswer)
                } else {
                    var opt = (0..200).random()
                    while (opt == correctAnswer) {
                        opt = (0..200).random()
                    }
                    optionsList.add(index, opt)
                }
            }
            option1.text = optionsList[0].toString()
            option2.text = optionsList[1].toString()
            option3.text = optionsList[2].toString()
            option4.text = optionsList[3].toString()
            clicked=true
        }
    }
/**
 * to set a timer of 15sec to select the option within that time
 * */
    private fun gameTimer() {
         timer = object: CountDownTimer(16000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timertvw.text=(millisUntilFinished/1000).toString() + " sec"
            }

            override fun onFinish() {
                resulttvw.text="Time Up"
                next.visibility = View.VISIBLE
                next.text = "Try Again"
                next.setOnClickListener {
                    newGame = true
                    score = 0
                    timer!!.cancel()
                    scoretvw.text = "Score"
                    startGame()
                }
            }
        }
        timer!!.start()
    }
/**
 * to check whether the selected answer is correct or not
 * if correct next question will be displayed otherwise the is restarted from first
 * */
    fun checkAnswer(view: View) {
        if (clicked) {
            clicked=false
            val selected = view.tag
            if (selected.toString().equals(correctAnswerPosition.toString())) {
                resulttvw.text = "Correct"
                score += 1
                next.text = "Next"
                scoretvw.text = score.toString()
                next.visibility = View.VISIBLE
                next.setOnClickListener {
                    newGame = true
                    timer!!.cancel()
                    startGame()
                }
            } else {
                resulttvw.text = "Wrong"
                next.visibility = View.VISIBLE
                next.text = "Try Again"
                next.setOnClickListener {
                    newGame = true
                    startGame()
                    timer!!.cancel()
                    score = 0
                    scoretvw.text = "Score"
                }
            }
        }
    }

}