package com.example.androidschool

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidschool.model.MoviesList
import com.example.androidschool.network.NetworkService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment(R.layout.fragment_search) {
    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var moviesRecyclerAdapter: MoviesRecyclerAdapter
    private lateinit var emptyLayout: ConstraintLayout
    private lateinit var moviesLayout: ConstraintLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViews(view)
        emptyLayout.visibility = View.VISIBLE
//        moviesLayout.visibility = View.VISIBLE
        view.findViewById<SearchView>(R.id.searchView)
            .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextSubmit(p0: String?): Boolean {
                    p0?.let { getMovies(it) }
                    return false
                }
            })
        val recyclerView = view.findViewById<RecyclerView>(R.id.moviesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        moviesRecyclerAdapter = MoviesRecyclerAdapter()
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

    private fun getMovies(query: String) {
        NetworkService.getInstance()
            ?.getRetrofitServiceApi()
            ?.getMovies(query)
            ?.enqueue(object : Callback<MoviesList> {
                override fun onResponse(
                    call: Call<MoviesList>,
                    response: Response<MoviesList>
                ) {
                    response.body()?.let {
                        print(it.docs)
                        viewModel.setMovies(it.docs)
                    }
                }

                override fun onFailure(call: Call<MoviesList>, t: Throwable) {
                    viewModel.setMovies(emptyList())
                }
            })
    }
}