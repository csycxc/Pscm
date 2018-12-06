/**
 * 
 */
package com.banry.pscm.workflow.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

/**
 * @author Xu Dingkui
 * @date 2018年1月15日
 */
public class MessagePush extends Thread {

	private static final Logger log = LoggerFactory.getLogger(MessagePush.class);
	private static final String appKey = "0fa1750aedf60c042effe4dc";
	private static final String masterSecret = "124eff9771e6472f0d04125c";
	private JPushClient jpushClient;
	private String title;
	private String message;
	private Map<String, String> extras;
	private Set<String> aliasSet;

	public MessagePush(String message) {
		this.message = message;
		jpushClient = new JPushClient(masterSecret, appKey);
	}

	public MessagePush(String message, String title, Map<String, String> extras) {
		this(message);
		this.title = title;
		this.extras = extras;
	}
	
	public MessagePush(String message, String title, Map<String, String> extras, Set<String> aliasSet) {
		this(message);
		this.title = title;
		this.extras = extras;
		this.aliasSet = aliasSet;
	}
	
	public void run() {
		long msgId = sendPushAlias(aliasSet);
	}
	/**
	 * 向所有人发送消息
	 * 
	 * @return 消息id
	 */
	public long sendPushAll() {
		PushPayload payload = buildPushObject_all_all_alert();
		long msgId = 0;
		try {
			PushResult result = jpushClient.sendPush(payload);
			msgId = result.msg_id;
		} catch (APIConnectionException e) {
			// TODO Auto-generated catch block
			log.error("Connection error. Should retry later. ", e);
		} catch (APIRequestException e) {
			log.info("HTTP Status: " + e.getStatus());
			msgId = e.getMsgId();
		}
		return msgId;
	}

	/**
	 * 向指定别名的客户端发送消息
	 * 
	 * @param alias
	 *            所有别名信息集合，这里表示发送所有用户编码
	 * @return 消息id
	 */
	public long sendPushAlias(Set<String> alias) {
		PushPayload payloadAlias = buildPushObject_android_alias_alertWithTitle(alias);
		long msgId = 0;
		try {
			PushResult result = jpushClient.sendPush(payloadAlias);
			msgId = result.msg_id;

		} catch (APIConnectionException e) {
			log.error("Connection error. Should retry later. ", e);
		} catch (APIRequestException e) {
			log.info("HTTP Status: " + e.getStatus());
			log.info("Error Code: " + e.getErrorCode());
			log.info("Error Message: " + e.getErrorMessage());
			log.info("Msg ID: " + e.getMsgId());
			msgId = e.getMsgId();
		}
		return msgId;
	}

	/**
	 * 向指定组发送消息
	 * 
	 * @param tag
	 *            组名称
	 * @return 消息id
	 */
	public long sendPushTag(String tag) {
		PushPayload payloadtag = buildPushObject_android_tag_alertWithTitle(tag);
		long msgId = 0;
		try {
			PushResult result = jpushClient.sendPush(payloadtag);
			msgId = result.msg_id;
			log.info("Got result - " + result);
		} catch (APIConnectionException e) {
			log.error("Connection error. Should retry later. ", e);

		} catch (APIRequestException e) {
			log.info("HTTP Status: " + e.getStatus());
			log.info("Error Code: " + e.getErrorCode());
			log.info("Error Message: " + e.getErrorMessage());
			log.info("Msg ID: " + e.getMsgId());
			msgId = e.getMsgId();
		}
		return msgId;
	}

	/**
	 * 下列封装了三种获得消息推送对象（PushPayload）的方法
	 * buildPushObject_android_alias_alertWithTitle、
	 * buildPushObject_android_tag_alertWithTitle、 buildPushObject_all_all_alert
	 */
	public PushPayload buildPushObject_android_alias_alertWithTitle(Set<String> alias) {
		return PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.alias(alias))
				.setNotification(Notification.android(message, title, extras)).build();
	}

	public PushPayload buildPushObject_android_tag_alertWithTitle(String tag) {
		return PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.tag(tag))
				.setNotification(Notification.android(message, title, extras)).build();
	}

	public  PushPayload buildPushObject_all_all_alert() {
        return PushPayload.alertAll(message);
    }
	
	public static void main(String[] args) {
		Map<String, String> extras = new HashMap<String, String>();
		extras.put("data", "{\"taskId\":\"123456\"}");
		MessagePush push= new MessagePush("内容","标题", extras);
		Set<String> aliasSet=new HashSet<String>();
        aliasSet.add("admin");
        long msgId=push.sendPushAlias(aliasSet);
        if (msgId == 0) {
        	System.out.println("11");
        } else {
        	System.out.println("222");
        }
	}
}