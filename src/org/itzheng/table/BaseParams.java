package org.itzheng.table;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.itzheng.utils.StrUtils;

/**
 * 请求的参数字段,比如传入fID,fPage
 * 
 * @author WL001
 *
 */
public class BaseParams {
	/*
	 * ================= ||||头部参数Header|||| ================
	 */
	/**
	 * 当前用户id
	 */
	public static final String fUserID = "fUserID";
	/**
	 * 设备类型：1 安卓，2 iOS
	 */
	public static final String fDeviceType = "fDeviceType";

	/*
	 * ================= ||||请求参数Json|||| ================
	 */
	/**
	 * 主键，获取用户详情时，主键为用户id，获取活动详情时，主键为活动id，以此类推
	 */
	public static final String fID = "fID";
	/**
	 * 分页请求当前页，从1开始算
	 */
	public static final String fPage = "fPage";
	/**
	 * 分页请求的大小，比如10条
	 */
	public static final String fSize = "fSize";
	/**
	 * 类型，不同请求，type对应不同的含义
	 */
	public static final String fType = "fType";
	/**
	 * 精度
	 */
	public static final String fLongitude = "fLongitude";
	/**
	 * 纬度
	 */
	public static final String fLatitude = "fLatitude";

	/**
	 * 获取头部的用户id
	 * 
	 * @param request
	 * @return
	 */
	public static String getFUserID(HttpServletRequest request) {
		return request.getHeader(BaseParams.fUserID);
	}

	/**
	 * 获取设备类型
	 * 
	 * @param request
	 * @return
	 */
	public static String getFDeviceType(HttpServletRequest request) {
		return request.getHeader(BaseParams.fDeviceType);
	}

	/**
	 * 获取page
	 * 
	 * @param fieldsValues
	 * @return
	 */
	public static int getFPage(Map<String, Object> fieldsValues) {
		int page = StrUtils.toInt(fieldsValues.get(BaseParams.fPage));
		if (page <= 0) {
			page = 1;
		}
		return page;
	}

	/**
	 * 获取size
	 * 
	 * @param fieldsValues
	 * @return
	 */
	public static int getFSize(Map<String, Object> fieldsValues) {
		int size = StrUtils.toInt(fieldsValues.get(BaseParams.fSize));
		if (size <= 0) {
			size = 10;
		}
		return size;
	}

	public static int getFType(Map<String, Object> fieldsValues) {
		return StrUtils.toInt(fieldsValues.get(BaseParams.fType));
	}

	/**
	 * 获取经度
	 * 
	 * @param fieldsValues
	 * @return
	 */
	public static double getFLongitude(Map<String, Object> fieldsValues) {
		return StrUtils.toDouble(fieldsValues.get(BaseParams.fLongitude));
	}

	/**
	 * 获取纬度
	 * 
	 * @param fieldsValues
	 * @return
	 */
	public static double getFLatitude(Map<String, Object> fieldsValues) {
		return StrUtils.toDouble(fieldsValues.get(BaseParams.fLatitude));
	}

	public static String getFID(Map<String, Object> fieldsValues) {
		return StrUtils.toString(fieldsValues.get(BaseParams.fID));

	}

}
