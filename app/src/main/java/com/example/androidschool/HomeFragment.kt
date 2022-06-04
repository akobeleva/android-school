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

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var moviesRecyclerAdapter: MoviesRecyclerAdapter
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var homeTitle: TextView? = null
    private var homeRecyclerView: RecyclerView? = null

    private val listener = object : MoviesRecyclerAdapter.OnMovieListener {
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
        findViews(view)
        subscribeToViewModel()

        viewModel.getStartMovies()

        swipeRefreshLayout?.setOnRefreshListener {
            viewModel.getNewMovies()
            swipeRefreshLayout?.isRefreshing = false
        }

        homeRecyclerView?.layoutManager = LinearLayoutManager(context)
        moviesRecyclerAdapter = MoviesRecyclerAdapter(listener)
        homeRecyclerView?.adapter = moviesRecyclerAdapter
    }

    @SuppressLint("SetTextI18n")
    private fun setTitle() {
        val year = viewModel.movies.value?.get(0)?.year
        homeTitle?.text = "Топ фильмов за $year год"
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

    override fun onDestroy() {
        super.onDestroy()
        swipeRefreshLayout = null
        homeTitle = null
        homeRecyclerView = null
    }
}