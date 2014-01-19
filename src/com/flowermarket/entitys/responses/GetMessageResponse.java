/**
 * 
 */
package com.flowermarket.entitys.responses;

import java.util.List;

import com.flowermarket.entitys.Message;
import com.flowermarket.http.base.HttpResponseEntity;

/**
 * @author Wind
 * 
 */
public class GetMessageResponse extends HttpResponseEntity {

	public List<Message> data;

}
