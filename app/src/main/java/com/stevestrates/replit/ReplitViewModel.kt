package com.stevestrates.replit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stevestrates.replit.models.*

class ReplitViewModel(private val repo: ReplitRepository) : ViewModel() {
    var command: String? = null

    private val _state = MutableLiveData<ReplitResult>(Initial)
    val state: LiveData<ReplitResult>
        get() = _state

    fun executeCode() {
        command?.let {
            updateResult(Loading)
            repo.execPython(it).subscribe(
                { commandResult -> updateResult(Success(commandResult)) },
                { error -> updateResult(Failure(error.message))}
            )
        }
    }

    private fun updateResult(state: ReplitResult) {
        _state.postValue(state)
    }

    fun onCodeChanged(code: String) {
        command = code
    }
}
