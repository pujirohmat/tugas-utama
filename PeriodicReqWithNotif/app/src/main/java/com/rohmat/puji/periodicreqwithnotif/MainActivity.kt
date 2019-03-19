package com.rohmat.puji.periodicreqwithnotif

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.work.WorkManager
import androidx.work.OneTimeWorkRequest
import javax.xml.datatype.DatatypeConstants.HOURS
import androidx.work.PeriodicWorkRequest
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //This is the subclass of our WorkRequest
//        val workRequest = OneTimeWorkRequest.Builder(MyWorker::class.java).build()

        val periodicWorkRequest = PeriodicWorkRequest.Builder(MyWorker::class.java, 10, TimeUnit.SECONDS).build()

        var btnEnqueue = findViewById<Button>(R.id.buttonEnqueue)

        //A click listener for the button
        //inside the onClick method we will perform the work
        btnEnqueue.setOnClickListener {
            WorkManager.getInstance().enqueue(periodicWorkRequest)

        }
    }
}
