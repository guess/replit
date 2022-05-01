package com.stevestrates.replit.models

sealed class ReplitResult
object Initial : ReplitResult()
object Loading : ReplitResult()
data class Success(val result: String) : ReplitResult()
data class Failure(val error: String?) : ReplitResult()
