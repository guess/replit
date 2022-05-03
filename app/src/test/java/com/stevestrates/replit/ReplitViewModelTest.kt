package com.stevestrates.replit

import androidx.lifecycle.Observer
import com.stevestrates.replit.models.*
import com.stevestrates.replit.features.replit.ReplitRepository
import com.stevestrates.replit.utils.InstantExecutorExtension
import com.stevestrates.replit.features.replit.ReplitViewModel
import io.mockk.*
import io.reactivex.Single
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
class ReplitViewModelTest {
    private val repo = mockk<ReplitRepository>()
    private val viewModel = ReplitViewModel(repo)

    @BeforeEach
    fun setup() {
        every { repo.execPython(any()) } returns Single.just("Test")
    }

    @Nested
    @DisplayName("when the code is changed")
    inner class CodeChanged {
        private val code = "print(\"Hello world\")"

        @BeforeEach
        fun setup() {
            viewModel.onCodeChanged(code)
        }

        @Test
        fun `then the command is updated`() {
            viewModel.command shouldBeEqualTo code
        }
    }

    @Nested
    @DisplayName("when the code is executed")
    inner class CodeExecuted {
        private val observer = mockk<Observer<ReplitResult>> { every { onChanged(any()) } just Runs }

        @Nested
        @DisplayName("when result is successful")
        inner class ResultSuccessful {
            private val result = "foobar"

            @BeforeEach
            fun setup() {
                every { repo.execPython(any()) } returns Single.just(result)
                viewModel.state.observeForever(observer)
                viewModel.onCodeChanged("code")
                viewModel.executeCode()
            }

            @Test
            fun `the state is updated`() {
                verifySequence {
                    observer.onChanged(Initial)
                    observer.onChanged(Loading)
                    observer.onChanged(Success(result))
                }
            }
        }

        @Nested
        @DisplayName("when result is not successful")
        inner class ResultError {
            private val errorMessage = "error"

            @BeforeEach
            fun setup() {
                every { repo.execPython(any()) } returns Single.error(Throwable(errorMessage))
                viewModel.state.observeForever(observer)
                viewModel.onCodeChanged("code")
                viewModel.executeCode()
            }

            @Test
            fun `the state is updated`() {
                verifySequence {
                    observer.onChanged(Initial)
                    observer.onChanged(Loading)
                    observer.onChanged(Failure(errorMessage))
                }
            }
        }
    }
}