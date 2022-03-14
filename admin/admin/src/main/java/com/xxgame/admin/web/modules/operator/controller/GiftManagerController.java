package com.xxgame.admin.web.modules.operator.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xxgame.admin.web.exception.BusinessException;
import com.xxgame.admin.web.model.Result;
import com.xxgame.admin.web.model.ResultCode;
import com.xxgame.admin.web.modules.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("giftManager")
@Api(tags = "礼包管理-1200")
public class GiftManagerController extends BaseController {

    /**
     * 礼包服url
     */
    @Value("${social.giftUrl}")
    private String giftUrl;

    @RequiresPermissions("createGift:create")
    @PutMapping(value = "createGift")
    @ApiOperation(value = "生成礼包码-1201")
    public void createGift(@RequestBody String body) {
        if (StringUtils.isBlank(body)) {
            throw new BusinessException(ResultCode.PARAM_EMPTY);
        }

        // 组装参数
        int cmd = 3000;
        ResponseEntity<Resource> entity = requestGiftServer(body, cmd, Resource.class);
        sendFileResponse(entity);
    }

    @RequiresPermissions("createGift:create")
    @PutMapping(value = "createGiftReward")
    @ApiOperation(value = "生成礼包-1201")
    public Result<String> createGiftReward(@RequestBody String body) {
        if (StringUtils.isBlank(body)) {
            throw new BusinessException(ResultCode.PARAM_EMPTY);
        }

        // 组装参数
        int cmd = 3002;
        ResponseEntity<String> entity = requestGiftServer(body, cmd, String.class);
        return Result.success(entity.getBody());
    }

    @RequiresPermissions("createGift:update")
    @PostMapping(value = "updateGiftReward")
    @ApiOperation(value = "修改礼包-1201")
    public Result<String> updateGiftReward(@RequestBody String body) {
        if (StringUtils.isBlank(body)) {
            throw new BusinessException(ResultCode.PARAM_EMPTY);
        }

        // 组装参数
        int cmd = 3003;
        ResponseEntity<String> entity = requestGiftServer(body, cmd, String.class);
        return Result.success(entity.getBody());
    }

    @RequiresPermissions("createGift:query")
    @GetMapping(value = "getGiftRewards")
    @ApiOperation(value = "查询所有礼包-1201")
    public Result<String> getGiftRewards() {
        // 组装参数
        int cmd = 3004;
        ResponseEntity<String> entity = requestGiftServer("{}", cmd, String.class);
        return Result.success(entity.getBody());
    }

    @RequiresPermissions("queryGift:query")
    @GetMapping(value = "queryGift")
    @ApiOperation(value = "查询可用礼包码-1202")
    public void queryGift(@RequestParam(required = false) Integer giftId) {
        // 组装参数
        int cmd = 3001;
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("cmd", cmd);
        if (giftId != null) {
            parameter.put("giftId", giftId);
        }

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(giftUrl);
        for (Map.Entry<String, Object> entry : parameter.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }
            uriBuilder.queryParam(entry.getKey(), entry.getValue());
        }
        URI uri = uriBuilder.build().encode().toUri();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_XHTML_XML);

        // 请求生成礼包
        ResponseEntity<Resource> entity = getRestTemplate().getForEntity(uri, Resource.class);
        sendFileResponse(entity);
    }

    @RequiresPermissions("queryGift:query")
    @GetMapping(value = "queryGiftCode")
    @ApiOperation(value = "查询指定礼包码-1202")
    public Result<String> queryGiftCode(@RequestParam String code) {
        // 组装参数
        int cmd = 3005;
        JSONObject json = new JSONObject();
        json.put("code", code);

        ResponseEntity<String> entity = requestGiftServer(json.toJSONString(), cmd, String.class);
        return Result.success(entity.getBody());
    }

    /**
     * 请求礼包服
     * @param body
     * @param cmd
     * @param responseType
     * @param <T>
     * @return
     */
    private <T> ResponseEntity<T> requestGiftServer(String body, int cmd, Class<T> responseType) {
        Map<String, Object> parameter = JSON.parseObject(body, new TypeReference<Map<String, Object>>(){});
        parameter.put("cmd", cmd);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(giftUrl);
        for (Map.Entry<String, Object> entry : parameter.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }
            uriBuilder.queryParam(entry.getKey(), entry.getValue());
        }
        URI uri = uriBuilder.build().encode().toUri();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_XHTML_XML);

        // 请求生成礼包码
        return getRestTemplate().getForEntity(uri, responseType);
    }

    /**
     * 发送响应文件下载
     * @param entity
     */
    private void sendFileResponse(ResponseEntity<Resource> entity) {
        try {
            String filename = new String("gift.txt".getBytes("GB2312"), "ISO8859-1");
            byte[] bytes = StreamUtils.copyToByteArray(entity.getBody().getInputStream());

            servletResponse.reset();
            servletResponse.setHeader("Content-disposition", "attachment; filename=" + filename);
            servletResponse.setContentType("text/plain");
            servletResponse.getOutputStream().write(bytes);
            servletResponse.flushBuffer();
        } catch (Exception e) {
            logger.error("响应礼包码异常", e);
        }
    }

    /**
     * 获取RestTemplate
     * @return
     */
    private RestTemplate getRestTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(3000);
        requestFactory.setReadTimeout(30 * 1000);
        return new RestTemplate(requestFactory);
    }

}
