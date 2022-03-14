package com.xxgame.admin.web.modules.customerservice.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xxgame.admin.web.interceptor.ServerRight;
import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.model.Result;
import com.xxgame.admin.web.model.ResultCode;
import com.xxgame.admin.web.modules.BaseController;
import com.xxgame.admin.web.modules.commons.GmClient;
import com.xxgame.admin.web.modules.commons.logdao.ChargeOrderLogDao;
import com.xxgame.admin.web.modules.customerservice.controller.model.*;
import com.xxgame.admin.web.modules.commons.service.QueryGameService;
import com.xxgame.admin.web.modules.customerservice.entity.BlockChat;
import com.xxgame.admin.web.modules.customerservice.entity.BlockPlayer;
import com.xxgame.admin.web.modules.customerservice.service.PlayerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("playerManager")
@Api(tags = "游戏角色管理-1100")
public class PlayerManagerController extends BaseController {

    @Autowired
    private GmClient gmClient;
    @Autowired
    private QueryGameService queryGameService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private ChargeOrderLogDao chargeOrderLogDao;
    @Autowired
    private PlayerDtoConverter playerDtoConverter;

    @RequiresPermissions("playerManager:query")
    @GetMapping(value = "playerList")
    @ApiOperation(value = "-1101")
    @ServerRight(argsIndex = 0)
    public Result<PageDto<PlayerInfoDto>> playerList(@RequestParam int serverId, @RequestParam int pageNo, @RequestParam int pageSize) {
        int[] queryArgs = toQueryArgs(pageNo, pageSize);
        String result = gmClient.listPlayers(serverId, queryArgs[0], queryArgs[1]);

        if (StringUtils.isBlank(result)) {
            return Result.success(PageDto.empty(pageNo, pageSize));
        }

        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONArray contents = jsonObject.getJSONArray("contents");
        int totalCount = jsonObject.getIntValue("totalCount");
        int totalPage = (int) Math.ceil(totalCount * 1.0F / pageSize);

        List<PlayerInfoDto> dtos = JSON.parseObject(contents.toJSONString(), new TypeReference<List<PlayerInfoDto>>(){});

        PageDto pageDto = new PageDto();
        pageDto.setContents(dtos);
        pageDto.setPageSize(pageSize);
        pageDto.setPageNo(pageNo);
        pageDto.setTotalPage(totalPage);
        pageDto.setTotalCount(totalCount);

        return Result.success(pageDto);
    }

    @RequiresPermissions("queryPlayerInfos:query")
    @GetMapping(value = "queryPlayerInfos")
    @ApiOperation(value = "查询玩家基本信息-1102", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
    @ServerRight(argsIndex = 0)
    public Result<List<PlayerInfoDto>> queryPlayerInfos(@RequestParam(required = true, name = "serverId") int serverId,
                                                        @RequestParam(required = false, name = "playerId") String playerId,
                                                        @RequestParam(required = false, name = "accountId") String accountId,
                                                        @RequestParam(required = false, name = "playerName") String playerName) {

        JSONArray jsonArray = queryGameService.queryPlayerInfos(serverId, playerId, accountId, playerName);
        if (jsonArray == null) {
            return Result.success(new ArrayList<>(0));
        }

        List<PlayerInfoDto> dtos = JSON.parseObject(jsonArray.toJSONString(), new TypeReference<List<PlayerInfoDto>>(){});
        if (dtos != null) {
            for (PlayerInfoDto dto : dtos) {
                int totalCharge = chargeOrderLogDao.playerTotalCharge(dto.getId());
                dto.setTotalCharge(totalCharge);
            }
        }

        return Result.success(dtos);
    }

    @RequiresPermissions("blockPlayer:query")
    @GetMapping(value = "queryBlockPlayers")
    @ApiOperation(value = "查询封号列表-1103")
    public Result<PageDto<BlockPlayerDto>> queryBlockPlayers(@RequestParam int pageNo, @RequestParam int pageSize) {
        Page<BlockPlayer> pageResult = playerService.findBlockPlayers(pageNo, pageSize);
        List<BlockPlayerDto> dtos = playerDtoConverter.toBlockPlayerDtos(pageResult.getContent());

        PageDto<BlockPlayerDto> pageDto = new PageDto<>();
        pageDto.setContents(dtos);
        this.setPageInfo(pageDto, pageResult);

        return Result.success(pageDto);
    }

    @RequiresPermissions("blockPlayer:query")
    @GetMapping(value = "queryBlockPlayersBySrv")
    @ApiOperation(value = "查询封号列表-1103")
    public Result<PageDto<BlockPlayerDto>> queryBlockPlayersBySrv(@RequestParam int serverId, @RequestParam int pageNo, @RequestParam int pageSize) {
        Page<BlockPlayer> pageResult = playerService.findBlockPlayers(serverId, pageNo, pageSize);
        List<BlockPlayerDto> dtos = playerDtoConverter.toBlockPlayerDtos(pageResult.getContent());

        PageDto<BlockPlayerDto> pageDto = new PageDto<>();
        pageDto.setContents(dtos);
        this.setPageInfo(pageDto, pageResult);

        return Result.success(pageDto);
    }

    @RequiresPermissions("blockPlayer:query")
    @GetMapping(value = "queryBlockPlayer")
    @ApiOperation(value = "查询玩家封号信息-1103", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
    public Result<BlockPlayerDto> queryBlockPlayer(@RequestParam(required = true, name = "serverId") int serverId,
                                                   @RequestParam(required = false, name = "playerId") String playerId,
                                                   @RequestParam(required = false, name = "accountId") String accountId,
                                                   @RequestParam(required = false, name = "playerName") String playerName) {
        long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
        if (playerIdLong == 0) {
            return Result.success(null);
        }

        BlockPlayer blockPlayer = playerService.findBlockPlayer(playerIdLong);
        if (blockPlayer == null) {
            return Result.success(null);
        }

        BlockPlayerDto dto = playerDtoConverter.toBlockPlayerDto(blockPlayer);
        return Result.success(dto);
    }

    @RequiresPermissions("blockPlayer:update")
    @PutMapping(value = "blockPlayer")
    @ApiOperation(value = "封号-1103", notes = "unBlockTime时间戳单位毫秒，-1表示永久。")
    @ServerRight(argsIndex = 0)
    public Result<Boolean> blockPlayer(@RequestParam(name = "serverId") int serverId,
                                       @RequestParam(name = "playerId") String playerId,
                                       @RequestParam(name = "unBlockTime") long unBlockTime,
                                       @RequestParam(required = false, name = "reason") String reason) {

        if (serverId < 0 || StringUtils.isBlank(playerId)) {
            return Result.error(ResultCode.PARAM_ERROR, "参数不正确。");
        }
        return playerService.blockPlayer(serverId, this.stringToLong(playerId), unBlockTime, reason);
    }

    @RequiresPermissions("blockPlayer:delete")
    @DeleteMapping(value = "unBlockPlayer")
    @ApiOperation(value = "解禁封号-1103")
    public Result<Boolean> unBlockPlayer(@RequestParam(name = "playerId") String playerId,
                                         @RequestParam(name = "serverId") int serverId) {
        if (StringUtils.isBlank(playerId)) {
            return Result.error(ResultCode.PARAM_ERROR, "参数不正确。");
        }
        return playerService.unBlockPlayer(this.stringToLong(playerId),serverId );
    }

    @RequiresPermissions("blockChat:query")
    @GetMapping(value = "queryBlockChats")
    @ApiOperation(value = "查询禁言列表-1104")
    public Result<PageDto<BlockChatDto>> queryBlockChats(@RequestParam int pageNo, @RequestParam int pageSize) {
        Page<BlockChat> pageResult = playerService.findBlockChats(pageNo, pageSize);
        List<BlockChatDto> dtos = playerDtoConverter.toBlockChatDtos(pageResult.getContent());

        PageDto<BlockChatDto> pageDto = new PageDto<>();
        pageDto.setContents(dtos);
        this.setPageInfo(pageDto, pageResult);

        return Result.success(pageDto);
    }

    @RequiresPermissions("blockChat:query")
    @GetMapping(value = "queryBlockChatsBySrv")
    @ApiOperation(value = "查询禁言列表-1104")
    public Result<PageDto<BlockChatDto>> queryBlockChatsBySrv(@RequestParam int serverId, @RequestParam int pageNo, @RequestParam int pageSize) {
        Page<BlockChat> pageResult = playerService.findBlockChats(serverId, pageNo, pageSize);
        List<BlockChatDto> dtos = playerDtoConverter.toBlockChatDtos(pageResult.getContent());

        PageDto<BlockChatDto> pageDto = new PageDto<>();
        pageDto.setContents(dtos);
        this.setPageInfo(pageDto, pageResult);

        return Result.success(pageDto);
    }

    @RequiresPermissions("blockChat:query")
    @GetMapping(value = "queryBlockChat")
    @ApiOperation(value = "查询禁言列表-1104", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
    public Result<BlockChatDto> queryBlockChat(@RequestParam(required = true, name = "serverId") int serverId,
                                               @RequestParam(required = false, name = "playerId") String playerId,
                                               @RequestParam(required = false, name = "accountId") String accountId,
                                               @RequestParam(required = false, name = "playerName") String playerName) {
        long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
        if (playerIdLong == 0) {
            return Result.success(null);
        }

        BlockChat blockChat = playerService.findBlockChat(playerIdLong);
        if (blockChat == null) {
            return Result.success(null);
        }

        BlockChatDto dto = playerDtoConverter.toBlockChatDto(blockChat);
        return Result.success(dto);
    }

    @RequiresPermissions("blockChat:update")
    @PutMapping(value = "blockChat")
    @ApiOperation(value = "禁言-1104", notes = "unBlockTime时间戳单位毫秒，-1表示永久，解封传0。")
    @ServerRight(argsIndex = 0)
    public Result<Boolean> blockChat(@RequestParam(name = "serverId") int serverId,
                                     @RequestParam(name = "playerId") String playerId,
                                     @RequestParam(name = "unBlockTime") long unBlockTime,
                                     @RequestParam(required = false, name = "reason") String reason) {

        if (serverId < 0 || StringUtils.isBlank(playerId)) {
            return Result.error(ResultCode.PARAM_ERROR, "参数不正确。");
        }
        return playerService.blockChat(serverId, this.stringToLong(playerId), unBlockTime, reason);
    }

    @RequiresPermissions("blockChat:delete")
    @DeleteMapping(value = "unBlockChat")
    @ApiOperation(value = "解除禁言-1104")
    public Result<Boolean> unBlockChat(@RequestParam(name = "playerId") String playerId,
                                       @RequestParam(name = "serverId") int serverId) {
        if (StringUtils.isBlank(playerId)) {
            return Result.error(ResultCode.PARAM_ERROR, "参数不正确。");
        }
        return playerService.unBlockChat(this.stringToLong(playerId),serverId);
    }

    @RequiresPermissions("getRankInfo:query")
    @GetMapping(value = "getRankInfo")
    @ApiOperation(value = "获取排行榜信息-1105", notes = "rankType：1-等级，2-战力，3-剑修，4-法修，5-灵修，6-野战，7-天梯，8-灵压，9-关卡")
    @ServerRight(argsIndex = 0)
    public Result<List<RankInfoDto>> getRankInfo(@RequestParam(name = "serverId") int serverId, @RequestParam(name = "rankType") int rankType) {

        String result = gmClient.queryRank(serverId, rankType);
        if (StringUtils.isBlank(result)) {
            return Result.error(ResultCode.FAIL, "请求超时或返回为空。");
        }

        List<RankInfoDto> dtos = JSON.parseObject(result, new TypeReference<List<RankInfoDto>>(){});
        return Result.success(dtos);
    }

    @RequiresPermissions("queryPlayerItems:query")
    @GetMapping(value = "queryPlayerItems")
    @ApiOperation(value = "查询玩家道具-1106", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。itemId=0或null查询所有。")
    @ServerRight(argsIndex = 0)
    public Result<List<PlayerItemDto>> queryPlayerItems(@RequestParam(name = "serverId") int serverId,
                                                        @RequestParam(required = false, name = "playerId") String playerId,
                                                        @RequestParam(required = false, name = "accountId") String accountId,
                                                        @RequestParam(required = false, name = "playerName") String playerName,
                                                        @RequestParam(required = false, name = "itemId") Integer itemId) {

        long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
        if (playerIdLong == 0) {
            return Result.success(null);
        }

        String result = gmClient.queryPlayerItems(serverId, playerIdLong, itemId);
        if (StringUtils.isBlank(result)) {
            return Result.error(ResultCode.FAIL, "请求超时或返回为空。");
        }

        List<PlayerItemDto> dtos = JSON.parseObject(result, new TypeReference<List<PlayerItemDto>>(){});
        return Result.success(dtos);
    }

    @RequiresPermissions("queryPlayerEquips:query")
    @GetMapping(value = "queryPlayerEquips")
    @ApiOperation(value = "查询玩家装备-1107", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。equipId=0或null查询所有。")
    @ServerRight(argsIndex = 0)
    public Result<List<PlayerEquipDto>> queryPlayerEquips(@RequestParam(name = "serverId") int serverId,
                                                          @RequestParam(required = false, name = "playerId") String playerId,
                                                          @RequestParam(required = false, name = "accountId") String accountId,
                                                          @RequestParam(required = false, name = "playerName") String playerName,
                                                          @RequestParam(required = false, name = "equipId") Integer equipId) {

        long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
        if (playerIdLong == 0) {
            return Result.success(null);
        }

        String result = gmClient.queryPlayerEquips(serverId, playerIdLong, equipId);
        if (StringUtils.isBlank(result)) {
            return Result.error(ResultCode.FAIL, "请求超时或返回为空。");
        }

        List<PlayerEquipDto> dtos = JSON.parseObject(result, new TypeReference<List<PlayerEquipDto>>(){});
        return Result.success(dtos);
    }

}
