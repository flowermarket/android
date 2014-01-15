package com.flowermarket.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.flowermarket.R;
import com.flowermarket.base.BaseActivity;
import com.flowermarket.entitys.BLawRegulation;
import com.flowermarket.utils.UserBarTools;

public class PlanDetailActivity extends BaseActivity {

	private TextView a1;
	private TextView a2;
	private TextView a3;
	private TextView a4;
	private TextView content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plan_detail_activity);
		UserBarTools.setUserBarValue(findViewById(R.id.userBar));

		a1 = (TextView) findViewById(R.id.a1);
		a2 = (TextView) findViewById(R.id.a2);
		a3 = (TextView) findViewById(R.id.a3);
		a4 = (TextView) findViewById(R.id.a4);
		content = (TextView) findViewById(R.id.content);

		BLawRegulation plan = (BLawRegulation) getIntent()
				.getSerializableExtra("plan");
		a1.setText(plan.a1);
		a2.setText(plan.a2);
		a3.setText(plan.a3);
		a4.setText(plan.a4);
		content.setText(plan.a10);
	}

}
