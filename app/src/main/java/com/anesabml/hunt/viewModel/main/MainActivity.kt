package com.anesabml.hunt.viewModel.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import coil.api.load
import com.anesabml.hunt.MyApp
import com.anesabml.hunt.R
import com.anesabml.hunt.databinding.ActivityMainBinding
import com.anesabml.hunt.model.User
import com.anesabml.lib.extension.hide
import com.anesabml.lib.extension.show
import com.anesabml.lib.extension.viewBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val KEY_USER = "user"

        fun createMainIntent(activity: AppCompatActivity, user: User): Intent {
            val intent = Intent(activity, MainActivity::class.java)
            intent.putExtra(KEY_USER, user)
            return intent
        }

        fun createMainIntent(activity: AppCompatActivity): Intent {
            return Intent(activity, MainActivity::class.java)
        }
    }

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApp).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val user = intent.getParcelableExtra<User>(KEY_USER)
        user?.let {
            displayUserInToolbar(it)
        }

        val navController = findNavController(R.id.fragment_container_view)

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.PostsFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.PostsFragment -> {
                    supportActionBar?.setDisplayShowTitleEnabled(false)
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

    private fun displayUserInToolbar(user: User) {
        with(binding) {
            userPhoto.load(user.profileImage)
            userName.text = user.name
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || findNavController(R.id.fragment_container_view).navigateUp()
    }
}
