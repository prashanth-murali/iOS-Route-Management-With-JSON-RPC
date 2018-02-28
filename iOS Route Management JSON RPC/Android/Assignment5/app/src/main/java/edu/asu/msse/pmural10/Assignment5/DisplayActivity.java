/*
* Created by Prashanth Murali on 2/09/17.
* Copyright © 2017 Prashanth Murali. All rights reserved.
* Right To Use for the instructor and the University to build and evaluate the software package
* @author Prashanth Murali mail to: pmurali10@asu.edu
* @version 1.0 March 24, 2017
 */
package edu.asu.msse.pmural10.Assignment5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {

    public EditText nametext, description, categorytext, addressline1, addressline2, elevationtext, latdata, longdata;
    public TextView namelabel, categorylabel, addresslabel, elevationlabel, latlabel, longlabel;
    public String name1, description1, category1, address1, address2, elevation1, latitude1, longitude1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);



        nametext = (EditText) findViewById(R.id.namedata);
        description = (EditText) findViewById(R.id.description);
        categorytext = (EditText) findViewById(R.id.categorydata);
        addressline1 = (EditText) findViewById(R.id.addressline1);
        addressline2 = (EditText) findViewById(R.id.addressline2);
        elevationtext = (EditText) findViewById(R.id.elevationdata);
        latdata = (EditText) findViewById(R.id.latdata);
        longdata = (EditText) findViewById(R.id.longdata);

        namelabel = (TextView) findViewById(R.id.namelabel);
        categorylabel = (TextView) findViewById(R.id.categorylabel);
        addresslabel = (TextView) findViewById(R.id.addresslabel);
        elevationlabel = (TextView) findViewById(R.id.elevationlabel);
        latlabel = (TextView) findViewById(R.id.latlabel);
        longlabel = (TextView) findViewById(R.id.longlabel);


        if(MainActivity.flag==0)
        {

            android.util.Log.d(this.getClass().getSimpleName(),"EEEEEEEEEEEEEE");
            nametext.setText(MainActivity.selectedPlace);
            description.setText(MainActivity.nametext.getText().toString());
            categorytext.setText(MainActivity.categorytext.getText().toString());
            description.setText(MainActivity.description.getText().toString());
            addressline1.setText(MainActivity.addressline1.getText().toString());
            addressline2.setText(MainActivity.addressline2.getText().toString());
            elevationtext.setText(MainActivity.elevationtext.getText().toString());
            latdata.setText(MainActivity.latdata.getText().toString());
            longdata.setText(MainActivity.longdata.getText().toString());
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.save) {


            String newName = nametext.getText().toString();
            String newDescription = description.getText().toString();
            String newCategory = categorytext.getText().toString();
            String newAddressTitle = addressline1.getText().toString();
            String newAddressStreet = addressline2.getText().toString();
            String newElevation = elevationtext.getText().toString();
            String newLatitude = latdata.getText().toString();
            String newLongitude = longdata.getText().toString();


            if(!MainActivity.selectedPlace.equals("")&&!newDescription.equals("")&&!newCategory.equals("")&&!newAddressTitle.equals("")&&!newAddressStreet.equals("")&&!newElevation.equals("")&&!newLatitude.equals("")&&!newLongitude.equals("")) {
                JSONObject jo = new JSONObject();
                try {
                    if(MainActivity.flag==0)
                    {
                        jo.put("name", MainActivity.selectedPlace);
                    }
                    else {
                        jo.put("name", newName);
                    }

                    jo.put("category", newCategory);
                    jo.put("description", newDescription);
                    jo.put("address-title", newAddressTitle);
                    jo.put("address-street", newAddressStreet);
                    jo.put("elevation", newElevation);
                    jo.put("latitude", newLatitude);
                    jo.put("longitude", newLongitude);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }


                Log.v("ADD PLACE", MainActivity.selectedPlace);

                if (MainActivity.flag == 1) {
                    try {
                        MethodInformation mi = new MethodInformation(this, MainActivity.urlET.getText().toString(), "add",
                                new Object[]{jo});
                        AsyncCollectionConnect ac = (AsyncCollectionConnect) new AsyncCollectionConnect().execute(mi);
                    } catch (Exception ex) {
                        android.util.Log.w(this.getClass().getSimpleName(), "Exception processing spinner selection: " +
                                ex.getMessage());
                    }

                }

                if (MainActivity.flag == 0) {
                    try {
                        MethodInformation mi = new MethodInformation(this, MainActivity.urlET.getText().toString(), "add",
                                new Object[]{jo});
                        AsyncCollectionConnect ac = (AsyncCollectionConnect) new AsyncCollectionConnect().execute(mi);
                    } catch (Exception ex) {
                        android.util.Log.w(this.getClass().getSimpleName(), "Exception processing spinner selection: " +
                                ex.getMessage());
                    }

                }
            }

            else{
                    Toast.makeText(getApplicationContext(),"Enter All Details",Toast.LENGTH_SHORT).show();
                    }

        MainActivity.flag=0;
        }


        Intent intent = new Intent(DisplayActivity.this, MainActivity.class);
            startActivity(intent);

        return super.onOptionsItemSelected(item);
        }

}
