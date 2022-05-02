package com.stevestrates.replit

import io.reactivex.Single

class ReplitRepository(private val api: IReplitApi) {
    fun execPython(command: String): Single<String> {
        return api.putExec(command).map { it.result }
    }
}