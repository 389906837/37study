package com.cloud.cang.weixin.api;

import com.cloud.cang.weixin.bean.Ticket;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;

import com.cloud.cang.weixin.client.LocalHttpClient;

/**
 * JSAPI ticket
 * @author zhouhong
 *
 */
public class TicketAPI extends BaseAPI{

	/**
	 * 获取 jsapi_ticket
	 * @param access_token access_token
	 * @return ticket
	 */
	public static Ticket ticketGetticket(String access_token){
		return ticketGetticket(access_token,"jsapi");
	}

	/**
	 * 获取 ticket
	 * @param access_token access_token
	 * @param type jsapi or wx_card
	 * @return ticket
	 */
	public static Ticket ticketGetticket(String access_token,String type){
		HttpUriRequest httpUriRequest = RequestBuilder.get()
				.setUri(BASE_URI + "/cgi-bin/ticket/getticket")
				.addParameter(getATPN(),access_token)
				.addParameter("type", type)
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,Ticket.class);
	}
}
