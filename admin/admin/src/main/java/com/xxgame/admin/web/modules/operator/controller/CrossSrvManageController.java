package com.xxgame.admin.web.modules.operator.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Joiner;
import com.xxgame.admin.web.model.Result;
import com.xxgame.admin.web.model.ResultCode;
import com.xxgame.admin.web.modules.BaseController;
import com.xxgame.admin.web.modules.commons.service.CenterSrvClient;
import com.xxgame.admin.web.modules.gameserver.GameServerService;
import com.xxgame.admin.web.modules.gameserver.entity.GameServer;
import com.xxgame.admin.web.modules.operator.controller.model.*;
import com.xxgame.admin.web.modules.operator.model.CenterCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("crossSrvManager")
@Api(tags = "跨服管理-1200")
public class CrossSrvManageController extends BaseController {

    @Autowired
    private CenterSrvClient centerSrvClient;
    @Autowired
    private GameServerService gameServerService;

    @RequiresPermissions("crossSrvManager:query")
    @GetMapping(value = "listGameSrvInfos")
    @ApiOperation(value = "游戏服配置列表-1211")
    public Result<List<GameNodeInfoDto>> listGameSrvInfos() {
        JSONObject respJson = centerSrvClient.doRequest(CenterCommand.LIST_GAME_SRV_INFOS, null);
        JSONArray jsonArray = respJson.getJSONArray("content");
        List<GameNodeInfoDto> dtos = JSON.parseObject(jsonArray.toJSONString(), new TypeReference<List<GameNodeInfoDto>>(){});
        if (dtos != null) {
            for (GameNodeInfoDto dto : dtos) {
                dto.setServerId(this.parseServerId(dto.getServerId()));
            }
        }

        return Result.success(dtos);
    }

    @RequiresPermissions("crossSrvManager:query")
    @GetMapping(value = "nodeInfo")
    @ApiOperation(value = "节点信息-1211")
    public Result<NodeInfoDto> nodeInfo(@RequestParam int nodeId) {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("nodeId", nodeId);

        JSONObject respJson = centerSrvClient.doRequest(CenterCommand.NODE_INFO, parameter);
        JSONObject jsonObject = respJson.getJSONObject("content");
        NodeInfoDto dto = JSON.parseObject(jsonObject.toJSONString(), new TypeReference<NodeInfoDto>(){});
        if (dto != null && dto.getServerIds() != null) {
            List<Integer> serverIds = new ArrayList<>();
            dto.getServerIds().forEach(id -> {
                int srv = this.parseServerId(id);
                serverIds.add(srv);
            });
            dto.setServerIds(serverIds);
        }

        return Result.success(dto);
    }

    @RequiresPermissions("crossSrvManager:update")
    @PostMapping(value = "updateNode")
    @ApiOperation(value = "修改节点配置-1211")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "servers", value = "多个服务器用,分隔", type = "string"),
    })
    public Result<Void> updateNode(@RequestParam int node, @RequestParam String servers) {
        if (node <= 0 || servers == null) {
            return Result.error(ResultCode.PARAM_EMPTY, "参数不能为空");
        }


        List<Integer> idList = new ArrayList<>();
        String[] idStrs = servers.split(",");
        for (String idStr : idStrs) {
            if (StringUtils.isBlank(idStr)) {
                continue;
            }
            GameServer gameServer = gameServerService.getCacheGameServer(Integer.parseInt(idStr));
            if (gameServer == null) {
                return Result.error(ResultCode.PARAM_EMPTY, "服务器id不正确。");
            }
            int opServerId = this.buildOpServerId(gameServer.getOperatorId(), gameServer.getId());
            idList.add(opServerId);
        }

        Map<String, Object> parameter = new HashMap<>();
        parameter.put("node", node);
        parameter.put("servers", Joiner.on(",").join(idList));

        centerSrvClient.doRequest(CenterCommand.UPDATE_NODE, parameter);

        return Result.success(null);
    }

    @RequiresPermissions("crossSrvManager:update")
    @PostMapping(value = "updateWorldServer")
    @ApiOperation(value = "修改世界服配置-1211")
    public Result<Void> updateWorldServer(@RequestBody WorldSrvRequest body) {
        if (body == null) {
            return Result.error(ResultCode.PARAM_EMPTY, "参数不能为空");
        }

        Map<String, Object> parameter = new HashMap<>();
        parameter.put("gameServer", JSONObject.toJSONString(body));

        centerSrvClient.doRequest(CenterCommand.UPDATE_WORLD_SRV, parameter);

        return Result.success(null);
    }

    @RequiresPermissions("crossSrvManager:delete")
    @DeleteMapping(value = "deleteNode")
    @ApiOperation(value = "删除节点-1211")
    public Result<Void> deleteNode(int node) {
        if (node <= 0) {
            return Result.error(ResultCode.PARAM_EMPTY, "参数不能为空");
        }

        Map<String, Object> parameter = new HashMap<>();
        parameter.put("node", node);

        centerSrvClient.doRequest(CenterCommand.DEL_NODE, parameter);

        return Result.success(null);
    }

    @RequiresPermissions("crossSrvManager:delete")
    @DeleteMapping(value = "deleteWorldSrv")
    @ApiOperation(value = "删除世界服-1211")
    public Result<Void> deleteWorldSrv(int worldId) {
        if (worldId <= 0) {
            return Result.error(ResultCode.PARAM_EMPTY, "参数不能为空");
        }

        Map<String, Object> parameter = new HashMap<>();
        parameter.put("worldId", worldId);

        centerSrvClient.doRequest(CenterCommand.DEL_WORLD_SRV, parameter);

        return Result.success(null);
    }

    @RequiresPermissions("crossSrvManager:query")
    @GetMapping(value = "worldSrvInfo")
    @ApiOperation(value = "世界服信息-1211")
    public Result<CrossWorldInfoDto> worldSrvInfo() {
        JSONObject respJson = centerSrvClient.doRequest(CenterCommand.WORLD_SRV_INFO, null);
        JSONObject jsonObject = respJson.getJSONObject("content");

        CrossWorldInfoDto dto = JSON.parseObject(jsonObject.toJSONString(), new TypeReference<CrossWorldInfoDto>(){});
        return Result.success(dto);
    }

    @RequiresPermissions("crossSrvManager:query")
    @GetMapping(value = "listNodeInfos")
    @ApiOperation(value = "节点配置列表-1211")
    public Result<List<NodeInfoDto>> listNodeInfos() {
        JSONObject respJson = centerSrvClient.doRequest(CenterCommand.LIST_NODE_INFOS, null);
        JSONArray jsonArray = respJson.getJSONArray("content");
        List<NodeInfoDto> dtos = JSON.parseObject(jsonArray.toJSONString(), new TypeReference<List<NodeInfoDto>>(){});
        if (dtos != null) {
            for (NodeInfoDto dto : dtos) {
                List<Integer> serverIds = new ArrayList<>();
                for (int serverId : dto.getServerIds()) {
                    serverIds.add(this.parseServerId(serverId));
                }
                dto.setServerIds(serverIds);
            }
        }

        return Result.success(dtos);
    }

    /**
     * 解析平台+服务器id组合后的serverId
     * @param opServerId
     * @return
     */
    private int parseServerId(int opServerId) {
        int operator = opServerId / 10000;
        return opServerId - operator * 10000;
    }

    /**
     * 生成平台+服务器id组合后的serverId
     * @param operatorId
     * @param serverId
     * @return
     */
    private int buildOpServerId(int operatorId, int serverId) {
        return operatorId * 10000 + serverId;
    }

}
