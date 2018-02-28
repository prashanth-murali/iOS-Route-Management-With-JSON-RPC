/*
* Created by Prashanth Murali on 2/09/17.
* Copyright © 2017 Prashanth Murali. All rights reserved.
* Right To Use for the instructor and the University to build and evaluate the software package
* @author Prashanth Murali mail to: pmurali10@asu.edu
* @version 1.0 March 24, 2017
 */
package edu.asu.msse.pmural10.Assignment5;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Vector;


public class Places {
    public String name;
    public String description;
    public String category;
    public String address_title;
    public String address_street;
    public String elevation;
    public String latitude;
    public String longitude;
    //public int studentid;
    //public Vector<String> takes;

    Places(String name, String description, String category, String addressTitle, String addressText, String elevation, String lat, String lon) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.address_title = addressTitle;
        this.address_street = addressText;
        this.elevation = elevation;
        this.latitude = lat;
        this.longitude = lon;
    }

    Places(String jsonStr){
        try{
            JSONObject jo = new JSONObject(jsonStr);

            name = jo.getString("name");
            category = jo.getString("category");
            description = jo.getString("description");
            address_title = jo.getString("address-title");
            address_street = jo.getString("address-street");
            elevation = jo.getString("elevation");
            latitude = jo.getString("latitude");
            longitude = jo.getString("longitude");

        }catch (Exception ex){
            System.out.println(this.getClass().getSimpleName()+
                    ": error converting from json string");
        }
    }

    public Places(JSONObject jsonObj){
        try{
            System.out.println("constructor from json received: "+
                    jsonObj.toString());
            name = jsonObj.getString("name");
            category = jsonObj.getString("category");
            description = jsonObj.getString("description");
            address_title = jsonObj.getString("address-title");
            address_street = jsonObj.getString("address-street");
            elevation = jsonObj.getString("elevation");
            latitude = jsonObj.getString("latitude");
            longitude = jsonObj.getString("longitude");

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public JSONObject toJson(){
        JSONObject jo = new JSONObject();
        try{
            jo.put("name",name);
            jo.put("category",category);
            jo.put("description",description);
            jo.put("address-title",address_title);
            jo.put("address-street",address_street);
            jo.put("elevation",elevation);
            jo.put("latitude",latitude);
            jo.put("longitude",longitude);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return jo;
    }

    public String toJsonString(){
        String ret = "";
        try{
            ret = this.toJson().toString();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ret;
    }
}
