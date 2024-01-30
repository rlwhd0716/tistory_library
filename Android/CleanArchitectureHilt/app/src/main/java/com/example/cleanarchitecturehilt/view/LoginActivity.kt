package com.example.cleanarchitecturehilt.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cleanarchitecturehilt.R
import com.example.cleanarchitecturehilt.databinding.ActivityLoginBinding
import com.example.cleanarchitecturehilt.lib.ui.AbsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AbsActivity<ActivityLoginBinding, LoginViewModel>(R.layout.activity_login) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override var bindingApply: (ActivityLoginBinding.() -> Unit)? = {
        viewModel.userLogin()
    }

    override var vmApply: (LoginViewModel.() -> Unit)? = {

    }
}