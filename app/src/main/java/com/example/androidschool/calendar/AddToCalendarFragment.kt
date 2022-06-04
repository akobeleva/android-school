package com.example.androidschool.calendar

import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.androidschool.R
import java.util.Calendar
import java.util.Date


class AddToCalendarFragment : Fragment(R.layout.calendar_layout) {
    private var date: Long = Date().time
    private val viewModel: AddToCalendarViewModel by viewModels()

    companion object {
        fun newInstance(movieId: Long): AddToCalendarFragment =
            AddToCalendarFragment().apply {
                val bundle = Bundle()
                bundle.putLong("movieId", movieId)
                arguments = bundle
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
        setMinCalendarDate(calendarView)
        view.findViewById<ImageView>(R.id.saveButton).setOnClickListener {
            arguments?.getLong("movieId")?.let {
                viewModel.setScheduledMovie(Pair(date, it))
                viewModel.addScheduledMovie()
            }
            requireActivity().supportFragmentManager.popBackStack()
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar[year, month] = dayOfMonth
            date = calendar.timeInMillis
        }
    }

    private fun setMinCalendarDate(calendarView: CalendarView) {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.DATE, Calendar.getInstance().getActualMinimum(Calendar.DATE))
        val minDate: Long = calendar.time.time
        calendarView.minDate = minDate
    }
}