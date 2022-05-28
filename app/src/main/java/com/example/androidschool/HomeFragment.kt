package com.example.androidschool

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.*
import kotlin.coroutines.suspendCoroutine

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var moviesRecyclerAdapter: MoviesRecyclerAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var homeTitle: TextView
    private lateinit var homeRecyclerView: RecyclerView
    private lateinit var moviesService : MoviesService

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesService = MoviesService.getInstance(requireActivity().applicationContext)
        findViews(view)
        subscribeToViewModel()

        val startMovies = moviesService.getStartMovies()
        viewModel.setMovies(startMovies)

        swipeRefreshLayout.setOnRefreshListener {
             GlobalScope.launch { updateMovies() }
        }

        homeRecyclerView.layoutManager = LinearLayoutManager(context)
        moviesRecyclerAdapter = MoviesRecyclerAdapter(requireActivity())
        homeRecyclerView.adapter = moviesRecyclerAdapter

    }

    private suspend fun updateMovies() = coroutineScope{
            val movies = moviesService.getNewMovies()
            viewModel.setMovies(movies)
            swipeRefreshLayout.isRefreshing = false
    }

    @SuppressLint("SetTextI18n")
    private fun setTitle() {
        val year = viewModel.movies.value?.get(0)?.year
        homeTitle.text = "Топ фильмов за $year год"
    }

    private fun findViews(view: View) {
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        homeTitle = view.findViewById(R.id.homeTitle)
        homeRecyclerView = view.findViewById(R.id.homeRecyclerView)
    }

    private fun subscribeToViewModel() {
        viewModel.movies.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                val text = "Проверьте соединение и попробуйте еще раз"
                val duration = Toast.LENGTH_LONG

                val toast = Toast.makeText(context, text, duration)
                toast.show()
            } else {
                moviesRecyclerAdapter.reload(it)
                setTitle()
            }
        }
    }
}