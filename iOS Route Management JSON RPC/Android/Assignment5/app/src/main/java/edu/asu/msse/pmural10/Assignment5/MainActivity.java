/*
* Created by Prashanth Murali on 2/09/17.
* Copyright © 2017 Prashanth Murali. All rights reserved.
* Right To Use for the instructor and the University to build and evaluate the software package
* @author Prashanth Murali mail to: pmurali10@asu.edu
* @version 1.0 March 24, 2017
 */
package edu.asu.msse.pmural10.Assignment5;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        DialogInterface.OnClickListener, TextView.OnEditorActionListener{


    public Spinner studentNamesSpinner;
    public static EditText urlET;
    public static String urlPass;
    public static EditText nametext, description, categorytext, addressline1, addressline2, elevationtext, latdata, longdata;
    public TextView namelabel, categorylabel, addresslabel, elevationlabel, latlabel, longlabel;
    public String name1, description1, category1, address1, address2, elevation1, latitude1, longitude1;
    public String[] names;
    public static int flag=0;

    public ArrayAdapter<String> adapter;
    public static String selectedPlace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nametext = (EditText) findViewById(R.id.namedata);
        description = (EditText) findViewById(R.id.description);
        categorytext = (EditText) findViewById(R.id.categorydata);
        addressline1 = (EditText) findViewById(R.id.addressline1);
        addressline2 = (EditText) findViewById(R.id.addressline2);
        elevationtext = (EditText) findViewById(R.id.elevationdata);
        latdata = (EditText) findViewById(R.id.latdata);
        longdata = (EditText) findViewById(R.id.longdata);

        nametext.setOnEditorActionListener(this);
        description.setOnEditorActionListener(this);
        categorytext.setOnEditorActionListener(this);
        addressline1.setOnEditorActionListener(this);
        addressline2.setOnEditorActionListener(this);
        elevationtext.setOnEditorActionListener(this);
        latdata.setOnEditorActionListener(this);
        longdata.setOnEditorActionListener(this);

        namelabel = (TextView) findViewById(R.id.namelabel);
        categorylabel = (TextView) findViewById(R.id.categorylabel);
        addresslabel = (TextView) findViewById(R.id.addresslabel);
        elevationlabel = (TextView) findViewById(R.id.elevationlabel);
        latlabel = (TextView) findViewById(R.id.latlabel);
        longlabel = (TextView) findViewById(R.id.longlabel);
        urlET = (EditText) findViewById(R.id.urlET);
        urlET.setOnEditorActionListener(this);

        names = new String[]{"unknown"};
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                new ArrayList<>(Arrays.asList(names)));

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         studentNamesSpinner = (Spinner) findViewById(R.id.studentNamesSpinner);
        studentNamesSpinner.setAdapter(adapter);
        studentNamesSpinner.setOnItemSelectedListener(this);


        try{
            MethodInformation mi = new MethodInformation(this, urlET.getText().toString(),"getNames",
                    new String[]{});
            AsyncCollectionConnect ac = (AsyncCollectionConnect) new AsyncCollectionConnect().execute(mi);
        } catch (Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(),"Exception creating adapter: "+
                    ex.getMessage());
        }
    }

    public boolean onEditorAction(TextView v, int actionId, KeyEvent event){

        android.util.Log.d(this.getClass().getSimpleName(), "onEditorAction: keycode " +
                ((event == null) ? "null" : event.toString()) + " actionId " + actionId);
        if(actionId== EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE){
            android.util.Log.d(this.getClass().getSimpleName(),"entry is: "+v.getText().toString());
        }
        return false; // without returning false, the keyboard will not disappear or move to next field
    }



    @Override
    public void onClick(DialogInterface dialog, int which) {
        Log.d(this.getClass().getSimpleName(), "in onClick");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedPlace = studentNamesSpinner.getSelectedItem().toString();
        android.util.Log.d(this.getClass().getSimpleName(),"spinner item selected "+selectedPlace);
        try{
            MethodInformation mi = new MethodInformation(this, urlET.getText().toString(),"get",
                    new String[]{selectedPlace});
            AsyncCollectionConnect ac = (AsyncCollectionConnect) new AsyncCollectionConnect().execute(mi);
        } catch (Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(),"Exception processing spinner selection: "+
                    ex.getMessage());
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menulayout,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        if(id==R.id.add){

            urlPass=urlET.getText().toString();
            Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
            startActivity(intent);
            flag=1;

        }

        if(id==R.id.edit){

            urlPass=urlET.getText().toString();
            selectedPlace=nametext.getText().toString();
            Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
            startActivity(intent);
            flag=0;

        }

        if(id==R.id.delete)
        {
            try {
                JSONObject jo = new JSONObject();
                try {
                    selectedPlace = nametext.getText().toString();
                    String newDescription = description.getText().toString();
                    String newCategory = categorytext.getText().toString();
                    String newAddressTitle = addressline1.getText().toString();
                    String newAddressStreet = addressline2.getText().toString();
                    String newElevation = elevationtext.getText().toString();
                    String newLatitude = latdata.getText().toString();
                    String newLongitude = longdata.getText().toString();


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                MethodInformation mi = new MethodInformation(this, MainActivity.urlET.getText().toString(), "remove",
                        new String[]{selectedPlace});
                AsyncCollectionConnect ac = (AsyncCollectionConnect) new AsyncCollectionConnect().execute(mi);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            nametext.setText("");
            description.setText("");
            categorytext.setText("");
            addressline1.setText("");
            addressline2.setText("");
            elevationtext.setText("");
            latdata.setText("");
            longdata.setText("");

            try{
                MethodInformation mi = new MethodInformation(this, urlET.getText().toString(),"getNames",
                        new String[]{});
                AsyncCollectionConnect ac = (AsyncCollectionConnect) new AsyncCollectionConnect().execute(mi);
            } catch (Exception ex){
                android.util.Log.w(this.getClass().getSimpleName(),"Exception creating adapter: "+
                        ex.getMessage());
            }
        }



        return super.onOptionsItemSelected(item);
    }

    public void refreshClicked(View v) {
        Log.d(this.getClass().getSimpleName(), "refreshClicked");
        try{
            MethodInformation mi = new MethodInformation(this, urlET.getText().toString(),"getNames",
                    new String[]{});
            AsyncCollectionConnect ac = (AsyncCollectionConnect) new AsyncCollectionConnect().execute(mi);
        } catch (Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(),"Exception creating adapter: "+
                    ex.getMessage());
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }


}
