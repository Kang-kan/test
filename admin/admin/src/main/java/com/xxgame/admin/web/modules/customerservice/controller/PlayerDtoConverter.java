package com.xxgame.admin.web.modules.customerservice.controller;

import com.xxgame.admin.web.modules.customerservice.controller.model.BlockChatDto;
import com.xxgame.admin.web.modules.customerservice.controller.model.BlockPlayerDto;
import com.xxgame.admin.web.modules.customerservice.entity.BlockChat;
import com.xxgame.admin.web.modules.customerservice.entity.BlockPlayer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色相关DTO转换器
 */
@Service
public class PlayerDtoConverter {

    /**
     * 转换成BlockPlayerDto列表
     * @param blockPlayers
     * @return
     */
    public List<BlockPlayerDto> toBlockPlayerDtos(List<BlockPlayer> blockPlayers) {
        List<BlockPlayerDto> dtos = new ArrayList<>();
        for (BlockPlayer blockPlayer : blockPlayers) {
            dtos.add(this.toBlockPlayerDto(blockPlayer));
        }

        return dtos;
    }

    /**
     * BlockPlayerDto
     * @param blockPlayer
     * @return
     */
    public BlockPlayerDto toBlockPlayerDto(BlockPlayer blockPlayer) {
        BlockPlayerDto dto = new BlockPlayerDto();
        dto.setPlayerId(blockPlayer.getPlayerId());
        dto.setServerId(blockPlayer.getServerId());
        dto.setAccount(blockPlayer.getAccount());
        dto.setChannelId(blockPlayer.getChannelId());
        dto.setPlayerName(blockPlayer.getPlayerName());
        dto.setUnBlockTime(blockPlayer.getUnBlockTime());
        dto.setReason(blockPlayer.getReason());
        dto.setUpdateTime(blockPlayer.getUpdateTime());

        return dto;
    }

    /**
     * 转换成BlockChatDto列表
     * @param blockChats
     * @return
     */
    public List<BlockChatDto> toBlockChatDtos(List<BlockChat> blockChats) {
        List<BlockChatDto> dtos = new ArrayList<>();
        for (BlockChat blockChat : blockChats) {
            dtos.add(this.toBlockChatDto(blockChat));
        }

        return dtos;
    }

    /**
     * BlockChatDto
     * @param blockChat
     * @return
     */
    public BlockChatDto toBlockChatDto(BlockChat blockChat) {
        BlockChatDto dto = new BlockChatDto();
        dto.setPlayerId(blockChat.getPlayerId());
        dto.setServerId(blockChat.getServerId());
        dto.setAccount(blockChat.getAccount());
        dto.setChannelId(blockChat.getChannelId());
        dto.setPlayerName(blockChat.getPlayerName());
        dto.setUnBlockTime(blockChat.getUnBlockTime());
        dto.setReason(blockChat.getReason());
        dto.setUpdateTime(blockChat.getUpdateTime());

        return dto;
    }
}
