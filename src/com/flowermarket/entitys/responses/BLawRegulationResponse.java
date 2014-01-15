package com.flowermarket.entitys.responses;

import java.util.List;

import com.flowermarket.entitys.BLawRegulation;
import com.flowermarket.http.base.HttpResponseEntity;

public class BLawRegulationResponse extends HttpResponseEntity {

	public List<BLawRegulation> data;

	public String[] toStrings() {
		if (data != null) {
			String[] result = new String[data.size()];
			for (int i = 0; i < data.size(); i++) {
				result[i] = data.get(i).a2;
			}
			return result;
		}
		return null;
	}

}
