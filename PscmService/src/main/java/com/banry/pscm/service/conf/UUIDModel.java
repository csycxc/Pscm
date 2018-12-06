/**
 * 
 * @auther chenshiyu
 */
package com.banry.pscm.service.conf;

import java.util.UUID;

/**
 * @author chenshiyu
 *
 */
public class UUIDModel {
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("\\-", "");
	}
}
