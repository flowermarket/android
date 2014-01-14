package com.flowermarket.commpoments;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.flowermarket.R;
import com.flowermarket.entitys.BCustomer;

public class ContactsListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<BCustomer> data;
	private Context context;

	public ContactsListAdapter(Context context) {
		this.context = context;
		mInflater = LayoutInflater.from(context);
	}

	public void setData(List<BCustomer> data) {
		this.data = data;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (data == null)
			return 0;
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		if (data == null)
			return null;
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.contact_list_item, null);
			holder = new ViewHolder();
			holder.contactName = (TextView) convertView
					.findViewById(R.id.contactName);
			holder.contactPhone = (TextView) convertView
					.findViewById(R.id.contactPhone);
			holder.contactPinyin = (TextView) convertView
					.findViewById(R.id.contactPinyin);
			holder.mailBtn = convertView.findViewById(R.id.mailBtn);
			holder.phoneBtn = convertView.findViewById(R.id.phoneBtn);
			holder.contactItemTitle = (TextView) convertView
					.findViewById(R.id.contactItemTitle);
			holder.contactLayout = convertView.findViewById(R.id.contactLayout);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		BCustomer custom = data.get(position);
		if (custom.ctel != null) {
			holder.contactLayout.setVisibility(View.VISIBLE);
			holder.contactItemTitle.setVisibility(View.GONE);
			holder.contactName.setText(custom.cname);
			holder.contactPhone.setText(custom.ctel);
			holder.contactPinyin.setText(custom.cnamepingyin);

			holder.mailBtn.setTag(custom.ctel);
			holder.phoneBtn.setTag(custom.ctel);

			holder.phoneBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (v.getTag() != null) {
						String number = v.getTag().toString();
						Intent intent = new Intent();
						intent.setAction(Intent.ACTION_CALL);
						intent.setData(Uri.parse("tel:" + number));
						context.startActivity(intent);
					}

				}
			});

			holder.mailBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (v.getTag() != null) {
						String number = v.getTag().toString();
						Intent intent = new Intent();
						intent.setAction(Intent.ACTION_SENDTO);
						intent.setData(Uri.parse("sms:" + number));
						context.startActivity(intent);
					}
				}
			});
		} else {
			holder.contactItemTitle.setVisibility(View.VISIBLE);
			holder.contactItemTitle.setText(custom.cnamepingyin);
			holder.contactLayout.setVisibility(View.GONE);
		}
		return convertView;
	}

	class ViewHolder {
		TextView contactName;
		TextView contactPhone;
		TextView contactPinyin;
		View mailBtn;
		View phoneBtn;
		View contactLayout;
		TextView contactItemTitle;
	}

}
