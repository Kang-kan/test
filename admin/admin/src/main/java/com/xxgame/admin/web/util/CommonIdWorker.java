package com.xxgame.admin.web.util;

import java.util.Random;

/**
 * 标准twitter snowflake id生成器
 * 
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000
 * 第一位为未使用，接下来的41位为毫秒级时间(41位的长度可以使用69年)，然后是5位datacenterId和5位workerId(10位的长度最多支持部署1024个节点） ，最后12位是毫秒内的计数（12位的计数顺序号支持每个节点每毫秒产生4096个ID序号）
 * 一共加起来刚好64位，为一个Long型。(转换成字符串长度为18)
 * 
 * @author zhujuan From: https://github.com/twitter/snowflake An object that
 *         generates IDs. This is broken into a separate class in case we ever
 *         want to support multiple worker threads per process
 */
public class CommonIdWorker {

	private final long workerId;
	private final long datacenterId;

	/**
	 * 开始时间 2017-01-01 00:00:00
	 */
	private final long twepoch = 1483200000000L;

	private final long workerIdBits = 5L;
	private final long datacenterIdBits = 5L;
	private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
	private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
	private final long sequenceBits = 12L;

	private final long workerIdShift = sequenceBits;
	private final long datacenterIdShift = sequenceBits + workerIdBits;
	private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
	private final long sequenceMask = -1L ^ (-1L << sequenceBits);

	private long lastTimestamp = -1L;
	private long sequence = 0L;

	private final Random random = new Random();
	
	/**
	 * 构造方法
	 * @param workerId 服务器id
	 * @param datacenterId 数据中心id
	 */
	public CommonIdWorker(long workerId, long datacenterId) {
		// sanity check for workerId
		if (workerId > maxWorkerId || workerId < 0) {
			throw new IllegalArgumentException("workerId 不能少于0 或大于" + maxWorkerId);
		}
		if (datacenterId > maxDatacenterId || datacenterId < 0) {
			throw new IllegalArgumentException("datacenter 不能少于0 或大于" + maxDatacenterId);
		}
		this.workerId = workerId;
		this.datacenterId = datacenterId;
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

		return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift)
				| (workerId << workerIdShift) | sequence;
	}

	protected long tilNextMillis(long lastTimestamp) {
		long timestamp = timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = timeGen();
		}
		return timestamp;
	}

	protected long timeGen() {
		return System.currentTimeMillis();
	}
	
}
