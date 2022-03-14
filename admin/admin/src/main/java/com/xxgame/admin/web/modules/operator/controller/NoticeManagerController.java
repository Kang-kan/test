package com.xxgame.admin.web.modules.operator.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xxgame.admin.web.model.Result;
import com.xxgame.admin.web.model.ResultCode;
import com.xxgame.admin.web.modules.BaseController;
import com.xxgame.admin.web.modules.commons.service.LoginSrvClient;
import com.xxgame.admin.web.modules.operator.ChatNoticeService;
import com.xxgame.admin.web.modules.operator.controller.model.ChatNoticeDto;
import com.xxgame.admin.web.modules.operator.controller.model.ChatNoticeRequest;
import com.xxgame.admin.web.modules.operator.entity.ChatNotice;
import com.xxgame.admin.web.modules.operator.model.ChatNoticeContext;
import com.xxgame.admin.web.modules.operator.repository.ChatNoticeRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("noticeManager")
@Api(tags = "公告管理-1200")
public class NoticeManagerController extends BaseController {

    @Autowired
    private LoginSrvClient loginSrvClient;
    @Autowired
    private ChatNoticeService chatNoticeService;
    @Autowired
    private ChatNoticeRepository chatNoticeRepository;
    @Autowired
    private NoticeDtoConverter noticeDtoConverter;

    /**
     * 登录界面公告
     */
    private final int LoginNotice = 1;

    /**
     * 游戏内公告
     */
    private final int GameNotice = 2;

    @RequiresPermissions("loginNoticeManager:query")
    @GetMapping(value = "getLoginNotice")
    @ApiOperation(value = "查询登录界面公告-1203")
    public Result<String> getLoginNotice() {
        String notice = loginSrvClient.queryNotice(LoginNotice);
        return Result.success(notice);
    }

    @RequiresPermissions("loginNoticeManager:update")
    @PostMapping(value = "setLoginNoticer")
    @ApiOperation(value = "设置登录界面公告-1203")
    public Result<Void> setLoginNoticer(@RequestBody String body) {
        if (body == null) {
            body = "";
        }
        JSONObject json = JSON.parseObject(body);
        String notice = json.get("notice").toString();
        loginSrvClient.setNotice(LoginNotice, notice);

        return Result.success(null);
    }

    @RequiresPermissions("gameNoticeManager:query")
    @GetMapping(value = "getGameNotice")
    @ApiOperation(value = "查询游戏内公告-1204")
    public Result<String> getGameNotice() {
        String notice = loginSrvClient.queryNotice(GameNotice);
        return Result.success(notice);
    }

    @RequiresPermissions("gameNoticeManager:update")
    @PostMapping(value = "setGameNotice")
    @ApiOperation(value = "设置游戏内公告-1204")
    public Result<Void> setGameNotice(@RequestBody String body) {
        if (body == null) {
            body = "";
        }
        JSONObject json = JSON.parseObject(body);
        String notice = json.get("notice").toString();
        loginSrvClient.setNotice(GameNotice, notice);

        return Result.success(null);
    }

    @RequiresPermissions(value = "chatNotice:query")
    @GetMapping(value = "getChatNotices")
    @ApiOperation(value = "获取跑马灯公告-1205")
    public Result<List<ChatNoticeDto>> getChatNotices() {
        List<ChatNotice> chatNotices = chatNoticeService.getAllChatNotices();
        List<ChatNoticeDto> dtos = noticeDtoConverter.toChatNoticeDtos(chatNotices);
        return Result.success(dtos);
    }

    @RequiresPermissions(value = { "chatNotice:create", "chatNotice:update" }, logical = Logical.OR)
    @PutMapping(value = "addChatNotice")
    @ApiOperation(value = "添加跑马灯公告-1205")
    public Result<ChatNoticeDto> addChatNotice(@RequestBody ChatNoticeRequest request) {
        Result<ChatNoticeDto> checkResult = this.checkParameter(request);
        if (checkResult != null) {
            return checkResult;
        }

        long requestUserId = this.getUserId();

        ChatNotice chatNotice = chatNoticeService.addChatNotice(requestUserId, request);
        ChatNoticeDto dto = noticeDtoConverter.toChatNoticeDto(chatNotice);

        return Result.success(dto);
    }

    @RequiresPermissions(value = { "chatNotice:create", "chatNotice:update" }, logical = Logical.OR)
    @PostMapping(value = "updateChatNotice/{chatNoticeId}")
    @ApiOperation(value = "修改跑马灯公告-1205")
    public Result<ChatNoticeDto> updateChatNotice(@PathVariable long chatNoticeId, @RequestBody ChatNoticeRequest request) {
        if (chatNoticeId <= 0) {
            return Result.error(ResultCode.PARAM_EMPTY, "公告id不能<=0");
        }
        Result<ChatNoticeDto> checkResult = this.checkParameter(request);
        if (checkResult != null) {
            return checkResult;
        }

        Optional<ChatNotice> optional = chatNoticeRepository.findById(chatNoticeId);
        if (!optional.isPresent()) {
            return Result.error(ResultCode.PARAM_EMPTY, "该公告不存在");
        }

        long requestUserId = this.getUserId();

        ChatNotice chatNotice = chatNoticeService.updateChatNotice(requestUserId, chatNoticeId, request);
        ChatNoticeDto dto = noticeDtoConverter.toChatNoticeDto(chatNotice);

        return Result.success(dto);
    }

    @RequiresPermissions(value = "chatNotice:delete", logical = Logical.OR)
    @DeleteMapping(value = "deleteChatNotice/{chatNoticeId}")
    @ApiOperation(value = "删除跑马灯公告-1205")
    public Result<Void> updateChatNotice(@PathVariable long chatNoticeId) {
        if (chatNoticeId <= 0) {
            return Result.error(ResultCode.PARAM_EMPTY, "公告id不能<=0");
        }

        Optional<ChatNotice> optional = chatNoticeRepository.findById(chatNoticeId);
        if (!optional.isPresent()) {
            return Result.error(ResultCode.PARAM_EMPTY, "该公告不存在");
        }

        chatNoticeService.deleteChatNotice(chatNoticeId);

        return Result.success(null);
    }

    @RequiresPermissions(value = { "chatNotice:create", "chatNotice:update" }, logical = Logical.OR)
    @PostMapping(value = "sendChatNotice")
    @ApiOperation(value = "发送一次性跑马灯公告-1205", notes = "返回失败的服务器列表，需要提示发送者。")
    public Result<List<Integer>> sendChatNotice(@RequestBody ChatNoticeRequest request) {
        if (request == null) {
            return Result.error(ResultCode.PARAM_EMPTY, "参数为空");
        }
        if (StringUtils.isBlank(request.getNotice())) {
            return Result.error(ResultCode.PARAM_EMPTY, "参数为空");
        }

        ChatNoticeContext noticeContext = new ChatNoticeContext();
        noticeContext.setServerIds(request.getServerIds());
        noticeContext.setChannels(request.getChannels());
        noticeContext.setNotice(request.getNotice());

        List<Integer> fails = chatNoticeService.sendNotice(noticeContext);

        return Result.success(fails);
    }

    /**
     * 检查参数
     * @param request
     * @return
     */
    private Result<ChatNoticeDto> checkParameter(ChatNoticeRequest request) {
        if (request == null) {
            return Result.error(ResultCode.PARAM_EMPTY, "参数为空");
        }
        if (StringUtils.isBlank(request.getNotice())) {
            return Result.error(ResultCode.PARAM_EMPTY, "公告内容不能为空");
        }
        if (request.getIntervalTime() <= 0) {
            return Result.error(ResultCode.PARAM_EMPTY, "时间间隔不能<=0");
        }

        return null;
    }

}
