package com.example.myapplication

import android.os.CountDownTimer
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    private lateinit var timer : CountDownTimer
    private val _seconds = MutableLiveData(0.0)
    private val _deciSeconds = MutableLiveData(0.0)
    private var _remainingTime: Long = 0L
    private var  isPaused = MutableLiveData<Boolean>()
    val seconds : LiveData<Double> get() = _seconds
    val deciSeconds : LiveData<Double> get() = _deciSeconds
    val paused : LiveData<Boolean> get() = isPaused

    fun resumeTimer() {

        startTimer(_remainingTime.toInt())
    }

    fun startTimer(time : Int = 10000) {
        isPaused.value = false
        _remainingTime = time.toLong()
        timer =  object : CountDownTimer(_remainingTime, 100L){
            override fun onTick(timeLeft: Long) {
                _remainingTime = timeLeft
                val value : Double = timeLeft.toDouble()
                if(Math.abs(_seconds.value!! - value) > 1 )
                    _seconds.value = (value/1000)
                _deciSeconds.value = value/1000
            }

            override fun onFinish() {
                _remainingTime = 0L
                _seconds.value = 0.0
                _deciSeconds.value = 0.0
//                Toast.makeText(MainActivity::class.java, "Finished", Toast.LENGTH_SHORT).show()
            }

        }.start()
    }

    fun stopTimer() {
        timer.cancel()
        if(_remainingTime < 100L) {
            _remainingTime = 0L
            _seconds.value = 0.0
            _deciSeconds.value = 0.0
            isPaused.value = false
        } else {
            isPaused.value = true
        }
    }
}