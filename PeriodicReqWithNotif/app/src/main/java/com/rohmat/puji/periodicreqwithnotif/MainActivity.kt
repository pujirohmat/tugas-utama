package com.rohmat.puji.periodicreqwithnotif

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.work.WorkManager
import androidx.work.OneTimeWorkRequest
import android.arch.lifecycle.Observer
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import org.jetbrains.annotations.Nullable
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //This is the subclass of our WorkRequest
//        val periodicWorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java).build()

        val periodicWorkRequest = PeriodicWorkRequest.Builder(MyWorker::class.java, 10, TimeUnit.HOURS).build()

        var btnEnqueue = findViewById<Button>(R.id.buttonEnqueue)
        var txtStatus = findViewById<TextView>(R.id.textViewStatus)

        //A click listener for the button
        //inside the onClick method we will perform the work
        btnEnqueue.setOnClickListener {
            WorkManager.getInstance().enqueue(periodicWorkRequest)

            WorkManager.getInstance().getWorkInfoByIdLiveData(periodicWorkRequest.getId())
                    .observe(this, Observer<WorkInfo>() {
                        fun onChanged(@Nullable workInfo: WorkInfo) {

                            //Displaying the status into TextView
                            txtStatus.append(workInfo.state.name + "\n")
                        }
                    })
        }


    }
}
