package com.example.androidschool.calendar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidschool.R

class CalendarFragment : Fragment(R.layout.fragment_calendar) {
    private val viewModel: CalendarViewModel by viewModels()
    private lateinit var calendarRecyclerAdapter: CalendarRecyclerAdapter
    private lateinit var calendarRecyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToViewModel()

        viewModel.getScheduledMovies()

        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView)
        calendarRecyclerView.layoutManager = LinearLayoutManager(context)
        calendarRecyclerAdapter = CalendarRecyclerAdapter(requireActivity())
        calendarRecyclerView.adapter = calendarRecyclerAdapter
    }

    private fun subscribeToViewModel() {
        viewModel.scheduledMovies.observe(viewLifecycleOwner) {
            calendarRecyclerAdapter.reload(it)

        }
    }
}