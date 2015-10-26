package sku.dbconn;

import android.os.AsyncTask;
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

/**
 * Created by 618 on 2015-09-17.
 */
public class RegisterAsync extends AsyncTask<String, Void, InputStream> {


    String requestURL = "http://117.17.143.104:8081/BitProject/androidMemberJoin";

    @Override
    protected InputStream doInBackground(String... params) {

        try {
            HttpClient client = new DefaultHttpClient();

            List<NameValuePair> dataList = new ArrayList<NameValuePair>();
            dataList.add(new BasicNameValuePair("id", params[0]));
            dataList.add(new BasicNameValuePair("password", params[1]));
            dataList.add(new BasicNameValuePair("name", params[2]));
            dataList.add(new BasicNameValuePair("phone", params[3]));
            dataList.add(new BasicNameValuePair("post1", params[4]));
            dataList.add(new BasicNameValuePair("post2", params[5]));
            dataList.add(new BasicNameValuePair("address", params[6]));
            dataList.add(new BasicNameValuePair("detail_address", params[7]));
            dataList.add(new BasicNameValuePair("email", params[8]));
            requestURL = requestURL + "?" + URLEncodedUtils.format(dataList, "UTF-8");

            Log.d("insert", dataList.toString());
            Log.d("requestURL", requestURL);

            HttpGet get = new HttpGet(requestURL);

            HttpResponse response = client.execute(get);

            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();

            Log.d("InputStream", is.toString());
            return is;
        }catch (Exception e){
            e.printStackTrace();
            return null;

        }


    }
}
