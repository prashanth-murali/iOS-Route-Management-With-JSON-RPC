package ser423.student.server;
/*
 * Copyright 2016 Tim Lindquist,
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Purpose: Example Java JsonRPC server for collection of students.
 *
 * Ser423 Mobile Applications
 * see http://pooh.poly.asu.edu/Mobile
 * @author Tim Lindquist Tim.Lindquist@asu.edu
 *         Software Engineering, CIDSE, IAFSE, ASU Poly
 * @version January 2016
 */

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Vector;
import java.util.Arrays;

public class Places {
   public String name;
   public String description;
   public String category;
   public String address_title;
   public String address_street;
   public String elevation;
   public String latitude;
   public String longitude;
			
   Places(String name,String description,String category,String address_title,String address_street,String elevation,String latitude,String longitude){
      this.name = name;
	  this.description = description;
	  this.category = category;
	  this.address_title = address_title;
	  this.address_street = address_street;
	  this.elevation = elevation;
	  this.latitude = latitude;
	  this.longitude = longitude;
      
   }

   Places(String jsonStr){
      try{
         JSONObject jo = new JSONObject(jsonStr);
         name = jo.getString("name");
		 description = jo.getString("description");
		 category = jo.getString("category");
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
         System.out.println(this.getClass().getSimpleName()+
                            ": error converting from json string");
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
         System.out.println(this.getClass().getSimpleName()+
                            ": error converting to json");
      }
      return jo;
   }

   public String toJsonString(){
      String ret = "";
      try{
         ret = this.toJson().toString();
      }catch (Exception ex){
         System.out.println(this.getClass().getSimpleName()+
                            ": error converting to json string");
      }
      return ret;
   }
}
