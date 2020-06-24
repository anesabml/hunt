package com.anesabml.producthunt.ui.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.observe
import com.anesabml.lib.extension.hide
import com.anesabml.lib.extension.show
import com.anesabml.lib.extension.showSnackBar
import com.anesabml.producthunt.R
import com.anesabml.producthunt.databinding.ActivityAuthentificationBinding
import com.anesabml.producthunt.model.Token
import com.anesabml.producthunt.utils.Constant
import com.anesabml.lib.extension.viewBinding
import com.anesabml.lib.network.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationActivity : AppCompatActivity() {

    companion object {
        const val KEY_TOKEN = "token"

        fun createIntent(activity: AppCompatActivity): Intent {
            return Intent(activity, AuthenticationActivity::class.java)
        }
    }

    private val binding by viewBinding(
        ActivityAuthentificationBinding::inflate
    )
    private val viewModel: AuthenticationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.launchUrl(this, Uri.parse(Constant.AUTH_API))

        viewModel.result.observe(this) {
            updateUi(it)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val code = intent?.data?.getQueryParameter("code")
        if (code != null) {
            viewModel.handleAuthorizationCode(code)
        } else {
            binding.root.showSnackBar(R.string.error_authentication)
        }
    }

    private fun updateUi(result: Result<Token>) {
        when (result) {
            Result.Loading -> {
                binding.progressBar.show()
            }
            is Result.Success -> {
                binding.progressBar.hide()
                setResult(
                    RESULT_OK,
                    Intent().apply { putExtra(KEY_TOKEN, result.data.accessToken) }
                )
                finish()
            }
            is Result.Error -> {
                binding.progressBar.hide()
                binding.root.showSnackBar(R.string.error_authentication)
            }
        }
    }
}
