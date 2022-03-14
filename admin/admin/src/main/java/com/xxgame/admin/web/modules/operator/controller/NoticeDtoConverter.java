package com.xxgame.admin.web.modules.operator.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xxgame.admin.web.modules.operator.controller.model.ChatNoticeDto;
import com.xxgame.admin.web.modules.operator.entity.ChatNotice;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * NoticeDtoConverter
 */
@Service
public class NoticeDtoConverter {

    /**
     * 转换成ChatNoticeDto
     * @param chatNotice
     * @return
     */
     public ChatNoticeDto toChatNoticeDto(ChatNotice chatNotice) {
         ChatNoticeDto dto = new ChatNoticeDto();
         dto.setId(chatNotice.getId());
         dto.setServerIds(JSON.parseObject(chatNotice.getServerIds(), new TypeReference<List<Integer>>(){}));
         dto.setChannels(JSON.parseObject(chatNotice.getChannels(), new TypeReference<List<String>>(){}));
         dto.setNotice(chatNotice.getNotice());
         dto.setIntervalTime(chatNotice.getIntervalTime());
         dto.setUpdateTime(chatNotice.getUpdateTime());

         return dto;
     }

    /**
     * 转换成ChatNoticeDto列表
     * @param chatNotices
     * @return
     */
    public List<ChatNoticeDto> toChatNoticeDtos(List<ChatNotice> chatNotices) {
        if (chatNotices == null || chatNotices.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<ChatNoticeDto> dtos = new ArrayList<>(chatNotices.size());
        for (ChatNotice chatNotice : chatNotices) {
            dtos.add(this.toChatNoticeDto(chatNotice));
        }

        return dtos;
    }

}
