package com.stevestrates.replit.features.replit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class ReplitViewModelFactory : ViewModelProvider.NewInstanceFactory(), KoinComponent {
    private val repo by inject<ReplitRepository>()
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = ReplitViewModel(repo) as T
}
