package com.testlab.yevhenbiletskiy.testlab.presentation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.testlab.yevhenbiletskiy.testlab.R
import com.testlab.yevhenbiletskiy.testlab.presentation.screens.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

}
