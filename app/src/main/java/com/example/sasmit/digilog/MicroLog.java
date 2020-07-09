/** -------------------------------------------------------------------
 * Developed with Love for Smartlink Network Systems by :-            |
 *  BITS PILANI Interns(2018)                                         |
 *      Dev Arora                                                     |
 *      Nikhil Khandelwal                                             |
 *      Sasmit Mati                                                   |
 *      Shubham Mittal                                                |
 *      Shubham Raj                                                   |
 * --------------------------------------------------------------------
 */

package com.example.sasmit.digilog;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.lang.String;


public class MicroLog extends AppCompatActivity {
    private static final String TAG = "MicroLog";
    Intent intent2;
    TextView dispText;
    ListView listView;
    ArrayAdapter arrayAdapter;
    ArrayList<String> secondactivList;

//    private ListView mDrawerList;
//    private DrawerLayout mDrawerLayout;
//    private ArrayAdapter<String> mAdapter;
//    private ActionBarDrawerToggle mDrawerToggle;
//    private String mActivityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_micro_log);


        listView = (ListView) findViewById(R.id.listviewSecond);
        secondactivList = new ArrayList<String>();
        intent2 = getIntent();

        get_data();

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, secondactivList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Specific.class);
                //intent.putExtra("name2", intent2.getStringExtra("name") + " List Item " + Integer.toString(position + 1));
                intent.putExtra("name4",secondactivList.get(position));
                startActivity(intent);
            }
        });


        dispText = (TextView) findViewById(R.id.textUpdate);
        dispText.setText(intent2.getStringExtra("name"));
//        secondactivList.add(intent2.getStringExtra("name") + " List Item 1");
//        secondactivList.add(intent2.getStringExtra("name") + " List Item 2");
//        secondactivList.add(intent2.getStringExtra("name") + " List Item 3");
//        secondactivList.add(intent2.getStringExtra("name") + " List Item 4");
//        secondactivList.add(intent2.getStringExtra("name") + " List Item 5");

    }

    public void get_data(){
        class GetData extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                //                Toast.makeText(getContext(),"workingking",Toast.LENGTH_SHORT).show();
                //                progressBar.setVisibility(View.VISIBLE);
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                //                progressBar.setVisibility(View.GONE);
                super.onPostExecute(s);
                parseJSON(s);
            }

            @Override
            protected String doInBackground(Void... params) {

                try {
                    String subgr2 = intent2.getStringExtra("name").replaceAll(" ","_-").replaceAll("&","and").replaceAll("/","_");
                    URL url = new URL(MacroLog.portal_url+"/sg2/"+subgr2+"/");
                    //                    String urlParams = "user_id=" + Constant.currentItemUser.getId()+
                    //                            "&amount=" + amount;
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setDoOutput(true);
                    StringBuilder sb = new StringBuilder();
                    //
                    //                    OutputStream os = con.getOutputStream();
                    //                    os.write(urlParams.getBytes());
                    //                    os.flush();
                    //                    os.close();

                    // secondactivList.add("khil");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    String s = sb.toString().trim();
                    return s;

                } catch (IOException e) {
                    e.printStackTrace();
                    return "error";
                }
            }

            private void parseJSON(String json) {
                try {
                    Log.d(TAG, "Response: " + json);
                    JSONObject obj = new JSONObject(json);
                    JSONArray array = obj.getJSONArray("model_list");

                    for (int i = 0; i < array.length(); i++) {
                        // secondactivList.add("nik");
                        secondactivList.add(array.getString(i));

                    }

                    arrayAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    //Toast.makeText(getContext(),"Please check your connection!",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }

        }
        GetData gd = new GetData();
        gd.execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            Intent intent= new Intent(this, MacroLog.class);
            this.startActivity(intent);

            return true;
        }

        if (id== R.id.distlist){
            Intent intent= new Intent(this, DistDesc.class);
            this.startActivity(intent);
            return true;
        }

        if (id== R.id.logout){
            Intent intent= new Intent(this, login.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}