package com.stevestrates.replit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stevestrates.replit.models.*

class ReplitViewModel(private val repo: ReplitRepository) : ViewModel() {
    private val command = MutableLiveData<String?>(null)

    private val _result = MutableLiveData<ReplitResult?>(Initial)
    val result: LiveData<ReplitResult?>
        get() = _result

    fun executeCode() {
        updateResult(Loading)
        command.value?.let {
            repo.execPython(it).subscribe(
                { commandResult -> updateResult(Success(commandResult)) },
                { error -> updateResult(Failure(error.message))}
            )
        }
    }

    private fun updateResult(state: ReplitResult) {
        _result.postValue(state)
    }

    fun onCodeChanged(code: String) {
        command.postValue(code)
    }
}
