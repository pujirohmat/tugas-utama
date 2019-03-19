package com.rohmat.puji.periodicreqwithnotif

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import android.support.annotation.NonNull
import android.support.v4.app.NotificationCompat
import android.app.NotificationManager
import android.app.NotificationChannel


class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    /*
    * This method is responsible for doing the work
    * so whatever work that is needed to be performed
    * we will put it here
    *
    * For example, here I am calling the method displayNotification()
    * It will display a notification
    * So that we will understand the work is executed
    * */

    override fun doWork(): Result {
        print("Muncul! Munculah!!!")
        displayNotification("My Worker", "Hey I finished my work")
        return Result.SUCCESS
    }

    /*
    * The method is doing nothing but only generating
    * a simple notification
    * If you are confused about it
    * you should check the Android Notification Tutorial
    * */
    private fun displayNotification(title: String, task: String) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel("simplifiedcoding", "simplifiedcoding", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, "simplifiedcoding")
                .setContentTitle(title)
                .setContentText(task)
                .setSmallIcon(R.mipmap.ic_launcher)

        notificationManager.notify(1, notification.build())
    }
}