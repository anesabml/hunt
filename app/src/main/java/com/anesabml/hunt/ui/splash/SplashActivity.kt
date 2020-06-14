package com.anesabml.hunt.ui.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.anesabml.hunt.R
import com.anesabml.hunt.databinding.ActivitySplashBinding
import com.anesabml.hunt.model.User
import com.anesabml.hunt.ui.auth.AuthenticationActivity
import com.anesabml.hunt.ui.main.MainActivity.Companion.createMainIntent
import com.anesabml.hunt.utils.SharedPref
import com.anesabml.hunt.worker.PodNotificationWorker
import com.anesabml.lib.extension.show
import com.anesabml.lib.extension.showSnackBar
import com.anesabml.lib.extension.viewBinding
import com.anesabml.lib.network.Result
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    companion object {
        const val AUTH_ACTIVITY_REQUEST = 343
    }

    private val binding: ActivitySplashBinding by viewBinding(ActivitySplashBinding::inflate)
    private val viewModel: SplashViewModel by viewModels()

    @Inject
    lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.userResult.observe(this) {
            updateUI(it)
        }

        if (sharedPref.token.isBlank()) {
            startAuth()
        } else {
            viewModel.getCurrentViewer()
        }

        if (sharedPref.shouldSetupPodWorker) {
            setupWorkManager()
            sharedPref.shouldSetupPodWorker = false
        }
    }

    private fun setupWorkManager() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val podRequest = PeriodicWorkRequestBuilder<PodNotificationWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .setInitialDelay(1, TimeUnit.DAYS)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "pod_notification_worker",
            ExistingPeriodicWorkPolicy.REPLACE,
            podRequest
        )
    }

    private fun startAuth() {
        startActivityForResult(
            AuthenticationActivity.createIntent(this),
            AUTH_ACTIVITY_REQUEST
        )
    }

    private fun updateUI(result: Result<User>) {
        when (result) {
            Result.Loading -> {
                binding.progressBar.show()
            }
            is Result.Success -> {
                startActivity(createMainIntent(this, result.data))
                finish()
            }
            is Result.Error -> {
                binding.root.showSnackBar(R.string.error_authentication)
                startActivity(createMainIntent(this))
                finish()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            AUTH_ACTIVITY_REQUEST -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val token = data.getStringExtra(AuthenticationActivity.KEY_TOKEN)
                    if (token.isNullOrBlank()) {
                        binding.root.showSnackBar(R.string.error_authentication)
                    } else {
                        sharedPref.token = token
                        viewModel.getCurrentViewer()
                    }
                }
            }
        }
    }
}
