package com.stevestrates.replit

import com.stevestrates.replit.features.replit.ReplitRepository
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import io.reactivex.observers.TestObserver

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ReplitRepositoryTest {
    private val api = mockk<IReplitApi>()
    private val repo = ReplitRepository(api)
    private lateinit var subscriber: TestObserver<String>
    private val expectedValue = "test"

    @BeforeEach
    fun setup() {
        every { api.putExec(any(), any()) } returns Single.just(ExecResult(expectedValue))
        subscriber = repo.execPython("foo").test()
    }

    @Test
    fun `it completes with no errors`() {
        subscriber.assertNoErrors()
    }

    @Test
    fun `it correctly maps the result`() {
        subscriber.assertValue(expectedValue)
    }
}