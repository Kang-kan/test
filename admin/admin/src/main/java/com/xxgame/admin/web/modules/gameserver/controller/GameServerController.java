package com.xxgame.admin.web.modules.gameserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.model.Result;
import com.xxgame.admin.web.model.ResultCode;
import com.xxgame.admin.web.modules.BaseController;
import com.xxgame.admin.web.modules.commons.GmClient;
import com.xxgame.admin.web.modules.gameserver.GameServerService;
import com.xxgame.admin.web.modules.gameserver.controller.model.GameServerDto;
import com.xxgame.admin.web.modules.gameserver.controller.model.GameServerRequest;
import com.xxgame.admin.web.modules.gameserver.entity.GameServer;
import com.xxgame.admin.web.modules.gameserver.repository.GameServerRepository;
import io.swagger.annotations.Api;
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

/**
 * 服务器管理接口
 * @author gil
 *
 */
@RestController
@RequestMapping("gameServer")
@Api(tags = "服务器管理-205")
public class GameServerController extends BaseController {


	@Autowired
	private GameServerRepository gameServerRepository;
	@Autowired
	private GameServerDtoConverter gameServerDtoConverter;
	@Autowired
	private GameServerService gameServerService;
	@Autowired
	private GmClient gmClient;

	@RequiresPermissions("gameServer:query")
	@GetMapping(value = "listServers")
	@ApiOperation(value = "服务器列表-205")
	public Result<PageDto<GameServerDto>> listServers(@RequestParam int pageNo, @RequestParam int pageSize) {
		PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
		Page<GameServer> pageResult = gameServerRepository.findAll(pageRequest);

		List<GameServerDto> dtos = gameServerDtoConverter.toGameServerDtos(pageResult.getContent());

		PageDto<GameServerDto> pageDto = new PageDto<>();
		pageDto.setContents(dtos);
		this.setPageInfo(pageDto, pageResult);

		return Result.success(pageDto);
	}

	@RequiresPermissions("gameServer:create")
	@PutMapping(value = "addServer")
	@ApiOperation(value = "增加服务器-205")
	public Result<GameServerDto> addServer(@RequestBody GameServerRequest request) {
		// 参数检查
		if (request == null) {
			return Result.error(ResultCode.PARAM_EMPTY, "参数不能为空");
		}
		if (StringUtils.isAnyBlank(request.getName())) {
			return Result.error(ResultCode.PARAM_EMPTY, "参数不能为空");
		}
		if (request.getId() <= 0) {
			return Result.error(ResultCode.PARAM_ERROR, "参数不能为空");
		}

		GameServer gameServer = gameServerService.addServer(request);
		GameServerDto dto = gameServerDtoConverter.toGameServerDto(gameServer);

		return Result.success(dto);
	}

	@RequiresPermissions("gameServer:update")
	@PostMapping(value = "updateServer")
	@ApiOperation(value = "修改服务器-205")
	public Result<GameServerDto> updateServer(@RequestBody GameServerRequest request) {
		// 参数检查
		if (request == null) {
			return Result.error(ResultCode.PARAM_EMPTY, "参数不能为空");
		}
		if (StringUtils.isAnyBlank(request.getName())) {
			return Result.error(ResultCode.PARAM_EMPTY, "参数不能为空");
		}
		if (request.getId() <= 0) {
			return Result.error(ResultCode.PARAM_ERROR, "参数不能为空");
		}

		Optional<GameServer> optional = gameServerRepository.findById(request.getId());
		if (!optional.isPresent()) {
			return Result.error(ResultCode.PARAM_ERROR, "服务器不存在");
		}

		GameServer gameServer = gameServerService.updateServer(optional.get(), request);
		GameServerDto dto = gameServerDtoConverter.toGameServerDto(gameServer);

		return Result.success(dto);
	}

	@RequiresPermissions("gameServer:update")
	@PostMapping(value = "reloadBaseData")
	@ApiOperation(value = "刷新数值配置表-205")
	public Result<List<Integer>> reloadBaseData(@RequestBody List<Integer> serverIds) {
		if (serverIds == null) {
			return Result.error(ResultCode.PARAM_EMPTY, "参数为空");
		}

		LinkedHashMap<Integer, ListenableFuture<ResponseEntity<String>>> futures = new LinkedHashMap<>();
		for (int serverId : serverIds) {
			ListenableFuture<ResponseEntity<String>> future = gmClient.reloadBaseData(serverId);
			futures.put(serverId, future);
		}

		List<Integer> fails = new ArrayList<>();
		for (Map.Entry<Integer, ListenableFuture<ResponseEntity<String>>> entry : futures.entrySet()) {
			int serverId = entry.getKey();
			try {
				ResponseEntity<String> responseEntity = entry.getValue().get(3, TimeUnit.SECONDS);
				if (responseEntity.getStatusCode() != HttpStatus.OK) {
					fails.add(serverId);
					logger.info("刷新数值配置表失败，serverId:{}", serverId);
					continue;
				}
				String resultStr = responseEntity.getBody();
				if (resultStr == null) {
					fails.add(serverId);
					logger.info("刷新数值配置表超时，serverId:{}", serverId);
					continue;
				}

				Map<String, Integer> resultMap = JSON.parseObject(resultStr, new TypeReference<Map<String, Integer>>(){});
				if (resultMap.get("resultCode") != 0) {
					fails.add(serverId);
					logger.info("刷新数值配置表返回失败，serverId:{} result:{}", serverId, resultStr);
					continue;
				}
			} catch (Exception e) {
				fails.add(serverId);
				String msg = String.format("刷新数值配置表异常，serverId:%s", serverId);
				logger.info(msg, e);
			}
		}

		return Result.success(fails);
	}

}