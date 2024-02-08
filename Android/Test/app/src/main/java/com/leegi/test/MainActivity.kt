package com.leegi.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.leegi.lib_test.TestObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        TestObject.showToast(this@MainActivity, "라이브러리 테스트")
    }
}