/*
* Created by Prashanth Murali on 2/09/17.
* Copyright © 2017 Prashanth Murali. All rights reserved.
* Right To Use for the instructor and the University to build and evaluate the software package
* @author Prashanth Murali mail to: pmurali10@asu.edu
* @version 1.0 March 24, 2017
 */
package edu.asu.msse.pmural10.Assignment5;

import android.os.AsyncTask;
import android.os.Looper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;


public class AsyncCollectionConnect extends AsyncTask<MethodInformation, Integer, MethodInformation> {

    @Override
    protected void onPreExecute(){
        android.util.Log.d(this.getClass().getSimpleName(),"in onPreExecute on "+
                (Looper.myLooper() == Looper.getMainLooper()?"Main thread":"Async Thread"));
    }

    @Override
    protected MethodInformation doInBackground(MethodInformation... aRequest){

        Places aPlace = new Places("unknown","unknown","unknown","unknown","unknown","unknown","unknown","unknown");
        android.util.Log.d(this.getClass().getSimpleName(),"in doInBackground on "+
                (Looper.myLooper() == Looper.getMainLooper()?"Main thread":"Async Thread"));
        try {
            JSONArray ja = new JSONArray(aRequest[0].params);
            android.util.Log.d(this.getClass().getSimpleName(),"params: "+ja.toString());
            String requestData = "{ \"jsonrpc\":\"2.0\", \"method\":\""+aRequest[0].method+"\", \"params\":"+ja.toString()+
                    ",\"id\":3}";
            android.util.Log.d(this.getClass().getSimpleName(),"requestData: "+requestData+" url: "+aRequest[0].urlString);
            JsonRPCRequestViaHttp conn = new JsonRPCRequestViaHttp((new URL(aRequest[0].urlString)), aRequest[0].parent);
            String resultStr = conn.call(requestData);
            aRequest[0].resultAsJson = resultStr;
        }catch (Exception ex){
            android.util.Log.d(this.getClass().getSimpleName(),"exception in remote call "+
                    ex.getMessage());
        }
        return aRequest[0];
    }

    @Override
    protected void onPostExecute(MethodInformation res){
        android.util.Log.d(this.getClass().getSimpleName(), "in onPostExecute on " +
                (Looper.myLooper() == Looper.getMainLooper() ? "Main thread" : "Async Thread"));
        android.util.Log.d(this.getClass().getSimpleName(), " resulting is: " + res.resultAsJson);
        try {
            if (res.method.equals("getNames")) {
                JSONObject jo = new JSONObject(res.resultAsJson);
                JSONArray ja = jo.getJSONArray("result");
                ArrayList<String> al = new ArrayList<String>();
                for (int i = 0; i < ja.length(); i++) {
                    al.add(ja.getString(i));
                }
                String[] names = al.toArray(new String[0]);
                res.parent.adapter.clear();
                for (int i = 0; i < names.length; i++) {
                    res.parent.adapter.add(names[i]);
                }
                res.parent.adapter.notifyDataSetChanged();
                if (names.length > 0){
                    try{

                        MethodInformation mi = new MethodInformation(res.parent, res.urlString, "get",
                                new String[]{names[0]});
                        AsyncCollectionConnect ac = (AsyncCollectionConnect) new AsyncCollectionConnect().execute(mi);
                    } catch (Exception ex){
                        android.util.Log.w(this.getClass().getSimpleName(),"Exception processing spinner selection: "+
                                ex.getMessage());
                    }
                }
            } else if (res.method.equals("get")) {
                JSONObject jo = new JSONObject(res.resultAsJson);
                Places aPlace = new Places(jo.getJSONObject("result"));
                //res.parent.studentidET.setText((new Integer(aStud.studentid)).toString());
                res.parent.nametext.setText(aPlace.name);
                res.parent.description.setText(aPlace.description);
                android.util.Log.d("JSON FILE",jo.toString());
                res.parent.categorytext.setText(aPlace.category);
                res.parent.addressline1.setText(aPlace.address_title);
                res.parent.addressline2.setText(aPlace.address_street);
                res.parent.elevationtext.setText(aPlace.elevation);
                res.parent.latdata.setText(aPlace.latitude);
                res.parent.longdata.setText(aPlace.longitude);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
