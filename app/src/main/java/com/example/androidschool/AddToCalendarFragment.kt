package com.example.androidschool

import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.ImageView
import androidx.fragment.app.Fragment
import java.util.*

class AddToCalendarFragment : Fragment(R.layout.calendar_layout) {
    private lateinit var moviesService: MoviesService

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
        val date: Long = calendar.time.time
        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)
        calendarView.minDate = date

        view.findViewById<ImageView>(R.id.saveButton).setOnClickListener {
            val selectedDate = calendarView.date
            val movieId = arguments?.getLong("movieId")
            if (movieId != null) {
                moviesService.addScheduledMovie(selectedDate, movieId)
            }
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}