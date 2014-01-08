package com.flowermarket.http;

import com.flowermarket.entitys.BCustomer;
import com.flowermarket.entitys.BLawRegulation;
import com.flowermarket.entitys.GooglePlot;
import com.flowermarket.entitys.Message;
import com.flowermarket.entitys.User;

public class HttpActions {

	/**
	 * 登录接口
	 * 
	 * @see User
	 */
	public final static String ACTION_LOGIN = "android!login";

	/**
	 * 修改密码
	 */
	public final static String ACTION_MODIFY_PASSWORD = "android!setPassWord";

	/**
	 * 获得通讯录
	 * 
	 * @see BCustomer
	 */
	public final static String ACTION_GET_BCUSTOM = "android!getBCustomer";

	/**
	 * 获得预案
	 * 
	 * @see BLawRegulation
	 */
	public final static String ACTION_GET_LAW_REGULATION = "android!getBLawRegulation";

	/**
	 * 发送消息
	 */
	public final static String ACTION_SEND_MESSAGE = "android!sendMsg";

	/**
	 * 获取消息
	 * 
	 * @see Message
	 */
	public final static String ACTION_GET_MESSAGE = "android!gainMsg";

	/**
	 * 获取历史消息
	 * 
	 * @see Message
	 */
	public final static String ACTION_GET_HTYMSG = "android!gainHtyMsg";

	/**
	 * 获取电子地图
	 * 
	 * @see GooglePlot
	 */
	public final static String ACTION_GET_GOOGLE_PLOT = "android!getGooglePlot";
}
