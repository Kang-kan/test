package com.xxgame.logsrv.module.collectlog.processor;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.EsClientHolder;
import com.xxgame.logsrv.module.collectlog.model.LogType;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 战斗力日志处理
 */
@Service
public class PowerLogProcessor extends AbstractCollectLogProcessor {

    @Autowired
    private EsClientHolder eslientHolder;

    private final String indexName = "powerlog";

    @Override
    public LogType logType() {
        return LogType.LOG_POWER;
    }

    @Override
    public void save(JSONObject jsonObject) throws IOException {
        long id = jsonObject.getLongValue("id");
        String account = jsonObject.getString("account").split("\\.")[0];
        jsonObject.put("account", account);

        IndexRequest request = new IndexRequest(indexName);
        request.id(id + "");
        request.source(jsonObject.toJSONString(), XContentType.JSON);

        eslientHolder.getClient().index(request, RequestOptions.DEFAULT);
    }
}
