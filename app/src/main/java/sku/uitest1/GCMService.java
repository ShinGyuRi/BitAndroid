package sku.uitest1;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 618 on 2015-10-21.
 */
public class GCMService extends Service {
    private static final String TAG = "GCMService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    Bundle bundle = intent.getExtras();

                    String requestURL = "http://117.17.143.104:8081/BitProject/GCMSend";
                    String url;
                    String regId = bundle.getString("regId");
                    String member_id = bundle.getString("id");

                    Log.d(TAG, regId);
                    Log.d(TAG, member_id);

                    HttpClient client = new DefaultHttpClient();

                    List<NameValuePair> dataList = new ArrayList<NameValuePair>();
                    dataList.add(new BasicNameValuePair("regId", regId));
                    dataList.add(new BasicNameValuePair("member_id", member_id));
                    url = requestURL + "?" + URLEncodedUtils.format(dataList, "UTF-8");

                    Log.d("token", dataList.toString());
                    Log.d("requestURL", url);

                    HttpGet get = new HttpGet(url);

                    HttpResponse response = client.execute(get);

                    HttpEntity entity = response.getEntity();
                    InputStream is = entity.getContent();

                    Log.d("InputStream", is.toString());

                    is.close();

                }catch (Exception e)    {
                    e.printStackTrace();
                }


            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, 0, 1000*60);
        return START_REDELIVER_INTENT;
    }
}
