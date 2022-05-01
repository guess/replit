package com.stevestrates.replit

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.PUT
import rx.Single

data class ExecRequest(val language: String, val command: String)
data class ExecResult(val result: String)

interface ReplitApiService {
    @Headers("Content-Type: application/json")
    @PUT("/exec")
    fun putExec(@Body body: ExecRequest): Single<ExecResult>
}
