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

public class Frag2 extends Fragment {
    private static final String TAG = "Fragment2";
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag2_layout, container, false);
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        get_data();

        // get the listview
        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                Log.d("Clicked",
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition));

                Intent intent = new Intent(getActivity().getApplicationContext(),MicroLog.class);
                intent.putExtra("name",
                        listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition));
                intent.putExtra("childPosition",childPosition);
                startActivity(intent);

                return false;
            }
        });

        // preparing list data

//        prepareListData();
//
//        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
//
//        // setting list adapter
//        expListView.setAdapter(listAdapter);

        return view;
    }
//    private void prepareListData()
//    {
//        listDataHeader = new ArrayList<String>();
//        listDataChild = new HashMap<String, List<String>>();
//
//        // Adding child data
//        listDataHeader.add("KVM Switching Solutions-2");
//        listDataHeader.add("LAN Extender & Print Server-2");
//        listDataHeader.add("Wireless Solutions-2");
//        listDataHeader.add("Switching Solutions-2");
//        listDataHeader.add("Storage Solutions-2");
//
//        // Adding child data
//        List<String> top250 = new ArrayList<String>();
//        top250.add("2KVM switching sublist 1");
//        top250.add("2KVM switching sublist 2");
//        top250.add("2KVM switching sublist 3");
//        top250.add("2KVM switching sublist 4");
//        top250.add("2KVM switching sublist 5");
//        top250.add("2KVM switching sublist 6");
//        top250.add("2KVM switching sublist 7");
//
//        List<String> nowShowing = new ArrayList<String>();
//        nowShowing.add("2Lan Extender sublist 1");
//        nowShowing.add("2Lan Extender sublist 2");
//        nowShowing.add("2Lan Extender sublist 3");
//        nowShowing.add("2Lan Extender sublist 4");
//        nowShowing.add("2Lan Extender sublist 5");
//        nowShowing.add("2Lan Extender sublist 6");
//
//        List<String> comingSoon = new ArrayList<String>();
//        comingSoon.add("24G/3G Adapters & Routers");
//        comingSoon.add("2Access Points");
//        comingSoon.add("2ADSL Routers");
//        comingSoon.add("2Managed Wireless Solution");
//        comingSoon.add("2VDSL Routers");
//
//        List<String> switchingsolu = new ArrayList<String>();
//        switchingsolu.add("2Switching solu sublist 1");
//        switchingsolu.add("2Switching solu sublist 2");
//        switchingsolu.add("2Switching solu sublist 3");
//        switchingsolu.add("2Switching solu sublist 4");
//        switchingsolu.add("2Switching solu sublist 5");
//
//        List<String> storagesolu = new ArrayList<String>();
//        storagesolu.add("2Storage solution sublist 1");
//        storagesolu.add("2Storage solution sublist 2");
//        storagesolu.add("2Storage solution sublist 3");
//        storagesolu.add("2Storage solution sublist 4");
//        storagesolu.add("2Storage solution sublist 5");
//
//        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
//        listDataChild.put(listDataHeader.get(1), nowShowing);
//        listDataChild.put(listDataHeader.get(2), comingSoon);
//        listDataChild.put(listDataHeader.get(3), switchingsolu);
//        listDataChild.put(listDataHeader.get(4), storagesolu);
//    }

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
                    URL url = new URL(MacroLog.portal_url+"/ap/passive/");
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

            private void parseJSON(String json) {
                try {
                    Log.d(TAG, "Response: " + json);
                    JSONArray root = new JSONArray(json);

                    listDataHeader.clear();
                    listDataChild.clear();

                    for(int i=0;i<root.length();i++){
                        JSONObject object = root.getJSONObject(i);
                        String header_name = object.getString("subgroup1");
                        JSONArray array = object.getJSONArray("subgroup2");

                        listDataHeader.add(header_name);

                        List<String> childList = new ArrayList<>();
                        for(int j=0;j<array.length();j++){
                            childList.add(array.getString(j));
                        }

                        listDataChild.put(header_name, childList);
                    }

                    listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

                    // setting list adapter
                    expListView.setAdapter(listAdapter);

//                    JSONObject response = root.getJSONObject("response");
//                    boolean success = response.getBoolean("success");

                } catch (JSONException e) {
                    Toast.makeText(getContext(),"Please check your connection!",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }
        GetData gd = new GetData();
        gd.execute();
    }




}

