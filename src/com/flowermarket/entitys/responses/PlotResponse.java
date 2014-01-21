package com.flowermarket.entitys.responses;

import java.util.List;

import com.flowermarket.entitys.GooglePlot;
import com.flowermarket.http.base.HttpResponseEntity;

@SuppressWarnings("serial")
public class PlotResponse extends HttpResponseEntity {

	public List<GooglePlot> data;

}
