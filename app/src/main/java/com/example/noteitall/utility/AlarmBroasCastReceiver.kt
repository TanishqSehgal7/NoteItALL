package com.example.noteitall.utility

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.noteitall.R
import com.example.noteitall.activities.MainActivity
import com.example.noteitall.activities.NotesActivity

class AlarmBroasCastReceiver : BroadcastReceiver(){

    override fun onReceive(context: Context?, intent: Intent?) {



        val intent=Intent(context,MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val pendingIntent=PendingIntent.getActivity(context,0,intent,0)

        val notificationBuilder=context?.let { NotificationCompat.Builder(it,"alarmNotificationForNote") }
        notificationBuilder?.setSmallIcon(R.drawable.ic_baseline_access_alarm_24)
            ?.setContentText("Reminder!!")
            ?.setContentTitle("Click here to see your note.")
            ?.setAutoCancel(true)
            ?.setDefaults(NotificationCompat.DEFAULT_ALL)
            ?.setPriority(NotificationCompat.PRIORITY_HIGH)

        notificationBuilder?.setContentIntent(pendingIntent)

        val notificationManager=context?.let { NotificationManagerCompat.from(it) }
        if (notificationBuilder != null) {
            notificationManager?.notify(123,notificationBuilder.build())
        }
    }
}