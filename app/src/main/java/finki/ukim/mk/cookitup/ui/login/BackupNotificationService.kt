package finki.ukim.mk.cookitup.ui.login

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import finki.ukim.mk.cookitup.R

class BackupNotificationService(private val context: Context) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    companion object{
        const val BACKUP_CHANNEL_ID = "backup_channel"
    }

    fun showNotification(){
        val notification = NotificationCompat.Builder(context, BACKUP_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_backup_done_24)
            .setContentTitle("Backup completed")
            .setContentText("Your recipes have been saved to the cloud")
            .build()

        notificationManager.notify(1,notification)
    }
}