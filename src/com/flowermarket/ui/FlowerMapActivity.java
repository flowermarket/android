package com.flowermarket.ui;

import java.util.List;

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
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.flowermarket.FlowerMarketApplication;
import com.flowermarket.R;
import com.flowermarket.entitys.GooglePlot;
import com.flowermarket.entitys.responses.PlotResponse;
import com.flowermarket.http.HttpRequestSession;
import com.flowermarket.http.HttpRequestSession.OnRequestCallback;
import com.flowermarket.http.base.HttpRequestEntity;
import com.flowermarket.http.base.HttpResponseEntity;
import com.flowermarket.utils.AMapUtil;
import com.flowermarket.utils.Tools;

public class FlowerMapActivity extends FragmentActivity implements
		LocationSource, AMapLocationListener {

	private AMap aMap;
	private LocationManagerProxy mAMapLocationManager;
	private AlertDialog.Builder gpsDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flower_map_activity);

		init();

		getMapData();
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

	private void getMapData() {
		HttpRequestEntity request = new HttpRequestEntity(
				"android!getGooglePlot", PlotResponse.class);
		request.addParam("uid", FlowerMarketApplication.user.uid);
		request.addParam("uuid", FlowerMarketApplication.uuid);
		HttpRequestSession.getInstance().requestSession(request,
				new OnRequestCallback() {

					@Override
					public void onSuccess(HttpResponseEntity resp) {
						List<GooglePlot> data = ((PlotResponse) resp).data;
						if (data != null && data.size() > 0) {
							GooglePlot plot = data.get(0);
							focusPoint(plot.plot_lat, plot.plot_lng);
							for (int i = 0; i < data.size(); i++) {
								drawMarker(data.get(i));
							}
						}
					}

					@Override
					public void onFailue(int statusCode,
							HttpResponseEntity entity) {
						if (entity != null && entity.msg != null) {
							Toast.makeText(getApplicationContext(), entity.msg,
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(getApplicationContext(), "请求数据失败",
									Toast.LENGTH_SHORT).show();
						}
					}
				});
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
		if (mAMapLocationManager == null) {
			mAMapLocationManager = LocationManagerProxy.getInstance(this);
		}
		// Location API定位采用GPS和网络混合定位方式，时间最短是5000毫秒
		mAMapLocationManager.requestLocationUpdates(
				LocationProviderProxy.AMapNetwork, 5000, 10, this);
	}

	@Override
	public void deactivate() {
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
		// focusCurrent(location.getLatitude(), location.getLongitude());
	}

	public void focusPoint(double lat, double lng) {
		LatLng point = new LatLng(lat, lng);
		aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15));
	}

	public void drawMarker(GooglePlot plot) {
		LatLng ll = new LatLng(plot.plot_lat, plot.plot_lng);
		MarkerOptions markerPosition = new MarkerOptions()
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.icon_map_marker))
				.draggable(true).position(ll).title(plot.ADname)
				.snippet(plot.ADname);
		aMap.addMarker(markerPosition);
	}

}
