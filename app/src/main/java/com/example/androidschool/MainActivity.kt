package com.example.androidschool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, MovieFragment())
                .commit()
        }

        findViewById<View>(R.id.homeButton).setOnClickListener {
            replaceFragment(HomeFragment())
        }
        findViewById<View>(R.id.searchButton).setOnClickListener {
            replaceFragment(SearchFragment())
        }
        findViewById<View>(R.id.calendarButton).setOnClickListener {
            replaceFragment(CalendarFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}