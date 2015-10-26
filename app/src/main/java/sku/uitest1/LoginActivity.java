package sku.uitest1;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qualcomm.vuforia.samples.Books.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import sku.vo.MemberVO;


/**
 * A login screen that offers login via email/password and via Google+ sign in.
 * <p/>
 * ************ IMPORTANT SETUP NOTES: ************
 * In order for Google+ sign in to work with your app, you must first go to:
 * https://developers.google.com/+/mobile/android/getting-started#step_1_enable_the_google_api
 * and follow the steps in "Step 1" to create an OAuth 2.0 client for your package.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button bLogin;
    EditText etId, etPassword;
    TextView tvRegisterLink;
    public JSONObject jsonObject;

    LoginAsync la = new LoginAsync(new AsyncResponse() {
        @Override
        public void processFinish(JSONObject output) {
            jsonObject = output;
            try {
                Log.d("la.vo", jsonObject.getString("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etId = (EditText) findViewById(R.id.etId);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);

        bLogin.setOnClickListener(this);
        tvRegisterLink.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.bLogin:
                String id = etId.getText().toString();
                String password = etPassword.getText().toString();

                la = new LoginAsync(new AsyncResponse() {
                    @Override
                    public void processFinish(JSONObject output) {
                        jsonObject = output;
                        try {
                            Log.d("la.vo", jsonObject.getString("id"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                la.execute(id, password);

                if(la.getStatus() == AsyncTask.Status.FINISHED) {
                    la.cancel(true);
                    finish();
                }




                break;

            case R.id.tvRegisterLink:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        la.cancel(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        la.cancel(true);
    }

    public class LoginAsync  extends AsyncTask<String, Void, JSONObject> {

        String requestURL = "http://117.17.143.104:8081/BitProject/androidLogin";
        String url;
        public MemberVO vo = new MemberVO();
        public AsyncResponse delegate = null;

        public LoginAsync(AsyncResponse asyncResponse) {
            delegate = asyncResponse;
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            try {
                HttpClient client = new DefaultHttpClient();

                List<NameValuePair> dataList = new ArrayList<NameValuePair>();
                dataList.add(new BasicNameValuePair("id", params[0]));
                dataList.add(new BasicNameValuePair("password", params[1]));
                url = requestURL + "?" + URLEncodedUtils.format(dataList, "UTF-8");

                Log.d("select", dataList.toString());
                Log.d("requestURL", url);

                HttpGet get = new HttpGet(url);

                HttpResponse response = client.execute(get);

                HttpEntity entity = response.getEntity();
                InputStream is = entity.getContent();

                Log.d("InputStream", is.toString());

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is, "UTF-8"));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null)
                {
                    builder.append(line);
                }

                Log.d("builder", builder.toString());

                JSONObject jsonObject = new JSONObject(builder.toString());

                Log.d("jsonObject", jsonObject.getString("id"));

                if(jsonObject != null) {
                    vo.setId(jsonObject.getString("id"));
                    vo.setPassword(jsonObject.getString("password"));
                    vo.setName(jsonObject.getString("name"));
                    vo.setPhone(jsonObject.getString("phone"));
                    vo.setPost1(jsonObject.getString("post1"));
                    vo.setPost2(jsonObject.getString("post2"));
                    vo.setAddress(jsonObject.getString("address"));
                    vo.setDetailAddress(jsonObject.getString("detail_address"));
                    vo.setEmail(jsonObject.getString("email"));
                }
                else if (jsonObject == null)    {
                    isCancelled();
                }

                Log.d("vo.id", vo.getId());

                is.close();


                return jsonObject;
            }catch (Exception e){
                e.printStackTrace();
                return null;

            }


        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);

            if(jsonObject != null) {
                delegate.processFinish(jsonObject);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                try {
                    Bundle extras = new Bundle();
                    extras.putString("id", jsonObject.getString("id"));
                    intent.putExtras(extras);
                }catch (Exception e)    {
                    e.printStackTrace();
                }

                startActivity(intent);

                isCancelled();



            }
        }

    }

}

