package com.xxgame.admin.web.modules.operator.controller;

import com.xxgame.admin.web.interceptor.ServerRight;
import com.xxgame.admin.web.model.PageDto;
import com.xxgame.admin.web.model.Result;
import com.xxgame.admin.web.model.ResultCode;
import com.xxgame.admin.web.modules.BaseController;
import com.xxgame.admin.web.modules.commons.GmClient;
import com.xxgame.admin.web.modules.gameserver.GameServerService;
import com.xxgame.admin.web.modules.operator.RewardMailService;
import com.xxgame.admin.web.modules.operator.controller.model.RewardMailDto;
import com.xxgame.admin.web.modules.operator.controller.model.RewardMailRequest;
import com.xxgame.admin.web.modules.operator.entity.RewardMail;
import com.xxgame.admin.web.modules.operator.repository.RewardMailRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("mailManager")
@Api(tags = "运营邮件-1200")
public class MailManagerController extends BaseController {

    @Autowired
    private GmClient gmClient;
    @Autowired
    private GameServerService gameServerService;
    @Autowired
    private RewardMailService rewardMailService;
    @Autowired
    private RewardMailRepository rewardMailRepository;
    @Autowired
    private MailManagerDtoConverter mailManagerDtoConverter;

    @RequiresPermissions("opMailManager:create")
    @PostMapping(value = "sendOpMail/{serverId}")
    @ApiOperation(value = "本服邮件-1206")
    @ServerRight(argsIndex = 0)
    public Result<Void> sendOpMail(@PathVariable int serverId, @RequestParam String title, @RequestParam String content) {
        if (StringUtils.isAnyBlank(title, content)) {
            return Result.error(ResultCode.PARAM_EMPTY, "参数不能为空");
        }

        ListenableFuture<ResponseEntity<String>> future = gmClient.sendSystemMail(serverId, title, content);
        try {
            ResponseEntity<String> responseEntity = future.get(3, TimeUnit.SECONDS);
            if (responseEntity.getStatusCode() != HttpStatus.OK) {
                logger.info("发送运营邮件失败，serverId:{} title:{} content:{}", serverId, title, content);
                return Result.error(ResultCode.FAIL, "发送运营邮件失败");
            }
            String result = responseEntity.getBody();
            if (result == null || !result.equals("0")) {
                logger.info("发送运营邮件返回错误，serverId:{} title:{} content:{} result:{}", serverId, title, content, result);
                return Result.error(ResultCode.FAIL, "发送运营邮件返回错误");
            }
        } catch (Exception e) {
            String msg = String.format("发送运营邮件异常，serverId:%s title:%s content:%s", serverId, title, content);
            logger.info(msg, e);
            return Result.error(ResultCode.FAIL, "发送运营邮件异常");
        }

        return Result.success(null);
    }

    @RequiresPermissions("opRewardMail:query")
    @GetMapping(value = "listRewardMail")
    @ApiOperation(value = "查看我的奖励邮件列表-1207")
    public Result<PageDto<RewardMailDto>> listRewardMail(@RequestParam int pageNo, @RequestParam int pageSize) {
        long requesterId = this.getUserId();

        Page<RewardMail> pageResult = rewardMailService.findRewardMails(requesterId, pageNo, pageSize);
        List<RewardMailDto> dtos = mailManagerDtoConverter.toRewardMailDtos(pageResult.getContent());

        PageDto<RewardMailDto> pageDto = new PageDto<>();
        pageDto.setContents(dtos);
        this.setPageInfo(pageDto, pageResult);

        return Result.success(pageDto);
    }

    @RequiresPermissions("opRewardMail:create")
    @PostMapping(value = "createRewardMail")
    @ApiOperation(value = "创建奖励邮件-1207")
    public Result<RewardMailDto> createRewardMail(@RequestBody RewardMailRequest request) {
        Result<RewardMailDto> checkResult = checkParameter(request);
        if (checkResult != null) {
            return checkResult;
        }

        long requestId = this.getUserId();
        for (int serverId : request.getServerIds()) {
            boolean gameRight = gameServerService.isHaveThisServer(requestId, serverId);
            if (!gameRight) {
                return Result.error(ResultCode.PARAM_ERROR, "没有该服务器的权限，serverId:" + serverId);
            }
        }

        RewardMail rewardMail = rewardMailService.createRewardMail(this.getUserId(), request);
        RewardMailDto dto = mailManagerDtoConverter.toRewardMailDto(rewardMail);

        return Result.success(dto);
    }

    @RequiresPermissions("opRewardMail:update")
    @PostMapping(value = "updateRewardMail/{rewardMailId}")
    @ApiOperation(value = "修改奖励邮件-1207", notes = "已审核或发送的不能修改")
    public Result<RewardMailDto> updateRewardMail(@PathVariable String rewardMailId, @RequestBody RewardMailRequest request) {
        Result<RewardMailDto> checkResult = checkParameter(request);
        if (checkResult != null) {
            return checkResult;
        }

        long mailIdLong = this.stringToLong(rewardMailId);
        Optional<RewardMail> optional = rewardMailRepository.findById(mailIdLong);
        if (!optional.isPresent()) {
            return Result.error(ResultCode.PARAM_ERROR, "id不正确");
        }
        RewardMail rewardMail = optional.get();
        if (rewardMail.getReviewTime() > 0) {
            return Result.error(ResultCode.PARAM_ERROR, "已审核的邮件不能更改");
        }
        if (rewardMail.getSendTime() > 0) {
            return Result.error(ResultCode.PARAM_ERROR, "已发送的邮件不能更改");
        }

        for (int serverId : request.getServerIds()) {
            boolean gameRight = gameServerService.isHaveThisServer(rewardMail.getCreatorId(), serverId);
            if (!gameRight) {
                return Result.error(ResultCode.PARAM_ERROR, "没有该服务器的权限，serverId:" + serverId);
            }
        }

        rewardMail = rewardMailService.updateRewardMail(rewardMail, request);
        RewardMailDto dto = mailManagerDtoConverter.toRewardMailDto(rewardMail);

        return Result.success(dto);
    }

    @RequiresPermissions("opRewardMail:delete")
    @DeleteMapping(value = "deleteRewardMail/{rewardMailId}")
    @ApiOperation(value = "删除奖励邮件-1207", notes = "已发送的不能删除")
    public Result<String> deleteRewardMail(@PathVariable String rewardMailId) {
        long mailIdLong = this.stringToLong(rewardMailId);
        Optional<RewardMail> optional = rewardMailRepository.findById(mailIdLong);
        if (!optional.isPresent()) {
            return Result.error(ResultCode.PARAM_ERROR, "id不正确");
        }
        RewardMail rewardMail = optional.get();
        if (rewardMail.getSendTime() > 0) {
            return Result.error(ResultCode.PARAM_ERROR, "已发送的邮件不能删除");
        }
        rewardMailRepository.deleteById(mailIdLong);

        return Result.success(rewardMailId);
    }

    @RequiresPermissions("opRewardMail:create")
    @PostMapping(value = "sendRewardMail")
    @ApiOperation(value = "发送奖励邮件-1207", notes = "审核通过才能发送，返回发送失败的服务器id列表")
    public Result<List<Integer>> sendRewardMail(@RequestParam String rewardMailId) {
        long mailIdLong = this.stringToLong(rewardMailId);
        Optional<RewardMail> optional = rewardMailRepository.findById(mailIdLong);
        if (!optional.isPresent()) {
            return Result.error(ResultCode.PARAM_ERROR, "id不正确");
        }
        RewardMail rewardMail = optional.get();
        if (rewardMail.getReviewerId() <= 0) {
            return Result.error(ResultCode.PARAM_ERROR, "未审核的邮件不能发送");
        }
        if (rewardMail.getSendTime() > 0) {
            return Result.error(ResultCode.PARAM_ERROR, "邮件已发送");
        }

        List<Integer> failSrvIds = rewardMailService.sendSrvRewardMail(this.getUserId(), rewardMail);
        return Result.success(failSrvIds);
    }

    /**
     * 参数检查
     * @param request
     * @return
     */
    private Result<RewardMailDto> checkParameter(RewardMailRequest request) {
        if (request == null) {
            return Result.error(ResultCode.FAIL, "参数不能为空");
        }
        if (StringUtils.isAnyBlank(request.getTitle(), request.getContent(), request.getRewards())) {
            return Result.error(ResultCode.PARAM_ERROR, "参数错误");
        }
        if (request.getRemark().length() > 32) {
            return Result.error(ResultCode.PARAM_MAX_LENGTH, "参数过长");
        }
        if (request.getTitle().length() > 128) {
            return Result.error(ResultCode.PARAM_MAX_LENGTH, "参数过长");
        }
        if (request.getContent().length() > 1024) {
            return Result.error(ResultCode.PARAM_MAX_LENGTH, "参数过长");
        }
        if (request.getRemark().length() > 1024) {
            return Result.error(ResultCode.PARAM_MAX_LENGTH, "参数过长");
        }
//        if (request.getEndTime() <= System.currentTimeMillis()) {
//            return Result.error(ResultCode.PARAM_ERROR, "参数错误");
//        }
//        if (request.getEndTime() <= request.getStartTime()) {
//            return Result.error(ResultCode.PARAM_ERROR, "参数错误");
//        }
        if (StringUtils.isBlank(request.getRewards())) {
            return Result.error(ResultCode.PARAM_ERROR, "参数错误");
        }
        // 全服邮件
        if (request.getType() == 1) {
            if (CollectionUtils.isNotEmpty(request.getPlayers())) {
                return Result.error(ResultCode.PARAM_ERROR, "参数错误");
            }
        } else if (request.getType() == 2) {
            // 人个邮件
            if (request.getServerIds().size() != 1) {
                return Result.error(ResultCode.PARAM_ERROR, "参数错误");
            }
            if (CollectionUtils.isEmpty(request.getPlayers())) {
                return Result.error(ResultCode.PARAM_ERROR, "参数错误");
            }
        }

        return null;
    }

    @RequiresPermissions("opRewardMailReview:query")
    @GetMapping(value = "listRewardMailReview")
    @ApiOperation(value = "查看所有奖励邮件-1208")
    public Result<PageDto<RewardMailDto>> listRewardMailReview(@RequestParam int pageNo, @RequestParam int pageSize) {
        Page<RewardMail> pageResult = rewardMailService.listRewardMail(pageNo, pageSize);

        List<RewardMailDto> dtos = mailManagerDtoConverter.toRewardMailDtos(pageResult.getContent());

        PageDto<RewardMailDto> pageDto = new PageDto<>();
        pageDto.setContents(dtos);
        this.setPageInfo(pageDto, pageResult);

        return Result.success(pageDto);
    }

    @RequiresPermissions("opRewardMailReview:update")
    @PostMapping(value = "reviewRewardMail")
    @ApiOperation(value = "审核奖励邮件-1208")
    public Result<RewardMailDto> reviewRewardMail(@RequestParam String rewardMailId) {
        long mailIdLong = this.stringToLong(rewardMailId);
        Optional<RewardMail> optional = rewardMailRepository.findById(mailIdLong);
        if (!optional.isPresent()) {
            return Result.error(ResultCode.PARAM_ERROR, "id不正确");
        }
        RewardMail rewardMail = optional.get();
        if (rewardMail.getReviewerId() > 0 || rewardMail.getSendTime() > 0) {
            return Result.error(ResultCode.PARAM_ERROR, "邮件已审核或发送");
        }
        rewardMail.setReviewerId(this.getUserId());
        rewardMail.setReviewTime(System.currentTimeMillis());
        rewardMailRepository.save(rewardMail);

        RewardMailDto dto = mailManagerDtoConverter.toRewardMailDto(rewardMail);
        return Result.success(dto);
    }

}
