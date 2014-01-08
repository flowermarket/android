package com.flowermarket.http;

import com.flowermarket.entitys.BCustomer;
import com.flowermarket.entitys.BLawRegulation;
import com.flowermarket.entitys.GooglePlot;
import com.flowermarket.entitys.Message;
import com.flowermarket.entitys.User;

public class HttpActions {

	/**
	 * ��¼�ӿ�
	 * 
	 * @see User
	 */
	public final static String ACTION_LOGIN = "android!login";

	/**
	 * �޸�����
	 */
	public final static String ACTION_MODIFY_PASSWORD = "android!setPassWord";

	/**
	 * ���ͨѶ¼
	 * 
	 * @see BCustomer
	 */
	public final static String ACTION_GET_BCUSTOM = "android!getBCustomer";

	/**
	 * ���Ԥ��
	 * 
	 * @see BLawRegulation
	 */
	public final static String ACTION_GET_LAW_REGULATION = "android!getBLawRegulation";

	/**
	 * ������Ϣ
	 */
	public final static String ACTION_SEND_MESSAGE = "android!sendMsg";

	/**
	 * ��ȡ��Ϣ
	 * 
	 * @see Message
	 */
	public final static String ACTION_GET_MESSAGE = "android!gainMsg";

	/**
	 * ��ȡ��ʷ��Ϣ
	 * 
	 * @see Message
	 */
	public final static String ACTION_GET_HTYMSG = "android!gainHtyMsg";

	/**
	 * ��ȡ���ӵ�ͼ
	 * 
	 * @see GooglePlot
	 */
	public final static String ACTION_GET_GOOGLE_PLOT = "android!getGooglePlot";
}
