package com.example.androidschool.calendar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidschool.MovieFragment
import com.example.androidschool.MoviesRecyclerAdapter
import com.example.androidschool.R

class CalendarFragment : Fragment(R.layout.fragment_calendar) {
    private val viewModel: CalendarViewModel by viewModels()
    private lateinit var calendarRecyclerAdapter: CalendarRecyclerAdapter
    private lateinit var calendarRecyclerView: RecyclerView

    private val listener = object : CalendarRecyclerAdapter.OnMovieListener {
        override fun onClick(movieId: Long) {
            val movieFragment = MovieFragment.newInstance(movieId)
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, movieFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToViewModel()

        viewModel.getScheduledMovies()

        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView)
        calendarRecyclerView.layoutManager = LinearLayoutManager(context)
        calendarRecyclerAdapter = CalendarRecyclerAdapter(listener)
        calendarRecyclerView.adapter = calendarRecyclerAdapter
    }

    private fun subscribeToViewModel() {
        viewModel.scheduledMovies.observe(viewLifecycleOwner) {
            calendarRecyclerAdapter.reload(it)

        }
    }
}