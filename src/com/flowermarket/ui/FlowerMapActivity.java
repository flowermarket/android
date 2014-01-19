package com.flowermarket.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.flowermarket.R;
import com.flowermarket.utils.AMapUtil;
import com.flowermarket.utils.Tools;

public class FlowerMapActivity extends FragmentActivity implements
		LocationSource, AMapLocationListener {

	private AMap aMap;
	private LocationManagerProxy mAMapLocationManager;
	private OnLocationChangedListener mListener;
	private AlertDialog.Builder gpsDialog;
	private Marker currentMarker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flower_map_activity);

		init();

		if (!Tools.isOPenGPS(this)) {
			showGPSDialog();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		// app.locationTools.setOnLocationCallback(this);
		setUpMapIfNeeded();
	}

	private void setUpMapIfNeeded() {
		if (aMap == null) {
			MyLocationStyle style = new MyLocationStyle();
			style.radiusFillColor(0x00000000);
			style.strokeWidth(0);
			aMap.setMyLocationStyle(style);
			if (aMap != null) {
				setUpMap();
			}
		}
	}

	private void showGPSDialog() {
		if (gpsDialog == null) {
			gpsDialog = new AlertDialog.Builder(this);
			gpsDialog.setMessage("还没打开GPS，不能继续使用，马上打开？");
			// gpsDialog.setCancelable(false);
			gpsDialog.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent callGPSSettingIntent = new Intent(
									android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
							startActivity(callGPSSettingIntent);
						}
					});
			gpsDialog.setNegativeButton("取消", null);
		}
		gpsDialog.show();
	}

	@Override
	protected void onPause() {
		super.onPause();
		deactivate();
	}

	/**
	 * 初始化AMap对象
	 */
	private void init() {
		if (aMap == null) {
			aMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			if (AMapUtil.checkReady(this, aMap)) {
				setUpMap();
			}
		}
	}

	private void setUpMap() {
		mAMapLocationManager = LocationManagerProxy.getInstance(this);
		aMap.setLocationSource(this);
		aMap.setMyLocationEnabled(true);

	}

	@Override
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
		if (mAMapLocationManager == null) {
			mAMapLocationManager = LocationManagerProxy.getInstance(this);
		}
		// Location API定位采用GPS和网络混合定位方式，时间最短是5000毫秒
		mAMapLocationManager.requestLocationUpdates(
				LocationProviderProxy.AMapNetwork, 5000, 10, this);
	}

	@Override
	public void deactivate() {
		mListener = null;
	}

	@Override
	public void onLocationChanged(Location location) {
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChanged(AMapLocation location) {
		focusCurrent(location.getLatitude(), location.getLongitude());
	}

	public void focusCurrent(double lat, double lng) {
		LatLng point = new LatLng(lat, lng);
		aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15));
	}

}
