package com.manish.googlemap;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * @author manish
 * 
 */
public class MainActivity extends FragmentActivity implements
		OnMapClickListener, OnMapLongClickListener, OnMarkerClickListener {

	private GoogleMap googleMap;
	private ArrayList<LatLng> arrayPoints = null;
	PolylineOptions polylineOptions;
	private boolean checkClick = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		arrayPoints = new ArrayList<LatLng>();
		SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		googleMap = fm.getMap();
		// display zoom map
		googleMap.setMyLocationEnabled(true);

		googleMap.setOnMapClickListener(this);
		googleMap.setOnMapLongClickListener(this);
		googleMap.setOnMarkerClickListener(this);

	}

	@Override
	public void onMapClick(LatLng point) {
		if (checkClick == false) {

			googleMap.addMarker(new MarkerOptions().position(point).icon(
					BitmapDescriptorFactory.fromResource(R.drawable.point)));
			arrayPoints.add(point);
		}
	}

	@Override
	public void onMapLongClick(LatLng point) {
		googleMap.clear();
		arrayPoints.clear();
		checkClick = false;
	}

	public void countPolygonPoints() {
		if (arrayPoints.size() >= 3) {
			checkClick = true;
			PolygonOptions polygonOptions = new PolygonOptions();
			polygonOptions.addAll(arrayPoints);
			polygonOptions.strokeColor(Color.BLUE);
			polygonOptions.strokeWidth(7);
			polygonOptions.fillColor(Color.CYAN);
			Polygon polygon = googleMap.addPolygon(polygonOptions);
		}
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		// TODO Auto-generated method stub
		System.out.println("Marker lat long=" + marker.getPosition());
		System.out.println("First postion check" + arrayPoints.get(0));
		System.out
				.println("**********All arrayPoints***********" + arrayPoints);
		if (arrayPoints.get(0).equals(marker.getPosition())) {
			System.out.println("********First Point choose************");
			countPolygonPoints();
		}
		return false;
	}
}