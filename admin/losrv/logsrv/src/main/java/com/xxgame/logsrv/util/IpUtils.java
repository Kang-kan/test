package com.xxgame.logsrv.util;

import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

/**
 * ip工具类
 * @author gil
 *
 */
public class IpUtils {

	/**
	 * 将ip地址转换成正则表达式
	 * @param ip
	 * @return
	 */
	public static Pattern ipPattern(String ip) {
		if (StringUtils.isEmpty(ip)) {
			return null;
		}
		
		String reg = ip.trim().replace(".", "[.]").replace("*", "[0-9]*");
		Pattern pattern = Pattern.compile(reg);
		
		return pattern;
	}
	
	/**
	 * 将多个ip地址转换成正则
	 * @param ipStr 以,号分隔
	 * @return
	 */
	public static Pattern[] ipPatterns(String ipStr) {
		if (StringUtils.isEmpty(ipStr)) {
			return new Pattern[]{};
		}
		
		String[] ips = ipStr.split(",");
		Pattern[] patterns = new Pattern[ips.length];
		for (int i = 0; i < ips.length; i++) {
			patterns[i] = ipPattern(ips[i]);
		}
		
		return patterns;
	}
	
	/**
	 * 是否是允许的ip
	 * @param patterns
	 * @param ip
	 * @return
	 */
	public static boolean isAllowIp(Pattern[] patterns, String ip) {
		if (patterns == null || patterns.length == 0) {
			return false;
		}
		if (StringUtils.isEmpty(ip)) {
			return false;
		}
		
		for (Pattern pattern : patterns) {
			if (pattern == null) {
				continue;
			}
			if (pattern.matcher(ip).matches()) {
				return true;
			}
		}
		
		return false;
	}
	
}
