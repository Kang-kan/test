package com.xxgame.logsrv.module;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.sniff.Sniffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

/**
 * elastic client
 * @author gil
 *
 */
@Component
public class EsClientHolder {

    @Value("${xxgame.es.hostUrls}")
    private String hostUrls;

    private RestHighLevelClient client = null;
    private Sniffer sniffer = null;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 初始化
     */
    @PostConstruct
    private void init() {
        String[] hostsArray = hostUrls.split(",");
        if (hostsArray == null || hostsArray.length == 0 ) {
            logger.info("请设置elasticsearch主机地址 xxgame.es.hostUrls");
            return;
        }
        HttpHost[] httpHosts = new HttpHost[hostsArray.length];
        for (int i = 0; i < hostsArray.length; i++) {
            String hostUrl = hostsArray[i];
            httpHosts[i] = HttpHost.create(hostUrl);
        }

        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(RestClient.builder(httpHosts));
        this.sniffer = Sniffer.builder(restHighLevelClient.getLowLevelClient()).build();
        this.client = restHighLevelClient;
    }

    /**
     * Actions to perform on shutdown.
     */
    @PreDestroy
    public void onShutdown() throws IOException {
        if (sniffer != null) {
            sniffer.close();
        }
        if (client != null) {
            client.close();
        }
    }

    /**
     * 获取client
     * @return
     */
    public RestHighLevelClient getClient() {
        if (this.client == null) {
            this.init();
        }

        return client;
    }

}