package com.example.androidschool

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CalendarFragment : Fragment(R.layout.fragment_calendar) {
    private lateinit var scheduledMoviesRecyclerAdapter: ScheduledMoviesRecyclerAdapter
    private lateinit var calendarRecyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendarRecyclerView.layoutManager = LinearLayoutManager(context)
        scheduledMoviesRecyclerAdapter = ScheduledMoviesRecyclerAdapter(requireActivity())
        calendarRecyclerView.adapter = scheduledMoviesRecyclerAdapter
    }
}