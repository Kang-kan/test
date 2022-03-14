package com.xxgame.logsrv.module.collectlog.repair.service;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.logsrv.module.collectlog.entity.MailLog;
import com.xxgame.logsrv.module.collectlog.repository.MailLogRepository;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 邮件日志修复数据
 */
@Service
public class MailLongRepairService {

    @Autowired
    private MailLogRepository mailLogRepository;

    /**
     * { id : JSONObject }
     */
    private final ConcurrentMap<Integer, JSONObject> configMap = new ConcurrentHashMap<>();

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 初始化
     */
    @PostConstruct
    private void init() throws Exception {
        // 读表
        loadConfig();
        // 修复数据
        // repair();
    }

    /**
     * 读表
     * @throws DocumentException
     * @throws IOException
     */
    private void loadConfig() throws DocumentException, IOException {
        ClassPathResource resource = new ClassPathResource("mailConfig.xml");

        SAXReader reader = new SAXReader();
        Document doc = reader.read(resource.getInputStream());
        List<Node> list = doc.selectNodes("/def/unit");
        for (Node node : list) {
            Element element = (Element) node;

            String idStr = element.attribute("id").getValue();
            int id = Integer.parseInt(idStr);
            String title = element.attribute("title").getValue();
            String text = element.attribute("text").getValue();
            text = this.stripHtml(text);

            JSONObject json = new JSONObject();
            json.put("id", id);
            json.put("title", title);
            json.put("text", text);

            configMap.put(id, json);
        }
    }

    /**
     * html解码，去除htm标签
     * @param text
     * @return
     */
    private String stripHtml(String text) {
        text = StringEscapeUtils.unescapeHtml3(text);
        text = text.replaceAll("</?[^>]+>", ""); // 剔出<html>的标签
        text = text.replaceAll("<a>\\s*|\t|\r|\n</a>|\\\\n", ""); // 去除字符串中的空格,回车,换行符,

        return text.trim();
    }

    /**
     * 修复数据
     */
    private void repair() {
        logger.info("开始修复邮件数据");

        int count = 0;
        List<MailLog> list = mailLogRepository.findAll();
        for (MailLog mailLog : list) {
            if (mailLog.getConfigId() == 0 || StringUtils.isNotBlank(mailLog.getTitle())) {
                continue;
            }

            JSONObject json = configMap.get(mailLog.getConfigId());
            if (json == null) {
                continue;
            }

            count++;

            mailLog.setTitle(json.getString("title"));
            mailLog.setMessage(json.getString("text"));
            mailLogRepository.save(mailLog);
        }

        logger.info("完成修复邮件数据，共多少条:{}", count);
    }

    /**
     * 获取邮件配置
     * @param configId
     * @return
     */
    public JSONObject getConfig(int configId) {
        return configMap.get(configId);
    }
}
