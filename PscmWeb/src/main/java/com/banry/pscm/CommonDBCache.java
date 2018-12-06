package com.banry.pscm;

import java.util.Hashtable;
import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;

public class CommonDBCache {
	
	public static Hashtable commonDBCache = new Hashtable();

	private static Logger log = LoggerFactory.getLogger(CommonDBCache.class);

	/**
	 * 构建公共库缓冲 查询公共库所有表信息，放到本地静态hashtable中，作为以后访问的缓冲区
	 * 
	 * @param ev
	 * @param restTemplate
	 */
	public static void  buildCommonDBCache(Environment ev, RestTemplate restTemplate) {		
		
		// clear commonDBCache first for recreating
//		commonDBCache.clear();
//		JSONArray majorSourceJson = geCommonbaseCatalogList("DEFAULT", restTemplate, ev);
//
//		String commonServiceRestRootURL = ev.getProperty("pscm.commonbase.rest.url");
//
//		String[] entityList = { "engdivision", "hazards","hiddentrouble","itemconsscheme","subdivwork","subdivworkquota" };
//
//		for (int i = 0; i < majorSourceJson.size(); i++) {
//			Hashtable majorSourceHashTable = new Hashtable();
//
//			LinkedHashMap majorSourceMap = (LinkedHashMap) majorSourceJson.get(i);
//			String majorSource = majorSourceMap.get("major_en") + "_" + majorSourceMap.get("source_en");
//			commonDBCache.put(majorSource, majorSourceHashTable);
//
//			for (int j = 0; j < entityList.length; j++) {
//				JSONArray objList = getEntityList(entityList[j], majorSource, restTemplate, ev,
//						commonServiceRestRootURL);
//				log.info("objList================"+objList.toString());
//				log.info("majorSource======================="+majorSource.toString());
//				if (objList != null)
//					majorSourceHashTable.put(entityList[j] + "List", objList);
//			}
//		}
	}

	/**
	 * 获取公共库目录数据
	 * 
	 * @param common_db
	 * @param restTemplate
	 * @param ev
	 * @return
	 */
	public static JSONArray geCommonbaseCatalogList(String common_db, RestTemplate restTemplate, Environment ev) {

		JSONArray majorSourceJson = restTemplate
				.getForEntity(ev.getProperty("pscm.commonbase.rest.url") + "dds/getCommonbaseCatalog", JSONArray.class)
				.getBody();
		return majorSourceJson;
	}

	/**
	 * 获取公共业务库中表的数据
	 * 
	 * @param entiey
	 *            RestAPI domain， 例如 engdivision或者hazards
	 * @param common_db
	 *            公共库名称(专业来源），例如 municipal_bridge_housing_dept
	 * @param restTemplate
	 * @param ev
	 * @param commonServiceRestURL
	 *            公共库RestAPI服务RootURL
	 * @return
	 */
	public static JSONArray getEntityList(String entiey, String common_db, RestTemplate restTemplate, Environment ev,
			String commonServiceRestURL) {
		
		// 首先切换到正确的公共应用库数据源
		restTemplate.getForEntity(commonServiceRestURL + "/dds/setdsname?dsname=com_" + common_db, JSONArray.class);
		
		JSONArray entityList = restTemplate.getForEntity(commonServiceRestURL + entiey + "/findall", JSONArray.class)
				.getBody();
		
		return entityList;
	}
}
