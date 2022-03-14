package com.xxgame.admin.web.modules.customerservice.controller;

import com.xxgame.admin.web.model.*;
import com.xxgame.admin.web.modules.BaseController;
import com.xxgame.admin.web.modules.commons.GmClient;
import com.xxgame.admin.web.modules.customerservice.controller.model.ChatLogSearch;
import com.xxgame.admin.web.modules.customerservice.controller.model.ChatRecordDto;
import com.xxgame.admin.web.modules.gameserver.GameServerService;
import com.xxgame.admin.web.modules.customerservice.service.ChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("chatManager")
@Api(tags = "聊天管理-900")
public class ChatManagerController extends BaseController {

    @Autowired
    private ChatService chatService;
    @Autowired
    private GameServerService gameServerService;
    @Autowired
    private GmClient gmClient;
    @Autowired
    private ChatDtoConverter chatDtoConverter;

    @GetMapping(value = "blockWords")
    @ApiOperation(value = "后台不用接")
    public Set<String> blockWords() {
        return chatService.getBlockWords();
    }

    @GetMapping(value = "getBlockWords")
    @RequiresPermissions("chatManager:query")
    @ApiOperation(value = "获取屏蔽词库-907")
    public Result<Set<String>> getBlockWords() {
        return Result.success(chatService.getBlockWords());
    }

    @PutMapping(value = "addBlockWord")
    @RequiresPermissions("chatManager:create")
    @ApiOperation(value = "添加屏蔽词-907")
    public Result<Void> addBlockWord(String blockWord) {
        if (StringUtils.isBlank(blockWord)) {
            return Result.success(null);
        }
        blockWord = StringUtils.trim(blockWord);
        boolean result = chatService.addBlockWord(blockWord);

        if (result) {
            List<Integer> serverIds = gameServerService.getAllOpenedServerIds();
            for (int serverId : serverIds) {
                gmClient.syncBlockWords(serverId);
            }
        }

        return Result.success(null);
    }

    @DeleteMapping(value = "removeBlockWord")
    @RequiresPermissions("chatManager:delete")
    @ApiOperation(value = "删除屏蔽词-907")
    public Result<Void> removeBlockWord(String blockWord) {
        if (StringUtils.isBlank(blockWord)) {
            return Result.success(null);
        }
        blockWord = StringUtils.trim(blockWord);
        boolean result = chatService.removeBlockWord(blockWord);

        if (result) {
            List<Integer> serverIds = gameServerService.getAllOpenedServerIds();
            for (int serverId : serverIds) {
                gmClient.syncBlockWords(serverId);
            }
        }

        return Result.success(null);
    }

    @PostMapping(value = "searchRecord")
    @RequiresPermissions("chatManager:query")
    @ApiOperation(value = "聊天记录搜索-907")
    public Result<PageDto<ChatRecordDto>> searchRecord(@RequestBody ChatLogSearch search) {
        if (search == null) {
            return Result.success(null);
        }
        if (search.getPageNo() < 0 || search.getPageSize() <= 0 || search.getPageSize() > 999) {
            return Result.error(ResultCode.PARAM_ERROR, "参数不正确");
        }
        if (StringUtils.isNotBlank(search.getKeyWord())) {
            if (search.getKeyWord().length() > 100) {
                return Result.error(ResultCode.PARAM_ERROR, "参数不正确");
            }
        }

        SearchResult searchResult = chatService.search(search);
        if (searchResult == null) {
            return Result.error(ResultCode.FAIL, "网络异常，请稍后再试。");
        }

        List<ChatRecordDto> dtos = chatDtoConverter.toChatRecordDtos(searchResult);
        int totalPage = (int) Math.ceil(searchResult.getTotalSize() / search.getPageSize());

        PageDto<ChatRecordDto> pageDto = new PageDto<>();
        pageDto.setPageNo(search.getPageNo());
        pageDto.setPageSize(search.getPageSize());
        pageDto.setTotalPage(totalPage);
        pageDto.setTotalCount(searchResult.getTotalSize());
        pageDto.setContents(dtos);

        return Result.success(pageDto);
    }

    @PostMapping(value = "scroll")
    @RequiresPermissions("chatManager:query")
    @ApiOperation(value = "滚动显示聊天记录-907")
    public Result<PageDto<ChatRecordDto>> scroll(@RequestBody ChatLogSearch search) {
        if (search == null) {
            return Result.success(null);
        }
        if (search.getPageNo() < 0 || search.getPageSize() <= 0 || search.getPageSize() > 999) {
            return Result.error(ResultCode.PARAM_ERROR, "参数不正确");
        }
        if (StringUtils.isNotBlank(search.getKeyWord())) {
            if (search.getKeyWord().length() > 100) {
                return Result.error(ResultCode.PARAM_ERROR, "参数不正确");
            }
        }

        SearchResult searchResult = chatService.scroll(search);
        if (searchResult == null) {
            return Result.error(ResultCode.FAIL, "网络异常，请稍后再试。");
        }

        List<ChatRecordDto> dtos = chatDtoConverter.toChatRecordDtos(searchResult);
        int totalPage = (int) Math.ceil(searchResult.getTotalSize() / search.getPageSize());

        PageDto<ChatRecordDto> pageDto = new PageDto<>();
        pageDto.setPageNo(search.getPageNo());
        pageDto.setPageSize(search.getPageSize());
        pageDto.setTotalPage(totalPage);
        pageDto.setTotalCount(searchResult.getTotalSize());
        pageDto.setContents(dtos);

        return Result.success(pageDto);
    }

}
