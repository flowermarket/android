package com.flowermarket.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.flowermarket.FlowerMarketApplication;
import com.flowermarket.R;
import com.flowermarket.base.BaseActivity;
import com.flowermarket.commpoments.ContactsListAdapter;
import com.flowermarket.entitys.BCustomer;
import com.flowermarket.entitys.responses.BCustomerResponse;
import com.flowermarket.http.HttpRequestSession;
import com.flowermarket.http.HttpRequestSession.OnRequestCallback;
import com.flowermarket.http.base.HttpRequestEntity;
import com.flowermarket.http.base.HttpResponseEntity;
import com.flowermarket.utils.Tools;
import com.flowermarket.utils.UserBarTools;

public class ContactListActivity extends BaseActivity implements
		OnClickListener {

	private ListView list;
	private ContactsListAdapter adapter;
	private EditText searchKey;
	private View searchBtn;

	private ProgressDialog progress;
	private ArrayList<Character> dividers = new ArrayList<Character>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_activity);

		UserBarTools.setUserBarValue(findViewById(R.id.userBar));

		list = (ListView) findViewById(R.id.list);
		adapter = new ContactsListAdapter(this);
		list.setAdapter(adapter);

		searchKey = (EditText) findViewById(R.id.searchKey);
		searchBtn = findViewById(R.id.searchBtn);
		searchBtn.setOnClickListener(this);

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
				"android!getBCutomer", BCustomerResponse.class);
		request.addParam("uid", FlowerMarketApplication.user.uid);
		request.addParam("uuid", FlowerMarketApplication.uuid);
		String q = searchKey.getText().toString().trim();
		if (q.length() > 0) {
			request.addParam("query", q);
		}
		HttpRequestSession.getInstance().requestSession(request,
				new OnRequestCallback() {

					@Override
					public void onSuccess(HttpResponseEntity resp) {
						progress.dismiss();
						dividers.clear();
						BCustomerResponse br = (BCustomerResponse) resp;
						for (int i = 0; i < br.data.size(); i++) {
							BCustomer contact = br.data.get(i);
							contact.cnamepingyin = Tools
									.getStringPinYin(contact.cname);
						}

						Comparator<BCustomer> comparator = new Comparator<BCustomer>() {

							@Override
							public int compare(BCustomer lhs, BCustomer rhs) {
								return lhs.cnamepingyin
										.compareTo(rhs.cnamepingyin);
							}
						};

						for (int i = 0; i < br.data.size(); i++) {
							BCustomer contact = br.data.get(i);
							if (!dividers.contains(contact.cnamepingyin
									.charAt(0))) {
								BCustomer di = new BCustomer();
								di.cnamepingyin = String
										.valueOf(contact.cnamepingyin.charAt(0));
								dividers.add(contact.cnamepingyin.charAt(0));
								br.data.add(i, di);
							}
						}

						Collections.sort(br.data, comparator);
						adapter.setData(br.data);
					}

					@Override
					public void onFailue(int statusCode,
							HttpResponseEntity entity) {
						progress.dismiss();
						if (entity != null && entity.msg != null) {
							Toast.makeText(getApplicationContext(), entity.msg,
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(getApplicationContext(), "获取通讯录失败",
									Toast.LENGTH_SHORT).show();
						}
					}
				});
	}

	@Override
	public void onClick(View v) {
		if (v == searchBtn) {
			if (searchKey.getText().toString().trim().length() == 0) {
				Toast.makeText(this, "请输入关键字", Toast.LENGTH_SHORT).show();
			} else {
				getData();
			}
		}
	}

}
