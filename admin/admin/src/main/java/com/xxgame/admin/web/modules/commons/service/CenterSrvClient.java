package com.xxgame.admin.web.modules.commons.service;

import com.alibaba.fastjson.JSONObject;
import com.xxgame.admin.web.exception.BusinessException;
import com.xxgame.admin.web.model.ResultCode;
import com.xxgame.admin.web.util.Md5DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.TreeMap;

/**
 * 中心服客户端
 */
@Component
public class CenterSrvClient {

    /**
     * 中心服
     */
    @Value("${social.centerSrv}")
    private String centerSrv;

    /**
     * centerKey
     */
    @Value("${social.centerKey}")
    private String centerKey;

    /**
     * 请求中心服
     * @param cmd
     * @param parameter
     * @return
     */
    public JSONObject doRequest(int cmd, Map<String, Object> parameter) {
        Map<String, Object> parameters = new TreeMap<>();
        parameters.put("cmd", cmd);
        if (parameter != null) {
            parameters.putAll(parameter);
        }
        String sign = this.sign(parameters);
        parameters.put("sign", sign);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(centerSrv);
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }
            uriBuilder.queryParam(entry.getKey(), entry.getValue());
        }
        URI uri = uriBuilder.build().encode().toUri();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_XHTML_XML);

        // 发送请求
        String respStr = getRestTemplate().getForObject(uri, String.class);
        if (respStr == null) {
            throw new BusinessException(ResultCode.FAIL, "请求跨服配置超时");
        }
        JSONObject jsonObject = JSONObject.parseObject(respStr);
        String errMsg = jsonObject.getString("errMsg");
        if (errMsg != null) {
            throw new BusinessException(ResultCode.FAIL, errMsg);
        }

        return jsonObject;
    }

    /**
     * 签名验证
     * @param parameters
     * @return
     */
    private String sign(Map<String, Object> parameters) {
        StringBuilder contentStr = new StringBuilder();
        for (Object param : parameters.values()) {
            contentStr.append(param.toString());
        }
        contentStr.append(centerKey);

        return Md5DigestUtils.digest(contentStr.toString());
    }

    /**
     * 获取RestTemplate
     * @return
     */
    private RestTemplate getRestTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(3000);
        requestFactory.setReadTimeout(3000);
        return new RestTemplate(requestFactory);
    }

}
