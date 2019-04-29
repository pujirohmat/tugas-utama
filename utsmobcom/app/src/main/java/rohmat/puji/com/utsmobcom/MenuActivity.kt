package rohmat.puji.com.utsmobcom

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_menu.view.*
import org.jetbrains.annotations.Nullable


class MenuActivity : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater!!.inflate(R.layout.activity_menu, container, false)
        val periodicWorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java).build()

        view.subscribeButton.setOnClickListener {
            Log.d(TAG, "Subscribing to UTSMobCom topic")
            // [START subscribe_topics]
            FirebaseMessaging.getInstance().subscribeToTopic("weather")
                    .addOnCompleteListener { task ->
                        var msg = "Subscribed to UTSMobCom topic"
                        if (!task.isSuccessful) {
                            msg = "Failed to subscribe to UTSMobCom topic"
                        }
                        Log.d(TAG, msg)
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }
            // [END subscribe_topics]
        }

        view.logTokenButton.setOnClickListener {
            // Get token
            // [START retrieve_current_token]
            FirebaseInstanceId.getInstance().instanceId
                    .addOnCompleteListener(OnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            Log.w(TAG, "getInstanceId failed", task.exception)
                            return@OnCompleteListener
                        }

                        // Get new Instance ID token
                        val token = task.result?.token

                        // Get new Instance ID token
                        val token2 = task.result?.getToken()

                        // Log and toast
                        val msg = "InstanceID Token: %s" + " batas " + token2.toString()
                        Log.d(TAG, msg)
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    })
            // [END retrieve_current_token]
        }

        view.workRequestButton.setOnClickListener {
            WorkManager.getInstance().enqueue(periodicWorkRequest)

            WorkManager.getInstance().getWorkInfoByIdLiveData(periodicWorkRequest.getId())
                    .observe(this, Observer<WorkInfo>() {
                        fun onChanged(@Nullable workInfo: WorkInfo) {

                            //Displaying the status into TextView
                            Log.w(TAG, workInfo.state.name + "\n")
                        }
                    })
        }

        return view
    }

    companion object {
        private const val TAG = "MenuActivity"
    }
}