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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import 	java.nio.charset.StandardCharsets;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DistProfile extends AppCompatActivity {
    private static final String TAG = "DistProfile";
    Intent intent2;
    Button button;
    EditText name,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dist_profile);

        button =(Button)findViewById(R.id.saveprof);
        name = (EditText)findViewById(R.id.name);
        address = (EditText)findViewById(R.id.address);

        intent2=getIntent();
        TextView pin_c= findViewById(R.id.pinshow);
        pin_c.setText(intent2.getStringExtra("pin"));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog dialog = ProgressDialog.show(DistProfile.this, "",
                        "Loading. Please wait...", true);

                get_data(name.getText().toString(),address.getText().toString());

                dialog.cancel();
            }
        });

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
    public void get_data(final String name, final String address){
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
                    String urlParameters = "name=" + name+ "&address=" + address + "&pincode=" + intent2.getStringExtra("pin");
                    byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
                    int postDataLength = postData.length;
                    String request = MacroLog.portal_url+"/new_distributor/";
                    URL url = new URL( request );
                    HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);
                    conn.setInstanceFollowRedirects(false);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestProperty("charset", "utf-8");
                    conn.setRequestProperty("Content-Length", Integer.toString(postDataLength ));
                    conn.setUseCaches(false);
                    try(DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                        wr.write( postData );
                        wr.flush();
                        wr.close();
                    }
//                    URL url = new URL(MacroLog.portal_url+"/new_distributor/");
//
//                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                    con.setDoOutput(true);
//
//
//                                        OutputStream os = con.getOutputStream();
//                                        os.write(urlParams.getBytes());
//                                        os.flush();
//                                        os.close();

                    // secondactivList.add("khil");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
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

                    if(obj.getString("success").equals("1")){
                        Intent intent = new Intent(DistProfile.this, DistDesc.class);
                        startActivity(intent);
                    }else{
                        AlertDialog alertDialog = new AlertDialog.Builder(
                                DistProfile.this).create();

                        // Setting Dialog Title
                        alertDialog.setTitle("Alert Dialog");

                        // Setting Dialog Message
                        alertDialog.setMessage("New Distributor was not formed! Please retry.");

                        alertDialog.setCancelable(true);


                        // Showing Alert Message
                        alertDialog.show();
                    }


                } catch (Exception e) {
//                    //Toast.makeText(getContext(),"Please check your connection!",Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();

                    AlertDialog alertDialog = new AlertDialog.Builder(
                            DistProfile.this).create();

                    // Setting Dialog Title
                    alertDialog.setTitle("Alert Dialog");
                    e.printStackTrace();
                    // Setting Dialog Message
                    alertDialog.setMessage("Connection Error!");

                    alertDialog.setCancelable(true);


                    // Showing Alert Message
                    alertDialog.show();

                }

            }

        }
        GetData gd = new GetData();
        gd.execute();

    }
}
