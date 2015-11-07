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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import sku.vo.AssignLectVO;
import sku.vo.MemberClassVO;

/**
 * Created by 618 on 2015-09-05.
 */
public class TabFragment2 extends Fragment implements View.OnClickListener{

    final String TAG = "TabFragment2";

    ArrayList<String> arraylist;
    ArrayList<String> arraylist2;
    ArrayList<String> arraylist3;
    static final List<String> lectNumList = new ArrayList<String>();
    static final List<String> teacherId = new ArrayList<String>();
    static String lectNum = null;

    static View view;

    Button btn_pay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_fragment_2, container, false);
        btn_pay = (Button)view.findViewById(R.id.btn_pay);

        btn_pay.setOnClickListener(this);

        new AllLectAsync().execute();


        return view;
    }

    @Override
    public void onClick(View v) {

    }

    public class AllLectAsync extends AsyncTask<String, Void, ArrayList> implements AdapterView.OnItemSelectedListener {

        String classListRequestURL = "http://117.17.143.104:8081/BitProject/androidAllClassList";

        @Override
        protected ArrayList doInBackground(String... params) {

            try {
                HttpClient client = new DefaultHttpClient();

                HttpGet get = new HttpGet(classListRequestURL);

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


                arraylist = new ArrayList<String>();

                if(jsonArray != null)  {
                    for (int i=0; i<jsonArray.length(); i++) {

                        arraylist.add(jsonArray.getJSONObject(i).getString("lect_name"));

                        lectNumList.add(jsonArray.getJSONObject(i).getString("lect_num"));

                        Log.d(TAG, jsonArray.getJSONObject(i).getString("lect_num"));
                    }
                }

                return arraylist;
            }catch (Exception e)    {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(final ArrayList arrayList) {
            super.onPostExecute(arrayList);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item, arrayList);
            //스피너 속성
            Spinner sp = (Spinner) view.findViewById(R.id.spi_class);
            sp.setPrompt("강의목록"); // 스피너 제목
            sp.setAdapter(adapter);
            sp.setOnItemSelectedListener(this);

        }


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
            Toast.makeText(getActivity(), arraylist.get(position), Toast.LENGTH_LONG).show();//해당목차눌렸을때
            Log.isLoggable(TAG, position);
            Log.d(TAG, String.valueOf(lectNumList.get(position)));
            new TeacherNameAsync().execute(lectNumList.get(position), Integer.toString(position));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public class TeacherNameAsync extends AsyncTask<String, Void, ArrayList> implements AdapterView.OnItemSelectedListener {

        String teacherNameRequestURL = "http://117.17.143.104:8081/BitProject/androidClassDetail";
        String url;

        @Override
        protected ArrayList doInBackground(String... params) {

            try {
                lectNum = params[1];
                HttpClient client = new DefaultHttpClient();

                List<NameValuePair> dataList = new ArrayList<NameValuePair>();
                dataList.add(new BasicNameValuePair("lect_num", params[0]));
                url = teacherNameRequestURL + "?" + URLEncodedUtils.format(dataList, "UTF-8");

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


                arraylist2 = new ArrayList<String>();

                if(jsonArray != null)  {
                    for (int i=0; i<jsonArray.length(); i++) {

                        arraylist2.add(jsonArray.getJSONObject(i).getString("teacher_name"));
                        teacherId.add(jsonArray.getJSONObject(i).getString("teacher_id"));

                        Log.d(TAG, jsonArray.getJSONObject(i).getString("teacher_name"));
                    }
                }

                return arraylist2;
            }catch (Exception e)    {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(final ArrayList arrayList) {
            super.onPostExecute(arrayList);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item, arrayList);
            //스피너 속성
            Spinner sp = (Spinner) view.findViewById(R.id.spi_teacher);
            sp.setPrompt("강사목록"); // 스피너 제목
            sp.setAdapter(adapter);
            sp.setOnItemSelectedListener(this);

        }


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getActivity(), arraylist2.get(position), Toast.LENGTH_LONG).show();//해당목차눌렸을때
            Log.isLoggable(TAG, position);
            try {
                new TimeListAsync().execute(lectNum, teacherId.get(position+1));
                Log.d(TAG, teacherId.get(position));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public class TimeListAsync extends AsyncTask<String, Void, ArrayList> implements AdapterView.OnItemSelectedListener {

        String teacherNameRequestURL = "http://117.17.143.104:8081/BitProject/andoridSelectTimetable";
        String url;


        @Override
        protected ArrayList doInBackground(String... params) {

            try {
                HttpClient client = new DefaultHttpClient();

                List<NameValuePair> dataList = new ArrayList<NameValuePair>();
                dataList.add(new BasicNameValuePair("lect_num", lectNumList.get(Integer.parseInt(params[0]))));
                dataList.add(new BasicNameValuePair("teacher_id", params[1]));
                url = teacherNameRequestURL + "?" + URLEncodedUtils.format(dataList, "UTF-8");

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


                arraylist3 = new ArrayList<String>();

                if(jsonArray != null)  {
                    for (int i=0; i<jsonArray.length(); i++) {

                        arraylist3.add(jsonArray.getJSONObject(i).getString("time_list"));

                        Log.d(TAG, jsonArray.getJSONObject(i).getString("time_list"));
                    }
                }

                return arraylist3;
            }catch (Exception e)    {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(final ArrayList arrayList) {
            super.onPostExecute(arrayList);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item, arrayList);
            //스피너 속성
            Spinner sp = (Spinner) view.findViewById(R.id.spi_timeList);
            sp.setPrompt("시간"); // 스피너 제목
            sp.setAdapter(adapter);
            sp.setOnItemSelectedListener(this);

        }


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getActivity(), arraylist3.get(position), Toast.LENGTH_LONG).show();//해당목차눌렸을때
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
