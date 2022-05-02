package com.stevestrates.replit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.stevestrates.replit.databinding.ActivityReplitBinding
import com.stevestrates.replit.models.Failure
import com.stevestrates.replit.models.Initial
import com.stevestrates.replit.models.Loading
import com.stevestrates.replit.models.Success
import org.koin.android.ext.android.inject

class ReplitActivity : AppCompatActivity() {
    private val viewModel: ReplitViewModel by viewModels { ReplitViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityReplitBinding.inflate(layoutInflater)

        binding.runButton.setOnClickListener {
            viewModel.executeCode()
        }

        viewModel.state.observe(this) {
            binding.resultsPreview.text = when(it) {
                Loading -> "Loading..."
                is Success -> it.result
                is Failure -> it.error
                else -> ""
            }
        }

        binding.codeEditor.addTextChangedListener {
            viewModel.onCodeChanged(it.toString())
        }

        setContentView(binding.root)
    }
}