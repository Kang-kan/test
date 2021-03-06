package com.xxgame.admin.web.modules.customerservice.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgame.admin.web.interceptor.ServerRight;
import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.model.Result;
import com.xxgame.admin.web.model.ResultCode;
import com.xxgame.admin.web.modules.BaseController;
import com.xxgame.admin.web.modules.auth.SystemUserRoleService;
import com.xxgame.admin.web.modules.auth.entity.SystemUserRole;
import com.xxgame.admin.web.modules.commons.GmClient;
import com.xxgame.admin.web.modules.customerservice.MessageStatus;
import com.xxgame.admin.web.modules.customerservice.controller.model.ChargeInfoDto;
import com.xxgame.admin.web.modules.customerservice.controller.model.CsMessageDto;
import com.xxgame.admin.web.modules.customerservice.controller.model.MessageRequest;
import com.xxgame.admin.web.modules.customerservice.entity.CustomerService;
import com.xxgame.admin.web.modules.customerservice.repository.CustomerServiceRepository;
import com.xxgame.admin.web.modules.customerservice.service.CustomerServiceService;
import com.xxgame.admin.web.modules.gameserver.GameServerService;
import com.xxgame.admin.web.modules.gameserver.entity.GameServer;
import com.xxgame.admin.web.modules.gameserver.entity.RoleGameServer;
import com.xxgame.admin.web.modules.gameserver.repository.RoleGameServerRepository;
import com.xxgame.admin.web.util.HttpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("cs")
@Api(tags = "??????-900")
public class CustomerServiceController extends BaseController {

    @Autowired
    private CustomerServiceRepository customerServiceRepository;
    @Autowired
    private CustomerServiceDtoConverter customerServiceDtoConverter;
    @Autowired
    private CustomerServiceService customerServiceService;
    @Autowired
    private GmClient gmClient;
    @Autowired
    private RoleGameServerRepository roleGameServerRepository;
    @Autowired
    private GameServerService gameServerService;
    @Autowired
    private SystemUserRoleService systemUserRoleService;

    @RequiresPermissions("csMessage:query")
    @GetMapping(value = "listCsMessage")
    @ApiOperation(value = "??????????????????")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "serverId", value = "?????????id????????????", type = "integer"),
            @ApiImplicitParam(name = "channelId", value = "??????id????????????", type = "string"),
            @ApiImplicitParam(name = "status", value = "??????", type = "integer")
    })
    @ServerRight(argsIndex = 0)
    public Result<PageDto<CsMessageDto>> listCsMessage(@RequestParam(required = false) Integer serverId,
                                                       @RequestParam(required = false) String channelId,
                                                       @RequestParam(required = false) Integer status,
                                                       @RequestParam int pageNo, @RequestParam int pageSize) {

        if (status == null) {
            status = MessageStatus.NONE.getValue();
        }

        Page<CustomerService> pageResult = null;
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);

        if (serverId == null) {
            pageResult = customerServiceRepository.findMessage(status, pageRequest);
        } else if (channelId == null) {
            pageResult = customerServiceRepository.findMessage(serverId, status, pageRequest);
        } else {
            pageResult = customerServiceRepository.findMessage(serverId, channelId, status, pageRequest);
        }

        List<CsMessageDto> dtos = customerServiceDtoConverter.toCsMessageDtos(pageResult.getContent());
        PageDto<CsMessageDto> pageDto = new PageDto<>();
        pageDto.setContents(dtos);
        this.setPageInfo(pageDto, pageResult);

        return Result.success(pageDto);
    }

    @RequiresPermissions("csMessage:query")
    @GetMapping(value = "findByPlayer")
    @ApiOperation(value = "????????????id????????????")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "playerId", value = "??????id", type = "string")
    })
    public Result<List<CsMessageDto>> findByPlayer(@RequestParam(required = false) String playerId) {
        if (StringUtils.isBlank(playerId)) {
            return Result.success(null);
        }

        List<CustomerService> customerServices = customerServiceRepository.findPlayerMessages(this.stringToLong(playerId));
        List<CsMessageDto> dtos = customerServiceDtoConverter.toCsMessageDtos(customerServices);
        return Result.success(dtos);
    }

    @RequiresPermissions("replyCsMessage:update")
    @PostMapping(value = "replyMessage")
    @ApiOperation(value = "????????????")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "??????id", type = "string"),
            @ApiImplicitParam(name = "replyTitle", value = "???????????????", type = "string"),
            @ApiImplicitParam(name = "replyText", value = "???????????????????????????256?????????", type = "string")
    })
    public Result<CsMessageDto> replyMessage(@RequestParam String id, @RequestParam String replyTitle, @RequestParam String replyText) {
        if (replyText.length() >= 512) {
            return Result.error(ResultCode.PARAM_ERROR, "??????????????????");
        }
        if (replyTitle.length() >= 30) {
            return Result.error(ResultCode.PARAM_ERROR, "??????????????????");
        }
        Result<CustomerService> result = customerServiceService.replyMessage(this.getUserId(), this.stringToLong(id), replyTitle, replyText);
        if (result.getCode() != ResultCode.SUCCESS) {
            return Result.error(result.getCode(), result.getMessage());
        }

        CsMessageDto dto = customerServiceDtoConverter.toCsMessageDto(result.getContent());
        return Result.success(dto);
    }

    @PostMapping(value = "leaveMessage")
    @ApiOperation(value = "??????")
    public Result<String> leaveMessage(@RequestBody MessageRequest request) {
        if (request.getServerId() == 0) {
            return Result.error(ResultCode.PARAM_ERROR, "???????????????");
        }
        if (StringUtils.isAnyBlank(request.getChannelId(), request.getPlayerName(), request.getContext())) {
            return Result.error(ResultCode.PARAM_ERROR, "???????????????");
        }
        if (request.getContext().length() > 256) {
            return Result.error(ResultCode.PARAM_ERROR, "???????????????");
        }

        customerServiceService.leaveMessage(request, HttpUtils.getRequestIp(this.servletRequest));
        return Result.success("OK");
    }

    @RequiresPermissions("chargeRank:query")
    @GetMapping(value = "chargeRank")
    @ApiOperation(value = "????????????????????????-905")
    @ServerRight(argsIndex = 0)
    public Result<List<ChargeInfoDto>> chargeRank(@RequestParam int serverId) {
        String result = gmClient.queryChargeRank(serverId);
        if (StringUtils.isBlank(result)) {
            return Result.error(ResultCode.FAIL, "?????????????????????");
        }

        List<ChargeInfoDto> dtos = JSON.parseObject(result, new TypeReference<List<ChargeInfoDto>>(){});
        return Result.success(dtos);
    }

    @RequiresPermissions("sendGmNoticeMail:create")
    @PostMapping(value = "sendGmNoticeMail")
    @ApiOperation(value = "??????????????????-906", notes = "?????????????????????????????????????????????????????????")
    public Result<List<Integer>> sendSystemMail(@RequestParam String title, @RequestParam String content) {
        if (StringUtils.isAnyBlank(title, content)) {
            return Result.error(ResultCode.PARAM_EMPTY, "??????????????????");
        }

        List<Integer> roleIds = new LinkedList<>();
        List<SystemUserRole> userRoles = systemUserRoleService.findByUserId(this.getUserId());
        userRoles.forEach(r -> roleIds.add(r.getRoleId()));

        List<RoleGameServer> roleGameServers = roleGameServerRepository.findByRoleIdIn(roleIds);
        LinkedHashMap<Integer, ListenableFuture<ResponseEntity<String>>> futures = new LinkedHashMap<>();
        for (RoleGameServer roleGameServer : roleGameServers) {
            int serverId = roleGameServer.getServerId();
            GameServer gameServer = gameServerService.getCacheGameServer(serverId);
            if (gameServer == null || !gameServer.isOpen()) {
                continue;
            }

            ListenableFuture<ResponseEntity<String>> future = gmClient.sendSystemMail(serverId, title, content);
            futures.put(serverId, future);
        }

        List<Integer> fails = new ArrayList<>();
        for (Map.Entry<Integer, ListenableFuture<ResponseEntity<String>>> entry : futures.entrySet()) {
            int serverId = entry.getKey();
            try {
                ResponseEntity<String> responseEntity = entry.getValue().get(3, TimeUnit.SECONDS);
                if (responseEntity.getStatusCode() != HttpStatus.OK) {
                    fails.add(serverId);
                    logger.info("???????????????????????????serverId:{} title:{} content:{}", serverId, title, content);
                    continue;
                }
                String result = responseEntity.getBody();
                if (result == null || !result.equals("0")) {
                    fails.add(serverId);
                    logger.info("?????????????????????????????????serverId:{} title:{} content:{} result:{}", serverId, title, content, result);
                    continue;
                }
            } catch (Exception e) {
                fails.add(serverId);
                String msg = String.format("???????????????????????????serverId:%s title:%s content:%s", serverId, title, content);
                logger.info(msg, e);
            }
        }

        return Result.success(fails);
    }
}
