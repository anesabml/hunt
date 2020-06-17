package com.anesabml.hunt.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import coil.api.load
import com.anesabml.hunt.R
import com.anesabml.hunt.databinding.ActivityMainBinding
import com.anesabml.hunt.model.User
import com.anesabml.hunt.ui.auth.AuthenticationActivity
import com.anesabml.hunt.utils.SharedPref
import com.anesabml.hunt.worker.PodNotificationWorker
import com.anesabml.lib.extension.hide
import com.anesabml.lib.extension.show
import com.anesabml.lib.extension.showSnackBar
import com.anesabml.lib.extension.viewBinding
import com.anesabml.lib.network.Result
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        const val AUTH_ACTIVITY_REQUEST = 343
    }

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController

    @Inject
    lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        setupNavigation()

        observeResult()

        authenticateUser()

        setupWorkManager()
    }

    private fun setupNavigation() {
        navController = findNavController(R.id.fragment_container_view)

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.PostsFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.LoadingFragment -> {
                    supportActionBar?.hide()
                    binding.userPhoto.show()
                    binding.userName.show()
                }
                R.id.PostsFragment -> {
                    supportActionBar?.run {
                        show()
                        supportActionBar?.setDisplayShowTitleEnabled(false)
                    }
                    binding.userPhoto.show()
                    binding.userName.show()
                }
                R.id.PostDetailsFragment -> {
                    supportActionBar?.setDisplayShowTitleEnabled(true)
                    binding.userPhoto.hide()
                    binding.userName.hide()
                }
            }
        }
    }

    private fun authenticateUser() {
        if (sharedPref.token.isBlank()) {
            startAuth()
        } else {
            viewModel.getCurrentViewer()
        }
    }

    private fun observeResult() {
        viewModel.userResult.observe(this) {
            updateUI(it)
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
            is Result.Success -> {
                displayUserInToolbar(result.data)
                navController.navigate(R.id.posts_graph)
            }
            is Result.Error -> {
                binding.root.showSnackBar(R.string.error_authentication)
                navController.navigate(R.id.posts_graph)
            }
        }
    }

    private fun displayUserInToolbar(user: User) {
        with(binding) {
            userPhoto.load(user.profileImage)
            userName.text = user.name
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || findNavController(R.id.fragment_container_view).navigateUp()
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
                        authenticateUser()
                    }
                }
            }
        }
    }
}
