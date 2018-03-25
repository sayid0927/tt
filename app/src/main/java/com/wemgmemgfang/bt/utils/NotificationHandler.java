package com.wemgmemgfang.bt.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.ui.activity.MainActivity;


public class NotificationHandler {
    // Notification handler singleton
    private static NotificationHandler nHandler;
    private static NotificationManager mNotificationManager;


    private NotificationHandler() {
    }


    /**
     * Singleton pattern implementation
     *
     * @return
     */
    public static NotificationHandler getInstance(Context context) {
        if (nHandler == null) {
            nHandler = new NotificationHandler();
            mNotificationManager =
                    (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return nHandler;
    }


    /**
     * Shows a simple notification
     *
     * @param context aplication context
     */
    public void createSimpleNotification(Context context) {
        // Creates an explicit intent for an Activity
        Intent resultIntent = new Intent(context, MainActivity.class);

        // Creating a artifical activity stack for the notification activity
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        // Pending intent to the notification manager
        PendingIntent resultPending = stackBuilder
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Building the notification
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.app_loading0) // notification icon
                .setContentTitle("I'm a simple notification") // main title of the notification
                .setContentText("I'm the text of the simple notification") // notification text
                .setContentIntent(resultPending); // notification intent

        // mId allows you to update the notification later on.
        mNotificationManager.notify(10, mBuilder.build());
    }


//	public void createExpandableNotification (Context context) {
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//			// Building the expandable content
//			NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
//			String lorem = context.getResources().getString(R.string.long_lorem);
//			String [] content = lorem.split("\\.");
//
//			inboxStyle.setBigContentTitle("This is a big title");
//			for (String line : content) {
//				inboxStyle.addLine(line);
//			}
//
//			// Building the notification
//			NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context)
//					.setSmallIcon(R.drawable.ic_launcher) // notification icon
//					.setContentTitle("Expandable notification") // title of notification
//					.setContentText("This is an example of an expandable notification") // text inside the notification
//					.setStyle(inboxStyle); // adds the expandable content to the notification
//
//			mNotificationManager.notify(11, nBuilder.build());
//
//		} else {
//			Toast.makeText(context, "Can't show", Toast.LENGTH_LONG).show();
//		}
//	}

    /**
     * 取消通知
     */
    public void cancelNotification(int progresID) {
        mNotificationManager.cancel(progresID);
    }

    public void updateProgressNotification(NotificationCompat.Builder nBuilder, int i , int progresID) {

        nBuilder
                .setProgress(100, i, false)
                .setSmallIcon(R.mipmap.app_loading0)
                .setContentInfo(i + " %");
                mNotificationManager.notify(progresID, nBuilder.build());
    }

    /**
     * Show a determinate and undeterminate progress notification
     *
     * @param context, activity context
     */
    public NotificationCompat.Builder createProgressNotification(final Context context, String Title, int progresID) {

        // building the notification
        final NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.app_loading0)
                .setContentTitle(Title)
//                .setContentText("0%")
                .setUsesChronometer(true)
                .setProgress(100, 0, true);
              mNotificationManager.notify(progresID, nBuilder.build());

             return nBuilder;

    }


//	public void createButtonNotification (Context context) {
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//			// Prepare intent which is triggered if the  notification button is pressed
//			Intent intent = new Intent(context, TestActivity.class);
//			PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);
//
//			// Building the notifcation
//			NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context)
//					.setSmallIcon(R.drawable.ic_launcher) // notification icon
//					.setContentTitle("Button notification") // notification title
//					.setContentText("Expand to show the buttons...") // content text
//					.setTicker("Showing button notification") // status bar message
//					.addAction(R.drawable.accept, "Accept", pIntent) // accept notification button
//					.addAction(R.drawable.cancel, "Cancel", pIntent); // cancel notification button
//
//			mNotificationManager.notify(1001, nBuilder.build());
//
//		} else {
//			Toast.makeText(context, "You need a higher version", Toast.LENGTH_LONG).show();
//		}
//	}
}
