package com.wgu.kylerdavisc196project;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.wgu.kylerdavisc196project.model.Assessment;
import com.wgu.kylerdavisc196project.model.Course;

public class MyReceiver extends BroadcastReceiver {
    static int alertId;
    String channelId = "TermSchedAlerts";
    String[] extraArray;
    QueryManager QM;
    String contentText;
    String titleText;

    @Override
    public void onReceive(Context context, Intent intent) {
        QM = new QueryManager(context);
        if(intent.hasExtra("frame")) {
            extraArray = intent.getStringArrayExtra("frame");
        }
        if(extraArray.length > 0){
            QM.open();
            if("course".equals(extraArray[0])) {
                Course course = QM.selectCourse(Long.parseLong(extraArray[1]));
                if("start".equals(extraArray[2])) {
                    titleText = course.getName() + " Alert";
                    contentText = course.getName() + " is Starting Today!";
                } else {
                    titleText = course.getName() + " Alert";
                    contentText = course.getName() + " is Ending Today!";
                }
                QM.close();
            } else if("assessment".equals(extraArray[0])) {
                Assessment assessment = QM.selectAssessment(Long.parseLong(extraArray[1]));
                titleText = assessment.getName() + " Alert";
                contentText = assessment.getName() + " due date is Today!";
            }
        }else {
            System.out.println("No array");
        }
        Toast.makeText(context, contentText, Toast.LENGTH_SHORT).show();
        createNotificationChannel(context, channelId);
        Notification n = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(titleText)
                .setContentText(contentText).build();
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(alertId++,n);

        //throw new UnsupportedOperationException("Not yet implemented");
    }
    private void createNotificationChannel(Context context, String channelId) {
            CharSequence name = context.getResources().getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
    }
}
