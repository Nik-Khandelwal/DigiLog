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

//import com.bumptech.glide.GlideContext;
//import com.bumptech.glide.annotation.GlideModule;
//import com.bumptech.glide.module.AppGlideModule;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.net.Uri;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

//import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.String;



public class Specific extends AppCompatActivity {
    private static final String TAG = "Specific";
    Intent intent2;
    TextView dispText, dispText2, dispText3, dispText4,dispText5;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific);

        intent2=getIntent();

        dispText = (TextView) findViewById(R.id.prodname);
        dispText.setText(intent2.getStringExtra("name4"));

        get_data();

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
                    String prod = intent2.getStringExtra("name4").replaceAll(" ","_-").replaceAll("&","and").replaceAll("/","_");
                    URL url = new URL(MacroLog.portal_url+"/prod/"+prod+"/");
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

            public GetData() {
                super();
            }

            private void parseJSON(String json) {
                try {
                    Log.d(TAG, "Response: " + json);
                    JSONObject obj = new JSONObject(json);

                    dispText2 = (TextView) findViewById(R.id.showdesc);
                    dispText2.setText(obj.getString("description"));

                    dispText3 = (TextView) findViewById(R.id.prodname);
                    dispText3.setText(obj.getString("model_name"));

                    dispText4 = (TextView) findViewById(R.id.prodno);
                    dispText4.setText(obj.getString("model_no"));

                    dispText5 = (TextView) findViewById(R.id.showusp);
                    dispText5.setText(obj.getString("usp"));

                    imageView = (ImageView) findViewById(R.id.imageView);
                    String img_url = MacroLog.portal_url + obj.getString("image");
//
//                    Glide.with(context).load(img_url)
//                            .thumbnail(0.5f)
//                            .crossFade()
//                            .into(imageView);

                        Picasso.with(Specific.this).load(img_url).into(imageView);

//                    String u = MacroLog.portal_url+obj.getString("image");
//                    URL url;
//                    try {
//                        url = new URL(u);
//                        try {
//                            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                            imageView.setImageBitmap(bmp);
//                        }catch (IOException e){
//                            e.printStackTrace();
//                        }
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    }




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

