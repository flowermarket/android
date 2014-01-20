package com.flowermarket.commpoments;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.flowermarket.FlowerMarketApplication;
import com.flowermarket.R;
import com.flowermarket.entitys.Message;
import com.flowermarket.utils.Tools;

public class MessageAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<Message> data;

	public MessageAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
	}

	public void setData(List<Message> data) {
		this.data = data;
		notifyDataSetChanged();
	}

	public void appendData(Message msg) {
		if (this.data != null) {
			this.data.add(msg);
			notifyDataSetChanged();
		}
	}

	public void appendData(List<Message> data) {
		if (this.data != null) {
			this.data.addAll(data);
			notifyDataSetChanged();
		}
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.msg_item, null);
			holder = new ViewHolder();
			holder.himBar = convertView.findViewById(R.id.himBar);
			holder.himHead = (ImageView) convertView.findViewById(R.id.himHead);
			holder.himMsg = (TextView) convertView.findViewById(R.id.himMsg);
			holder.himTime = (TextView) convertView.findViewById(R.id.himTime);
			holder.meBar = convertView.findViewById(R.id.meBar);
			holder.meHead = (ImageView) convertView.findViewById(R.id.meHead);
			holder.meMsg = (TextView) convertView.findViewById(R.id.meMsg);
			holder.meTime = (TextView) convertView.findViewById(R.id.meTime);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Message msg = data.get(position);
		if (msg.uid != null) {
			if (msg.uid.equals(FlowerMarketApplication.user.uid)) {
				// me
				holder.himBar.setVisibility(View.GONE);
				holder.meBar.setVisibility(View.VISIBLE);
				holder.meMsg.setText(msg.message);
				holder.meTime.setText(Tools.getDateTimeString(new Date(Long
						.parseLong(msg.time))));
			} else {
				holder.himBar.setVisibility(View.VISIBLE);
				holder.meBar.setVisibility(View.GONE);
				holder.himMsg.setText(msg.message);
				holder.himTime.setText(Tools.getDateTimeString(new Date(Long
						.parseLong(msg.time))));
			}
		}

		return convertView;
	}

	class ViewHolder {
		View himBar;
		ImageView himHead;
		TextView himTime;
		TextView himMsg;
		View meBar;
		ImageView meHead;
		TextView meTime;
		TextView meMsg;
	}

}
