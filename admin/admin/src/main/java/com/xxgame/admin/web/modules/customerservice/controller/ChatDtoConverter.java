package com.xxgame.admin.web.modules.customerservice.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xxgame.admin.web.model.SearchResult;
import com.xxgame.admin.web.modules.customerservice.controller.model.ChatReceiverDto;
import com.xxgame.admin.web.modules.customerservice.controller.model.ChatRecordDto;
import com.xxgame.admin.web.modules.customerservice.entity.BlockChat;
import com.xxgame.admin.web.modules.customerservice.entity.BlockPlayer;
import com.xxgame.admin.web.modules.customerservice.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class ChatDtoConverter {

    final int BLOCK_CHAT = 1;
    final int BLOCK_PLAYER = 2;

    @Autowired
    private PlayerService playerService;

    /**
     * 转换成ChatRecordDto列表
     * @param searchResult
     * @return
     */
    public List<ChatRecordDto> toChatRecordDtos(SearchResult searchResult) {
        List<ChatRecordDto> dtos = new LinkedList<>();
        if (searchResult == null || searchResult.getRows() == null) {
            return dtos;
        }

        long now = System.currentTimeMillis();

        for (Map<String, Object> row : searchResult.getRows()) {
            ChatRecordDto dto = new ChatRecordDto();
            dto.setId(((Number) row.get("id")).longValue());
            dto.setServerId((int) row.get("serverId"));
            dto.setAccount((String) row.get("account"));
            dto.setChannelId((String) row.get("channelId"));
            dto.setPlayerId(((Number) row.get("playerId")).longValue());
            dto.setPlayerName((String) row.get("playerName"));
            dto.setLevel((int) row.get("level"));
            dto.setVipLevel((int) row.get("vipLevel"));
            dto.setChatChannel((int) row.get("chatChannel"));
            dto.setContent((String) row.get("content"));

            JSONObject receiverJson = (JSONObject) row.get("receiver");
            if (receiverJson != null) {
                dto.setReceiver(JSON.parseObject(receiverJson.toJSONString(), new TypeReference<ChatReceiverDto>(){}));
            }

            {
                BlockChat blockChat = playerService.findBlockChat(dto.getPlayerId());
                if (blockChat != null) {
                    if (blockChat.getUnBlockTime() > now || blockChat.getUnBlockTime() < 0) {
                        dto.setStatus(BLOCK_CHAT);
                    }
                }
            }
            {
                BlockPlayer blockPlayer = playerService.findBlockPlayer(dto.getPlayerId());
                if (blockPlayer != null) {
                    if (blockPlayer.getUnBlockTime() > now || blockPlayer.getUnBlockTime() < 0) {
                        dto.setStatus(BLOCK_PLAYER);
                    }
                }
            }

            dto.setTime((long) row.get("time"));

            dtos.add(dto);
        }

        return dtos;
    }

}
