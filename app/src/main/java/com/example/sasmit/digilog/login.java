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
import android.content.DialogInterface;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.*;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.List;
import java.lang.String;


import static android.content.DialogInterface.*;

public class login extends AppCompatActivity {

    private static final String TAG = "Important";
    ProgressDialog progressDialog;
    private static boolean Logged_in = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        final Button button = findViewById(R.id.login);
        final EditText uid = findViewById(R.id.uid);
        final EditText pwd = findViewById(R.id.pwd);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String user = uid.getText().toString();
                String pswd = pwd.getText().toString();


                get_data(user, pswd);



            }
        });
    }
    public void get_data(final String user, final String pswd){
        class GetData extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                progressDialog.setMessage("Please wait!");
                progressDialog.show();
                //                Toast.makeText(getContext(),"workingking",Toast.LENGTH_SHORT).show();
                //                progressBar.setVisibility(View.VISIBLE);
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                //                progressBar.setVisibility(View.GONE)
                progressDialog.dismiss();
                super.onPostExecute(s);
                parseJSON(s);
            }

            @Override
            protected String doInBackground(Void... params) {

                try {
                    URL url = new URL(MacroLog.portal_url+"/splogin/");
                    String urlParams = "username=" + user +
                            "&password=" + pswd;
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
                        Intent intent = new Intent(login.this, MacroLog.class);
                        startActivity(intent);
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(
                                login.this).create();

                        // Setting Dialog Title
                        alertDialog.setTitle("Alert Dialog");

                        // Setting Dialog Message
                        alertDialog.setMessage("Please Enter Correct UserID and Password!!!!");

                        alertDialog.setCancelable(true);


                        // Setting OK Button
                   /*alertDialog.setButton("OK", new OnClickListener() {

                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           dialogInterface.cancel();
                       }


                       public void onClick(DialogInterface dialog) {
                       dialog.cancel();
                       }

                   });*/

                        // Showing Alert Message
                        alertDialog.show();
                    }


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
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState");
    }

    @Override
    public void onBackPressed() {

    }
}
