package com.algonquincollege.tonk0006.acfinder;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Pin each campus of Algonquin College on a Google map.
 *
 * @author Vladimir V. Tonkonogov (tonk0006)
 * @version 2.0
 *
 */

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, Constants {

    private GoogleMap mMap;
    private Geocoder mGeocoder;

    /** Locate and pin locationName to the map. */
    private void pin ( String locationName ) {
        try {
            Address address = mGeocoder.getFromLocationName(locationName, 1).get(0);
            LatLng ll = new LatLng( address.getLatitude(), address.getLongitude() );
            mMap.addMarker( new MarkerOptions().position(ll).title(locationName) );
            mMap.moveCamera( CameraUpdateFactory.newLatLng(ll) );
            Toast.makeText(this, "Pinned: " + locationName, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Not found: " + locationName, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // instantiate
        mGeocoder = new Geocoder( this );
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final EditText userLocation = (EditText) findViewById(R.id.userLocation);

        // register an anonymous inner class as the event handler for the userLocation
        userLocation.setOnEditorActionListener( new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_NULL && event.getAction( ) == KeyEvent.ACTION_DOWN && event.getKeyCode( ) == KeyEvent.KEYCODE_ENTER) {
                    String newLocation = userLocation.getText().toString();
                    if ( newLocation.isEmpty() ) {
                        userLocation.setError("Location field cannot be empty");
                        userLocation.requestFocus();
                        return true;
                    } else {
                        MapsActivity.this.pin(newLocation);
                        userLocation.setText("");
                    }
                    return true;
                } else {
                    return false;
                }
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //pin each of Algonquin College's campuses to the map
        pin( JAZAN );
        pin( PEMBROKE );
        pin( PERTH );
        pin( WOODROFFE );
    }
}
