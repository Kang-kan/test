package com.xxgame.admin.web.util;

import java.util.Random;

/**
 * 较短的id生成器。特点，频率较低，长度比通用id要短。
 * 
 * @see CommonIdWorker
 */
public class ShortIdWorker {

	private final long serverId;

	/**
	 * 开始时间（0.1秒） 2017-01-01 00:00:00
	 */
	private final long twepoch = 14832000000L;

	private final long serverIdBits = 6L;
	private final long maxServerId = -1L ^ (-1L << serverIdBits);
	
	/**
	 * 每0.1秒最多可生成2047个id
	 */
	private final long sequenceBits = 11L;

	private final long workerIdShift = sequenceBits;
	private final long timestampLeftShift = sequenceBits + serverIdBits;
	private final long sequenceMask = -1L ^ (-1L << sequenceBits);

	private long lastTimestamp = -1L;
	private long sequence = 0L;

	private final Random random = new Random();
	
	/**
	 * 构造方法
	 * @param serverId 服务器id
	 */
	public ShortIdWorker(long serverId) {
		// sanity check for workerId
		if (serverId > maxServerId || serverId < 0) {
			throw new IllegalArgumentException("serverId 不能少于0 或大于" + maxServerId);
		}
		this.serverId = serverId;
	}

	/**
	 * id
	 * @return
	 */
	public synchronized long nextId() {
		long timestamp = timeGen();

		if (timestamp < lastTimestamp) {
			throw new RuntimeException("系统时间不能倒退.");
		}

		if (lastTimestamp == timestamp) {
			sequence = (sequence + 1) & sequenceMask;
			if (sequence == 0) {
				timestamp = tilNextMillis(lastTimestamp);
			}
		} else {
			sequence = random.nextInt(10);
		}

		lastTimestamp = timestamp;

		return ((timestamp - twepoch) << timestampLeftShift) | (serverId << workerIdShift) | sequence;
	}

	protected long tilNextMillis(long lastTimestamp) {
		long timestamp = timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = timeGen();
		}
		return timestamp;
	}

	protected long timeGen() {
		return System.currentTimeMillis() / 100;
	}
	
}
