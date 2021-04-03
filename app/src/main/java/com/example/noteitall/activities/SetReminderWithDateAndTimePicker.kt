package com.example.noteitall.activities

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import com.example.noteitall.R
import java.util.*

class SetReminderWithDateAndTimePicker : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_reminder_with_date_and_time_picker)


        val calendar=Calendar.getInstance()
        val datePickerDialog=DatePickerDialog(this, DatePickerDialog.OnDateSetListener {
                view, year, month, day ->
            Toast.makeText(this,"DatePicker Working Fine",Toast.LENGTH_SHORT).show()
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
        datePickerDialog.show()

    }
}