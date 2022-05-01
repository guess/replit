package com.stevestrates.replit

import io.mockk.every
import io.mockk.mockk

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import rx.Single
import rx.observers.TestSubscriber

class ReplitRepositoryTest {
    private val api = mockk<IReplitApi>()
    private val repo = ReplitRepository(api)
    private val subscriber = TestSubscriber<String>()
    private val expectedValue = "test"

    @BeforeEach
    fun setup() {
        every { api.putExec(any(), any()) } returns Single.just(ExecResult(expectedValue))
        repo.execPython("foo").subscribe(subscriber)
    }

    @Test
    fun `it completes with no errors`() {
        subscriber.assertCompleted()
        subscriber.assertNoErrors()
    }

    @Test
    fun `it correctly maps the result`() {
        subscriber.assertValue(expectedValue)
    }
}