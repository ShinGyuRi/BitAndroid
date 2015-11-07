package sku.uitest1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.qualcomm.vuforia.samples.Books.R;
import com.qualcomm.vuforia.samples.Books.ui.ActivityList.AboutScreen;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import sku.vo.MemberClassVO;


/**
 * Created by 618 on 2015-09-05.
 */
public class TabFragment1 extends Fragment {

    ListView listView;
    ImageView imageView;
    RegLectAsync ra;
    private static long SPLASH_MILLIS = 450;

    public static TabFragment1 newInstance(String id) {
        TabFragment1 f = new TabFragment1();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("id", id);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ra = new RegLectAsync();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_fragment_1, container, false);
        listView=(ListView)view.findViewById(R.id.listview);
        imageView=(ImageView)view.findViewById(R.id.imageview);

//        Log.d("exe.id", getArguments().getString("id"));

        ra.execute(getArguments().getString("id"));
//        Log.d("frag.id", getArguments().getString("id"));



        return view;
    }

    public class RegLectAsync extends AsyncTask<String, Void, JSONArray> {

        String requestURL = "http://117.17.143.104:8081/BitProject/androidMemberClassList";
        String url;

        ArrayList<Listviewitem> data=new ArrayList<>();
        MemberClassVO memberClass = new MemberClassVO();

        @Override
        protected JSONArray doInBackground(String... params) {

            try {
                HttpClient client = new DefaultHttpClient();

                List<NameValuePair> dataList = new ArrayList<NameValuePair>();
                dataList.add(new BasicNameValuePair("member_id", params[0]));
                Log.d("member_id", params[0]);
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

                JSONArray jsonArray = new JSONArray(builder.toString());

                Log.d("jsonArray", jsonArray.getJSONObject(0).getString("lect_img"));

                String imageUrl;
                Bitmap classIcon;

                if(jsonArray != null)  {
                    for (int i=0; i<jsonArray.length(); i++) {

                        memberClass.setLect_img(jsonArray.getJSONObject(i).getString("lect_img"));
                        memberClass.setLect_name(jsonArray.getJSONObject(i).getString("lect_name"));
                        memberClass.setLect_time(jsonArray.getJSONObject(i).getString("lect_time"));
                        memberClass.setTeacher_name(jsonArray.getJSONObject(i).getString("teacher_name"));
                        memberClass.setExplanation(jsonArray.getJSONObject(i).getString("explanation"));

                        imageUrl = memberClass.getLect_img();

                        is = new java.net.URL(imageUrl).openStream();
                        classIcon = BitmapFactory.decodeStream(is);

                        Listviewitem memberClassList = new Listviewitem(classIcon, memberClass.getLect_name(), memberClass.getLect_time(), memberClass.getTeacher_name());

                        data.add(memberClassList);
                    }
                }

                return jsonArray;
            }catch (Exception e)    {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(final JSONArray jsonArray) {
            super.onPostExecute(jsonArray);


            ListviewAdapter adapter=new ListviewAdapter(getActivity(), R.layout.list_item,data);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            try {
                                Intent intent = new Intent(getActivity(),
                                        AboutScreen.class);
                                intent.putExtra("ACTIVITY_TO_LAUNCH",
                                        "app.Books.Books");
                                intent.putExtra("ABOUT_TEXT_TITLE", "Books");
                                intent.putExtra("ABOUT_TEXT", "Books/CR_about.html");
                                startActivity(intent);
                            }catch (Exception e)    {
                                e.printStackTrace();
                            }

                        }

                    }, SPLASH_MILLIS);
                }
            });


        }
    }
}
