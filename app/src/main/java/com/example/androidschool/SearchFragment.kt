package com.example.androidschool

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchFragment : Fragment(R.layout.fragment_search) {
    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var moviesRecyclerAdapter: MoviesRecyclerAdapter
    private lateinit var emptyLayout: ConstraintLayout
    private lateinit var moviesLayout: ConstraintLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViews(view)
        emptyLayout.visibility = View.VISIBLE

        view.findViewById<SearchView>(R.id.searchView)
            .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextSubmit(p0: String?): Boolean {
                    p0?.let {
                        viewModel.searchMovies(it)
                    }
                    return false
                }
            })
        val recyclerView = view.findViewById<RecyclerView>(R.id.moviesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        moviesRecyclerAdapter = MoviesRecyclerAdapter(requireActivity())
        recyclerView.adapter = moviesRecyclerAdapter

        subscribeToViewModel()
    }

    private fun findViews(view: View) {
        emptyLayout = view.findViewById(R.id.emptyLayout)
        moviesLayout = view.findViewById(R.id.moviesLayout)
    }

    private fun subscribeToViewModel() {
        viewModel.movies.observe(viewLifecycleOwner) {
            moviesRecyclerAdapter.reload(it)
            if (it.isEmpty()) {
                emptyLayout.visibility = View.VISIBLE
                moviesLayout.visibility = View.GONE
            } else {
                emptyLayout.visibility = View.GONE
                moviesLayout.visibility = View.VISIBLE
            }
        }
    }
}