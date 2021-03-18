package com.example.noteitall.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import com.example.noteitall.R

class NotesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        WindowManager.LayoutParams.FLAG_FULLSCREEN


        setContentView(R.layout.activity_notes)
        val backButton:ImageButton=findViewById(R.id.NotActivityExit)
        backButton.setOnClickListener {
            finish()
        }
    }
}