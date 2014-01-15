package com.flowermarket.ui;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.flowermarket.FlowerMarketApplication;
import com.flowermarket.R;
import com.flowermarket.base.BaseActivity;
import com.flowermarket.entitys.BLawRegulation;
import com.flowermarket.entitys.responses.BLawRegulationResponse;
import com.flowermarket.http.HttpRequestSession;
import com.flowermarket.http.HttpRequestSession.OnRequestCallback;
import com.flowermarket.http.base.HttpRequestEntity;
import com.flowermarket.http.base.HttpResponseEntity;
import com.flowermarket.utils.UserBarTools;

public class PlanListActivity extends BaseActivity implements
		OnItemClickListener {

	private ListView list;
	private ArrayAdapter<String> adapter;
	private List<BLawRegulation> plans;

	private ProgressDialog progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plan_list_activity);
		UserBarTools.setUserBarValue(findViewById(R.id.userBar));
		list = (ListView) findViewById(R.id.list);
		list.setOnItemClickListener(this);
		getData();
	}

	private void showProgress() {
		if (progress == null) {
			progress = new ProgressDialog(this);
			progress.setMessage("正在加载中...");
		}
		progress.show();
	}

	private void getData() {
		showProgress();
		HttpRequestEntity request = new HttpRequestEntity(
				"android!getBLawRegulation", BLawRegulationResponse.class);
		request.addParam("uid", FlowerMarketApplication.user.uid);
		request.addParam("uuid", FlowerMarketApplication.uuid);
		HttpRequestSession.getInstance().requestSession(request,
				new OnRequestCallback() {

					@Override
					public void onSuccess(HttpResponseEntity resp) {
						progress.dismiss();
						plans = ((BLawRegulationResponse) resp).data;
						String[] result = ((BLawRegulationResponse) resp)
								.toStrings();
						if (result != null) {
							adapter = new ArrayAdapter<String>(
									getApplicationContext(),
									R.layout.plan_list_item, result);
							list.setAdapter(adapter);
						}
					}

					@Override
					public void onFailue(int statusCode,
							HttpResponseEntity entity) {
						progress.dismiss();
						if (entity != null && entity.msg != null) {
							Toast.makeText(getApplicationContext(), entity.msg,
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(getApplicationContext(), "网络异常",
									Toast.LENGTH_SHORT).show();
						}
					}
				});
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		if (plans != null) {
			Intent intent = new Intent(this, PlanDetailActivity.class);
			intent.putExtra("plan", plans.get(position));
			startActivity(intent);
		}
	}
}
