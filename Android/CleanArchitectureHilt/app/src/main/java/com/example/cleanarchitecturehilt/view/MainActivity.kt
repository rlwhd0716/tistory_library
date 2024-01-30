package com.example.cleanarchitecturehilt.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cleanarchitecturehilt.R
import com.example.cleanarchitecturehilt.databinding.ActivityMainBinding
import com.example.cleanarchitecturehilt.lib.ui.AbsActivity

class MainActivity : AbsActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}