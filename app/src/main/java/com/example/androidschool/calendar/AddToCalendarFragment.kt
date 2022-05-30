package com.example.androidschool.calendar

import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.androidschool.R
import com.example.androidschool.data.MoviesService
import java.util.*


class AddToCalendarFragment : Fragment(R.layout.calendar_layout) {
    private lateinit var moviesService: MoviesService
    private var date: Long = Date().time

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
        moviesService = MoviesService.getInstance(requireContext())
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.DATE, Calendar.getInstance().getActualMinimum(Calendar.DATE))
        val minDate: Long = calendar.time.time
        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
        calendarView.minDate = minDate

        view.findViewById<ImageView>(R.id.saveButton).setOnClickListener {
            val movieId = arguments?.getLong("movieId")
            if (movieId != null) {
                MoviesService.addScheduledMovie(date, movieId)
            }
            requireActivity().supportFragmentManager.popBackStack()
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar[year, month] = dayOfMonth
            date = calendar.timeInMillis
        }
    }
}