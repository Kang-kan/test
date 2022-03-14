package com.xxgame.admin.web.util;

/**
 * 分区分服主键id帮助类
 *
 *
 */
public abstract class PlayerPartitionIdHelper {

	// 头段(这个是没有意义的主要是确保id整齐所有long数值型id都 10... 14个零开头) (考虑到id的可读性不采用位运算)
	public static final long HEAD_FRAGMENT = (long) Math.pow(10, 15);
	// 运营商段10的15次方 这样规划支持 1-99个运营商
	public static final long OPERATOR_FRAGMENT = (long) Math.pow(10, 0);
	// 运营商段 10的11次方 这样规划支持 1-9999台服 剩余部分为自增id 1-10亿
	public static final long SERVER_FRAGMENT = (long) Math.pow(10, 2);
	// 自增长部分端
	public static final long AUTO_INCREMENT_FRAGMENT = (long) Math.pow(10, 7);

	/**
	 * 根据运营商、服id、字增长部分构建最终的主键值
	 * 
	 * @param operator
	 *            运营商标识
	 * @param server
	 *            服id
	 * @param autoincrement
	 *            自增长部分
	 * @return 主键值
	 */
	public static long buildPrimaryKey(int operator, int server, long autoincrement) {
		return HEAD_FRAGMENT + OPERATOR_FRAGMENT * operator + server * SERVER_FRAGMENT
				+ autoincrement * AUTO_INCREMENT_FRAGMENT;
	}

	/**
	 * 从指定的主键值中解析出运营商id出来
	 * 
	 * @param id
	 * @return
	 */
	public static int parseOperator(long id) {
		return (int) ((id - HEAD_FRAGMENT) % SERVER_FRAGMENT);// 头 + 自增长 + 服标识 + 运营商
	}

	/**
	 * 从指定的主键值中解析出服id出来
	 * 
	 * @param id
	 * @return
	 */
	public static int parseServer(long id) {
		return (int) (((id - HEAD_FRAGMENT) % AUTO_INCREMENT_FRAGMENT) / SERVER_FRAGMENT);// 头 + 自增长 + 服标识 + 运营商
	}

	/**
	 * 从指定的主键值中解析出子增长部分出来
	 * 
	 * @param id
	 * @return
	 */
	public static long parseAutoincrement(long id) {
		return (id - HEAD_FRAGMENT) / AUTO_INCREMENT_FRAGMENT;// 头 + 自增长 + 服标识 + 运营商
	}

	/**
	 * 从指定的主键值中生成唯一Int值
	 * 
	 * @param id
	 * @return
	 */
	public static int getUniqueId(long id) {
		return parseOperator(id) * 10000000 + parseServer(id) * 10000 + (int) parseAutoincrement(id);
	}
}
