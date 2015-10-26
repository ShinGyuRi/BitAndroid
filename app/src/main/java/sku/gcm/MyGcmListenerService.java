package sku.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.qualcomm.vuforia.samples.Books.R;

import sku.uitest1.MainActivity;

/**
 * Created by 618 on 2015-09-24.
 */
public class MyGcmListenerService extends GcmListenerService {
    Bitmap icon;

    private static final String TAG = "MyGcmListenerService";

    /**
     *
     * @param from SenderID 값을 받아온다.
     * @param data Set형태로 GCM으로 받은 데이터 payload이다.
     */
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String id = data.getString("id");
        String lect_img = data.getString("lect_img");
        String lect_name = data.getString("lect_name");
        String book_name = data.getString("book_name");
        String lect_time = data.getString("lect_time");


        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Id: "+ id);
        Log.d(TAG, "Lect_img: " + lect_img);
        Log.d(TAG, "Lect_name: " + lect_name);
        Log.d(TAG, "Book_name: " + book_name);
        Log.d(TAG, "lect_time: " + lect_time);

        // GCM으로 받은 메세지를 디바이스에 알려주는 sendNotification()을 호출한다.
        sendNotification(id, lect_name, book_name, lect_time);
    }


    /**
     * 실제 디바에스에 GCM으로부터 받은 메세지를 알려주는 함수이다. 디바이스 Notification Center에 나타난다.
     * @param id
     * @param lect_name
     * @param book_name
     * @param lect_time
     */
    private void sendNotification(String id, String lect_name, String book_name, String lect_time) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("id", id);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.book_icon1)
                .setTicker("수업시작 시간입니다")
                .setContentTitle(lect_name)
                .setContentText("교재: "+book_name+" 시간: "+lect_time)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}