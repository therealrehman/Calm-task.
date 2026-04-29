package com.example.calmtask.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.calmtask.MainActivity

object NotificationHelper {
    const val CHANNEL_REMINDERS = "calmtask_reminders"

    fun createChannel(context: Context) {
        val channel = NotificationChannel(
            CHANNEL_REMINDERS,
            "CalmTask Reminders",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply { description = "Morning, evening and task reminders" }
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .createNotificationChannel(channel)
    }

    fun showMorningNotification(context: Context, name: String) {
        val intent = PendingIntent.getActivity(
            context, 0,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val displayName = if (name.isBlank()) "" else " $name"
        val notif = NotificationCompat.Builder(context, CHANNEL_REMINDERS)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Good morning ☀️")
            .setContentText("Ready for today's plan,$displayName?")
            .setContentIntent(intent)
            .setAutoCancel(true)
            .build()
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .notify(1001, notif)
    }

    fun showNightNotification(context: Context, name: String, completedCount: Int) {
        val intent = PendingIntent.getActivity(
            context, 0,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val displayName = if (name.isBlank()) "" else ", $name"
        val notif = NotificationCompat.Builder(context, CHANNEL_REMINDERS)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Evening check-in 🌙")
            .setContentText("You completed $completedCount tasks. Ready to close the day$displayName?")
            .setContentIntent(intent)
            .setAutoCancel(true)
            .build()
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .notify(1002, notif)
    }
}
