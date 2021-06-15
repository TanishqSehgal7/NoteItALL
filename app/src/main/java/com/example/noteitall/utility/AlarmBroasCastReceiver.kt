package com.example.noteitall.utility

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Bundle
import android.provider.ContactsContract
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.noteitall.R
import com.example.noteitall.activities.MainActivity
import com.example.noteitall.activities.NotesActivity
import com.example.noteitall.entities.Note

class AlarmBroasCastReceiver : BroadcastReceiver(){

    override fun onReceive(context: Context?, intent: Intent?) {

        val note:Note
            val titleOfNote = intent?.getStringExtra(NotesActivity.EXTRA_TITLE).toString()
            val contentOfNote = intent?.getStringExtra(NotesActivity.EXTRA_CONTENT)?.trim().toString()

        note = Note(titleOfNote, contentOfNote)

            val intent = Intent(context, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

            val notificationBuilder = context?.let { NotificationCompat.Builder(it, "alarmNotificationForNote") }

        notificationBuilder?.setSmallIcon(R.drawable.ic_baseline_access_alarm_24)
            ?.setContentText(note.titleOFNote)
            ?.setContentTitle("Reminder!!")
            ?.setAutoCancel(true)
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