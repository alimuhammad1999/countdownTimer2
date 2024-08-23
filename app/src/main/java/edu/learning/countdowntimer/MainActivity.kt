package edu.learning.countdowntimer;

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.MainActivityViewModel
import edu.learning.countdowntimer.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bi = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.seconds.observe(this) { bi.textView.text = it.toInt().toString() }
        viewModel.deciSeconds.observe(this) { bi.preciseTextView.text = String.format("%s", it.toString().substring(0,3)) }
        viewModel.paused.observe(this) {
            if (it) bi.stopButton.text = "Resume"
            else bi.stopButton.text = "Stop"
        }
        bi.startButton.setOnClickListener {
            val inputString : String = bi.inputValue.text.toString()
            if(inputString != "") {
                val input : Int = Integer.parseInt(inputString)
                if (input > 1000)
                    viewModel.startTimer(time = input)
            } else {
                viewModel.startTimer()
            }
        }
        bi.stopButton.setOnClickListener {
            if(viewModel.paused.value == true) viewModel.resumeTimer()
            viewModel.stopTimer()
        }
    }
}