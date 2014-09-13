package edu.uiuc.anymap;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by andrey on 9/13/14.
 */
public class MyLocationListener implements LocationListener {
    public String locString;

    @Override
    public void onLocationChanged(Location location) {
        System.out.println("Location changed!!!");
        locString = "Lat: " + location.getLatitude() + "\nLng: " + location.getLongitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}

    @Override
    public void onProviderEnabled(String s) {}

    @Override
    public void onProviderDisabled(String s) {}
}
