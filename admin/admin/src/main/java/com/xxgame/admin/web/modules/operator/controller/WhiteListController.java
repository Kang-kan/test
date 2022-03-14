package com.xxgame.admin.web.modules.operator.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xxgame.admin.web.model.Result;
import com.xxgame.admin.web.model.ResultCode;
import com.xxgame.admin.web.modules.BaseController;
import com.xxgame.admin.web.modules.commons.service.LoginSrvClient;
import com.xxgame.admin.web.modules.operator.controller.model.WhiteAccountDto;
import com.xxgame.admin.web.modules.operator.controller.model.WhiteBlackIpDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("whitelist")
@Api(tags = "白名单-1200")
public class WhiteListController extends BaseController {

    @Autowired
    private LoginSrvClient loginSrvClient;

    @RequiresPermissions("whitelist:query")
    @GetMapping(value = "getLoginGateStatus")
    @ApiOperation(value = "查看登录服入口开关-1210")
    public Result<Boolean> getLoginGateStatus() {
        boolean result = loginSrvClient.getLoginServerGate();
        return Result.success(result);
    }

    @RequiresPermissions("whitelist:update")
    @PostMapping(value = "setLoginGateStatus")
    @ApiOperation(value = "设置登录服入口开关-1210")
    public Result<Boolean> setLoginGateStatus(@RequestParam boolean open) {
        boolean result = loginSrvClient.setLoginServerGate(open);
        return Result.success(result);
    }

    @RequiresPermissions("whitelist:create")
    @PutMapping(value = "addWhiteAccount")
    @ApiOperation(value = "增加白名单用户-1210")
    public Result<Void> addWhiteAccount(@RequestParam String account, @RequestParam String remark) {
        if (StringUtils.isAnyBlank(account, remark)) {
            return Result.error(ResultCode.PARAM_EMPTY, "参数不能为空");
        }
        loginSrvClient.addWhiteAccount(account, remark);

        return Result.success(null);
    }

    @RequiresPermissions("whitelist:delete")
    @DeleteMapping(value = "removeWhiteAccount")
    @ApiOperation(value = "删除白名单用户-1210")
    public Result<Void> removeWhiteAccount(@RequestParam String account) {
        if (StringUtils.isAnyBlank(account)) {
            return Result.error(ResultCode.PARAM_EMPTY, "参数不能为空");
        }
        loginSrvClient.removeWhiteAccount(account);

        return Result.success(null);
    }

    @RequiresPermissions("whitelist:query")
    @GetMapping(value = "getWhiteAccounts")
    @ApiOperation(value = "获取所有白名单用户-1210")
    public Result<List<WhiteAccountDto>> getWhiteAccounts() {
        JSONObject json = loginSrvClient.getWhiteAccounts();
        String content = json.getString("content");
        List<WhiteAccountDto> result = JSONObject.parseObject(content, new TypeReference<List<WhiteAccountDto>>(){});

        return Result.success(result);
    }

    @RequiresPermissions("whitelist:create")
    @PutMapping(value = "addWhiteIp")
    @ApiOperation(value = "增加白名单ip-1210")
    public Result<Void> addWhiteIp(@RequestParam String ip, @RequestParam String remark) {
        if (StringUtils.isAnyBlank(ip, remark)) {
            return Result.error(ResultCode.PARAM_EMPTY, "参数不能为空");
        }
        loginSrvClient.addWhiteIp(ip, remark);

        return Result.success(null);
    }

    @RequiresPermissions("whitelist:delete")
    @DeleteMapping(value = "removeWhiteIp")
    @ApiOperation(value = "删除白名单ip-1210")
    public Result<Void> removeWhiteIp(@RequestParam String ip) {
        if (StringUtils.isAnyBlank(ip)) {
            return Result.error(ResultCode.PARAM_EMPTY, "参数不能为空");
        }
        loginSrvClient.removeWhiteIp(ip);

        return Result.success(null);
    }

    @RequiresPermissions("whitelist:query")
    @GetMapping(value = "getWhiteIps")
    @ApiOperation(value = "获取所有白名单ip-1210")
    public Result<List<WhiteBlackIpDto>> getWhiteIps() {
        JSONObject json = loginSrvClient.getWhiteIps();
        String content = json.getString("content");
        List<WhiteBlackIpDto> result = JSONObject.parseObject(content, new TypeReference<List<WhiteBlackIpDto>>(){});

        return Result.success(result);
    }

    @RequiresPermissions("whitelist:create")
    @PutMapping(value = "addBlackIp")
    @ApiOperation(value = "增加黑名单ip-1210")
    public Result<Void> addBlackIp(@RequestParam String ip, @RequestParam String remark) {
        if (StringUtils.isAnyBlank(ip, remark)) {
            return Result.error(ResultCode.PARAM_EMPTY, "参数不能为空");
        }
        loginSrvClient.addBlackIp(ip, remark);

        return Result.success(null);
    }

    @RequiresPermissions("whitelist:delete")
    @DeleteMapping(value = "removeBlackIp")
    @ApiOperation(value = "删除黑名单ip-1210")
    public Result<Void> removeBlackIp(@RequestParam String ip) {
        if (StringUtils.isAnyBlank(ip)) {
            return Result.error(ResultCode.PARAM_EMPTY, "参数不能为空");
        }
        loginSrvClient.removeBlackIp(ip);

        return Result.success(null);
    }

    @RequiresPermissions("whitelist:query")
    @GetMapping(value = "getBlackIps")
    @ApiOperation(value = "获取所有黑名单ip-1210")
    public Result<List<WhiteBlackIpDto>> getBlackIps() {
        JSONObject json = loginSrvClient.getBlackIps();
        String content = json.getString("content");
        List<WhiteBlackIpDto> result = JSONObject.parseObject(content, new TypeReference<List<WhiteBlackIpDto>>(){});

        return Result.success(result);
    }

}
