package com.example.karthikslaptop.wuatest1;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.SupportMapFragment;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
//Google Maps page
public class Location_2 extends FragmentActivity implements OnMapReadyCallback {
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Marker marker;
        setContentView(R.layout.activity_location_2);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }
    //Sets Map conditions
    @Override
    public void onMapReady(final GoogleMap map) {
        final Marker Final_marker;
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            Marker marker;

            LatLng Locations = new LatLng(0,0);
            //On Hold, the mao adds a marker at that point
            public void onMapLongClick(LatLng latLng) {
                if (marker != null) {
                    marker.remove();
                }
                marker = map.addMarker(new MarkerOptions().position(latLng).title("Your Location"));
                Locations = marker.getPosition();
                //Information gets saved and recovered from previous page
                double Lat = Locations.latitude;
                double Lng = Locations.longitude;
                String Name = getIntent().getStringExtra("Name");
                String Password = getIntent().getStringExtra("Password");
                String Email = getIntent().getStringExtra("Email");
                String Region = getIntent().getStringExtra("Region");
                Region = Region.replaceAll("\\s+","");
                if(Region.equals("US&Canada"))
                {
                    Region = "us_and_canada";
                }
                Log.e("Region", Region);
                //Sets up connection for inserting user into table
                String Link = "http://128.46.69.133/~vipuser3/insertLatLnginto.php?Lat=" + Lat + "+&Lng=" + Lng + "+&Name=" + Name + "+&Password=" + Password + "+&Email=" + Email + "+&Region=" + Region;

                final String uName = Name;
                if (Lat == 0 || Lng == 0) {
                    Log.e("Missing Input", "Missing Input");
                } else {
                    //Changes code to allow Javascript to recognize certain errors and recognize that the Connection is made correctly
                    //Adapted from code found at http://developer.android.com/reference/android/os/StrictMode.ThreadPolicy.Builder.html
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                    StrictMode.setThreadPolicy(policy);
                    try {
                        //Sets up request from app
                        //Source:http://www.programcreek.com/java-api-examples/org.apache.http.client.methods.HttpGet
                        URL url = new URL(Link);
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpGet request = new HttpGet();
                        request.setURI(new URI(Link));
                        HttpResponse response = httpclient.execute(request);

                    } catch (Exception e) {
                        Log.e("log_tag", "Error in http connection " + e.toString());
                    }
                }
            }
        });
        //Button Command to move onto next page
        Button btnSimple = (Button) findViewById(R.id.Btn2);
        btnSimple.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String uName = getIntent().getStringExtra("Name");
                String Re = getIntent().getStringExtra("Region");

                Intent intent1 = new Intent(v.getContext(), PeopleInterest.class);
                intent1.putExtra("Name", uName);
                intent1.putExtra("Re",Re);
                startActivityForResult(intent1, 0);

            }


        });
    }


}