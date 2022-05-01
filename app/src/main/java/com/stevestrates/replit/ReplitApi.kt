package com.stevestrates.replit

import rx.Single
import rx.schedulers.Schedulers

interface IReplitApi {
    fun putExec(command: String, language: String = "python"): Single<ExecResult>
}

class ReplitApi(private val service: ReplitApiService) : IReplitApi {
    override fun putExec(command: String, language: String): Single<ExecResult> {
        return service.putExec(ExecRequest(language, command))
            .subscribeOn(Schedulers.io())
    }
}