package com.example.androidschool

import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.ImageView
import androidx.fragment.app.Fragment
import java.util.*

class AddToCalendarFragment : Fragment(R.layout.calendar_layout) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.DATE, Calendar.getInstance().getActualMinimum(Calendar.DATE))
        val date: Long = calendar.time.time
        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
        calendarView.minDate = date

        view.findViewById<ImageView>(R.id.saveButton).setOnClickListener {
            val selectedDate = calendarView.date
        }
    }
}