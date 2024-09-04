package com.example.stopwatch

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private var isrunning = false
    private var timermiliseconds = 0L

    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object :Runnable{
        override fun run() {
            timermiliseconds+=10
//            val hours = timermiliseconds/3600
//            val minutes = (timermiliseconds%3600)/60
//            val seconds = timermiliseconds%60

            val minutes = (timermiliseconds / 1000) / 60
            val seconds = (timermiliseconds / 1000) % 60
            val miliseconds = (timermiliseconds%1000)/10
            val time = String.format("%02d:%02d:%02d",minutes,seconds,miliseconds)
            binding.timerTxt.text = time

            handler.postDelayed(this,10)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.startBtn.setOnClickListener {
            startTimer()
        }
        binding.stopBtn.setOnClickListener {
            stopTimer()
        }
        binding.resetBtn.setOnClickListener {
            resetTimer()
        }

        
    }

    private fun startTimer(){
        if (!isrunning){
            handler.postDelayed(runnable,10)
            isrunning = true

            binding.startBtn.isEnabled = false
            binding.stopBtn.isEnabled = true
            binding.resetBtn.isEnabled = true
        }
    }
    private fun stopTimer(){
        if (isrunning){
            handler.removeCallbacks(runnable)
            isrunning = false

            binding.startBtn.isEnabled = true
            binding.startBtn.text = "RESUME"
            binding.stopBtn.isEnabled = false
            binding.resetBtn.isEnabled = true
        }
    }
    private fun resetTimer(){
        stopTimer()

        timermiliseconds = 0
        binding.timerTxt.text = "00:00:00"

        binding.startBtn.isEnabled = true
        binding.resetBtn.isEnabled = false
        binding.startBtn.text = "START"
    }
}