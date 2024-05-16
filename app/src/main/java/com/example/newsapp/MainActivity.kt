package com.example.newsapp

import android.app.Fragment
import android.app.FragmentManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.newsapp.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Fragmentを追加する
        if (savedInstanceState == null) {
            val fragmentManager: FragmentManager = getFragmentManager()
            val fragmentTransaction = fragmentManager.beginTransaction()
            val topFragment: Fragment = TopFragment()
            fragmentTransaction.add(R.id.fragment_container, topFragment)
            fragmentTransaction.commit()
        }
    }
}