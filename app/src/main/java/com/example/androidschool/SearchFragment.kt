package com.example.androidschool

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment

class SearchFragment : Fragment(R.layout.fragment_search) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ConstraintLayout>(R.id.emptyLayout).visibility = View.VISIBLE
    }
}