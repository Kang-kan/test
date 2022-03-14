package com.xxgame.admin.web.modules.gamelog.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgame.admin.web.interceptor.ServerRight;
import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.model.Result;
import com.xxgame.admin.web.model.ResultCode;
import com.xxgame.admin.web.model.SearchResult;
import com.xxgame.admin.web.modules.BaseController;
import com.xxgame.admin.web.modules.gamelog.controller.model.*;
import com.xxgame.admin.web.modules.commons.service.QueryGameService;
import com.xxgame.admin.web.modules.commons.logdao.*;
import com.xxgame.admin.web.modules.gamelog.service.GameLogService;
import com.xxgame.admin.web.util.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 日志查询相关接口
 * @author gil
 *
 */
@RestController
@RequestMapping("gamelog")
@Api(tags = "日志查询-1000")
public class GameLogController extends BaseController {

	@Autowired
	private QueryGameService queryGameService;
	@Autowired
	private GameLogService gameLogService;
	@Autowired
	private LoginLogDao loginLogDao;
	@Autowired
	private LogoutLogDao logoutLogDao;
	@Autowired
	private GoldLogDao goldLogDao;
	@Autowired
	private MoneyLogDao moneyLogDao;
	@Autowired
	private ItemLogDao itemLogDao;
	@Autowired
	private MarketLogDao marketLogDao;
	@Autowired
	private ChargeOrderLogDao chargeOrderLogDao;
	@Autowired
	private ExpLogDao expLogDao;
	@Autowired
	private RExpLogDao rExpLogDao;
	@Autowired
	private FashionLogDao fashionLogDao;
	@Autowired
	private MailLogDao mailLogDao;
	@Autowired
	private ShareLogDao shareLogDao;
	@Autowired
	private ExpliotLogDao expliotLogDao;
	@Autowired
	private SealStoneLogDao sealStoneLogDao;
	@Autowired
	private OfflineRewardLogDao offlineRewardLogDao;
	@Autowired
	private SerialGiftLogDao serialGiftLogDao;
	@Autowired
	private CopyLogDao copyLogDao;
	@Autowired
	private RebirthLogDao rebirthLogDao;
	@Autowired
	private FamilyLogDao familyLogDao;
	@Autowired
	private OtherResourceLogDao otherResourceLogDao;
	@Autowired
	private AchieveLogDao achieveLogDao;
	@Autowired
	private TitleLogDao titleLogDao;
	@Autowired
	private ChopLogDao chopLogDao;
	@Autowired
	private PokedexLogDao pokedexLogDao;
	@Autowired
	private PracticeLogDao practiceLogDao;
	@Autowired
	private SkillLogDao skillLogDao;
	@Autowired
	private SkillBookLogDao skillBookLogDao;
	@Autowired
	private MeridianLogDao meridianLogDao;
	@Autowired
	private EnhanceLogDao enhanceLogDao;
	@Autowired
	private WeaponSoulLogDao weaponSoulLogDao;
	@Autowired
	private TotemLogDao totemLogDao;
	@Autowired
	private TotemExpandLogDao totemExpandLogDao;
	@Autowired
	private BossDeadLogDao bossDeadLogDao;
	@Autowired
	private GmGoldLogDao gmGoldLogDao;
	@Autowired
	private HyzLogDao hyzLogDao;
	@Autowired
	private GameLogDtoConverter gameLogDtoConverter;

	@RequiresPermissions("queryPlayerLoginLog:query")
	@GetMapping(value = "queryPlayerLoginLog")
	@ApiOperation(value = "查询玩家登录日志-1001", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerLoginLogDto>> queryPlayerLoginLog(@RequestParam(required = true, name = "serverId") int serverId,
																  @RequestParam(required = false, name = "playerId") String playerId,
																  @RequestParam(required = false, name = "accountId") String accountId,
																  @RequestParam(required = false, name = "playerName") String playerName,
																  @RequestParam(required = true, name = "pageNo") int pageNo,
																  @RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = loginLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);
		List<PlayerLoginLogDto> dtos = gameLogDtoConverter.toPlayerLoginLogDtos(rowPage.getContents());

		PageDto<PlayerLoginLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("queryPlayerLogoutLog:query")
	@GetMapping(value = "queryPlayerLogoutLog")
	@ApiOperation(value = "查询玩家登出日志-1002", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerLogoutLogDto>> queryPlayerLogoutLog(@RequestParam(required = true, name = "serverId") int serverId,
																	@RequestParam(required = false, name = "playerId") String playerId,
																	@RequestParam(required = false, name = "accountId") String accountId,
																	@RequestParam(required = false, name = "playerName") String playerName,
																	@RequestParam(required = true, name = "pageNo") int pageNo,
																	@RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = logoutLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);
		List<PlayerLogoutLogDto> dtos = gameLogDtoConverter.toPlayerLogoutLogDtos(rowPage.getContents());

		PageDto<PlayerLogoutLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("queryPlayerGoldLog:query")
	@GetMapping(value = "queryPlayerGoldLog")
	@ApiOperation(value = "查询玩家仙晶日志-1003", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "type", value = "0-查询所有，1-查询消费，2-查询产出")
	})
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerResourceLogDto>> queryPlayerGoldLog(@RequestParam(required = true, name = "serverId") int serverId,
																	@RequestParam(required = false, name = "playerId") String playerId,
																	@RequestParam(required = false, name = "accountId") String accountId,
																	@RequestParam(required = false, name = "playerName") String playerName,
																	@RequestParam(required = true, name = "type") int type,
																	@RequestParam(required = true, name = "pageNo") int pageNo,
																	@RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = null;
		if (type == 1) {
			rowPage = goldLogDao.findPlayerDecLog(playerIdLong, pageNo, pageSize);
		} else if (type == 2) {
			rowPage = goldLogDao.findPlayerRewardLog(playerIdLong, pageNo, pageSize);
		} else {
			rowPage = goldLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);
		}

		List<PlayerResourceLogDto> dtos = gameLogDtoConverter.toPlayerResourceDtos(rowPage.getContents());

		PageDto<PlayerResourceLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("queryPlayerGoldLog:query")
	@GetMapping(value = "queryPlayerGoldLog1")
	@ApiOperation(value = "查询玩家仙晶日志1-1003", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "type", value = "0-查询所有，1-查询消费，2-查询产出"),
			@ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "integer"),
			@ApiImplicitParam(name = "endTime", value = "结束年月日，如：20190931", type = "integer"),
			@ApiImplicitParam(name = "fuctionType", value = "变化类型，空为所有类型", type = "string"),
	})
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerResourceLogDto>> queryPlayerGoldLog1(@RequestParam(required = true, name = "serverId") int serverId,
																	 @RequestParam(required = false, name = "playerId") String playerId,
																	 @RequestParam(required = false, name = "accountId") String accountId,
																	 @RequestParam(required = false, name = "playerName") String playerName,
																	 @RequestParam(required = true, name = "type") int type,
																	 @RequestParam(required = true, name = "startTime") int startTime,
																	 @RequestParam(required = true, name = "endTime") int endTime,
																	 @RequestParam(required = false, name = "fuctionType") String fuctionType,
																	 @RequestParam(required = true, name = "pageNo") int pageNo,
																	 @RequestParam(required = true, name = "pageSize") int pageSize) {

		Date startDate = DateUtils.numberToDate(startTime, DateUtils.PATTERN_YYYYMMDD);
		Date endDate = DateUtils.numberToDate(endTime, DateUtils.PATTERN_YYYYMMDD);
		endDate = DateUtils.nextDayAm0(endDate);

		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = null;
		if (type == 1) {
			if (StringUtils.isBlank(fuctionType)) {
				rowPage = goldLogDao.findPlayerDecLog(playerIdLong, startDate.getTime(), endDate.getTime(), pageNo, pageSize);
			} else {
				rowPage = goldLogDao.findPlayerDecLog(playerIdLong, startDate.getTime(), endDate.getTime(), fuctionType, pageNo, pageSize);
			}
		} else if (type == 2) {
			if (StringUtils.isBlank(fuctionType)) {
				rowPage = goldLogDao.findPlayerRewardLog(playerIdLong, startDate.getTime(), endDate.getTime(), pageNo, pageSize);
			} else {
				rowPage = goldLogDao.findPlayerRewardLog(playerIdLong, startDate.getTime(), endDate.getTime(), fuctionType, pageNo, pageSize);
			}
		} else {
			if (StringUtils.isBlank(fuctionType)) {
				rowPage = goldLogDao.findPlayerLog(playerIdLong, startDate.getTime(), endDate.getTime(), pageNo, pageSize);
			} else {
				rowPage = goldLogDao.findPlayerLog(playerIdLong, startDate.getTime(), endDate.getTime(), fuctionType, pageNo, pageSize);
			}
		}

		List<PlayerResourceLogDto> dtos = gameLogDtoConverter.toPlayerResourceDtos(rowPage.getContents());

		PageDto<PlayerResourceLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("queryPlayerMoneyLog:query")
	@GetMapping(value = "queryPlayerMoneyLog")
	@ApiOperation(value = "查询玩家金币日志-1004", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "type", value = "0-查询所有，1-查询消费，2-查询产出")
	})
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerResourceLogDto>> queryPlayerMoneyLog(@RequestParam(required = true, name = "serverId") int serverId,
																	 @RequestParam(required = false, name = "playerId") String playerId,
																	 @RequestParam(required = false, name = "accountId") String accountId,
																	 @RequestParam(required = false, name = "playerName") String playerName,
																	 @RequestParam(required = true, name = "type") int type,
																	 @RequestParam(required = true, name = "pageNo") int pageNo,
																	 @RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = null;
		if (type == 1) {
			rowPage = moneyLogDao.findPlayerDecLog(playerIdLong, pageNo, pageSize);
		} else if (type == 2) {
			rowPage = moneyLogDao.findPlayerRewardLog(playerIdLong, pageNo, pageSize);
		} else {
			rowPage = moneyLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);
		}

		List<PlayerResourceLogDto> dtos = gameLogDtoConverter.toPlayerResourceDtos(rowPage.getContents());

		PageDto<PlayerResourceLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("queryPlayerMoneyLog:query")
	@GetMapping(value = "queryPlayerMoneyLog1")
	@ApiOperation(value = "查询玩家金币日志1-1004", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "type", value = "0-查询所有，1-查询消费，2-查询产出"),
			@ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "integer"),
			@ApiImplicitParam(name = "endTime", value = "结束年月日，如：20190931", type = "integer"),
			@ApiImplicitParam(name = "functionType", value = "变化类型，空为所有类型", type = "string")
	})
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerResourceLogDto>> queryPlayerMoneyLog1(@RequestParam(required = true, name = "serverId") int serverId,
																	  @RequestParam(required = false, name = "playerId") String playerId,
																	  @RequestParam(required = false, name = "accountId") String accountId,
																	  @RequestParam(required = false, name = "playerName") String playerName,
																	  @RequestParam(required = true, name = "type") int type,
																	  @RequestParam(required = true, name = "startTime") int startTime,
																	  @RequestParam(required = true, name = "endTime") int endTime,
																	  @RequestParam(required = false, name = "functionType") String functionType,
																	  @RequestParam(required = true, name = "pageNo") int pageNo,
																	  @RequestParam(required = true, name = "pageSize") int pageSize) {

		Date startDate = DateUtils.numberToDate(startTime, DateUtils.PATTERN_YYYYMMDD);
		Date endDate = DateUtils.numberToDate(endTime, DateUtils.PATTERN_YYYYMMDD);
		endDate = DateUtils.nextDayAm0(endDate);

		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = null;
		if (type == 1) {
			if (StringUtils.isBlank(functionType)) {
				rowPage = moneyLogDao.findPlayerDecLog(playerIdLong, startDate.getTime(), endDate.getTime(), pageNo, pageSize);
			} else {
				rowPage = moneyLogDao.findPlayerDecLog(playerIdLong, startDate.getTime(), endDate.getTime(), functionType, pageNo, pageSize);
			}
		} else if (type == 2) {
			if (StringUtils.isBlank(functionType)) {
				rowPage = moneyLogDao.findPlayerRewardLog(playerIdLong, startDate.getTime(), endDate.getTime(), pageNo, pageSize);
			} else {
				rowPage = moneyLogDao.findPlayerRewardLog(playerIdLong, startDate.getTime(), endDate.getTime(), functionType, pageNo, pageSize);
			}
		} else {
			if (StringUtils.isBlank(functionType)) {
				rowPage = moneyLogDao.findPlayerLog(playerIdLong, startDate.getTime(), endDate.getTime(), pageNo, pageSize);
			} else {
				rowPage = moneyLogDao.findPlayerLog(playerIdLong, startDate.getTime(), endDate.getTime(), functionType, pageNo, pageSize);
			}
		}

		List<PlayerResourceLogDto> dtos = gameLogDtoConverter.toPlayerResourceDtos(rowPage.getContents());

		PageDto<PlayerResourceLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("queryPlayerItemLog:query")
	@GetMapping(value = "queryPlayerItemLog")
	@ApiOperation(value = "查询玩道具日志-1005", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "type", value = "0-查询所有，1-查询消费，2-查询产出")
	})
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerItemLogDto>> queryPlayerItemLog(@RequestParam(required = true, name = "serverId") int serverId,
														  		@RequestParam(required = false, name = "playerId") String playerId,
														  		@RequestParam(required = false, name = "accountId") String accountId,
														  		@RequestParam(required = false, name = "playerName") String playerName,
														  		@RequestParam(required = true, name = "type") int type,
														  		@RequestParam(required = true, name = "pageNo") int pageNo,
														  		@RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = null;
		if (type == 1) {
			rowPage = itemLogDao.findPlayerDecLog(playerIdLong, pageNo, pageSize);
		} else if (type == 2) {
			rowPage = itemLogDao.findPlayerRewardLog(playerIdLong, pageNo, pageSize);
		} else {
			rowPage = itemLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);
		}

		List<PlayerItemLogDto> dtos = gameLogDtoConverter.toPlayerItemLogDtos(rowPage.getContents());

		PageDto<PlayerItemLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("queryPlayerItemLog:query")
	@GetMapping(value = "queryPlayerItemLog1")
	@ApiOperation(value = "查询玩道具日志1-1005", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "type", value = "0-查询所有，1-查询消费，2-查询产出"),
			@ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "integer"),
			@ApiImplicitParam(name = "endTime", value = "结束年月日，如：20190931", type = "integer"),
			@ApiImplicitParam(name = "itemId", value = "道具id，0-查询所有", type = "int")
	})
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerItemLogDto>> queryPlayerItemLog1(@RequestParam(required = true, name = "serverId") int serverId,
																 @RequestParam(required = false, name = "playerId") String playerId,
																 @RequestParam(required = false, name = "accountId") String accountId,
																 @RequestParam(required = false, name = "playerName") String playerName,
																 @RequestParam(required = true, name = "type") int type,
																 @RequestParam(required = true, name = "startTime") int startTime,
																 @RequestParam(required = true, name = "endTime") int endTime,
																 @RequestParam(required = false, name = "itemId") int itemId,
																 @RequestParam(required = true, name = "pageNo") int pageNo,
																 @RequestParam(required = true, name = "pageSize") int pageSize) {

		Date startDate = DateUtils.numberToDate(startTime, DateUtils.PATTERN_YYYYMMDD);
		Date endDate = DateUtils.numberToDate(endTime, DateUtils.PATTERN_YYYYMMDD);
		endDate = DateUtils.nextDayAm0(endDate);

		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = null;
		if (type == 1) {
			if (itemId == 0) {
				rowPage = itemLogDao.findPlayerDecLog(playerIdLong, startDate.getTime(), endDate.getTime(), pageNo, pageSize);
			} else {
				rowPage = itemLogDao.findPlayerDecLog(playerIdLong, startDate.getTime(), endDate.getTime(), itemId, pageNo, pageSize);
			}
		} else if (type == 2) {
			if (itemId == 0) {
				rowPage = itemLogDao.findPlayerRewardLog(playerIdLong, startDate.getTime(), endDate.getTime(), pageNo, pageSize);
			} else {
				rowPage = itemLogDao.findPlayerRewardLog(playerIdLong, startDate.getTime(), endDate.getTime(), itemId, pageNo, pageSize);
			}
		} else {
			if (itemId == 0) {
				rowPage = itemLogDao.findPlayerLog(playerIdLong, startDate.getTime(), endDate.getTime(), pageNo, pageSize);
			} else {
				rowPage = itemLogDao.findPlayerLog(playerIdLong, startDate.getTime(), endDate.getTime(), itemId, pageNo, pageSize);
			}
		}

		List<PlayerItemLogDto> dtos = gameLogDtoConverter.toPlayerItemLogDtos(rowPage.getContents());

		PageDto<PlayerItemLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("querPlayerMarketLog:query")
	@GetMapping(value = "queryPlayerMarketLog")
	@ApiOperation(value = "查询玩家商城日志-1006", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerMarketLogDto>> queryPlayerMarketLog(@RequestParam(required = true, name = "serverId") int serverId,
																   @RequestParam(required = false, name = "playerId") String playerId,
																   @RequestParam(required = false, name = "accountId") String accountId,
																   @RequestParam(required = false, name = "playerName") String playerName,
																   @RequestParam(required = true, name = "pageNo") int pageNo,
																   @RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = marketLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);

		List<PlayerMarketLogDto> dtos = gameLogDtoConverter.toPlayerMarketLogDtos(rowPage.getContents());

		PageDto<PlayerMarketLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("querPlayerMarketLog:query")
	@GetMapping(value = "queryPlayerMarketLog1")
	@ApiOperation(value = "查询玩家商城日志1-1006", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "integer"),
			@ApiImplicitParam(name = "endTime", value = "结束年月日，如：20190931", type = "integer"),
			@ApiImplicitParam(name = "itemId", value = "道具id，0-查询所有", type = "int")
	})
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerMarketLogDto>> queryPlayerMarketLog1(@RequestParam(required = true, name = "serverId") int serverId,
																	 @RequestParam(required = false, name = "playerId") String playerId,
																	 @RequestParam(required = false, name = "accountId") String accountId,
																	 @RequestParam(required = false, name = "playerName") String playerName,
																	 @RequestParam(required = true, name = "startTime") int startTime,
																	 @RequestParam(required = true, name = "endTime") int endTime,
																	 @RequestParam(required = false, name = "itemId") int itemId,
																	 @RequestParam(required = true, name = "pageNo") int pageNo,
																	 @RequestParam(required = true, name = "pageSize") int pageSize) {

		Date startDate = DateUtils.numberToDate(startTime, DateUtils.PATTERN_YYYYMMDD);
		Date endDate = DateUtils.numberToDate(endTime, DateUtils.PATTERN_YYYYMMDD);
		endDate = DateUtils.nextDayAm0(endDate);

		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = null;
		if (itemId == 0) {
			rowPage = marketLogDao.findPlayerLog(playerIdLong, startDate.getTime(), endDate.getTime(), pageNo, pageSize);
		} else {
			rowPage = marketLogDao.findPlayerLog(playerIdLong, startDate.getTime(), endDate.getTime(), itemId, pageNo, pageSize);
		}

		List<PlayerMarketLogDto> dtos = gameLogDtoConverter.toPlayerMarketLogDtos(rowPage.getContents());

		PageDto<PlayerMarketLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("querPlayerMarketLog:query")
	@GetMapping(value = "queryMarketLogByItem")
	@ApiOperation(value = "根据道具id查找商城日志-1006")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerMarketLogDto>> queryMarketLogByItem(@RequestParam(name = "serverId") int serverId,
																	@RequestParam(name = "itemId") int itemId,
																	@RequestParam(name = "pageNo") int pageNo,
																	@RequestParam(name = "pageSize") int pageSize) {

		PageDto<Map<String, Object>> rowPage = marketLogDao.findTiemLog(serverId, itemId, pageNo, pageSize);

		List<PlayerMarketLogDto> dtos = gameLogDtoConverter.toPlayerMarketLogDtos(rowPage.getContents());

		PageDto<PlayerMarketLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("querPlayerMarketLog:query")
	@GetMapping(value = "queryMarketLogByItem1")
	@ApiOperation(value = "根据道具id查找商城日志1-1006")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerMarketLogDto>> queryMarketLogByItem1(@RequestParam(name = "serverId") int serverId,
																	 @RequestParam(name = "itemId") int itemId,
																	 @RequestParam(name = "startTime") int startTime,
																	 @RequestParam(name = "endTime") int endTime,
																	 @RequestParam(name = "pageNo") int pageNo,
																	 @RequestParam(name = "pageSize") int pageSize) {

		Date startDate = DateUtils.numberToDate(startTime, DateUtils.PATTERN_YYYYMMDD);
		Date endDate = DateUtils.numberToDate(endTime, DateUtils.PATTERN_YYYYMMDD);
		endDate = DateUtils.nextDayAm0(endDate);

		PageDto<Map<String, Object>> rowPage = marketLogDao.findTiemLog(serverId, itemId, startDate.getTime(), endDate.getTime(), pageNo, pageSize);

		List<PlayerMarketLogDto> dtos = gameLogDtoConverter.toPlayerMarketLogDtos(rowPage.getContents());

		PageDto<PlayerMarketLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("querPlayerChargeLog:query")
	@GetMapping(value = "queryPlayerChargeLogById")
	@ApiOperation(value = "根据游戏定单id查找充值日志-1007")
	public Result<ChargeOrderDto> queryPlayerChargeLogById(@RequestParam String orderId) {
		if (StringUtils.isBlank(orderId)) {
			return Result.success(null);
		}
		Map<String, Object> rowMap = chargeOrderLogDao.findByOrderId(this.stringToLong(orderId));
		if (rowMap == null || rowMap.isEmpty()) {
			return Result.success(null);
		}

		ChargeOrderDto dto = gameLogDtoConverter.toChargeOrderDto(rowMap);
		return Result.success(dto);
	}

	@RequiresPermissions("querPlayerChargeLog:query")
	@GetMapping(value = "queryPlayerChargeLogs")
	@ApiOperation(value = "查看充值日志-1007")
	public Result<PageDto<ChargeOrderDto>> queryPlayerChargeLogs(@RequestParam(required = false, name = "serverId") Integer serverId,
																 @RequestParam(required = true, name = "pageNo") int pageNo,
																 @RequestParam(required = true, name = "pageSize") int pageSize) {

		PageDto<Map<String, Object>> rowPage = null;
		if (serverId == null || serverId <= 0) {
			rowPage = chargeOrderLogDao.findPlayerLogs(pageNo, pageSize);
		} else {
			rowPage = chargeOrderLogDao.findPlayerLogs(serverId, pageNo, pageSize);
		}
		List<ChargeOrderDto> dtos = gameLogDtoConverter.toChargeOrderDtos(rowPage.getContents());

		PageDto<ChargeOrderDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("querPlayerChargeLog:query")
	@GetMapping(value = "queryPlayerChargeLog")
	@ApiOperation(value = "查询玩家充值日志-1007", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<ChargeOrderDto>> queryPlayerChargeLog(@RequestParam(required = true, name = "serverId") int serverId,
																@RequestParam(required = false, name = "playerId") String playerId,
																@RequestParam(required = false, name = "accountId") String accountId,
																@RequestParam(required = false, name = "playerName") String playerName,
																@RequestParam(required = true, name = "pageNo") int pageNo,
																@RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = chargeOrderLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);

		List<ChargeOrderDto> dtos = gameLogDtoConverter.toChargeOrderDtos(rowPage.getContents());

		PageDto<ChargeOrderDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("querPlayerExpLog:query")
	@GetMapping(value = "exportChargeBill")
	@ApiOperation(value = "导出充值账单-1008")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "integer"),
			@ApiImplicitParam(name = "endTime", value = "结束年月日，如：20190931", type = "integer")
	})
	public Result<List<ChargeBillDto>> exportChargeBill(@RequestParam int startTime, @RequestParam int endTime) {
		Date startDate = DateUtils.numberToDate(startTime, DateUtils.PATTERN_YYYYMMDD);
		Date endDate = DateUtils.numberToDate(endTime, DateUtils.PATTERN_YYYYMMDD);
		endDate = DateUtils.nextDayAm0(endDate);

		List<Map<String, Object>> rowMaps = chargeOrderLogDao.exportLog(startDate.getTime(), endDate.getTime());
		List<ChargeBillDto> dtos = gameLogDtoConverter.toChargeBillDtos(rowMaps);

		return Result.success(dtos);
	}

	@RequiresPermissions("querPlayerExpLog:query")
	@GetMapping(value = "queryPlayerExpLog")
	@ApiOperation(value = "查询玩家经验日志-1008", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerResourceLogDto>> queryPlayerExpLog(@RequestParam(required = true, name = "serverId") int serverId,
																   @RequestParam(required = false, name = "playerId") String playerId,
																   @RequestParam(required = false, name = "accountId") String accountId,
																   @RequestParam(required = false, name = "playerName") String playerName,
																   @RequestParam(required = true, name = "pageNo") int pageNo,
																   @RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = expLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);

		List<PlayerResourceLogDto> dtos = gameLogDtoConverter.toPlayerResourceDtos(rowPage.getContents());

		PageDto<PlayerResourceLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("querPlayerRExpLog:query")
	@GetMapping(value = "queryPlayerRExpLog")
	@ApiOperation(value = "查询玩家修为日志-1009", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerResourceLogDto>> queryPlayerRExpLog(@RequestParam(required = true, name = "serverId") int serverId,
																   @RequestParam(required = false, name = "playerId") String playerId,
																   @RequestParam(required = false, name = "accountId") String accountId,
																   @RequestParam(required = false, name = "playerName") String playerName,
																   @RequestParam(required = true, name = "pageNo") int pageNo,
																   @RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = rExpLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);

		List<PlayerResourceLogDto> dtos = gameLogDtoConverter.toPlayerResourceDtos(rowPage.getContents());

		PageDto<PlayerResourceLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("querPlayerFashionLog:query")
	@GetMapping(value = "queryPlayerFashionLog")
	@ApiOperation(value = "查询玩家时装日志-1010", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerFashionLogDto>> queryPlayerFashionLog(@RequestParam(required = true, name = "serverId") int serverId,
																     @RequestParam(required = false, name = "playerId") String playerId,
																     @RequestParam(required = false, name = "accountId") String accountId,
																     @RequestParam(required = false, name = "playerName") String playerName,
																     @RequestParam(required = true, name = "pageNo") int pageNo,
																     @RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = fashionLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);

		List<PlayerFashionLogDto> dtos = gameLogDtoConverter.toPlayerFashionLogDtos(rowPage.getContents());

		PageDto<PlayerFashionLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("querPlayerMailLog:query")
	@GetMapping(value = "queryPlayerMailLog")
	@ApiOperation(value = "查询玩家邮件日志-1011", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerMailLogDto>> queryPlayerMailLog(@RequestParam(required = true, name = "serverId") int serverId,
															   @RequestParam(required = false, name = "playerId") String playerId,
															   @RequestParam(required = false, name = "accountId") String accountId,
															   @RequestParam(required = false, name = "playerName") String playerName,
															   @RequestParam(required = true, name = "pageNo") int pageNo,
															   @RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = mailLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);

		List<PlayerMailLogDto> dtos = gameLogDtoConverter.toPlayerMailLogDtos(rowPage.getContents());

		PageDto<PlayerMailLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("querPlayerMailLog:query")
	@GetMapping(value = "queryPlayerMailLog1")
	@ApiOperation(value = "查询玩家邮件日志1-1011", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "integer"),
			@ApiImplicitParam(name = "endTime", value = "结束年月日，如：20190931", type = "integer"),
			@ApiImplicitParam(name = "title", value = "邮件标题，空为所有邮件", type = "string")
	})
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerMailLogDto>> queryPlayerMailLog1(@RequestParam(required = true, name = "serverId") int serverId,
																 @RequestParam(required = false, name = "playerId") String playerId,
																 @RequestParam(required = false, name = "accountId") String accountId,
																 @RequestParam(required = false, name = "playerName") String playerName,
																 @RequestParam(required = true, name = "startTime") int startTime,
																 @RequestParam(required = true, name = "endTime") int endTime,
																 @RequestParam(required = false, name = "title") String title,
																 @RequestParam(required = true, name = "pageNo") int pageNo,
																 @RequestParam(required = true, name = "pageSize") int pageSize) {

		Date startDate = DateUtils.numberToDate(startTime, DateUtils.PATTERN_YYYYMMDD);
		Date endDate = DateUtils.numberToDate(endTime, DateUtils.PATTERN_YYYYMMDD);
		endDate = DateUtils.nextDayAm0(endDate);

		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = null;
		if (StringUtils.isBlank(title)) {
			rowPage = mailLogDao.findPlayerLog(playerIdLong, startDate.getTime(), endDate.getTime(), pageNo, pageSize);
		} else {
			rowPage = mailLogDao.findPlayerLog(playerIdLong, startDate.getTime(), endDate.getTime(), title, pageNo, pageSize);
		}

		List<PlayerMailLogDto> dtos = gameLogDtoConverter.toPlayerMailLogDtos(rowPage.getContents());

		PageDto<PlayerMailLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("querPlayerMailLog:query")
	@GetMapping(value = "findMailLogBySrvTitle")
	@ApiOperation(value = "根据服务器id邮件标题分页查找邮件日志-1011")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerMailLogDto>> findMailLogBySrvTitle(@RequestParam(name = "serverId") int serverId,
																@RequestParam("title") String title,
																@RequestParam(name = "pageNo") int pageNo,
																@RequestParam(name = "pageSize") int pageSize) {
		title = StringEscapeUtils.escapeHtml4(title);
		if (StringUtils.isBlank(title)) {
			return Result.error(ResultCode.PARAM_ERROR, "标题不能为空。");
		}

		PageDto<Map<String, Object>> rowPage = mailLogDao.findBySrvTitle(serverId, title, pageNo, pageSize);

		List<PlayerMailLogDto> dtos = gameLogDtoConverter.toPlayerMailLogDtos(rowPage.getContents());

		PageDto<PlayerMailLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("querPlayerShareLog:query")
	@GetMapping(value = "queryPlayerShareLog")
	@ApiOperation(value = "查询玩家分享日志-1012", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerShareLogDto>> queryPlayerShareLog(@RequestParam(required = true, name = "serverId") int serverId,
															     @RequestParam(required = false, name = "playerId") String playerId,
															     @RequestParam(required = false, name = "accountId") String accountId,
															     @RequestParam(required = false, name = "playerName") String playerName,
															     @RequestParam(required = true, name = "pageNo") int pageNo,
															     @RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = shareLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);

		List<PlayerShareLogDto> dtos = gameLogDtoConverter.toPlayerShareLogDtos(rowPage.getContents());

		PageDto<PlayerShareLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("querPlayerShareLog:query")
	@GetMapping(value = "queryPlayerShareLog1")
	@ApiOperation(value = "查询玩家分享日志1-1012", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerShareLogDto>> queryPlayerShareLog1(@RequestParam(required = true, name = "serverId") int serverId,
																   @RequestParam(required = false, name = "playerId") String playerId,
																   @RequestParam(required = false, name = "accountId") String accountId,
																   @RequestParam(required = false, name = "playerName") String playerName,
																   @RequestParam(required = true, name = "startTime") int startTime,
																   @RequestParam(required = true, name = "endTime") int endTime,
																   @RequestParam(required = true, name = "pageNo") int pageNo,
																   @RequestParam(required = true, name = "pageSize") int pageSize) {

		Date startDate = DateUtils.numberToDate(startTime, DateUtils.PATTERN_YYYYMMDD);
		Date endDate = DateUtils.numberToDate(endTime, DateUtils.PATTERN_YYYYMMDD);
		endDate = DateUtils.nextDayAm0(endDate);

		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = shareLogDao.findPlayerLog(playerIdLong, startDate.getTime(), endDate.getTime(), pageNo, pageSize);

		List<PlayerShareLogDto> dtos = gameLogDtoConverter.toPlayerShareLogDtos(rowPage.getContents());

		PageDto<PlayerShareLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("querPlayerExpliotLog:query")
	@GetMapping(value = "queryPlayerExpliotLog")
	@ApiOperation(value = "查询玩家灵压日志-1013", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "type", value = "0-查询所有，1-查询消费，2-查询产出")
	})
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerResourceLogDto>> queryPlayerExpliotLog(@RequestParam(required = true, name = "serverId") int serverId,
																      @RequestParam(required = false, name = "playerId") String playerId,
																      @RequestParam(required = false, name = "accountId") String accountId,
																      @RequestParam(required = false, name = "playerName") String playerName,
																      @RequestParam(required = true, name = "type") int type,
																      @RequestParam(required = true, name = "pageNo") int pageNo,
																      @RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = null;
		if (type == 1) {
			rowPage = expliotLogDao.findPlayerDecLog(playerIdLong, pageNo, pageSize);
		} else if (type == 2) {
			rowPage = expliotLogDao.findPlayerRewardLog(playerIdLong, pageNo, pageSize);
		} else {
			rowPage = expliotLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);
		}

		List<PlayerResourceLogDto> dtos = gameLogDtoConverter.toPlayerResourceDtos(rowPage.getContents());

		PageDto<PlayerResourceLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("querPlayerSealStoneLog:query")
	@GetMapping(value = "queryPlayerSealStoneLog")
	@ApiOperation(value = "查询玩家符文日志-1014", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerSealStoneLogDto>> queryPlayerSealStoneLog(@RequestParam(required = true, name = "serverId") int serverId,
																	     @RequestParam(required = false, name = "playerId") String playerId,
																	     @RequestParam(required = false, name = "accountId") String accountId,
																	     @RequestParam(required = false, name = "playerName") String playerName,
																	     @RequestParam(required = true, name = "pageNo") int pageNo,
																	     @RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = sealStoneLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);
		List<PlayerSealStoneLogDto> dtos = gameLogDtoConverter.toPlayerSealStoneLogDtos(rowPage.getContents());

		PageDto<PlayerSealStoneLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("querPlayerSealStoneLog:query")
	@GetMapping(value = "queryPlayerSealStoneLog1")
	@ApiOperation(value = "查询玩家符文日志1-1014", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "opValue", value = "操作类型，-1为所有类型", type = "integer"),
	})
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerSealStoneLogDto>> queryPlayerSealStoneLog1(@RequestParam(required = true, name = "serverId") int serverId,
																		   @RequestParam(required = false, name = "playerId") String playerId,
																		   @RequestParam(required = false, name = "accountId") String accountId,
																		   @RequestParam(required = false, name = "playerName") String playerName,
																		   @RequestParam(required = true, name = "startTime") int startTime,
																		   @RequestParam(required = true, name = "endTime") int endTime,
																		   @RequestParam(required = true, name = "opValue") int opValue,
																		   @RequestParam(required = true, name = "pageNo") int pageNo,
																		   @RequestParam(required = true, name = "pageSize") int pageSize) {

		Date startDate = DateUtils.numberToDate(startTime, DateUtils.PATTERN_YYYYMMDD);
		Date endDate = DateUtils.numberToDate(endTime, DateUtils.PATTERN_YYYYMMDD);
		endDate = DateUtils.nextDayAm0(endDate);

		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = null;
		if (opValue == -1) {
			rowPage = sealStoneLogDao.findPlayerLog(playerIdLong, startDate.getTime(), endDate.getTime(), pageNo, pageSize);
		} else {
			rowPage = sealStoneLogDao.findPlayerLog(playerIdLong, startDate.getTime(), endDate.getTime(), opValue, pageNo, pageSize);
		}

		List<PlayerSealStoneLogDto> dtos = gameLogDtoConverter.toPlayerSealStoneLogDtos(rowPage.getContents());

		PageDto<PlayerSealStoneLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("querPlayerOfflineRewardLog:query")
	@GetMapping(value = "queryPlayerOfflineRewardLog")
	@ApiOperation(value = "查询玩家离线挂机日志-1015", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerOfflineRewardLogDto>> queryPlayerOfflineRewardLog(@RequestParam(required = true, name = "serverId") int serverId,
																				 @RequestParam(required = false, name = "playerId") String playerId,
																				 @RequestParam(required = false, name = "accountId") String accountId,
																				 @RequestParam(required = false, name = "playerName") String playerName,
																				 @RequestParam(required = true, name = "pageNo") int pageNo,
																				 @RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = offlineRewardLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);
		List<PlayerOfflineRewardLogDto> dtos = gameLogDtoConverter.toPlayerOfflineRewardLogDtos(rowPage.getContents());

		PageDto<PlayerOfflineRewardLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("querPlayerSerialGiftLog:query")
	@GetMapping(value = "queryPlayerSerialGiftLog")
	@ApiOperation(value = "查询玩家礼包码日志-1016", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<List<PlayerSerialGiftLogDto>> queryPlayerSerialGiftLog(@RequestParam(required = true, name = "serverId") int serverId,
																		@RequestParam(required = false, name = "playerId") String playerId,
																		@RequestParam(required = false, name = "accountId") String accountId,
																		@RequestParam(required = false, name = "playerName") String playerName) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		List<Map<String, Object>> rowMaps = serialGiftLogDao.findPlayerLog(playerIdLong);
		List<PlayerSerialGiftLogDto> dtos = gameLogDtoConverter.toPlayerSerialGiftLogDtos(rowMaps);

		return Result.success(dtos);
	}

	@RequiresPermissions("querPlayerSerialGiftLog:query")
	@GetMapping(value = "querySerialGiftLog")
	@ApiOperation(value = "根据礼包码查询日志-1016")
	public Result<List<PlayerSerialGiftLogDto>> querySerialGiftLog(@RequestParam String cdKey) {
		List<Map<String, Object>> rowMaps = serialGiftLogDao.findByCdKey(cdKey);
		List<PlayerSerialGiftLogDto> dtos = gameLogDtoConverter.toPlayerSerialGiftLogDtos(rowMaps);

		return Result.success(dtos);
	}

	@RequiresPermissions("querPlayerCopyLog:query")
	@GetMapping(value = "queryPlayerCopyLog")
	@ApiOperation(value = "查询玩家副本日志-1017", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerCopyLogDto>> queryPlayerCopyLog(@RequestParam(required = true, name = "serverId") int serverId,
															   @RequestParam(required = false, name = "playerId") String playerId,
															   @RequestParam(required = false, name = "accountId") String accountId,
															   @RequestParam(required = false, name = "playerName") String playerName,
															   @RequestParam(required = true, name = "pageNo") int pageNo,
															   @RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = copyLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);
		List<PlayerCopyLogDto> dtos = gameLogDtoConverter.toPlayerCopyLogDtos(rowPage.getContents());

		PageDto<PlayerCopyLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("querPlayerCopyLog:query")
	@GetMapping(value = "queryPlayerCopyLog1")
	@ApiOperation(value = "查询玩家副本日志1-1017", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerCopyLogDto>> queryPlayerCopyLog1(@RequestParam(required = true, name = "serverId") int serverId,
																 @RequestParam(required = false, name = "playerId") String playerId,
																 @RequestParam(required = false, name = "accountId") String accountId,
																 @RequestParam(required = false, name = "playerName") String playerName,
																 @RequestParam(required = true, name = "startTime") int startTime,
																 @RequestParam(required = true, name = "endTime") int endTime,
																 @RequestParam(required = true, name = "pageNo") int pageNo,
																 @RequestParam(required = true, name = "pageSize") int pageSize) {

		Date startDate = DateUtils.numberToDate(startTime, DateUtils.PATTERN_YYYYMMDD);
		Date endDate = DateUtils.numberToDate(endTime, DateUtils.PATTERN_YYYYMMDD);
		endDate = DateUtils.nextDayAm0(endDate);

		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = copyLogDao.findPlayerLog(playerIdLong, startDate.getTime(), endDate.getTime(), pageNo, pageSize);
		List<PlayerCopyLogDto> dtos = gameLogDtoConverter.toPlayerCopyLogDtos(rowPage.getContents());

		PageDto<PlayerCopyLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("querPlayerRebirthLog:query")
	@GetMapping(value = "queryPlayerRebirthLog")
	@ApiOperation(value = "查询玩家转生日志-1018", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<List<PlayerRebirthLogDto>> queryPlayerRebirthLog(@RequestParam(required = true, name = "serverId") int serverId,
																  @RequestParam(required = false, name = "playerId") String playerId,
																  @RequestParam(required = false, name = "accountId") String accountId,
																  @RequestParam(required = false, name = "playerName") String playerName) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		List<Map<String, Object>> rowMaps = rebirthLogDao.findPlayerLog(playerIdLong);
		List<PlayerRebirthLogDto> dtos = gameLogDtoConverter.toPlayerRebirthLogDtos(rowMaps);

		return Result.success(dtos);
	}

	@RequiresPermissions("querFamilyLog:query")
	@GetMapping(value = "queryFamilyLog")
	@ApiOperation(value = "查询仙盟日志-1019", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<FamilyLogDto>> queryFamilyLog(@RequestParam(required = true, name = "serverId") int serverId,
													   @RequestParam(required = false, name = "playerId") String playerId,
													   @RequestParam(required = false, name = "accountId") String accountId,
													   @RequestParam(required = false, name = "playerName") String playerName,
													   @RequestParam(required = true, name = "pageNo") int pageNo,
													   @RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = familyLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);
		List<FamilyLogDto> dtos = gameLogDtoConverter.toFamilyLogDtos(rowPage.getContents());

		PageDto<FamilyLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("querFamilyLog:query")
	@GetMapping(value = "queryFamilyLogByIdOrName")
	@ApiOperation(value = "根据id或名字查询仙盟日志-1019", notes = "familyId、familyName 二个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<FamilyLogDto>> queryFamilyLogByIdOrName(@RequestParam(required = true, name = "serverId") int serverId,
																  @RequestParam(required = false, name = "familyId") String familyId,
																  @RequestParam(required = false, name = "familyName") String familyName,
																  @RequestParam(required = true, name = "pageNo") int pageNo,
																  @RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toFamilyId(serverId, familyId, familyName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = familyLogDao.findByFamilyId(playerIdLong, pageNo, pageSize);
		List<FamilyLogDto> dtos = gameLogDtoConverter.toFamilyLogDtos(rowPage.getContents());

		PageDto<FamilyLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("querOtherResourceLog:query")
	@GetMapping(value = "queryOtherResourceLog")
	@ApiOperation(value = "查询玩家其它货币日志-1020", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "type", value = "0-查询所有，1-查询消费，2-查询产出")
	})
	@ServerRight(argsIndex = 0)
	public Result<PageDto<OtherResourceLogDto>> queryOtherResourceLog(@RequestParam(required = true, name = "serverId") int serverId,
																	 @RequestParam(required = false, name = "playerId") String playerId,
																	 @RequestParam(required = false, name = "accountId") String accountId,
																	 @RequestParam(required = false, name = "playerName") String playerName,
																	 @RequestParam(required = true, name = "type") int type,
																	 @RequestParam(required = true, name = "pageNo") int pageNo,
																	 @RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = null;
		if (type == 1) {
			rowPage = otherResourceLogDao.findPlayerDecLog(playerIdLong, pageNo, pageSize);
		} else if (type == 2) {
			rowPage = otherResourceLogDao.findPlayerRewardLog(playerIdLong, pageNo, pageSize);
		} else {
			rowPage = otherResourceLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);
		}

		List<OtherResourceLogDto> dtos = gameLogDtoConverter.toOtherResourceLogDtos(rowPage.getContents());

		PageDto<OtherResourceLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("querOtherResourceLog:query")
	@GetMapping(value = "queryOtherResourceLog1")
	@ApiOperation(value = "查询玩家其它货币日志1-1020", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "type", value = "0-查询所有，1-查询消费，2-查询产出"),
			@ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "integer"),
			@ApiImplicitParam(name = "endTime", value = "结束年月日，如：20190931", type = "integer"),
			@ApiImplicitParam(name = "currency", value = "其它货币类型JSON数组，目前有：竞技积分、公会贡献、商店积分", type = "string")
	})
	@ServerRight(argsIndex = 0)
	public Result<PageDto<OtherResourceLogDto>> queryOtherResourceLog1(@RequestParam(required = true, name = "serverId") int serverId,
																	   @RequestParam(required = false, name = "playerId") String playerId,
																	   @RequestParam(required = false, name = "accountId") String accountId,
																	   @RequestParam(required = false, name = "playerName") String playerName,
																	   @RequestParam(required = true, name = "type") int type,
																	   @RequestParam(required = true, name = "startTime") int startTime,
																	   @RequestParam(required = true, name = "endTime") int endTime,
																	   @RequestParam(required = true, name = "currency") String currencys,
																	   @RequestParam(required = true, name = "pageNo") int pageNo,
																	   @RequestParam(required = true, name = "pageSize") int pageSize) {

		List<Integer> currencyList = JSON.parseObject(currencys, new TypeReference<List<Integer>>(){});

		Date startDate = DateUtils.numberToDate(startTime, DateUtils.PATTERN_YYYYMMDD);
		Date endDate = DateUtils.numberToDate(endTime, DateUtils.PATTERN_YYYYMMDD);
		endDate = DateUtils.nextDayAm0(endDate);

		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = null;
		if (type == 1) {
			rowPage = otherResourceLogDao.findPlayerDecLog(playerIdLong, startDate.getTime(), endDate.getTime(), currencyList, pageNo, pageSize);
		} else if (type == 2) {
			rowPage = otherResourceLogDao.findPlayerRewardLog(playerIdLong, startDate.getTime(), endDate.getTime(), currencyList, pageNo, pageSize);
		} else {
			rowPage = otherResourceLogDao.findPlayerLog(playerIdLong, startDate.getTime(), endDate.getTime(), currencyList, pageNo, pageSize);
		}

		List<OtherResourceLogDto> dtos = gameLogDtoConverter.toOtherResourceLogDtos(rowPage.getContents());

		PageDto<OtherResourceLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("querPlayerAchieveLog:query")
	@GetMapping(value = "queryPlayerAchieveLog")
	@ApiOperation(value = "查询成就日志-1021", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerAchieveLoDto>> queryPlayerAchieveLog(@RequestParam(required = true, name = "serverId") int serverId,
													   				@RequestParam(required = false, name = "playerId") String playerId,
													   				@RequestParam(required = false, name = "accountId") String accountId,
													   				@RequestParam(required = false, name = "playerName") String playerName,
													   				@RequestParam(required = true, name = "pageNo") int pageNo,
													   				@RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = achieveLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);
		List<PlayerAchieveLoDto> dtos = gameLogDtoConverter.toPlayerAchieveLoDtos(rowPage.getContents());

		PageDto<PlayerAchieveLoDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("querPlayerAchieveLog:query")
	@GetMapping(value = "queryPlayerAchieveLog1")
	@ApiOperation(value = "查询成就日志1-1021", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerAchieveLoDto>> queryPlayerAchieveLog1(@RequestParam(required = true, name = "serverId") int serverId,
																	  @RequestParam(required = false, name = "playerId") String playerId,
																	  @RequestParam(required = false, name = "accountId") String accountId,
																	  @RequestParam(required = false, name = "playerName") String playerName,
																	  @RequestParam(required = true, name = "startTime") int startTime,
																	  @RequestParam(required = true, name = "endTime") int endTime,
																	  @RequestParam(required = true, name = "pageNo") int pageNo,
																	  @RequestParam(required = true, name = "pageSize") int pageSize) {

		Date startDate = DateUtils.numberToDate(startTime, DateUtils.PATTERN_YYYYMMDD);
		Date endDate = DateUtils.numberToDate(endTime, DateUtils.PATTERN_YYYYMMDD);
		endDate = DateUtils.nextDayAm0(endDate);

		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = achieveLogDao.findPlayerLog(playerIdLong, startDate.getTime(), endDate.getTime(), pageNo, pageSize);
		List<PlayerAchieveLoDto> dtos = gameLogDtoConverter.toPlayerAchieveLoDtos(rowPage.getContents());

		PageDto<PlayerAchieveLoDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("querPlayerTitleLog:query")
	@GetMapping(value = "queryPlayerTitleLog")
	@ApiOperation(value = "查询称号日志-1022", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerTitleLogDto>> queryPlayerTitleLog(@RequestParam(required = true, name = "serverId") int serverId,
																  @RequestParam(required = false, name = "playerId") String playerId,
																  @RequestParam(required = false, name = "accountId") String accountId,
																  @RequestParam(required = false, name = "playerName") String playerName,
																  @RequestParam(required = true, name = "pageNo") int pageNo,
																  @RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = titleLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);
		List<PlayerTitleLogDto> dtos = gameLogDtoConverter.toPlayerTitleLogDtos(rowPage.getContents());

		PageDto<PlayerTitleLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("querPlayerTitleLog:query")
	@GetMapping(value = "queryPlayerTitleLog1")
	@ApiOperation(value = "查询称号日志1-1022", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerTitleLogDto>> queryPlayerTitleLog1(@RequestParam(required = true, name = "serverId") int serverId,
																   @RequestParam(required = false, name = "playerId") String playerId,
																   @RequestParam(required = false, name = "accountId") String accountId,
																   @RequestParam(required = false, name = "playerName") String playerName,
																   @RequestParam(required = true, name = "startTime") int startTime,
																   @RequestParam(required = true, name = "endTime") int endTime,
																   @RequestParam(required = true, name = "pageNo") int pageNo,
																   @RequestParam(required = true, name = "pageSize") int pageSize) {

		Date startDate = DateUtils.numberToDate(startTime, DateUtils.PATTERN_YYYYMMDD);
		Date endDate = DateUtils.numberToDate(endTime, DateUtils.PATTERN_YYYYMMDD);
		endDate = DateUtils.nextDayAm0(endDate);

		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = titleLogDao.findPlayerLog(playerIdLong, startDate.getTime(), endDate.getTime(), pageNo, pageSize);
		List<PlayerTitleLogDto> dtos = gameLogDtoConverter.toPlayerTitleLogDtos(rowPage.getContents());

		PageDto<PlayerTitleLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("querPlayerChopLog:query")
	@GetMapping(value = "queryPlayerChopLog")
	@ApiOperation(value = "查询神器日志-1023", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerChopLogDto>> queryPlayerChopLog(@RequestParam(required = true, name = "serverId") int serverId,
															   @RequestParam(required = false, name = "playerId") String playerId,
															   @RequestParam(required = false, name = "accountId") String accountId,
															   @RequestParam(required = false, name = "playerName") String playerName,
															   @RequestParam(required = true, name = "pageNo") int pageNo,
															   @RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = chopLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);
		List<PlayerChopLogDto> dtos = gameLogDtoConverter.toPlayerChopLogDtos(rowPage.getContents());

		PageDto<PlayerChopLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("querPlayerPokedexLog:query")
	@GetMapping(value = "queryPlayerPokedexLog")
	@ApiOperation(value = "查询异闻录日志-1024", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerPokedexLogDto>> queryPlayerPokedexLog(@RequestParam(required = true, name = "serverId") int serverId,
																	 @RequestParam(required = false, name = "playerId") String playerId,
																	 @RequestParam(required = false, name = "accountId") String accountId,
																	 @RequestParam(required = false, name = "playerName") String playerName,
																	 @RequestParam(required = true, name = "pageNo") int pageNo,
																	 @RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = pokedexLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);
		List<PlayerPokedexLogDto> dtos = gameLogDtoConverter.toPlayerPokedexLogDtos(rowPage.getContents());

		PageDto<PlayerPokedexLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("querPlayerPokedexLog:query")
	@GetMapping(value = "queryPlayerPokedexLog1")
	@ApiOperation(value = "查询异闻录日志1-1024", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerPokedexLogDto>> queryPlayerPokedexLog1(@RequestParam(required = true, name = "serverId") int serverId,
																	   @RequestParam(required = false, name = "playerId") String playerId,
																	   @RequestParam(required = false, name = "accountId") String accountId,
																	   @RequestParam(required = false, name = "playerName") String playerName,
																	   @RequestParam(required = true, name = "startTime") int startTime,
																	   @RequestParam(required = true, name = "endTime") int endTime,
																	   @RequestParam(required = true, name = "pageNo") int pageNo,
																	   @RequestParam(required = true, name = "pageSize") int pageSize) {

		Date startDate = DateUtils.numberToDate(startTime, DateUtils.PATTERN_YYYYMMDD);
		Date endDate = DateUtils.numberToDate(endTime, DateUtils.PATTERN_YYYYMMDD);
		endDate = DateUtils.nextDayAm0(endDate);

		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = pokedexLogDao.findPlayerLog(playerIdLong, startDate.getTime(), endDate.getTime(), pageNo, pageSize);
		List<PlayerPokedexLogDto> dtos = gameLogDtoConverter.toPlayerPokedexLogDtos(rowPage.getContents());

		PageDto<PlayerPokedexLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("querPlayerPracticeLog:query")
	@GetMapping(value = "queryPlayerPracticeLog")
	@ApiOperation(value = "查询伙伴圣魂守护兽日志-1025", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerPracticeLogDto>> queryPlayerPracticeLog(@RequestParam(required = true, name = "serverId") int serverId,
																	   @RequestParam(required = false, name = "playerId") String playerId,
																	   @RequestParam(required = false, name = "accountId") String accountId,
																	   @RequestParam(required = false, name = "playerName") String playerName,
																	   @RequestParam(required = true, name = "pageNo") int pageNo,
																	   @RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = practiceLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);
		List<PlayerPracticeLogDto> dtos = gameLogDtoConverter.toPlayerPracticeLogDtos(rowPage.getContents());

		PageDto<PlayerPracticeLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("queryPlayerSkillLog:query")
	@GetMapping(value = "queryPlayerSkillLog")
	@ApiOperation(value = "查询玩家技能日志-1026", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerSkillLogDto>> queryPlayerSkillLog(@RequestParam(required = true, name = "serverId") int serverId,
																  @RequestParam(required = false, name = "playerId") String playerId,
																  @RequestParam(required = false, name = "accountId") String accountId,
																  @RequestParam(required = false, name = "playerName") String playerName,
																  @RequestParam(required = true, name = "pageNo") int pageNo,
																  @RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = skillLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);
		List<PlayerSkillLogDto> dtos = gameLogDtoConverter.toPlayerSkillLogDtos(rowPage.getContents());

		PageDto<PlayerSkillLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("queryPlayerSkillBookLog:query")
	@GetMapping(value = "queryPlayerSkillBookLog")
	@ApiOperation(value = "查询玩家心法技能日志-1027", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerSkillBookLogDto>> queryPlayerSkillBookLog(@RequestParam(required = true, name = "serverId") int serverId,
																  @RequestParam(required = false, name = "playerId") String playerId,
																  @RequestParam(required = false, name = "accountId") String accountId,
																  @RequestParam(required = false, name = "playerName") String playerName,
																  @RequestParam(required = true, name = "pageNo") int pageNo,
																  @RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = skillBookLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);
		List<PlayerSkillBookLogDto> dtos = gameLogDtoConverter.toPlayerSkillBookLogDtos(rowPage.getContents());

		PageDto<PlayerSkillBookLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("queryPlayerMeridianLog:query")
	@GetMapping(value = "queryPlayerMeridianLog")
	@ApiOperation(value = "查询玩家星宿日志-1028", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerMeridianLogDto>> queryPlayerMeridianLog(@RequestParam(required = true, name = "serverId") int serverId,
																		  @RequestParam(required = false, name = "playerId") String playerId,
																		  @RequestParam(required = false, name = "accountId") String accountId,
																		  @RequestParam(required = false, name = "playerName") String playerName,
																		  @RequestParam(required = true, name = "pageNo") int pageNo,
																		  @RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = meridianLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);
		List<PlayerMeridianLogDto> dtos = gameLogDtoConverter.toPlayerMeridianLogDtos(rowPage.getContents());

		PageDto<PlayerMeridianLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("queryPlayerEnhanceLog:query")
	@GetMapping(value = "queryPlayerEnhanceLog")
	@ApiOperation(value = "查询玩家强化日志-1029", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerEnhanceLogDto>> queryPlayerEnhanceLog(@RequestParam(required = true, name = "serverId") int serverId,
																	  @RequestParam(required = false, name = "playerId") String playerId,
																	  @RequestParam(required = false, name = "accountId") String accountId,
																	  @RequestParam(required = false, name = "playerName") String playerName,
																	  @RequestParam(required = true, name = "pageNo") int pageNo,
																	  @RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = enhanceLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);
		List<PlayerEnhanceLogDto> dtos = gameLogDtoConverter.toPlayerEnhanceLogDtos(rowPage.getContents());

		PageDto<PlayerEnhanceLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("queryPlayerEnhanceLog:query")
	@GetMapping(value = "queryPlayerEnhanceLog1")
	@ApiOperation(value = "查询玩家强化日志1-1029", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "type", value = "强化类型，-1为所有"),
	})
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerEnhanceLogDto>> queryPlayerEnhanceLog1(@RequestParam(required = true, name = "serverId") int serverId,
																	   @RequestParam(required = false, name = "playerId") String playerId,
																	   @RequestParam(required = false, name = "accountId") String accountId,
																	   @RequestParam(required = false, name = "playerName") String playerName,
																	   @RequestParam(required = true, name = "startTime") int startTime,
																	   @RequestParam(required = true, name = "endTime") int endTime,
																	   @RequestParam(required = true, name = "type") int type,
																	   @RequestParam(required = true, name = "pageNo") int pageNo,
																	   @RequestParam(required = true, name = "pageSize") int pageSize) {

		Date startDate = DateUtils.numberToDate(startTime, DateUtils.PATTERN_YYYYMMDD);
		Date endDate = DateUtils.numberToDate(endTime, DateUtils.PATTERN_YYYYMMDD);
		endDate = DateUtils.nextDayAm0(endDate);

		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = null;
		if (type == -1) {
			rowPage = enhanceLogDao.findPlayerLog(playerIdLong, startDate.getTime(), endDate.getTime(), pageNo, pageSize);
		} else {
			rowPage = enhanceLogDao.findPlayerLog(playerIdLong, startDate.getTime(), endDate.getTime(), type, pageNo, pageSize);
		}

		List<PlayerEnhanceLogDto> dtos = gameLogDtoConverter.toPlayerEnhanceLogDtos(rowPage.getContents());

		PageDto<PlayerEnhanceLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("queryPlayerWeaponSoulLog:query")
	@GetMapping(value = "queryPlayerWeaponSoulLog")
	@ApiOperation(value = "查询玩家器灵日志-1030", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerWeaponSoulLogDto>> queryPlayerWeaponSoulLog(@RequestParam(required = true, name = "serverId") int serverId,
																		 	@RequestParam(required = false, name = "playerId") String playerId,
																		 	@RequestParam(required = false, name = "accountId") String accountId,
																		 	@RequestParam(required = false, name = "playerName") String playerName,
																		 	@RequestParam(required = true, name = "pageNo") int pageNo,
																		 	@RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = weaponSoulLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);
		List<PlayerWeaponSoulLogDto> dtos = gameLogDtoConverter.toPlayerWeaponSoulLogDtos(rowPage.getContents());

		PageDto<PlayerWeaponSoulLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("queryPlayerTotemLog:query")
	@GetMapping(value = "queryPlayerTotemLog")
	@ApiOperation(value = "查询玩家仙兽蕴养日志-1031", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerTotemLogDto>> queryPlayerTotemLog(@RequestParam(required = true, name = "serverId") int serverId,
																  @RequestParam(required = false, name = "playerId") String playerId,
																  @RequestParam(required = false, name = "accountId") String accountId,
																  @RequestParam(required = false, name = "playerName") String playerName,
																  @RequestParam(required = true, name = "pageNo") int pageNo,
																  @RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = totemLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);
		List<PlayerTotemLogDto> dtos = gameLogDtoConverter.toPlayerTotemLogDtos(rowPage.getContents());

		PageDto<PlayerTotemLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("queryPlayerTotemExpandLog:query")
	@GetMapping(value = "queryPlayerTotemExpandLog")
	@ApiOperation(value = "查询玩家仙兽灵感日志-1032", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerTotemExpandLogDto>> queryPlayerTotemExpandLog(@RequestParam(required = true, name = "serverId") int serverId,
																			  @RequestParam(required = false, name = "playerId") String playerId,
																			  @RequestParam(required = false, name = "accountId") String accountId,
																			  @RequestParam(required = false, name = "playerName") String playerName,
																			  @RequestParam(required = true, name = "pageNo") int pageNo,
																			  @RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = totemExpandLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);
		List<PlayerTotemExpandLogDto> dtos = gameLogDtoConverter.toPlayerTotemExpandLogDtos(rowPage.getContents());

		PageDto<PlayerTotemExpandLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("queryBossDeadLog:query")
	@GetMapping(value = "queryPlayerBossDeadLog")
	@ApiOperation(value = "查询玩家boss击杀日志-1033", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "playType", value = "0-查询所有，1-活动boss，2-遭遇 boss...")
	})
	@ServerRight(argsIndex = 0)
	public Result<PageDto<BossDeadLogDto>> queryPlayerBossDeadLog(@RequestParam(required = true, name = "serverId") int serverId,
																  @RequestParam(required = false, name = "playerId") String playerId,
																  @RequestParam(required = false, name = "accountId") String accountId,
																  @RequestParam(required = false, name = "playerName") String playerName,
																  @RequestParam(required = true, name = "playType") int playType,
																  @RequestParam(required = true, name = "pageNo") int pageNo,
																  @RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = null;
		if (playType == 0) {
			rowPage = bossDeadLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);
		} else {
			rowPage = bossDeadLogDao.findPlayerLog(playerIdLong, playType, pageNo, pageSize);
		}

		List<BossDeadLogDto> dtos = gameLogDtoConverter.toBossDeadLogDtos(rowPage.getContents());

		PageDto<BossDeadLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("queryBossDeadLog:query")
	@GetMapping(value = "queryBossDeadLogByBattleId")
	@ApiOperation(value = "根据战斗id查找boss击杀日志-1033")
	public Result<List<BossDeadLogDto>> queryBossDeadLogByBattleId(@RequestParam String battleId) {
		long battleIdLong = this.stringToLong(battleId);

		List<Map<String, Object>> rowMaps = bossDeadLogDao.findByBattleId(battleIdLong);
		List<BossDeadLogDto> dtos = gameLogDtoConverter.toBossDeadLogDtos(rowMaps);

		return Result.success(dtos);
	}

	@RequiresPermissions("queryBossDeadLog:query")
	@GetMapping(value = "queryBossDeadLogBySrvAndType")
	@ApiOperation(value = "根据服务器id、玩法类型查找boss击杀日志-1033")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "startTime", value = "开始年月日，如：20190908", type = "int"),
			@ApiImplicitParam(name = "endTime", value = "结束年月日，如：20191031", type = "int")
	})
	@ServerRight(argsIndex = 0)
	public Result<PageDto<BossDeadLogDto>> queryBossDeadLogBySrvAndType(@RequestParam(required = true, name = "serverId") int serverId,
																		@RequestParam(required = true, name = "playType") int playType,
																		@RequestParam(required = true, name = "startTime") int startTime,
																		@RequestParam(required = true, name = "endTime") int endTime,
																  		@RequestParam(required = true, name = "pageNo") int pageNo,
																  		@RequestParam(required = true, name = "pageSize") int pageSize) {

		Date startDate = DateUtils.numberToDate(startTime, DateUtils.PATTERN_YYYYMMDD);
		Date endDate = DateUtils.numberToDate(endTime, DateUtils.PATTERN_YYYYMMDD);
		endDate = DateUtils.nextDayAm0(endDate);

		PageDto<Map<String, Object>> rowPage = bossDeadLogDao.findByTimeAndSrv(serverId, playType, startDate.getTime(), endDate.getTime(), pageNo, pageSize);
		List<BossDeadLogDto> dtos = gameLogDtoConverter.toBossDeadLogDtos(rowPage.getContents());

		PageDto<BossDeadLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("queryPlayerGmGoldLog:query")
	@GetMapping(value = "queryPlayerGmGoldLog")
	@ApiOperation(value = "查询玩家内币日志-1034", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "type", value = "0-查询所有，1-查询消费，2-查询产出")
	})
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerResourceLogDto>> queryPlayerGmGoldLog(@RequestParam(required = true, name = "serverId") int serverId,
																	  @RequestParam(required = false, name = "playerId") String playerId,
																	  @RequestParam(required = false, name = "accountId") String accountId,
																	  @RequestParam(required = false, name = "playerName") String playerName,
																	  @RequestParam(required = true, name = "type") int type,
																	  @RequestParam(required = true, name = "pageNo") int pageNo,
																	  @RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = null;
		if (type == 1) {
			rowPage = gmGoldLogDao.findPlayerDecLog(playerIdLong, pageNo, pageSize);
		} else if (type == 2) {
			rowPage = gmGoldLogDao.findPlayerRewardLog(playerIdLong, pageNo, pageSize);
		} else {
			rowPage = gmGoldLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);
		}

		List<PlayerResourceLogDto> dtos = gameLogDtoConverter.toPlayerResourceDtos(rowPage.getContents());

		PageDto<PlayerResourceLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("queryPlayerHyzLog:query")
	@GetMapping(value = "queryPlayerHyzLog")
	@ApiOperation(value = "查询玩家混元珠日志-1035", notes = "playerId、accountId、playerName 三个参数传其中1个就好，按先后顺序优先使用。")
	@ServerRight(argsIndex = 0)
	public Result<PageDto<PlayerHyzLogDto>> queryPlayerHyzLog(@RequestParam(required = true, name = "serverId") int serverId,
															  @RequestParam(required = false, name = "playerId") String playerId,
															  @RequestParam(required = false, name = "accountId") String accountId,
															  @RequestParam(required = false, name = "playerName") String playerName,
															  @RequestParam(required = true, name = "pageNo") int pageNo,
															  @RequestParam(required = true, name = "pageSize") int pageSize) {
		long playerIdLong = queryGameService.toPlayerId(serverId, playerId, accountId, playerName);
		if (playerIdLong == 0) {
			return Result.success(null);
		}

		PageDto<Map<String, Object>> rowPage = hyzLogDao.findPlayerLog(playerIdLong, pageNo, pageSize);
		List<PlayerHyzLogDto> dtos = gameLogDtoConverter.toPlayerHyzLogDtos(rowPage.getContents());

		PageDto<PlayerHyzLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(pageNo);
		pageDto.setPageSize(pageSize);
		pageDto.setTotalPage(rowPage.getTotalPage());
		pageDto.setTotalCount(rowPage.getTotalCount());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}

	@RequiresPermissions("powerLog:query")
	@PostMapping(value = "queryPowerLog")
	@ApiOperation(value = "查询玩家战斗力日志-1036")
	public Result<PageDto<PlayerPowerLogDto>> queryPowerLog(@RequestBody LogSearchRequest request) {
		if (request == null) {
			return Result.success(null);
		}
		if (request.getPageNo() < 0 || request.getPageSize() <= 0 || request.getPageSize() > 999) {
			return Result.error(ResultCode.PARAM_ERROR, "参数不正确");
		}

		SearchResult searchResult = gameLogService.search("powerLog", request);
		if (searchResult == null) {
			return Result.error(ResultCode.FAIL, "网络异常，请稍后再试。");
		}

		List<PlayerPowerLogDto> dtos = gameLogDtoConverter.toPowerLogDtos(searchResult);
		int totalPage = (int) Math.ceil(searchResult.getTotalSize() / request.getPageSize());

		PageDto<PlayerPowerLogDto> pageDto = new PageDto<>();
		pageDto.setPageNo(request.getPageNo());
		pageDto.setPageSize(request.getPageSize());
		pageDto.setTotalPage(totalPage);
		pageDto.setTotalCount(searchResult.getTotalSize());
		pageDto.setContents(dtos);

		return Result.success(pageDto);
	}
}