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

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Profs extends AppCompatActivity {
    private static final String TAG = "Profs";
    Intent intent2;
    TextView dispText;
    EditText fb;
    ListView listView;
    Spinner dropdown;
    Button sub;
    String fback;
    ArrayAdapter arrayAdapter;
    ArrayList<String> secondactivList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profs);

        MacroLog.currentpage = 1;

        //listView = (ListView) findViewById(R.id.dislist);
        dropdown = (Spinner) findViewById(R.id.dislist);
        secondactivList = new ArrayList<String>();
        sub=findViewById(R.id.submit);
        fb=findViewById(R.id.feedback);

        intent2=getIntent();

        get_data();

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, secondactivList);
        dropdown.setAdapter(arrayAdapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> adapterView, View view,final int i, long l) {
//                Intent intent = new Intent(getApplicationContext(), Specific.class);
//                intent.putExtra("name4",secondactivList.get(i));
//                startActivity(intent);

                sub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        fback=fb.getText().toString();
                        String item=adapterView.getItemAtPosition(i).toString();

                        ProgressDialog dialog = ProgressDialog.show(Profs.this, "",
                                "Loading. Please wait...", true);

                        get2_data(item, fback);

                        dialog.cancel();

                    }
                });


                Log.d("Yipeee", "Response: " + adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getApplicationContext(), Specific.class);
//                //intent.putExtra("name2", intent2.getStringExtra("name") + " List Item " + Integer.toString(position + 1));
//                intent.putExtra("name4",secondactivList.get(position));
//                startActivity(intent);
//            }
//        });



        TextView pin_code= findViewById(R.id.pincode);
        pin_code.setText(intent2.getStringExtra("pin"));


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
                    String pin = intent2.getStringExtra("pin");
                    URL url = new URL(MacroLog.portal_url+"/distributors_list/"+pin+"/");
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
                    JSONArray array = obj.getJSONArray("distributors_list");

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

    public void get2_data(final String name,final String feedb){
        class Get2Data extends AsyncTask<Void, Void, String> {
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
                    URL url = new URL(MacroLog.portal_url+"/distributor_feedback/");
                    String urlParams = "name=" + name +
                            "&feedback=" + feedb;
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setDoOutput(true);
                    StringBuilder sb = new StringBuilder();

                    OutputStream os = con.getOutputStream();
                    os.write(urlParams.getBytes());
                    os.flush();
                    os.close();

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
                    if (obj.getString("success").equals("1")) {

                        Intent intent = new Intent(Profs.this, DistDesc.class);
                        startActivity(intent);
                    }
                    else
                    {
                        AlertDialog alertDialog = new AlertDialog.Builder(
                                Profs.this).create();

                        // Setting Dialog Title
                        alertDialog.setTitle("Alert Dialog");

                        // Setting Dialog Message
                        alertDialog.setMessage("Feedback submission not successful! Please retry.");

                        alertDialog.setCancelable(true);


                        // Showing Alert Message
                        alertDialog.show();

                    }

                } catch (JSONException e) {
//                    //Toast.makeText(getContext(),"Please check your connection!",Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();

                    AlertDialog alertDialog = new AlertDialog.Builder(
                            Profs.this).create();

                    // Setting Dialog Title
                    alertDialog.setTitle("Alert Dialog");

                    // Setting Dialog Message
                    alertDialog.setMessage("Something went wrong! Please retry.");

                    alertDialog.setCancelable(true);


                    // Showing Alert Message
                    alertDialog.show();

                }

            }

        }
        Get2Data gd = new Get2Data();
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
            Intent intent=new Intent(this, MacroLog.class);
            this.startActivity(intent);
            return true;
        }

        if (id== R.id.distlist){
            Intent intent=new Intent(this, DistDesc.class);
            this.startActivity(intent);
            return true;
        }

        if (id== R.id.logout){
            Intent intent=new Intent(this, login.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

