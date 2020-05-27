package com.anesabml.hunt.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.anesabml.hunt.R
import com.anesabml.hunt.model.Post
import com.anesabml.hunt.viewModel.main.MainActivity
import javax.inject.Inject

class MyNotificationManager @Inject constructor(private val context: Context) {

    private val quoteChannelId = "posts"
    private val quoteChannelName = context.getString(R.string.new_posts)
    private val quoteChannelDescription = context.getString(R.string.new_posts_description)
    private val quoteNotificationId = 1

    private val notificationManager: NotificationManagerCompat by lazy {
        NotificationManagerCompat.from(context)
    }

    fun showPostNotification(post: Post) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    quoteChannelId, quoteChannelName, NotificationManager.IMPORTANCE_DEFAULT
                ).apply { description = quoteChannelDescription }
            )
        }

        // Create an explicit intent for an Activity in your app
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val builder = NotificationCompat.Builder(context, quoteChannelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(context.getString(R.string.product_of_the_day))
            .setContentText(post.tagline)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(post.description)
            )
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        notificationManager.notify(quoteNotificationId, builder.build())
    }
}
