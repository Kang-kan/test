package com.xxgame.admin.web.modules.commons.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xxgame.admin.web.exception.BusinessException;
import com.xxgame.admin.web.model.ResultCode;
import com.xxgame.admin.web.modules.gameserver.GameServerService;
import com.xxgame.admin.web.modules.gameserver.entity.GameServer;
import com.xxgame.admin.web.util.LoginSrvUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录服客户端
 */
@Component
public class LoginSrvClient {

    /**
     * login服
     */
    @Value("${social.loginSrv}")
    private String loginSrv;

    @Autowired
    private GameServerService gameServerService;

    /**
     * 获取公告
     * @param index
     * @return
     */
    public String queryNotice(int index) {
        String parameter = String.format("?cmd=2000&index=%s", index);
        JSONObject jsonObject = this.sendRequest(parameter);
        return jsonObject.getString("notice");
    }

    /**
     * 设置公告
     * @param index
     * @param notice
     * @return
     */
    public void setNotice(int index, String notice) {
        String parameter = String.format("?cmd=2001&index=%s&notice=%s", index, notice);
        this.sendRequest(parameter);
    }

    /**
     * 设置登录服关闭提示
     * @param serverIds
     * @param text
     * @return
     */
    public void sendLoginServerStopDesc(List<Integer> serverIds, String text) {
        List<Integer> srvIds = new ArrayList<>(serverIds.size());
        for (int serverId : serverIds) {
            GameServer gameServer = gameServerService.getCacheGameServer(serverId);
            int opServerId = gameServer.getOperatorId() * 10000000 + serverId;
            srvIds.add(opServerId);
        }

        String parameter = String.format("?cmd=2002&serverIds=%s&text=%s", JSON.toJSONString(srvIds), text);
        this.sendRequest(parameter);
    }

    /**
     * 查看登录服开关
     * @return
     */
    public boolean getLoginServerGate() {
        String parameter = String.format("?cmd=2003");
        JSONObject jsonObject = this.sendRequest(parameter);
        return jsonObject.getBoolean("open");
    }

    /**
     * 设置登录服开关
     * @return
     */
    public boolean setLoginServerGate(boolean open) {
        String parameter = String.format("?cmd=2004&open=%s", open);
        JSONObject jsonObject = this.sendRequest(parameter);
        return jsonObject.getBoolean("open");
    }

    /**
     * 增加白名单用户
     * @param account
     * @param remark
     */
    public void addWhiteAccount(String account, String remark) {
        String parameter = String.format("?cmd=2005&account=%s&remark=%s", account, remark);
        this.sendRequest(parameter);
    }

    /**
     * 删除白名单用户
     * @param account
     */
    public void removeWhiteAccount(String account) {
        String parameter = String.format("?cmd=2006&account=%s", account);
        this.sendRequest(parameter);
    }

    /**
     * 获取所有白名单用户
     */
    public JSONObject getWhiteAccounts() {
        String parameter = String.format("?cmd=2007");
        return this.sendRequest(parameter);
    }

    /**
     * 增加ip白名单
     * @param ip
     * @param remark
     */
    public void addWhiteIp(String ip, String remark) {
        String parameter = String.format("?cmd=2008&ip=%s&remark=%s", ip, remark);
        this.sendRequest(parameter);
    }

    /**
     * 删除ip白名单
     * @param ip
     */
    public void removeWhiteIp(String ip) {
        String parameter = String.format("?cmd=2009&ip=%s", ip);
        this.sendRequest(parameter);
    }

    /**
     * 获取所有白名单ip
     */
    public JSONObject getWhiteIps() {
        String parameter = String.format("?cmd=2010");
        return this.sendRequest(parameter);
    }

    /**
     * 增加ip黑名单
     * @param ip
     * @param remark
     */
    public void addBlackIp(String ip, String remark) {
        String parameter = String.format("?cmd=2011&ip=%s&remark=%s", ip, remark);
        this.sendRequest(parameter);
    }

    /**
     * 删除ip黑名单
     * @param ip
     */
    public void removeBlackIp(String ip) {
        String parameter = String.format("?cmd=2012&ip=%s", ip);
        this.sendRequest(parameter);
    }

    /**
     * 获取所有黑名单ip
     */
    public JSONObject getBlackIps() {
        String parameter = String.format("?cmd=2013");
        return this.sendRequest(parameter);
    }

    /**
     * 发送请求
     * @param parameter
     * @return
     */
    private JSONObject sendRequest(String parameter) {
        String body = LoginSrvUtil.encrypt(parameter);

        HttpHeaders header = new HttpHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>(body, header);
        RestTemplate loginSrvClient = this.loginSrvClient();

        ResponseEntity<String> responseEntity = loginSrvClient.postForEntity(loginSrv, httpEntity, String.class);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new BusinessException(ResultCode.FAIL, "请求登录服超时");
        }

        String plaintext = LoginSrvUtil.decrypt(responseEntity.getBody());
        JSONObject jsonObject = JSONObject.parseObject(plaintext);

        if (jsonObject.getIntValue("code") != 0) {
            throw new BusinessException(ResultCode.FAIL, "请求登录服返回失败");
        }

        return jsonObject;
    }

    /**
     * 获取RestTemplate
     * @return
     */
    private RestTemplate loginSrvClient() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(3000);
        requestFactory.setReadTimeout(3000);
        return new RestTemplate(requestFactory);
    }

}
