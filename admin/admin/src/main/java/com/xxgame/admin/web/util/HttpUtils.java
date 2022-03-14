package com.xxgame.admin.web.util;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.impl.io.DefaultHttpResponseParserFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * http工具类
 * @author gil
 *
 */
public class HttpUtils {

	/**
	 * 连接池管理
	 */
	private static PoolingHttpClientConnectionManager manager = null;
	
	/**
	 * httpClient
	 */
	private static CloseableHttpClient httpClient = null;
	
	/**
	 * 锁对象
	 */
	private static final Object lockObj = new Object();
	
	/**
	 * 获取httpClient
	 * @return
	 */
	public static CloseableHttpClient getHttpClient() {
		if (httpClient != null) {
			return httpClient;
		}
		
		synchronized (lockObj) {
			if (httpClient != null) {
				return httpClient;
			}
			
			// 注册访问协议相关的socket工厂
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("http", PlainConnectionSocketFactory.INSTANCE)
					.register("https", SSLConnectionSocketFactory.getSystemSocketFactory())
					.build();
			// HttpConnection工厂：配置写请求/解析响应处理器
			HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory = 
					new ManagedHttpClientConnectionFactory(DefaultHttpRequestWriterFactory.INSTANCE, DefaultHttpResponseParserFactory.INSTANCE);
			// DNS解析器
			DnsResolver dnsResolver = SystemDefaultDnsResolver.INSTANCE;
			manager = new PoolingHttpClientConnectionManager(socketFactoryRegistry, connFactory, dnsResolver);
			
			// 默认为socket配置
			SocketConfig defaultSocketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
			manager.setDefaultSocketConfig(defaultSocketConfig);
			
			// 设置整个连接池的最大连接数
			manager.setMaxTotal(300);
			// 每个路由的默认最大连接数，每个路由实际最大连接数默认为DeafultMaxPerRoute控制，而MaxTotal是控制整个池的最大数，设置过小无法支持大并发，路由是对maxTotal的细分
			manager.setDefaultMaxPerRoute(200);
			// 在从连接池获取连接时，连接不活跃多长时间后需要进行一次验证，默认2s
			manager.setValidateAfterInactivity(5000);
			
			// 默认请求配置
			RequestConfig defaultRequestConfig = RequestConfig.custom()
					.setConnectTimeout(2000) // 连接超时时间
					.setSocketTimeout(5000) // 等待响应超时时间
					.setConnectionRequestTimeout(2000) // 从连接池获取连接的等待超时时间
					.build();
			
			httpClient = HttpClients.custom()
					.setConnectionManager(manager)
					.setConnectionManagerShared(false) // 连接池不是共享模式
					.evictIdleConnections(60, TimeUnit.SECONDS) // 回收空闲连接
					.evictExpiredConnections() // 回收过期连接
					.setConnectionTimeToLive(60, TimeUnit.SECONDS) // 连接存活时间，如果不设置则根据长连接信息决定
					.setDefaultRequestConfig(defaultRequestConfig)
					.setConnectionReuseStrategy(DefaultConnectionReuseStrategy.INSTANCE) // 连接重用策略，即是否能keepAlive
					.setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE) // 长连接多少时间
					.setRetryHandler(new DefaultHttpRequestRetryHandler(0, false)) // 重试次数，默认是3次
					.build();
					
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					try {
						httpClient.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
		
		return httpClient;
	}
	
	/**
	 * 获取请求ip
	 * @param request
	 * @return
	 */
	public static String getRequestIp(HttpServletRequest request) {
		String requestIp = request.getHeader("x-forwarded-for");
		if (requestIp == null || requestIp.length() == 0 || "unknown".equalsIgnoreCase(requestIp)) {
			requestIp = request.getHeader("Proxy-Client-IP");
		}
		if (requestIp == null || requestIp.length() == 0 || "unknown".equalsIgnoreCase(requestIp)) {
			requestIp = request.getHeader("WL-Proxy-Client-IP");
		}
        if (requestIp == null || requestIp.length() == 0 || "unknown".equalsIgnoreCase(requestIp)) {
        	requestIp = request.getHeader("HTTP_CLIENT_IP");
        }
        if (requestIp == null || requestIp.length() == 0 || "unknown".equalsIgnoreCase(requestIp)) {
        	requestIp = request.getHeader("HTTP_X_FORWARDED_FOR"); 
        }
		if (requestIp == null || requestIp.length() == 0 || "unknown".equalsIgnoreCase(requestIp)) {
			requestIp = request.getRemoteAddr();
		}
		if (requestIp == null || requestIp.equals("")) {
			return requestIp;
		}
		
		String[] ips = null;
		if (requestIp.contains(",")) {
			ips = requestIp.split(",");
		} else if (requestIp.contains(" ")) {
			ips = requestIp.split(" ");
		}
		if (ips != null) {
			for (String ip : ips) {
				if (ip == null || ip.equals("")) {
					requestIp = ip;
					break;
				}
			}
		}
		
		return requestIp.trim();
	}
	
}
