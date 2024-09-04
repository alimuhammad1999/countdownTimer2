package edu.learning.countdowntimer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.os.CountDownTimer as CountDownTimer

class counDownViewModel : ViewModel() {
    private lateinit var timer : CountDownTimer

    private val _seconds = MutableLiveData(0.0)
    val seconds : LiveData<Double> get() = _seconds

    private val _deciSeconds = MutableLiveData(0.0)
    val deciSeconds : LiveData<Double> get() = _deciSeconds

    private var _remainingTime = 0L

    private val isPaused = MutableLiveData(false)
    val paused : LiveData<Boolean> get() = isPaused

    fun startTimer(time : Long = 10000){
        if(_remainingTime > 0)
            stopTimer()
        runTimer(time)
    }

    fun pauseTimer() {
        timer.cancel()
    }

    fun resumeTimer() {

    }

    fun stopTimer() {
        timer.cancel()
        clearValues()
    }

    private fun clearValues() {
        _remainingTime = 0L
        isPaused.value = false
        _seconds.value = 0.0
        _deciSeconds.value = 0.0
    }

    private fun runTimer(time : Long) {
        timer = object : CountDownTimer(time, 100){
            override fun onTick(timeLeft: Long) {
                _remainingTime = timeLeft
                val value : Double = timeLeft.toDouble()
                if(Math.abs(_seconds.value!! - value) > 1 )
                    _seconds.value = (value/1000)
                _deciSeconds.value = value/1000
            }

            override fun onFinish() {
                clearValues()
            }
        }.start()
    }

}