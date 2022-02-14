package com.example.noteitall.utility

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.noteitall.R
import com.example.noteitall.activities.NotesActivity
import com.example.noteitall.entities.Note

class AlarmBroasCastReceiver : BroadcastReceiver(){



    override fun onReceive(context: Context?, intent: Intent?) {


            val intentTarget = Intent(context, NotesActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intentTarget, PendingIntent.FLAG_UPDATE_CURRENT)
            val notificationBuilder = context?.let { NotificationCompat.Builder(it, "alarmNotificationForNote") }
            val notificationTitle=intentTarget.getStringExtra("notificationTitle")
            val notificationText=intentTarget.getStringExtra("notificationContent")
            val note=Note(notificationTitle.toString(),notificationText.toString())

        notificationBuilder?.setSmallIcon(R.drawable.ic_baseline_access_alarm_24)
            ?.setContentTitle(note.titleOFNote)
            ?.setContentText("Click to check note")
            ?.addAction(R.drawable.ic_baseline_open_in_new_24,"Check Note",pendingIntent)
            ?.setAutoCancel(true)
            ?.setVisibility(VISIBILITY_PUBLIC)
            ?.setDefaults(NotificationCompat.DEFAULT_ALL)?.priority = NotificationCompat.PRIORITY_HIGH

        val alarmSound=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        notificationBuilder?.setSound(alarmSound)?.setColor(ContextCompat.getColor(context,R.color.MyYellow))

            notificationBuilder?.setContentIntent(pendingIntent)

            val notificationManager = context?.let { NotificationManagerCompat.from(it) }
            if (notificationBuilder != null) {
                notificationManager?.notify(123, notificationBuilder.build())
            }
    }
}