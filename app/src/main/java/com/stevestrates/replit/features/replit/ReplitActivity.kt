package com.stevestrates.replit.features.replit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.amrdeveloper.codeview.CodeView
import com.stevestrates.replit.R
import com.stevestrates.replit.databinding.ActivityReplitBinding
import com.stevestrates.replit.models.Failure
import com.stevestrates.replit.models.Loading
import com.stevestrates.replit.models.Success
import com.stevestrates.replit.ui.editor.PythonLanguage

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
                Loading -> getString(R.string.loading)
                is Success -> it.result
                is Failure -> it.error
                else -> ""
            }
        }

        binding.codeEditor.addTextChangedListener {
            viewModel.onCodeChanged(it.toString())
        }
        styleCodeEditor(binding.codeEditor)

        setContentView(binding.root)
    }

    private fun styleCodeEditor(codeEditor: CodeView) {
        PythonLanguage.applyMonokaiTheme(this, codeEditor)
        codeEditor.setIndentationStarts(PythonLanguage.indentationStarts)
        codeEditor.setIndentationEnds(PythonLanguage.indentationEnds)
    }
}
