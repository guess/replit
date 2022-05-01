package com.stevestrates.replit

import rx.Single

class ReplitRepository(private val api: IReplitApi) {
    fun execPython(command: String): Single<String> {
        return api.putExec(command).map { it.result }
    }
}